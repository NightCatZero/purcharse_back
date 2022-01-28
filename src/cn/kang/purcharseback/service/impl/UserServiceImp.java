package cn.kang.purcharseback.service.impl;

import cn.kang.purcharseback.dao.UserDao;
import cn.kang.purcharseback.dao.impl.UserDaoImp;
import cn.kang.purcharseback.pojo.User;
import cn.kang.purcharseback.service.UserService;

public class UserServiceImp implements UserService {
    private UserDao userDao=new UserDaoImp();

    @Override
    public User login(User user) {
        User user1=userDao.login(user);
        return user1;
    }
}
