package sun.ioc.bean;

/**
 * 第四种：对象类型的赋值
 */
public class ObjectBean {

    private Byte varByte;
    private Character character;
    private Short varShort;
    private Integer integer;
    private Float varFloat;
    private Long varLong;
    private Double varDouble;
    private String string;
    /** 自定义bean */
    private StringBean stringBean;
    private StringConstructorBean stringConstructorBean;
    private IntegerBean integerBean;
    private IntegerConstructorBean integerConstructorBean;

    @Override
    public String toString() {
        return "ObjectBean{" +
                "varByte=" + varByte +
                ", character=" + character +
                ", varShort=" + varShort +
                ", integer=" + integer +
                ", varFloat=" + varFloat +
                ", varLong=" + varLong +
                ", varDouble=" + varDouble +
                ", string='" + string + '\'' +
                ", stringBean=" + stringBean +
                ", stringConstructorBean=" + stringConstructorBean +
                ", integerBean=" + integerBean +
                ", integerConstructorBean=" + integerConstructorBean +
                '}';
    }

    public void setVarByte(Byte varByte) {
        this.varByte = varByte;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

    public void setVarShort(Short varShort) {
        this.varShort = varShort;
    }

    public void setInteger(Integer integer) {
        this.integer = integer;
    }

    public void setVarFloat(Float varFloat) {
        this.varFloat = varFloat;
    }

    public void setVarLong(Long varLong) {
        this.varLong = varLong;
    }

    public void setVarDouble(Double varDouble) {
        this.varDouble = varDouble;
    }

    public void setString(String string) {
        this.string = string;
    }

    public void setStringBean(StringBean stringBean) {
        this.stringBean = stringBean;
    }

    public void setStringConstructorBean(StringConstructorBean stringConstructorBean) {
        this.stringConstructorBean = stringConstructorBean;
    }

    public void setIntegerBean(IntegerBean integerBean) {
        this.integerBean = integerBean;
    }

    public void setIntegerConstructorBean(IntegerConstructorBean integerConstructorBean) {
        this.integerConstructorBean = integerConstructorBean;
    }
}
