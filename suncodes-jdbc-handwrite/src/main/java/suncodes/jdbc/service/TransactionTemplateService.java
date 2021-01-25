package suncodes.jdbc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import suncodes.jdbc.dao.ResourceDAO;

@Service
public class TransactionTemplateService {

    @Autowired
    private TransactionTemplate transactionTemplate;
    @Autowired
    private ResourceDAO resourceDAO;

    public void f() {
        transactionTemplate.execute(transactionStatus -> {
            try {
                f1();
            } catch (Exception e) {
                e.printStackTrace();
                transactionStatus.setRollbackOnly();
            }
            return null;
        });
    }

    public void f1() {
        resourceDAO.add("ceshi", "ceshi", "ceshi");
        int i = 1 / 0;
        System.out.println("我是add方法");
        resourceDAO.add("ceshi1", "ceshi1", "ceshi1");
    }
}
