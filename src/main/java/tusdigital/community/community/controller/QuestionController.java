package tusdigital.community.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import tusdigital.community.community.enums.CommentTypeEnum;
import tusdigital.community.community.exception.CustomizeErrorCode;
import tusdigital.community.community.exception.CustomizeException;
import tusdigital.community.community.service.CommentService;
import tusdigital.community.community.service.QuestionService;
import tusdigital.community.community.vo.CommentVo;
import tusdigital.community.community.vo.QuestionVo;

import java.util.List;

@Controller
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @Autowired
    CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") String id, Model model) {
        Integer questionId = null;
        try {
            questionId = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            throw new CustomizeException(CustomizeErrorCode.INVALID_INPUT);
        }

        //找问题  问题表+用户表
        QuestionVo questionvo = questionService.findOneById(questionId);

        //找评论 评论表+用户表
        List<CommentVo> commentVos = commentService.listByQuestionId(questionId, CommentTypeEnum.QUESTION.getType());

        List<QuestionVo> relatequest = questionService.selectRelated(questionvo);

        questionService.incView(questionId);
//        System.out.println(questionvo);
//        System.out.println(commentVos);

        model.addAttribute("question",questionvo);
        model.addAttribute("comments",commentVos);
        model.addAttribute("relatequestion",relatequest);

        return "community/question";
    }

}
