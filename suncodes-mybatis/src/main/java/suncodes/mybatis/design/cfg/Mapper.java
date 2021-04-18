package suncodes.mybatis.design.cfg;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Mapper {
    /** SQL */
    private String queryString;
    /** 实体类的全限定类名 */
    private String resultType;
}
