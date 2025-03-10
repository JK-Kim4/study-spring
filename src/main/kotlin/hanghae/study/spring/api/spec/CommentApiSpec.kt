package hanghae.study.spring.api.spec

import hanghae.study.spring.api.dto.CommentDetailResponseDto
import hanghae.study.spring.api.dto.CommentSaveDto
import hanghae.study.spring.api.dto.CommentUpdateDto
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity

@Tag(name = "댓글 API", description = "댓글 관리 API")
interface CommentApiSpec {

    @Operation( summary = "댓글 등록 API ", method = "POST",
                description =   "- 토큰을 검사하여, 유효한 토큰일 경우에만 댓글 작성 가능\n" +
                                "- 선택한 게시글의 DB 저장 유무를 확인하기\n" +
                                "- 선택한 게시글이 있다면 댓글을 등록하고 등록된 댓글 반환하기",
                security = [SecurityRequirement(name = "Authorization")],)
    fun save(commentSaveDto: CommentSaveDto,
             httpServletRequest: HttpServletRequest): ResponseEntity<CommentDetailResponseDto>

    @Operation( summary = "댓글 수정 API ", method = "PUT",
        description =   "토큰을 검사한 후, 유효한 토큰이면서 해당 사용자가 작성한 댓글만 수정 가능\n" +
                "선택한 댓글의 DB 저장 유무를 확인하기\n" +
                "선택한 댓글이 있다면 댓글 수정하고 수정된 댓글 반환하기",
        security = [SecurityRequirement(name = "Authorization")],)
    fun update(id: Long, commentUpdateDto: CommentUpdateDto,
               httpServletRequest: HttpServletRequest) : ResponseEntity<CommentDetailResponseDto>

    @Operation( summary = "댓글 삭제 API ", method = "DELETE",
        description =   "토큰을 검사한 후, 유효한 토큰이면서 해당 사용자가 작성한 댓글만 수정 가능\n" +
                "선택한 댓글의 DB 저장 유무를 확인하기\n" +
                "선택한 댓글이 있다면 댓글 수정하고 수정된 댓글 반환하기",
        security = [SecurityRequirement(name = "Authorization")],)
    fun delete(id: Long, httpServletRequest: HttpServletRequest) : ResponseEntity<String>

}