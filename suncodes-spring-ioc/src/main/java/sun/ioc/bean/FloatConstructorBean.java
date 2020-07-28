package sun.ioc.bean;

/**
 * 第二种：浮点，包装及非包装类型
 */
public class FloatConstructorBean {

    private float aFloat;
    private Float aFloatF;
    private double aDouble;
    private Double aDoubleD;
    private Boolean isBoolean;

    public FloatConstructorBean(float aFloat, Float aFloatF,
                                double aDouble, Double aDoubleD,
                                Boolean isBoolean) {
        this.aFloat = aFloat;
        this.aFloatF = aFloatF;
        this.aDouble = aDouble;
        this.aDoubleD = aDoubleD;
        this.isBoolean = isBoolean;
    }

    @Override
    public String toString() {
        return "FloatBean{" +
                "aFloat=" + aFloat +
                ", aFloatF=" + aFloatF +
                ", aDouble=" + aDouble +
                ", aDoubleD=" + aDoubleD +
                ", isBoolean=" + isBoolean +
                '}';
    }
}
