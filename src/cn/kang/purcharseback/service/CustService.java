package cn.kang.purcharseback.service;

import cn.kang.purcharseback.pojo.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface CustService {
    void registCust(String custname,String custmail,String password,String custqq) throws SQLException;
    Customer loginCust(String custname, String password);
    Boolean checkName(String custname);
    Boolean checkMail(String custmail);
}
