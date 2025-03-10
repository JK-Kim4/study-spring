package hanghae.study.spring.service

import hanghae.study.spring.api.dto.CommentDetailResponseDto
import hanghae.study.spring.api.dto.CommentSaveDto
import hanghae.study.spring.api.dto.CommentUpdateDto
import jakarta.servlet.http.HttpServletRequest

interface CommentService {

    fun save(commentSaveDto: CommentSaveDto, httpServletRequest: HttpServletRequest) : CommentDetailResponseDto

    fun update(id : Long, commentUpdateDto: CommentUpdateDto, httpServletRequest: HttpServletRequest) : CommentDetailResponseDto

    fun deleteById(id: Long, httpServletRequest: HttpServletRequest) : String

    fun findCommentListByToken(httpServletRequest: HttpServletRequest) : CommentDetailResponseDto

}