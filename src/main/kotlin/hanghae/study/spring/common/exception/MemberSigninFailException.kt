package hanghae.study.spring.common.exception

//(400) 올바르지 않은 사용자 정보
class MemberSigninFailException(
    val errorMessage: String? = "사용자 정보가 올바르지않습니다.") : RuntimeException() {
}