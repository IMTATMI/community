package tusdigital.community.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tusdigital.community.community.domain.Question;
import tusdigital.community.community.domain.User;
import tusdigital.community.community.exception.CustomizeErrorCode;
import tusdigital.community.community.exception.CustomizeException;
import tusdigital.community.community.service.QuestionService;
import tusdigital.community.community.service.UserService;
import tusdigital.community.community.vo.QuestionVo;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private UserService userService;

    @GetMapping("/publish")
    public String publish(){
        return "community/publish";
    }

    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name = "id") int id,
                       Model model) {
        QuestionVo questionVo = questionService.findOneById(id);
//        User user = userService.findById(questionVo.getCreator());
//        questionVo.setUser(user);
        model.addAttribute("title", questionVo.getTitle());
        model.addAttribute("description", questionVo.getDescrpition());
        model.addAttribute("tag", questionVo.getTag());
        model.addAttribute("id", questionVo.getId());
        //model.addAttribute("tags", TagCache.get());
        return "community/publish";
    }

    @PostMapping("/publish")
    public String publish(@RequestParam("title") String title,
                          @RequestParam("description") String description,
                          @RequestParam("tag") String tag,
                          @RequestParam(value = "id", required = false) Integer id,
                          HttpServletRequest request,
                          Model model
                          ){

        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);

        if (title == null || title ==""){
            model.addAttribute("error","标题不能为空");
            return "community/publish";
        }
        if (description == null || description ==""){
            model.addAttribute("error","补充不能为空");
            return "community/publish";
        }
        if (tag == null || tag ==""){
            model.addAttribute("error","标签不能为空");
            return "community/publish";
        }


        User user = (User) request.getSession().getAttribute("user");

        if (user == null){
            model.addAttribute("error","用户未登录");
            return "community/publish";
        }

        Question question = new Question();
        question.setTitle(title);
        question.setDescrpition(description);
        question.setTag(tag);

        if (id != null){
            QuestionVo questionVo = questionService.findOneById(id);

            if (questionVo  != null){
                question.setId(id);
            }

            if (user.getId() == questionVo.getUser().getId()){
                question.setModified_time(System.currentTimeMillis());
                questionService.updataQuestion(question);

                return "redirect:/";
            }
            // 想偷偷改页面就拦下来
            throw new CustomizeException(CustomizeErrorCode.WTF_YOUDO);
//            return "redirect:/";
        }


        question.setCreator(user.getId());
        question.setStatus(1);
        question.setCreat_time(System.currentTimeMillis());
        question.setModified_time(question.getCreat_time());
        questionService.addQuestion(question);

        //失败还是在原来位置 生成提示
        return "redirect:/";
    }





}
