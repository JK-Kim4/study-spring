package hanghae.study.spring.api

import hanghae.study.spring.api.dto.PostDetailResponseDto
import hanghae.study.spring.api.dto.PostListResponseDto
import hanghae.study.spring.api.dto.PostSaveDto
import hanghae.study.spring.api.dto.PostUpdateDto
import hanghae.study.spring.api.spec.PostApiSpec
import hanghae.study.spring.service.PostService
import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.Valid
import lombok.RequiredArgsConstructor
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@Controller
@RequiredArgsConstructor
@RequestMapping("/v1/api/post")
class PostApiController(private val postService: PostService) : PostApiSpec {


    @GetMapping("/posts")
    override fun findAll(): ResponseEntity<List<PostListResponseDto>> {
        return ResponseEntity.ok(postService.findPostListOrderByIdDesc())
    }

    @GetMapping("/{id}")
    override fun findById(@PathVariable("id") id: Long): ResponseEntity<PostDetailResponseDto> {
        return ResponseEntity.ok(postService.findPostById(id))
    }

    @PostMapping
    override fun save(@RequestBody @Valid postSaveDto: PostSaveDto,
                      httpServletRequest: HttpServletRequest): ResponseEntity<PostDetailResponseDto> {
        return ResponseEntity.ok(postService.save(postSaveDto, httpServletRequest))
    }

    @PutMapping("/{id}")
    override fun update(@PathVariable("id") id: Long, postUpdateDto: PostUpdateDto,
                        httpServletRequest: HttpServletRequest): ResponseEntity<PostDetailResponseDto> {
        return ResponseEntity.ok(postService.update(id, postUpdateDto, httpServletRequest))
    }

    @DeleteMapping("/{id}")
    override fun delete(@PathVariable("id") id: Long,
                        httpServletRequest: HttpServletRequest): ResponseEntity<Any?> {
        return ResponseEntity.ok(postService.deleteById(id, httpServletRequest))
    }
}