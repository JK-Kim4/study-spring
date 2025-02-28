package hanghae.study.spring.common.exception

class MemberSigninFailException(val errorCodes: String) : RuntimeException() {
}