package cn.kang.purcharseback.dao.impl;

import cn.kang.purcharseback.dao.BaseDao;
import cn.kang.purcharseback.dao.UserDao;
import cn.kang.purcharseback.dao.tools.DBPool;
import cn.kang.purcharseback.pojo.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImp implements UserDao {
    private BaseDao baseDao=new BaseDaoImp();

    @Override
    public User login(User user) {
        String sql="select id,username,password from user where username = ? and password = ?";
        User user1=null;
        ResultSet rs=null;
        Connection connection=new DBPool().getConnection();
        try {
            rs=baseDao.query(connection,sql,user.getUsername(),user.getPassword());
            if(rs!=null && rs.next()){
                user1=new User(rs.getInt(1),rs.getString(2),rs.getString(3));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            try {
                rs.close();
                connection.close();
                connection=null;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return user1;
    }
}
