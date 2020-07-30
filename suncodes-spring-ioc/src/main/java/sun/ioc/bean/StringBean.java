package sun.ioc.bean;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StringBean that = (StringBean) o;
        return aChar == that.aChar &&
                aByte == that.aByte &&
                Objects.equals(string, that.string) &&
                Objects.equals(stringBuilder, that.stringBuilder) &&
                Objects.equals(stringBuffer, that.stringBuffer) &&
                Objects.equals(character, that.character) &&
                Objects.equals(aByteB, that.aByteB);
    }

    @Override
    public int hashCode() {
        return Objects.hash(string, stringBuilder, stringBuffer, aChar, character, aByte, aByteB);
    }
}
