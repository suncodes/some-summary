package suncodes.jdbc.aspectj;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.support.TransactionTemplate;
import suncodes.jdbc.utils.TransactionUtils;

@Component
@Aspect
public class ResourceAspectJ {
    @Autowired
    private TransactionUtils transactionUtils;

    /**
     * 异常通知
     */
    @AfterThrowing("execution(* suncodes.jdbc.service.ResourceAspectjService.add(..))")
    public void afterThrowing() {
        System.out.println("程序已经回滚");
        // 获取程序当前事务 进行回滚
        TransactionStatus begin = transactionUtils.begin();
        transactionUtils.rollback(begin);
//        TransactionStatus transactionStatus = TransactionAspectSupport.currentTransactionStatus();
//        transactionStatus.setRollbackOnly();
    }

    /**
     * 环绕通知
     */
    @Around("execution(* suncodes.jdbc.service.ResourceAspectjService.add(..))")
    public void around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("开启事务");
        TransactionStatus begin = transactionUtils.begin();
        proceedingJoinPoint.proceed();
        transactionUtils.commit(begin);
        System.out.println("提交事务");
    }
}