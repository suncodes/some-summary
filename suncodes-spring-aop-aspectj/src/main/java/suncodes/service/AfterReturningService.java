package suncodes.service;

public class AfterReturningService {
    public String f(String s, String s1) {
        System.out.println("执行方法：AfterReturningService.f()" + s + s1);
        return "AfterReturningService.f()";
    }
}
