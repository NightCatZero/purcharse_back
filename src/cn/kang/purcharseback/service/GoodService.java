package cn.kang.purcharseback.service;

import cn.kang.purcharseback.pojo.Good;

import java.sql.SQLException;
import java.util.List;

public interface GoodService {
    void add(Good good) throws SQLException;
    List<Good> queryByCri(String id,String goodname,String goodtype) throws SQLException;
    List<Good> queryByPage(String pageNow,String pageSize) throws SQLException;
    int queryTotalRow() throws SQLException;
    List<String> queryAllType() throws SQLException;
    List<Good> findGoodByType(String type) throws SQLException;
    Good findGoodById(String id,int amount) throws SQLException;
    void deleteGood(String id) throws SQLException;
    void modifiyGood(String id,String goodname,String goodtype,String price,String oldId) throws SQLException;
}
