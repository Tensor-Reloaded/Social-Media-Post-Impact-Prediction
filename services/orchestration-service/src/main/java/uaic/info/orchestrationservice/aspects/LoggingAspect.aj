package uaic.info.orchestrationservice.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Aspect
public class LoggingAspect {
    @Before("execution(* uaic.info.orchestrationservice.clients.*.*(..) )")
    public void logBeforeFeignCall(JoinPoint joinPoint) {
        log.info(String.format("Calling feign client %s %s",
                joinPoint.getClass(),
                joinPoint.getSignature().getName())
        );
    }
}
