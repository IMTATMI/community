package tusdigital.community.community.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tusdigital.community.community.domain.User;
import tusdigital.community.community.service.UserService;
import tusdigital.community.community.utils.MD5Util;
import tusdigital.community.community.utils.StringUtil;

import java.util.List;
import java.util.Map;

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
                        Map<String,Object> map){
        if(StringUtil.isEmpty(username) || StringUtil.isEmpty(password)){
            map.put("msg","账号密码不能为空");
            return "user/login";
        }else{
            User user = userService.findLogin(username,password);
            if (user == null){
                map.put("msg","用户名密码错误");
                return "user/login";
            }else {
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
        if (users != null){
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
            userService.addUser(user);
            return "redirect:/user/login";
        }
    }




}
