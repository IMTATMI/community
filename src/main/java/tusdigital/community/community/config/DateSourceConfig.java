package tusdigital.community.community.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

/**
 * Configuration 作为springboot的设置条件 类似 application 里的设置一样
 * PropertySource value  读取文件  key作为value 注入到下列变量
 * 该类为druid数据库连接池的配置
 */


@Configuration
@PropertySource(value="classpath:db.properties",ignoreResourceNotFound=true)
public class DateSourceConfig {

    @Value("${db.url}")
    private String url;

    @Value("${db.driverClassName}")
    private String driverClassName;

    @Value("${db.username}")
    private String userName;

    @Value("${db.password}")
    private String password;


    @Bean
    public DataSource getDataSource(){
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl(url);
        druidDataSource.setUsername(userName);
        druidDataSource.setPassword(password);
        druidDataSource.setDriverClassName(driverClassName);
        return druidDataSource;

    }

}
