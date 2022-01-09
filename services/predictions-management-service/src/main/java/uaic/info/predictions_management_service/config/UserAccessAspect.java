package uaic.info.predictions_management_service.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class UserAccessAspect {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Before("execution(* uaic.info.predictions_management_service.controllers.*.*(..))")
    public void before(JoinPoint joinPoint) {
        //Advice
        logger.info("Check for user access");
        logger.info("Allowed execution for {}", joinPoint);
    }

    @AfterReturning(value = "execution(* uaic.info.predictions_management_service.controllers.*.*(..))", returning = "result")
    public void afterReturning(JoinPoint joinPoint, Object result) {
        logger.info("{} returned with value {}", joinPoint, result);
    }

    @AfterThrowing(value = "execution(* uaic.info.predictions_management_service.controllers.*.*(..))")
    public void afterThrowing(JoinPoint joinPoint) {
        logger.info("{} threw exception", joinPoint);
    }
}
