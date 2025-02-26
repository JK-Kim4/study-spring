package hanghae.study.spring.common.exception

class MemberNameDuplicateException(
    val errorCode: String,
) : RuntimeException() {
}