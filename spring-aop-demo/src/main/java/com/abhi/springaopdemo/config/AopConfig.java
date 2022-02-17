package com.abhi.springaopdemo.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class AopConfig {
    @Before(value = "execution(* com.abhi.springaopdemo.service.HiByeService.sayHi(..))")
    public void beforeMethodExecution(JoinPoint joinPoint) {
        System.out.print("Before -> ");
        System.out.print(joinPoint.getSignature().getName() + " called with -> ");
        System.out.println(Arrays.toString(joinPoint.getArgs()));
    }

    @After("createPointCut()")
    public void afterMethodExecution(JoinPoint joinPoint) {
        System.out.print("After -> ");
        System.out.print(joinPoint.getSignature().getName() + " called with -> ");
        System.out.println(Arrays.toString(joinPoint.getArgs()));
    }

    @Around(value = "execution(* com.abhi.springaopdemo.service.HiByeService.doSomething(..))")
    public void aroundMethodExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.print("Around -> ");
        System.out.println(joinPoint.getSignature().getName() + " called with before execution -> " + Arrays.toString(joinPoint.getArgs()));

        Object returnValue = joinPoint.proceed();

        System.out.println(joinPoint.getSignature().getName() + " called with after execution -> " + returnValue);

    }

    @Pointcut("execution(* com.abhi.springaopdemo.service.HiByeService.sayBye(..))")
    public void createPointCut() {

    }
}
