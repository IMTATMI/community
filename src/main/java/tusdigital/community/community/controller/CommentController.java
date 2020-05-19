package tusdigital.community.community.controller;


import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tusdigital.community.community.domain.Comment;
import tusdigital.community.community.domain.User;
import tusdigital.community.community.enums.CommentTypeEnum;
import tusdigital.community.community.exception.CustomizeErrorCode;
import tusdigital.community.community.service.CommentService;
import tusdigital.community.community.vo.CommentCreateVo;
import tusdigital.community.community.vo.CommentVo;
import tusdigital.community.community.vo.ResultVo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @ResponseBody
    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public Object post(@RequestBody CommentCreateVo commentCreateVo,
                       HttpServletRequest request){


//        System.out.println(commentCreateVo);
        //判断用户是否为空
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return ResultVo.errorOf(CustomizeErrorCode.NO_LOGIN);
        }

        if (commentCreateVo == null || StringUtils.isBlank(commentCreateVo.getContent())){
            return ResultVo.errorOf(CustomizeErrorCode.CONTENT_IS_EMPTY);
        }

        //从json 拿下信息  parentId父级ID  content内容  type是回复还是楼中楼;
        Comment comment = new Comment();
        comment.setParentId(commentCreateVo.getParentId());
        comment.setContent(commentCreateVo.getContent());
        comment.setType(commentCreateVo.getType());
        comment.setCreatetime(System.currentTimeMillis());
        comment.setModifiedtime(System.currentTimeMillis());
        comment.setCommentator(user.getId());
        comment.setLikecount(0);

//        System.out.println(comment);
//        System.out.println(user);

        //将信息塞入 comment 并到处理层执行插入（在里面处理不正常情况）
        commentService.insert(comment, user);

        //返回成功信息
        return ResultVo.okOf();
    }


    /**
     * 二级评论展开时访问这里  使用json把id带过来显示
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/comment/{id}", method = RequestMethod.GET)
    public ResultVo<List<CommentVo>> comments(@PathVariable(name = "id") int id) {
        List<CommentVo> commentDTOS = commentService.listByQuestionId(id,CommentTypeEnum.COMMENT.getType());
        return ResultVo.okOf(commentDTOS);
    }

}
