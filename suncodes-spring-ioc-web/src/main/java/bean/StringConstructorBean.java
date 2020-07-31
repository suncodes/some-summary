package bean;

public class StringConstructorBean {

    private String string;
    private StringBuilder stringBuilder;
    private StringBuffer stringBuffer;
    private char aChar;
    private Character character;
//    private byte aByte;
//    private Byte aByteB;

    public StringConstructorBean(String string,
                                 StringBuilder stringBuilder, StringBuffer stringBuffer,
                                 char aChar, Character character) {
        this.string = string;
        this.stringBuilder = stringBuilder;
        this.stringBuffer = stringBuffer;
        this.aChar = aChar;
        this.character = character;
    }

    @Override
    public String toString() {
        return "StringBean{" +
                "string='" + string + '\'' +
                ", stringBuilder=" + stringBuilder +
                ", stringBuffer=" + stringBuffer +
                ", aChar=" + aChar +
                ", character=" + character +
                '}';
    }
}
