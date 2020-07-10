package sun.ioc.enums;

public enum SexEnum {
    /** 男 */
    MAN(0, "男"),
    /** 女 */
    WOMAN(1, "女");
    private Integer code;
    private String desc;

    SexEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
