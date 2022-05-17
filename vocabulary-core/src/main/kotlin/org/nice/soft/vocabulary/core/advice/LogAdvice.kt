package org.nice.soft.vocabulary.core.advice

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.slf4j.LoggerFactory.getLogger
import org.springframework.stereotype.Component

@Aspect
@Component
class LogAdvice {

    @Around("execution(* org.nice.soft.vocabulary.core.service.*.*(..))")
    fun logInCaseOfError(joinPoint: ProceedingJoinPoint): Any? {
        val returnValue: Any? = try {
            joinPoint.proceed()
        } catch (e: Exception) {
            val logger = getLogger(joinPoint.target.javaClass.name)
            logger.error("Error occurred", e)
            throw e
        }
        return returnValue
    }
}