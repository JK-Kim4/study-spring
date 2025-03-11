package hanghae.study.spring.common.exception

//(401)인증 정보 미포함 요청
class EmptyTokenException(
    val errorMessage: String? = "인증 정보가 포함되어있지 않습니다.") : RuntimeException() {

}
