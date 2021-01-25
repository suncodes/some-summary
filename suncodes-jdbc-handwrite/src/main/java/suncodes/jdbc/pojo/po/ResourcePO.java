package suncodes.jdbc.pojo.po;

/**
 * 资源对应的PO
 * @author sunchuizhe
 */
public class ResourcePO {
    private Integer id;
    private String resourceName;
    private String type;
    private String address;

    public ResourcePO() {
    }

    public ResourcePO(Integer id, String resourceName, String type, String address) {
        this.id = id;
        this.resourceName = resourceName;
        this.type = type;
        this.address = address;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "ResourcePO{" +
                "id=" + id +
                ", resourceName='" + resourceName + '\'' +
                ", type='" + type + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
