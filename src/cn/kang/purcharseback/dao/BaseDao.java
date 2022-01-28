package cn.kang.purcharseback.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface BaseDao {
    ResultSet query(Connection connection,String sql, Object... params) throws SQLException;
    void update(Connection connection,String sql,Object... params) throws SQLException;
}
