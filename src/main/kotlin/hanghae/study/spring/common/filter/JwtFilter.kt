package hanghae.study.spring.common.filter

import com.fasterxml.jackson.databind.ObjectMapper
import hanghae.study.spring.common.exception.EmptyTokenException
import hanghae.study.spring.common.exception.TokenException
import hanghae.study.spring.common.jwt.JwtUtil
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import lombok.RequiredArgsConstructor
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter


@Component
@RequiredArgsConstructor
class JwtFilter(val jwtUtil: JwtUtil) : OncePerRequestFilter() {

    private val logger = LoggerFactory.getLogger(JwtFilter::class.java)

    private val targetUrlPrefix = "/v1/api/post"

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain
    ) {
        val requestUri = request.requestURI
        logger.info("request url = $requestUri")
        if(requestUri.startsWith(targetUrlPrefix)) {
            logger.info("token validation start")
            val token = this.resoleTokenFromRequest(request)
            try {
                jwtUtil.validateToken(token)
                filterChain.doFilter(request, response)
            }catch (e: TokenException){
                response.setStatus(HttpStatus.UNAUTHORIZED.value())
                response.setContentType("application/json")
                response.setCharacterEncoding("UTF-8")
                val json = ObjectMapper().writeValueAsString(e.errorMessage)
                response.writer.write(json)
            }
        }else{
            filterChain.doFilter(request, response)
        }
    }

    /* TODO
    *  filter 발생 exception의 경우 Dispathersevlet의 영향을 받지 않음
    *  자체 Response 생성 필요
    * */
    private fun resoleTokenFromRequest(request: HttpServletRequest): String {
        logger.info("resolve token to header start")
        val token = jwtUtil.resolveToken(request)
        if(token != null){
            logger.info("token = " + token)
            return token
        }else{
            throw EmptyTokenException("토큰이 존재하지않습니다.")
        }
    }

}