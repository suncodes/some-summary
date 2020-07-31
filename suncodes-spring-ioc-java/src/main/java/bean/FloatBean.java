package bean;

/**
 * 第二种：浮点，包装及非包装类型
 */
public class FloatBean {

    private float aFloat;
    private Float aFloatF;
    private double aDouble;
    private Double aDoubleD;
    private Boolean isBoolean;

    public void setAFloat(float aFloat) {
        this.aFloat = aFloat;
    }

    public void setAFloatF(Float aFloatF) {
        this.aFloatF = aFloatF;
    }

    public void setADouble(double aDouble) {
        this.aDouble = aDouble;
    }

    public void setADoubleD(Double aDoubleD) {
        this.aDoubleD = aDoubleD;
    }

    /**
     * 使用alt+insert快捷键生成的set方法，前面没有is
     * @param aBoolean
     */
    public void setBoolean(Boolean aBoolean) {
        isBoolean = aBoolean;
    }

    @Override
    public String toString() {
        return "FloatBean{" +
                "aFloat=" + aFloat +
                ", aFloatF=" + aFloatF +
                ", aDouble=" + aDouble +
                ", aDoubleD=" + aDoubleD +
                '}';
    }
}
