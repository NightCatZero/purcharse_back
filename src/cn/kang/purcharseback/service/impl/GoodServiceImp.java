package cn.kang.purcharseback.service.impl;

import cn.kang.purcharseback.dao.GoodDao;
import cn.kang.purcharseback.dao.impl.GoodDaoImp;
import cn.kang.purcharseback.pojo.Good;
import cn.kang.purcharseback.service.GoodService;

import java.sql.SQLException;
import java.util.List;

public class GoodServiceImp implements GoodService {
    private GoodDao goodDao=new GoodDaoImp();
    @Override
    public void add(Good good) throws SQLException {
        goodDao.add(good);
    }

    @Override
    public List<Good> queryByCri(String id, String goodname, String goodtype) throws SQLException {
        return goodDao.queryByCri(id, goodname, goodtype);
    }

    @Override
    public List<Good> queryByPage(String pageNow, String pageSize) throws SQLException {
        return goodDao.queryByPage(pageNow, pageSize);
    }

    @Override
    public int queryTotalRow() throws SQLException {
        return goodDao.queryTotalRow();
    }

    @Override
    public List<String> queryAllType() throws SQLException {
        return goodDao.queryAllType();
    }

    @Override
    public List<Good> findGoodByType(String type) throws SQLException {
        return goodDao.findGoodByType(type);
    }

    @Override
    public Good findGoodById(String id,int amount) throws SQLException {
        return goodDao.findGoodById(id,amount);
    }

    @Override
    public void deleteGood(String id) throws SQLException {
        goodDao.deleteGood(id);
    }

    @Override
    public void modifiyGood(String id,String goodname,String goodtype,String price,String oldId) throws SQLException {
        goodDao.modifiyGood(id, goodname, goodtype, price, oldId);
    }
}
