package bean;

import java.util.List;
import java.util.Map;

public class MapBean {
    private Map<StringBean, FloatBean> stringBeanFloatBeanMap;
    private Map<List<String>, List<String>> listListMap;
    private Map<List<String>, List<StringBean>> listListBeanMap;
    private Map<String,IntegerBean> stringIntegerBeanMap;
    private Map<String,String> stringStringMap;

    @Override
    public String toString() {
        return "MapBean{" +
                "stringStringMap=" + stringStringMap +
                ", stringIntegerBeanMap=" + stringIntegerBeanMap +
                ", stringBeanFloatBeanMap=" + stringBeanFloatBeanMap +
                ", listListMap=" + listListMap +
                ", listListBeanMap=" + listListBeanMap +
                '}';
    }

    public void setStringStringMap(Map<String, String> stringStringMap) {
        this.stringStringMap = stringStringMap;
    }

    public void setStringIntegerBeanMap(Map<String, IntegerBean> stringIntegerBeanMap) {
        this.stringIntegerBeanMap = stringIntegerBeanMap;
    }

    public void setStringBeanFloatBeanMap(Map<StringBean, FloatBean> stringBeanFloatBeanMap) {
        this.stringBeanFloatBeanMap = stringBeanFloatBeanMap;
    }

    public void setListListMap(Map<List<String>, List<String>> listListMap) {
        this.listListMap = listListMap;
    }

    public void setListListBeanMap(Map<List<String>, List<StringBean>> listListBeanMap) {
        this.listListBeanMap = listListBeanMap;
    }
}
