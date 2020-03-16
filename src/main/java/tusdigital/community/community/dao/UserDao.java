package tusdigital.community.community.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import tusdigital.community.community.domain.User;

import java.util.List;

@Mapper
public interface UserDao {

    List<User> findAllUser();

    User login(@Param("loginName") String loginName, @Param("password") String password);

    int addUser(User user);

    List<User> findByName(@Param("name") String name);

}
