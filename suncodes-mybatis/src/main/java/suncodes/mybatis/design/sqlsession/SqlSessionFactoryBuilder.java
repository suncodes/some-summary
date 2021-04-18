package suncodes.mybatis.design.sqlsession;

import suncodes.mybatis.design.cfg.Configuration;
import suncodes.mybatis.design.util.XMLConfigBuilder;

import java.io.InputStream;

public class SqlSessionFactoryBuilder {
    public SqlSessionFactory build(InputStream inputStream) {
        Configuration configuration = XMLConfigBuilder.loadConfiguration(inputStream);
        return new DefaultSqlSessionFactory(configuration);
    }
}
