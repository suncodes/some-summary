package suncodes.service;

import suncodes.pojo.bo.HelloBO;

@Deprecated
public class TargetService {

    public void f(String s) {
        System.out.println(">>>>>>>>>>>>>>>>>" + s);
    }

    public void f1(HelloBO s) {
        System.out.println(">>>>>>>>>>>>>>>>>" + s);
    }

    public void f2() {
        System.out.println(">>>>>>>>>>>>>>>>>");
    }
}
