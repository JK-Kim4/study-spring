package hanghae.study.spring.common.exception

class MemberInvalidateException(
    val errorMessage: String? = "사용자 정보가 올바르지않습니다.",
) : RuntimeException()

