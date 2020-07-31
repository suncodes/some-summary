package bean;

import java.util.Arrays;

public class ArrayBean {

    private Integer[] integers;
    private String[] strings;
    private StringBean[] stringBeans;

    @Override
    public String toString() {
        return "ArrayBean{" +
                "integers=" + Arrays.toString(integers) +
                ", strings=" + Arrays.toString(strings) +
                ", stringBeans=" + Arrays.toString(stringBeans) +
                '}';
    }

    public void setIntegers(Integer[] integers) {
        this.integers = integers;
    }

    public void setStrings(String[] strings) {
        this.strings = strings;
    }

    public void setStringBeans(StringBean[] stringBeans) {
        this.stringBeans = stringBeans;
    }
}
