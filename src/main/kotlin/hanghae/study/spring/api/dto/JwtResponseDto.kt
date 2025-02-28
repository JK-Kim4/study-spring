package hanghae.study.spring.api.dto

import java.time.LocalDateTime

class JwtResponseDto(
    val status: String,
    val tokenIssuedAt: LocalDateTime,
)