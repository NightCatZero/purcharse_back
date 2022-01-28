package cn.kang.purcharseback.dao;

import cn.kang.purcharseback.pojo.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface CustDao {
    void registCust(String custname,String custmail,String password,String custqq) throws SQLException;
    Customer loginCust(String custname, String password);
    Boolean checkName(String custname);
    Boolean checkMail(String custmail);
}
