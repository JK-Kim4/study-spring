package hanghae.study.spring.common.exception.handler

import org.springframework.core.annotation.Order
import org.springframework.web.bind.annotation.RestControllerAdvice

@Order(1)
@RestControllerAdvice
class GlobalExceptionHandler {
}