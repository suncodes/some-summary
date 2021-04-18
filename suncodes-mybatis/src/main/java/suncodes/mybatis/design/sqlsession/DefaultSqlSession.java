package suncodes.mybatis.design.sqlsession;

import suncodes.mybatis.design.cfg.Configuration;
import suncodes.mybatis.design.sqlsession.proxy.MapperProxy;
import suncodes.mybatis.design.util.DataSourceUtil;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;

public class DefaultSqlSession implements SqlSession {

    private Configuration configuration;
    private Connection connection;

    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
        this.connection = DataSourceUtil.getConnection(configuration);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getMapper(Class<T> daoInterface) {
        return (T)Proxy.newProxyInstance(DefaultSqlSession.class.getClassLoader(),
                new Class[]{daoInterface}, new MapperProxy(configuration.getMappers(), connection));
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
