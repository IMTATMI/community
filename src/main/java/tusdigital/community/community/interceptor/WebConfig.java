package tusdigital.community.community.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig  implements WebMvcConfigurer {

    //配置拦截器的低地方
    @Autowired
    private SesstionInterceptor sesstionInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 什么 拦截器  拦截什么（这里所有的请求都要拦截）  excludePathPatterns排除拦截什么
        registry.addInterceptor(sesstionInterceptor).addPathPatterns("/**").excludePathPatterns("/static/**");
    }
}
