package tusdigital.community.community.service;

import tusdigital.community.community.domain.Comment;
import tusdigital.community.community.domain.User;
import tusdigital.community.community.vo.CommentVo;

import java.util.List;

public interface CommentService {
    int findByCount();
    int addComment(Comment comment);
    Comment findById(Integer id);
//    int updateComment(Comment comment);

    void insert(Comment comment, User user);

    List<CommentVo> listByQuestionId(Integer id);
}
