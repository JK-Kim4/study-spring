package hanghae.study.spring.common.exception

//(400) 유효하지 않은 토큰
class TokenException(
    val errorMessage: String? = "토근 정보가 유효하지않습니다.") : RuntimeException() {
}