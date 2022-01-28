package cn.kang.purcharseback.dao.tools;

import com.alibaba.druid.pool.DruidDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DBPool {
    private static DataSource dataSource;
    static {
        dataSource=new DruidDataSource();
        ((DruidDataSource)dataSource).setDriverClassName("com.mysql.jdbc.Driver");
        ((DruidDataSource)dataSource).setUrl("jdbc:mysql://localhost:3306/purcharse?useUnicode=true&characterEncoding=UTF-8&useSSL=false");
        ((DruidDataSource)dataSource).setUsername("root");
        ((DruidDataSource)dataSource).setPassword("123456");
        ((DruidDataSource)dataSource).setMinIdle(5);
        ((DruidDataSource)dataSource).setMaxActive(10);
    }

    public static void main(String[] args) {
        DBPool pool=new DBPool();
        System.out.println(pool.getConnection());
    }

    public Connection getConnection(){
        try {
            return dataSource.getConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}
