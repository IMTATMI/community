package tusdigital.community.community.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import tusdigital.community.community.domain.Comment;
import tusdigital.community.community.domain.User;
import tusdigital.community.community.exception.CustomizeErrorCode;
import tusdigital.community.community.service.CommentService;
import tusdigital.community.community.vo.CommentCreateVo;
import tusdigital.community.community.vo.CommentVo;
import tusdigital.community.community.vo.ResultVo;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @ResponseBody
    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public Object post(@RequestBody CommentCreateVo commentCreateVo,
                       HttpServletRequest request){

        //判断用户是否为空
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return ResultVo.errorOf(CustomizeErrorCode.NO_LOGIN);
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

        //将信息塞入 comment 并到处理层执行插入（在里面处理不正常情况）
        commentService.insert(comment, user);

        //返回成功信息
        return ResultVo.okOf();
    }
}
