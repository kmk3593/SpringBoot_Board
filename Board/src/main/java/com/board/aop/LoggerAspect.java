package com.board.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggerAspect {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	// Advice 설정. 타겟메서드의 이전과 이후에 처리할 코드 정의
	// execution으로 프인트컷(=어드바이스를 지정할 위치를 묶는 개념) 지정
	@Around("execution(* com.board..controller.*Controller.*(..)) or execution(* com.board..service.*Impl.*(..)) or execution(* com.board..mapper.*Mapper.*(..))")
	public Object printLog(ProceedingJoinPoint joinPoint) throws Throwable {

		String type = "";
		// getSignature는 함수에 대한 정보를 가지고 있고 getDeclaringTypeName함수는 그 함수가 정의되어있는 타입(클래스) 리턴
		String name = joinPoint.getSignature().getDeclaringTypeName();

		if (name.contains("Controller") == true) {
			type = "Controller ===> ";

		} else if (name.contains("Service") == true) {
			type = "ServiceImpl ===> ";

		} else if (name.contains("Mapper") == true) {
			type = "Mapper ===> ";
		}
		
// 		어느 패키지의 어느 클래스에서 어떤 함수가 호출되었는지 로그 찍음
//		logger.debug(type + name + "." + joinPoint.getSignature().getName() + "()");
//		return joinPoint.proceed();

		logger.debug("어라운드전 : "+type + name + "." + joinPoint.getSignature().getName() + "()");
		Object result =  joinPoint.proceed();
		logger.debug("어라운드후 : "+type + name + "." + joinPoint.getSignature().getName() + "()");
		return result;
	}

}