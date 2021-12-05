package uaic.info.account_management_service.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class RemoveAccountAspect {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Before(value = "execution(* uaic.info.account_management_service.controllers.AccountManagementServiceController.removeById(..))")
//@Before(value = "execution(* uaic.info.account_management_service.controllers.MockViewController.deleteAccount(..))")
    public void beforeDelete(JoinPoint joinPoint) {
        logger.info("Executing {}", joinPoint.getSignature());
        logger.info("Delete initialized");
    }

    @After(value = "execution(* uaic.info.account_management_service.controllers.AccountManagementServiceController.removeById(..))")
//@After(value = "execution(* uaic.info.account_management_service.controllers.MockViewController.deleteAccount(..))")
    public void afterDelete(JoinPoint joinPoint) {
        logger.info("Executing {}", joinPoint.getSignature());
        logger.info("Delete finalized");
    }
}
