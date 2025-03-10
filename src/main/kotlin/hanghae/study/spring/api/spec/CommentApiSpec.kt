package hanghae.study.spring.api.spec

import hanghae.study.spring.api.dto.CommentDetailResponseDto
import hanghae.study.spring.api.dto.CommentSaveDto
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
             httpServletRequest: HttpServletRequest
    ): ResponseEntity<CommentDetailResponseDto>

}