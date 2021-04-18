package suncodes.mybatis.design.cfg;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Configuration {
    private String driver;
    private String url;
    private String username;
    private String password;
    private Map<String,Mapper> mappers = new HashMap<>();

    public Map<String, Mapper> getMappers() {
        return mappers;
    }

    public void setMappers(Map<String, Mapper> mappers) {
        //此处需要使用追加的方式
        this.mappers.putAll(mappers);
    }
}
