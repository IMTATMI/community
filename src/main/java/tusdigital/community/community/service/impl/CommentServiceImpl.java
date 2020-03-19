package tusdigital.community.community.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tusdigital.community.community.dao.CommentDao;
import tusdigital.community.community.dao.QuestionDao;
import tusdigital.community.community.dao.UserDao;
import tusdigital.community.community.domain.Comment;
import tusdigital.community.community.domain.Question;
import tusdigital.community.community.domain.User;
import tusdigital.community.community.enums.CommentTypeEnum;
import tusdigital.community.community.exception.CustomizeErrorCode;
import tusdigital.community.community.exception.CustomizeException;
import tusdigital.community.community.service.CommentService;
import tusdigital.community.community.vo.CommentVo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl  implements CommentService {

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private QuestionDao questionDao;

    @Autowired
    private UserDao userDao;

    @Override
    public int findByCount() {
        return commentDao.findByCount();
    }

    @Override
    public int addComment(Comment comment) {
        return 0;
    }

//    @Override
//    public int addComment(Comment comment) {
//        return commentDao.addComment(comment);
//    }

    @Override
    public Comment findById(Integer id) {
        return commentDao.findById(id);
    }



    @Override
    @Transactional
    public void insert(Comment comment, User commentator) {


        //没有登录 无法回复
        if (comment.getParentId() == null || comment.getParentId() == 0) {
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }

        //postman 操作json 不带父级 拦下
        // 或后面是 不是回复类型
        if (comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())) {
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }

        //Interger 比较 Interger 在127以下是可以判断的 在128以上不行  这里只有 1和2 也可以用intValue()转成int比较
        // 如果是楼中楼
        if (comment.getType().intValue() == CommentTypeEnum.COMMENT.getType()) {
            //看他的父级是什么
            Comment dbcomment = commentDao.findById(comment.getParentId());

            if (dbcomment == null){
                throw  new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }

//            commentDao.addComment(comment);
            commentDao.addComment(comment.getParentId(),comment.getType(),comment.getCommentator(),comment.getCreatetime()
                    ,comment.getModifiedtime(), comment.getLikecount(),comment.getStatus(),comment.getContent(),comment.getCommentcount());

        }else{
            //是回复 找帖子

            Question question = questionDao.findOneById(comment.getParentId());

            if (question == null){
                throw  new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }

            //回复新加回复
            comment.setCommentcount(0);
            comment.setStatus(0);


            commentDao.addComment(comment.getParentId(),comment.getType(),comment.getCommentator(),comment.getCreatetime()
            ,comment.getModifiedtime(), comment.getLikecount(),comment.getStatus(),comment.getContent(),comment.getCommentcount());


            //问题评论+1
            question.setComment_count(1);
            questionDao.incCommentCount(question);
        }



    }

    @Override
    public List<CommentVo> listByQuestionId(Integer id) {
        //找到父级ID  而且是种类为1（代表是一级回复）
        List<Comment> comments = commentDao.findByOneQuestion(id,1);
        List<CommentVo> commentVos = new ArrayList<>();
        if (comments.size() == 0 ){
            return commentVos;
        }

//        List<Integer> Collectors = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toList());
        //把所有的comment 遍历 并且找到每一条对应的user 压进 CommentVo 并返回
        for (Comment comment : comments) {
            User user = userDao.findById(comment.getCommentator());
            CommentVo commentVo =  new CommentVo();
            BeanUtils.copyProperties(comment,commentVo);
            commentVo.setUser(user);
            commentVos.add(commentVo);
        }

        return commentVos;
    }


}
