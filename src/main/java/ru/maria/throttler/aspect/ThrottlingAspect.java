package ru.maria.throttler.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ru.maria.throttler.service.ThrottlerService;

import javax.servlet.ServletRequest;

/**
 * Aspect for annotation {@link Throttling}
 */
@Aspect
@Component
public class ThrottlingAspect {

    @Autowired
    private ThrottlerService throttlerService;

    @Autowired
    private ServletRequest request;

    @Around("@annotation(Throttling)")
    public Object check(ProceedingJoinPoint joinPoint) throws Throwable {
        boolean check=throttlerService.check(request.getRemoteAddr());
        if (check)
            return joinPoint.proceed();
        else return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
    }
}
