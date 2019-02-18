package com.test.unit5;

import java.sql.SQLException;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;

import com.mysql.jdbc.Driver;
import com.mysql.jdbc.PreparedStatement;

/**
 * 行集测试
 * @author REN
 *
 */
public class RowSetTest {

    public static void main(String[] args) throws SQLException {
        RowSetFactory rsfactory = RowSetProvider.newFactory();
        CachedRowSet crs = rsfactory.createCachedRowSet();
        
        // 将resultSet里面的数据填充到cacheRowSet
//        crs.populate(resultSet);
//        crs.setCommand("");
//        crs.setPageSize(3); 只需要3行记录
//        crs.execute(); 将查询结果填充到crs
        //如果我们修改了rowSet,需要把修改同步到数据库，可以使用 
//        crs.acceptChanges(); 只有设置了url,username,password;这个方法才有用
//        crs.acceptChanges(con);
        // 当我们修改rowSet时， 如果数据库的数据也修改了，那么这个时候将无法同步过去，
        // 会抛出一个SyncProviderException异常.只有修改前的rowSet与数据库一致(即数据库
//        没有修改时), 才能同步成功
        
          
    }

}
