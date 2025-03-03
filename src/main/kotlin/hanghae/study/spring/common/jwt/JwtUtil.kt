package hanghae.study.spring.common.jwt

import hanghae.study.spring.common.exception.TokenException
import hanghae.study.spring.domain.Role
import io.jsonwebtoken.*
import io.jsonwebtoken.security.Keys
import jakarta.annotation.PostConstruct
import jakarta.servlet.http.HttpServletRequest
import lombok.Getter
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import java.security.Key
import java.util.*


@Component
@Getter
class JwtUtil(
    // Header KEY 값
    val AUTHORIZATION_HEADER: String = "Authorization",
    // 사용자 권한 값의 KEY
    val AUTHORIZATION_KEY: String = "TOKEN_TIME",
    // Token 식별자
    val BEARER_PREFIX: String = "Bearer",
    // 토큰 만료시간
    val TOKEN_TIME: Long = 60 * 60 * 1000L,
    ) {

    @Value("\${jwt.secret.key}")
    lateinit var secretKey : String
    private var key: Key? = null
    private val signatureAlgorithm: SignatureAlgorithm = SignatureAlgorithm.HS256
    private val log = LoggerFactory.getLogger(JwtUtil::class.java)

    @PostConstruct
    fun init() {
        val bytes: ByteArray = Base64.getDecoder().decode(secretKey)
        key = Keys.hmacShaKeyFor(bytes)
    }

    //Header 에 포함되어있는 Token 가져옴
    fun resolveToken(httpServletRequest: HttpServletRequest): String? {
        val header = httpServletRequest.getHeader(AUTHORIZATION_HEADER)
        if(StringUtils.hasText(header) && header.startsWith(BEARER_PREFIX)) {
            return header.substring(BEARER_PREFIX.length + 1)
        }
        return null;
    }

    //Token 생성
    fun createToken(userName: String, role: Role): String {
        val date = Date()

        return BEARER_PREFIX + " " + Jwts.builder()
            .claim(AUTHORIZATION_KEY, role)
            .setSubject(userName)
             .setExpiration(Date(date.time+ TOKEN_TIME))
             .setIssuedAt(date)
             .signWith(key, signatureAlgorithm)
            .compact();
    }

    fun validateToken(token: String) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token)
        } catch (e: SecurityException) {
            log.info("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.")
            throw TokenException("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.")
        } catch (e: MalformedJwtException) {
            log.info("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.")
            throw TokenException("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.")
        } catch (e: ExpiredJwtException) {
            log.info("Expired JWT token, 만료된 JWT token 입니다.")
            throw TokenException("Expired JWT token, 만료된 JWT token 입니다.")
        } catch (e: UnsupportedJwtException) {
            log.info("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.")
            throw TokenException("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.")
        } catch (e: IllegalArgumentException) {
            log.info("JWT claims is empty, 잘못된 JWT 토큰 입니다.")
            throw TokenException("JWT claims is empty, 잘못된 JWT 토큰 입니다.")
        } catch (e: SignatureException) {
            throw TokenException("JWT signature does not match locally computed signature.")
        }
    }

    //Token 포함 사용자 정보 추출
    fun getUserInfoFromToken(token: String?): Claims {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).body
    }
}