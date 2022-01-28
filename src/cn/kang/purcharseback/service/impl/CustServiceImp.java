package cn.kang.purcharseback.service.impl;

import cn.kang.purcharseback.dao.CustDao;
import cn.kang.purcharseback.dao.impl.CustDaoImp;
import cn.kang.purcharseback.pojo.Customer;
import cn.kang.purcharseback.service.CustService;

import java.sql.SQLException;

public class CustServiceImp implements CustService {
    private CustDao custDao=new CustDaoImp();
    @Override
    public void registCust(String custname, String custmail, String password, String custqq) throws SQLException {
        custDao.registCust(custname, custmail, password, custqq);
    }

    @Override
    public Customer loginCust(String custname, String password) {
        Customer customer=custDao.loginCust(custname,password);
        return customer;
    }

    @Override
    public Boolean checkName(String custname) {
        return custDao.checkName(custname);
    }

    @Override
    public Boolean checkMail(String custmail) {
        return custDao.checkMail(custmail);
    }
}
