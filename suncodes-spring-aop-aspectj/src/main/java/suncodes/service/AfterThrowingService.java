package suncodes.service;

public class AfterThrowingService {
    public void f() /*throws IllegalAccessException*/ {
        System.out.println("执行方法：AfterThrowingService.f()");
//        throw new IllegalAccessException("asasasasa");
        int a = 1/0;
    }
}
