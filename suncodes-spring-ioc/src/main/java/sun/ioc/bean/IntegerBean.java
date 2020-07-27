package sun.ioc.bean;

/**
 * 第一种类型：整形,setter
 */
public class IntegerBean {
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

    public void setId(Integer id) {
        this.id = id;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public void setaShort(short aShort) {
        this.aShort = aShort;
    }

    public void setaShort1(Short aShort1) {
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
