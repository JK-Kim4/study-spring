package hanghae.study.spring.api.spec

import hanghae.study.spring.api.dto.PostDetailResponseDto
import hanghae.study.spring.api.dto.PostListResponseDto
import hanghae.study.spring.api.dto.PostSaveDto
import hanghae.study.spring.api.dto.PostUpdateDto
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity

@Tag(name = "게시글 API", description = "게시글 관리 API")
interface PostApiSpec {

    @Operation( summary =       "전체 게시글 목록 조회 API", method = "GET",
                description =   "- 제목, 작성자명, 작성 내용, 작성 날짜를 조회하기\n" +
                                "- 작성 날짜 기준 내림차순으로 정렬하기",
                security = [SecurityRequirement(name = "Authorization")],)
    fun findAll(): ResponseEntity<List<PostListResponseDto>>

    @Operation( summary =       "선택한 게시글 조회 API ", method = "GET",
                description =   "선택한 게시글의 제목, 작성자명, 작성 날짜, 작성 내용을 조회하기 \n" +
                                "(검색 기능이 아닙니다. 간단한 게시글 조회만 구현해주세요.)",
                security = [SecurityRequirement(name = "Authorization")],)
    fun findById(id: Long): ResponseEntity<PostDetailResponseDto>

    @Operation( summary =       "게시글 작성 API ", method = "POST",
                description =   "- 제목, 작성자명, 비밀번호, 작성 내용을 저장하고\n" +
                                "- 저장된 게시글을 Client 로 반환하기",
                security = [SecurityRequirement(name = "Authorization")],)
    fun save(postSaveDto: PostSaveDto,
             httpServletRequest: HttpServletRequest): ResponseEntity<PostDetailResponseDto>

    @Operation( summary =       "선택한 게시글 수정 API", method = "PUT",
                description =   "- 수정을 요청할 때 수정할 데이터와 비밀번호를 같이 보내서 서버에서 비밀번호 일치 여부를 확인 한 후\n" +
                                "- 제목, 작성자명, 작성 내용을 수정하고 수정된 게시글을 Client 로 반환하기",
                security = [SecurityRequirement(name = "Authorization")],)
    fun update(id: Long, postUpdateDto: PostUpdateDto,
               httpServletRequest: HttpServletRequest): ResponseEntity<PostDetailResponseDto>

    @Operation( summary =       "선택한 게시글 삭제 API", method = "DELETE",
                description =   "- 삭제를 요청할 때 비밀번호를 같이 보내서 서버에서 비밀번호 일치 여부를 확인 한 후\n" +
                                "- 선택한 게시글을 삭제하고 Client 로 성공했다는 표시 반환하기",
                security = [SecurityRequirement(name = "Authorization")],)
    fun delete(id: Long,
               httpServletRequest: HttpServletRequest): ResponseEntity<Any?>

}