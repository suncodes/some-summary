package suncodes.pojo.bo;

public class HelloBO {
    private String hello;
    private String world;

    public String getHello() {
        return hello;
    }

    public void setHello(String hello) {
        this.hello = hello;
    }

    public String getWorld() {
        return world;
    }

    public void setWorld(String world) {
        this.world = world;
    }

    @Override
    public String toString() {
        return "HelloBO{" +
                "hello='" + hello + '\'' +
                ", world='" + world + '\'' +
                '}';
    }
}
