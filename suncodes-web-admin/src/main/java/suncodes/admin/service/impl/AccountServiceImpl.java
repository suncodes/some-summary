package suncodes.admin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import suncodes.admin.bean.Account;
import suncodes.admin.mapper.AccountMapper;
import suncodes.admin.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    AccountMapper accountMapper;

    public Account getAcctByid(Long id){
        return accountMapper.getAcct(id);
    }
}
