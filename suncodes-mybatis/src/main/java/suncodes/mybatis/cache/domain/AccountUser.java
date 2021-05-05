package suncodes.mybatis.cache.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 一对一，多对一
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountUser {
    private Integer id;
    private Integer uid;
    private Double money;
    private User user;
}
