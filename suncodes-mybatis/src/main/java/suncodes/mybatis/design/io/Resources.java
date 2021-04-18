package suncodes.mybatis.design.io;

import java.io.InputStream;

/**
 * 使用类加载器读取配置文件
 * @author sunchuizhe
 */
public class Resources {

    /**
     * 根据资源路径获取字节流
     * @param filePath
     * @return
     */
    public static InputStream getResourceAsStream(String filePath) {
        return Resources.class.getClassLoader().getResourceAsStream(filePath);
    }
}
