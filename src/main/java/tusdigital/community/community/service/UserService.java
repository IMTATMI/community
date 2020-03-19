package tusdigital.community.community.service;

import tusdigital.community.community.domain.User;

import java.util.List;

public interface UserService {
    List<User> findAllUser();
    User findLogin(String username, String password);
    int addUser(User user);
    List<User> findByName(String name);
    User findByToken(String token);
    User findById(int id);
    int updateUser(User user);

}
