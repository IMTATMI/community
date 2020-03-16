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

import java.util.List;

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
                           @RequestParam(name = "state") String state
                             ){

//        System.out.println(clientId+" "+clientSecret+" "+redirectUri);
        AccessToken accessToken = new AccessToken();
        accessToken.setClient_id(clientId);
        accessToken.setClient_secret(clientSecret);
        accessToken.setCode(code);
        accessToken.setRedirect_uri(redirectUri);
        accessToken.setState(state);

        String accessTokens =githubProvider.getAccessToken(accessToken);
        User user = githubProvider.getuser(accessTokens);
        System.out.println(user.getName());

        //如果拿到user 那就插入
        if (user!=null){
            //看看有没有重名且是通过github登录的
           List<User> users = userService.findByName(user.getName());
            boolean flag =true;
            //找到重名的
            if (users != null) {
                //可能存在两个 判断是否已经注册过 没有插入 有就不管
                for (User user1 : users) {
                    if (user1.getName().equals(user.getName()) && user1.getLogintype() == user.getLogintype()) {
                        flag = false;
                    }
                }
            }
            if (flag){
                userService.addUser(user);
            }
        }


        return "index";
    }
}
