package hanghae.study.spring.common.exception

//(401)미인가 사용자의 요청
class MemberInvalidateException(
    val errorMessage: String? = "사용자 정보가 올바르지않습니다.",
) : RuntimeException()

