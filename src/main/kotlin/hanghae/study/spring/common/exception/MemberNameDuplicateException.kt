package hanghae.study.spring.common.exception

//(400) 사용자 계정 중복
class MemberNameDuplicateException(
    val errorMessage: String? = "이미 사용중인 계정입니다.",
) : RuntimeException() {
}