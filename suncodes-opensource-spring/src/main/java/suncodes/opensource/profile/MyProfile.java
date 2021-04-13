package suncodes.opensource.profile;

public class MyProfile {
    private String env;

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    @Override
    public String toString() {
        return "MyProfile{" +
                "env='" + env + '\'' +
                '}';
    }
}
