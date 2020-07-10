package sun.ioc.bo;

import org.springframework.stereotype.Component;

@Component
public class UserBO {
    private String username;
    private int age;
    private Enum anEnum;

    public UserBO() {
    }

    public String getUsername() {
        return username;
    }

//    public void setUsername(String username) {
//        this.username = username;
//    }

    public int getAge() {
        return age;
    }

//    public void setAge(int age) {
//        this.age = age;
//    }

    public Enum getAnEnum() {
        return anEnum;
    }

//    public void setAnEnum(Enum anEnum) {
//        this.anEnum = anEnum;
//    }

    @Override
    public String toString() {
        return "UserBO{" +
                "username='" + username + '\'' +
                ", age=" + age +
                ", anEnum=" + anEnum +
                '}';
    }
}
