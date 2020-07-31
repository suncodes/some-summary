package bean;

/**
 * 第一种类型：整形,构造函数
 */
public class IntegerConstructorBean {
    /**
     * 整形，包装类型
     */
    private Integer id;
    /**
     * 整形，基本类型
     */
    private int age;
    private Long time;
    private long length;
    private short aShort;
    private Short aShort1;

    public IntegerConstructorBean(Integer id, int age, Long time, long length, short aShort, Short aShort1) {
        this.id = id;
        this.age = age;
        this.time = time;
        this.length = length;
        this.aShort = aShort;
        this.aShort1 = aShort1;
    }

    @Override
    public String toString() {
        return "IntegerBean{" +
                "id=" + id +
                ", age=" + age +
                ", time=" + time +
                ", length=" + length +
                ", aShort=" + aShort +
                ", aShort1=" + aShort1 +
                '}';
    }
}
