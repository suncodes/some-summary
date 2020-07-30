package sun.ioc.bean;

import java.util.Properties;

public class PropertiesBean {

    private Properties properties;

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    @Override
    public String toString() {
        return "PropertiesBean{" +
                "properties=" + properties +
                '}';
    }
}
