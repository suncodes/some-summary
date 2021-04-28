package suncodes.mybatis.manytable.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * 一对多
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAccount {
    private Integer id;
    private String username;
    private Date birthday;
    private String sex;
    private String address;
    private List<Account> accountList;
}
