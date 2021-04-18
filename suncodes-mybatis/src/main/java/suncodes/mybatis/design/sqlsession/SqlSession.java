package suncodes.mybatis.design.sqlsession;

public interface SqlSession {
    <T> T getMapper(Class<T> daoInterface);

    void close();
}
