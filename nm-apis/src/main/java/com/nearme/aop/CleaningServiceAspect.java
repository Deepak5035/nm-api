package com.nearme.aop;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.nearme.service.impl.AuthenticationForUserImpl;

@Aspect
@Component
public class CleaningServiceAspect {

	private static final Logger logger = LogManager.getLogger(AuthenticationForUserImpl.class);
	

//	//@Before(value = "execution(* com.nearme.service.impl.CleaningServiceDetailsServiceImpl.*(..)) and args(serviceType)")
//	//@Before(value ="execution ( * public *  cleaningService *(..))")
//	public void beforeAdvice(JoinPoint joinPoint, String serviceType) {
//		
//		System.out.println("Before method:" + joinPoint.getSignature());
//
//		System.out.println("Cleaning Service Data Retrive Started");
//	}
//
////	@After(value = "execution(* com.nearme.service.impl.CleaningServiceDetailsServiceImpl.*(..)) and args(serviceType)")
//	public void afterAdvice(JoinPoint joinPoint, String serviceType) {
//		
//		System.out.println("After method:" + joinPoint.getSignature());
//
//		System.out.println("Successfully Retrived Cleaning Service Data");
//	}
	
	@Around("execution(* *(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        Object proceed = joinPoint.proceed();

        long executionTime = System.currentTimeMillis() - startTime;

        System.out.println(joinPoint.getSignature() + " executed in " + executionTime + "ms");

        return proceed;
    }
}
