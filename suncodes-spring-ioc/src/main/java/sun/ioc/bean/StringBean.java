package sun.ioc.bean;

/**
 * 第三种：字符型
 */
public class StringBean {

    private String string;
    private StringBuilder stringBuilder;
    private StringBuffer stringBuffer;
    private char aChar;
    private Character character;
    private byte aByte;
    private Byte aByteB;

    public void setString(String string) {
        this.string = string;
    }

    public void setStringBuilder(StringBuilder stringBuilder) {
        this.stringBuilder = stringBuilder;
    }

    public void setStringBuffer(StringBuffer stringBuffer) {
        this.stringBuffer = stringBuffer;
    }

    public void setaChar(char aChar) {
        this.aChar = aChar;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

    public void setaByte(byte aByte) {
        this.aByte = aByte;
    }

    public void setaByteB(Byte aByteB) {
        this.aByteB = aByteB;
    }

    @Override
    public String toString() {
        return "StringBean{" +
                "string='" + string + '\'' +
                ", stringBuilder=" + stringBuilder +
                ", stringBuffer=" + stringBuffer +
                ", aChar=" + aChar +
                ", character=" + character +
                ", aByte=" + aByte +
                ", aByteB=" + aByteB +
                '}';
    }
}
