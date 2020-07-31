package bean;

import java.util.List;

/**
 * 第五种：list集合
 */
public class ListBean {

    private List list;
    private List<String> stringList;
    private List<IntegerBean> integerBeanList;
    private List<List<StringBean>> listList;

    @Override
    public String toString() {
        return "ListBean{" +
                "list=" + list +
                ", stringList=" + stringList +
                ", integerBeanList=" + integerBeanList +
                ", listList=" + listList +
                '}';
    }

    public void setList(List list) {
        this.list = list;
    }

    public void setStringList(List<String> stringList) {
        this.stringList = stringList;
    }

    public void setIntegerBeanList(List<IntegerBean> integerBeanList) {
        this.integerBeanList = integerBeanList;
    }

    public void setListList(List<List<StringBean>> listList) {
        this.listList = listList;
    }
}
