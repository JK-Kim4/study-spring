package hanghae.study.spring.api

import hanghae.study.spring.api.dto.CommentDetailResponseDto
import hanghae.study.spring.api.dto.CommentSaveDto
import hanghae.study.spring.api.dto.CommentUpdateDto
import hanghae.study.spring.api.spec.CommentApiSpec
import hanghae.study.spring.common.jwt.JwtUtil
import hanghae.study.spring.domain.Member
import hanghae.study.spring.service.CommentService
import hanghae.study.spring.service.MemberService
import jakarta.servlet.http.HttpServletRequest
import lombok.RequiredArgsConstructor
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

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
        httpServletRequest: HttpServletRequest): ResponseEntity<CommentDetailResponseDto> {
        val member = getMemberFromToken(httpServletRequest)

        return ResponseEntity.ok(commentService.save(commentSaveDto, member))
    }

    @PutMapping("/{id}")
    override fun update(
        @PathVariable(name = "id") id: Long, commentUpdateDto: CommentUpdateDto,
        httpServletRequest: HttpServletRequest): ResponseEntity<CommentDetailResponseDto> {
        val member = getMemberFromToken(httpServletRequest)

        return ResponseEntity.ok(commentService.update(id, commentUpdateDto, member))
    }

    @DeleteMapping("/{id}")
    override fun delete(@PathVariable(name = "id") id: Long,
                        httpServletRequest: HttpServletRequest): ResponseEntity<String> {
        val member = getMemberFromToken(httpServletRequest)

        return ResponseEntity.ok(commentService.deleteById(id, member))
    }

    private fun getMemberFromToken(httpServletRequest: HttpServletRequest): Member {
        val tokenMemberName = jwtUtil.getUserInfoFromToken(jwtUtil.resolveToken(httpServletRequest)).subject

        return memberService.findByName(tokenMemberName)
    }

}