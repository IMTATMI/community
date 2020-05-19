package tusdigital.community.community.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tusdigital.community.community.domain.User;
import tusdigital.community.community.service.UserService;
import tusdigital.community.community.utils.MD5Util;
import tusdigital.community.community.utils.StringUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("/login")
    public String login(){
        return "user/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        HttpServletResponse response,
                        Map<String,Object> map){

        //登录 判断账号密码状态
        if(StringUtil.isEmpty(username) || StringUtil.isEmpty(password)){
            map.put("msg","账号密码不能为空");
            return "user/login";
        }else{
            //非空 找是否有该用户 且 密码对不对
            User user = userService.findLogin(username,password);
            if (user == null){
                map.put("msg","用户名密码错误");
                return "user/login";
            }else {
                //有这个用户  获取该用户token 放入cookie 实现长期登录
                String token = user.getToken();
                user.setModifidetime(System.currentTimeMillis());
                userService.updateUser(user);
                Cookie cookie =new Cookie("token",token);
                //如果不setpath 这个cookie 的权限只有在 /user/xxxxx 下才有用   index会无法拿到
                cookie.setPath("/");
                response.addCookie(cookie);
                return "redirect:/";
            }
        }
    }

    @GetMapping("/register")
    public String register(){
        return "user/register";
    }


    @PostMapping("/register")
    public String register(User ruser,@RequestParam("confirm") String confirm,Map<String,Object> err){

        if (!ruser.getEmail().matches("[\\w\\.\\-]+@([\\w\\-]+\\.)+[\\w\\-]+")) {
            err.put("err","邮箱有误");
            return "user/register";
        }

        if (!confirm.equals(ruser.getPassword())){
            err.put("err","两次密码不一致");
            return "user/register";
        }


        List<User> users = userService.findByName(ruser.getName());
        //list需要 isEmpty()
        if (!users.isEmpty()){
            err.put("err","该账号已注册请重新输入");
            return "user/register";
        }else {
            User user = new User();
            user.setName(ruser.getName());
            user.setPassword(MD5Util.digest(ruser.getPassword()));
            user.setEmail(ruser.getEmail());
            user.setLogintype(1);
            user.setUsertype(1);
            user.setCredit(0);
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setCreatetime(System.currentTimeMillis());
            user.setModifidetime(System.currentTimeMillis());
            user.setAvatar_url("/static/pic/default.jpg");
            userService.addUser(user);
            return "redirect:/user/login";
        }
    }




}
