package sasa.jovanovic.springdemo.aspect;

import java.util.logging.Logger;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CRMLoggingAspect {

	// setup logger
	private Logger myLogger = Logger.getLogger(getClass().getName());
	
	// setup pointcut declarations
	@Pointcut("execution(* sasa.jovanovic.springdemo.controller.*.*(..)")
	private void forControllerPackage() {}
	
	// do the same for service and dao
	@Pointcut("execution(* sasa.jovanovic.springdemo.service.*.*(..)")
	private void forServicePackage() {}
	
	@Pointcut("execution(* sasa.jovanovic.springdemo.dao.*.*(..)")
	private void forDaoPackage() {}
	
	@Pointcut("forControllerPackage() || forServicePackage() || forDaoPackage()")
	private void forAppFlow() {}
	
	// add @Before advice
	
	// add @AfterReturning advice
}
