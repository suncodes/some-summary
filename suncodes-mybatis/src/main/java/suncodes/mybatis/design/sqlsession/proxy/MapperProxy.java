package suncodes.mybatis.design.sqlsession.proxy;

import suncodes.mybatis.design.cfg.Mapper;
import suncodes.mybatis.design.util.Executor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.Map;

public class MapperProxy implements InvocationHandler {

    private Map<String, Mapper> mappers;
    private Connection connection;

    public MapperProxy(Map<String, Mapper> mappers, Connection connection) {
        this.mappers = mappers;
        this.connection = connection;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 1.获取方法名
        String methodName = method.getName();
        // 2.获取方法所在类
        String className = method.getDeclaringClass().getName();
        // key
        String key = className + "." + methodName;
        Mapper mapper = mappers.get(key);
        if (mapper == null) {
            throw new RuntimeException("传入的参数有误");
        }
        return new Executor().selectList(mapper, connection);
    }
}
