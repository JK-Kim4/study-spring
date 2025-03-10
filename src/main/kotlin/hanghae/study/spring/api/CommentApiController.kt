package hanghae.study.spring.api

import hanghae.study.spring.api.dto.CommentDetailResponseDto
import hanghae.study.spring.api.dto.CommentSaveDto
import hanghae.study.spring.api.spec.CommentApiSpec
import hanghae.study.spring.common.jwt.JwtUtil
import hanghae.study.spring.domain.Member
import hanghae.study.spring.service.CommentService
import hanghae.study.spring.service.MemberService
import jakarta.servlet.http.HttpServletRequest
import lombok.RequiredArgsConstructor
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/comment")
class CommentApiController(
    private val commentService: CommentService,
    private val memberService: MemberService,
    private val jwtUtil: JwtUtil) : CommentApiSpec {

    @PostMapping
    override fun save(
        @RequestBody commentSaveDto: CommentSaveDto,
        httpServletRequest: HttpServletRequest
    ): ResponseEntity<CommentDetailResponseDto> {
        val member = getMemberFromToken(httpServletRequest)

        return ResponseEntity.ok(commentService.save(commentSaveDto, member))
    }

    private fun getMemberFromToken(httpServletRequest: HttpServletRequest): Member {
        val tokenMemberName = jwtUtil.getUserInfoFromToken(jwtUtil.resolveToken(httpServletRequest)).subject

        return memberService.findByName(tokenMemberName)
    }
}