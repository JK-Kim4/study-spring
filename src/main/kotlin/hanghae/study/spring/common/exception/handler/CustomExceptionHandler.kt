package hanghae.study.spring.common.exception.handler

import hanghae.study.spring.common.exception.*
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.LoggerFactory
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
class CustomExceptionHandler {

    private val logger = LoggerFactory.getLogger(CustomExceptionHandler::class.java)

    @ExceptionHandler(EmptyTokenException::class)
    fun handleException(httpServletRequest: HttpServletRequest, exception: EmptyTokenException): ResponseEntity<String> {

        logger.error("[ERROR-]\t{} \t{}", httpServletRequest.method, httpServletRequest.requestURI)
        logger.error(exception.javaClass.simpleName, exception)

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exception.errorMessage)
    }

    @ExceptionHandler(MemberInvalidateException::class)
    fun handleException(httpServletRequest: HttpServletRequest, exception: MemberInvalidateException): ResponseEntity<String> {

        logger.error("[ERROR-]\t{} \t{}", httpServletRequest.method, httpServletRequest.requestURI)
        logger.error(exception.javaClass.simpleName, exception)

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exception.errorMessage)
    }

    @ExceptionHandler(MemberNameDuplicateException::class)
    fun handleException(httpServletRequest: HttpServletRequest, exception: MemberNameDuplicateException): ResponseEntity<String> {

        logger.error("[ERROR-]\t{} \t{}", httpServletRequest.method, httpServletRequest.requestURI)
        logger.error(exception.javaClass.simpleName, exception)

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.errorMessage)
    }

    @ExceptionHandler(MemberNotFoundException::class)
    fun handleException(httpServletRequest: HttpServletRequest, exception: MemberNotFoundException): ResponseEntity<String> {

        logger.error("[ERROR-]\t{} \t{}", httpServletRequest.method, httpServletRequest.requestURI)
        logger.error(exception.javaClass.simpleName, exception)

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.errorMessage)
    }

    @ExceptionHandler(MemberSigninFailException::class)
    fun handleException(httpServletRequest: HttpServletRequest,exception: MemberSigninFailException): ResponseEntity<String> {

        logger.error("[ERROR-]\t{} \t{}", httpServletRequest.method, httpServletRequest.requestURI)
        logger.error(exception.javaClass.simpleName, exception)

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.errorMessage)
    }

    @ExceptionHandler(TokenException::class)
    fun handleException(httpServletRequest: HttpServletRequest,exception: TokenException): ResponseEntity<String> {

        logger.error("[ERROR-]\t{} \t{}", httpServletRequest.method, httpServletRequest.requestURI)
        logger.error(exception.javaClass.simpleName, exception)

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.errorMessage)
    }

}