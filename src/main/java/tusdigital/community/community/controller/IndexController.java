package tusdigital.community.community.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tusdigital.community.community.domain.Question;
import tusdigital.community.community.domain.User;
import tusdigital.community.community.service.QuestionService;
import tusdigital.community.community.service.UserService;
import tusdigital.community.community.vo.PaginationVo;
import tusdigital.community.community.vo.QuestionVo;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    UserService userService;

    @Autowired
    QuestionService questionService;

    @GetMapping({"/","/index"})
    public String index(@RequestParam(name = "page",defaultValue = "1") int page,
                        @RequestParam(name = "size",defaultValue = "5") int size,
                        @RequestParam(name = "search", required = false) String search,
                        Model model){



//        PaginationVo pagination = questionService.findAllPassQuestion(page,size);
        PaginationVo pagination = questionService.findAllPassQuestion(search,page,size);


        model.addAttribute("pagination",pagination);
        model.addAttribute("search", search);


        return "index";
    }


    @GetMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response) {
        request.getSession().removeAttribute("user");
        Cookie cookie = new Cookie("token", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }

    //测试页面用
    @RequestMapping("/test")
    public String test(){
        return "test";
    }



    //根据id删除 这个是result风格的删除  反正不用 就看看
    @RequestMapping("/test/{id}")
    public String delete(@PathVariable("id") Integer id){
       return "";
    }

}
