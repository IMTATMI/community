package tusdigital.community.community.provider;

import com.alibaba.fastjson.JSON;
import okhttp3.*;
import org.springframework.stereotype.Component;
import tusdigital.community.community.domain.AccessToken;
import tusdigital.community.community.domain.User;

import java.io.IOException;

/**
 * component 把该类注入IOC容器中
 */

@Component
public class GithubProvider {

   public String getAccessToken(AccessToken accessToken){
       MediaType mediaType = MediaType.get("application/json; charset=utf-8");

       OkHttpClient client = new OkHttpClient();


           RequestBody body = RequestBody.create(mediaType,JSON.toJSONString(accessToken));
           Request request = new Request.Builder()
                   .url("https://github.com/login/oauth/access_token")
                   .post(body)
                   .build();
           try (Response response = client.newCall(request).execute()) {
               String res = response.body().string();
               //获取token 往github传以获取用户  考验字符串拆分的时候到了 长这个样↓
               //access_token=76b1e1185e248f66d3837a716f4732d13c62c3f4&scope=user&token_type=bearer
               //76b1e1185e248f66d3837a716f4732d13c62c3f4需要这个去github获取用户
               String[] tokenstr = res.split("&");
               String[] token = tokenstr[0].split("=");
               return token[1];
           }catch (Exception e){
                e.printStackTrace();
           }
           return null;
   }

   public User getuser(String accessToken){
       OkHttpClient client = new OkHttpClient();
       Request request = new Request.Builder()
               .url("https://api.github.com/user?access_token="+accessToken)
               .build();

       try {
           Response response = client.newCall(request).execute();
           String string =  response.body().string();
           User user =JSON.parseObject(string,User.class);
           user.setLogintype(2);
           user.setUsertype(1);
           return user;
       } catch (IOException e) {
           e.printStackTrace();
       }
       return null;

   }

}
