package suncodes.jdbc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import suncodes.jdbc.dao.ResourceDAO;

@Service
public class ResourceAspectjService {
    @Autowired
    private ResourceDAO resourceDAO;

    public void add() {
        resourceDAO.add("ceshi", "ceshi", "ceshi");
        int i = 1 / 0;
        System.out.println("我是add方法");
        resourceDAO.add("ceshi1", "ceshi1", "ceshi1");
    }
}
