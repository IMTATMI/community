package tusdigital.community.community.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tusdigital.community.community.dao.UserDao;
import tusdigital.community.community.domain.User;
import tusdigital.community.community.service.UserService;
import tusdigital.community.community.utils.MD5Util;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public List<User> findAllUser() {
        return userDao.findAllUser();
    }

    @Override
    public User findLogin(String username, String password) {
        return userDao.login(username, MD5Util.digest(password));
    }

    @Override
    public int addUser(User user) {
        return userDao.addUser(user);
    }

    @Override
    public List<User> findByName(String name) {
        return userDao.findByName(name);
    }

    @Override
    public User findByToken(String token) {
        return userDao.findByToken(token);
    }

    @Override
    public User findById(int id) {
        return userDao.findById(id);
    }

    @Override
    public int updateUser(User user) {
        return userDao.updateUser(user);
    }



}
