package tusdigital.community.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import tusdigital.community.community.domain.User;
import tusdigital.community.community.service.QuestionService;
import tusdigital.community.community.service.UserService;
import tusdigital.community.community.vo.PaginationVo;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by codedrinker on 2019/5/15.
 */
@Controller
public class ProfileController {
    @Autowired
    private QuestionService questionService;

    @Autowired
    private UserService userService;


    @GetMapping("/profile/{action}")
    public String profile(HttpServletRequest request,
                          @PathVariable(name = "action") String action,
                          Model model,
                          @RequestParam(name = "page", defaultValue = "1") Integer page,
                          @RequestParam(name = "size", defaultValue = "5") Integer size) {

//        Cookie[] cookies = request.getCookies();
//
//        User user =null;
//        //测试会将cookies全删做测试 要判断 要不就nullpoint
//        if (cookies != null){
//            for (Cookie cookie : cookies) {
//                //有token 就找与之对应的用户 直接帮他登了
//                if (cookie.getName().equals("token")){
//                    String token = cookie.getValue();
//                    user = userService.findByToken(token);
//                    if (user!=null){
//                        request.getSession().setAttribute("user",user);
//                    }
//                    break;
//                }
//            }
//        }
        User user = (User) request.getSession().getAttribute("user");

        if (user == null){
            return "redirect:/";
        }

        if ("questions".equals(action)) {
            model.addAttribute("section", "questions");
            model.addAttribute("sectionName", "我的提问");
            PaginationVo paginationvo = questionService.list(user.getId(),page, size);
//            System.out.println(paginationvo);

            model.addAttribute("pagination", paginationvo);
       }
       else if ("replies".equals(action)) {
            model.addAttribute("section", "replies");
            model.addAttribute("sectionName", "最新回复");
        }

        return "community/profile";
    }
}
