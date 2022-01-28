package cn.kang.purcharseback.dao.impl;

import cn.kang.purcharseback.dao.BaseDao;
import cn.kang.purcharseback.dao.CustDao;
import cn.kang.purcharseback.dao.tools.DBPool;
import cn.kang.purcharseback.pojo.Customer;
import cn.kang.purcharseback.pojo.Good;
import cn.kang.purcharseback.pojo.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CustDaoImp implements CustDao {
    private BaseDao baseDao=new BaseDaoImp();
    @Override
    public void registCust(String custname, String custmail, String password, String custqq) throws SQLException {
        String sql="insert into customer(custname,custmail,password,custqq) values(?,?,?,?)";
        Connection connection=new DBPool().getConnection();
        try {
            connection.setAutoCommit(false);
            baseDao.update(connection,sql,custname,custmail,password,custqq);
            connection.commit();
        } catch (SQLException throwables) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                throw e;
            }
            throw throwables;
        }finally {
            try {
                connection.close();
                connection=null;
            } catch (SQLException throwables) {
                throw throwables;
            }
        }
    }

    @Override
    public Customer loginCust(String custname, String password) {
        String sql="select * from customer where custname = ? and password = ?";
        Customer cust=null;
        ResultSet rs=null;
        Connection connection=new DBPool().getConnection();
        try {
            rs=baseDao.query(connection,sql,custname,password);
            if(rs!=null && rs.next()){
                cust=new Customer(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4));
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
        return cust;
    }

    @Override
    public Boolean checkName(String custname) {
        String sql="select * from customer where custname = ?";
        Customer cust=null;
        ResultSet rs=null;
        Connection connection=new DBPool().getConnection();
        try {
            rs=baseDao.query(connection,sql,custname);
            if(rs!=null && rs.next()){
                cust=new Customer(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4));
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
        if(cust==null){
            return false;
        }else {
            return true;
        }
    }

    @Override
    public Boolean checkMail(String custmail) {
        String sql="select * from customer where custmail = ?";
        Customer cust=null;
        ResultSet rs=null;
        Connection connection=new DBPool().getConnection();
        try {
            rs=baseDao.query(connection,sql,custmail);
            if(rs!=null && rs.next()){
                cust=new Customer(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4));
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
        if(cust==null){
            return false;
        }else {
            return true;
        }
    }
}
