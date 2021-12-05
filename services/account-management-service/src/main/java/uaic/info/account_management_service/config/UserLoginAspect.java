package uaic.info.account_management_service.config;

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
public class UserLoginAspect {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Before(value = "execution(* uaic.info.account_management_service.controllers.GetTwitterTokenController.getToken(..))")
    public void beforeLogin(JoinPoint joinPoint) {
        logger.info("Executing {}", joinPoint.getSignature());
        logger.info("Login initialized");
    }

    @AfterReturning(value = "execution(* uaic.info.account_management_service.controllers.TwitterCallbackController.twitterCallback(..))")
    public void afterLogin(JoinPoint joinPoint) {
        logger.info("Executing {}", joinPoint.getSignature());
        logger.info("Login finalized");
    }

    @AfterThrowing(value = "execution(* uaic.info.account_management_service.controllers.TwitterCallbackController.twitterCallback(..))")
    public void afterLoginThrowing(JoinPoint joinPoint) {
        logger.info("{} threw exception", joinPoint);
    }
}
