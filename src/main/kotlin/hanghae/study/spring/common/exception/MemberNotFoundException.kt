package hanghae.study.spring.common.exception

class MemberNotFoundException(val errorCode : String) : RuntimeException() {
}