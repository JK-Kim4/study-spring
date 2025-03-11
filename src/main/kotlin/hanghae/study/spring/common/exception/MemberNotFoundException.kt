package hanghae.study.spring.common.exception

//(404) 사용자 정보가 존재하지않습니다.
class MemberNotFoundException(
    val errorMessage : String? = "사용자 정보가 존재하지않습니다.") : RuntimeException() {
}