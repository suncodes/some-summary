package suncodes.jdbc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import suncodes.jdbc.dao.ResourceDAO;
import suncodes.jdbc.utils.TransactionUtils;

@Service
public class ResourceService {
    @Autowired
    private ResourceDAO resourceDAO;
    @Autowired
    private TransactionUtils transactionUtils;

    public void add() {
        TransactionStatus transactionStatus = null;
        try {
            transactionStatus = transactionUtils.begin();
            resourceDAO.add("ceshi", "ceshi", "ceshi");
            int i = 1 / 0;
            System.out.println("我是add方法");
            resourceDAO.add("ceshi1", "ceshi1", "ceshi1");
            transactionUtils.commit(transactionStatus);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (transactionStatus != null) {
                transactionUtils.rollback(transactionStatus);
            }
        }

    }

}
