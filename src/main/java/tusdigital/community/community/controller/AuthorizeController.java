package tusdigital.community.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tusdigital.community.community.domain.AccessToken;
import tusdigital.community.community.domain.User;
import tusdigital.community.community.provider.GithubProvider;
import tusdigital.community.community.service.UserService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

@Controller
@PropertySource(value="classpath:github.properties",ignoreResourceNotFound=true)
public class AuthorizeController {

    @Autowired
    private UserService userService;

    @Autowired
    private GithubProvider githubProvider;

    @Value("${git.clientId}")
    private String clientId;

    @Value("${git.clientSecret}")
    private String clientSecret;

    @Value("${git.redirectUri}")
    private String redirectUri;



    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletResponse response){

        //读取github api的对应值
        AccessToken accessToken = new AccessToken();
        accessToken.setClient_id(clientId);
        accessToken.setClient_secret(clientSecret);
        accessToken.setCode(code);
        accessToken.setRedirect_uri(redirectUri);
        accessToken.setState(state);

        //封装好 发给github 获取 accesstoken
        String accessTokens =githubProvider.getAccessToken(accessToken);

        //通过accesstoken 获取一个json 自己选取信息 保存进user
        User gituser = githubProvider.getuser(accessTokens);
//        System.out.println(gituser);


        //如果拿到user 分情况
        if (gituser!=null){
            // token 如果数据库存在 就找 不存在新加入在插入
            String token = null;

            //login type=2 git用户   type1 普通用户
            gituser.setLogintype(2);



            //查找数据库是否有重名
           List<User> users = userService.findByName(gituser.getName());

            boolean flag =true;

            //找到重名的
            if (!users.isEmpty()) {
                //可能存在两个(一个Github,一个手动注册)  用户名相等且登录种类相等 就获取已有token
                for (User user : users) {
                    //名字相等 且 logintype相等
                    if (user.getName().equals(gituser.getName()) && user.getLogintype() == gituser.getLogintype()) {
                        token = user.getToken();
                        user.setModifidetime(System.currentTimeMillis());
                        userService.updateUser(user);
                        flag = false;
                    }
                }
            }
            //如果用户没有 补全信息加入数据库
            if (flag){
                token = UUID.randomUUID().toString();
                gituser.setUsertype(1);
                gituser.setToken(token);
                gituser.setCreatetime(System.currentTimeMillis());
                gituser.setModifidetime(System.currentTimeMillis());
                userService.addUser(gituser);
            }

            //把token 放进cookie 以后验证token就知道是哪个用户登陆了  实现长期记住账号

            response.addCookie(new Cookie("token",token));
            return "redirect:/";

        }else {
            System.out.println("github不鸟我");
            return "user/login";
        }



    }
}
