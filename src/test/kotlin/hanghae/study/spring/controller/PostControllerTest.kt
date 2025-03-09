package hanghae.study.spring.controller

import com.fasterxml.jackson.databind.ObjectMapper
import hanghae.study.spring.api.dto.PostSaveDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import kotlin.test.Test


@SpringBootTest
@AutoConfigureMockMvc
class PostControllerTest {

    @Autowired
    lateinit var mvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Test
    fun post_save_test_password_validation_password_bad_request() {
        val title = "test title"
        val content = "test content"
        val password = "11"
        val authorName = "test author"

        val postSaveDto = PostSaveDto(title, content)
        val requestJson = objectMapper.writeValueAsString(postSaveDto)

        val resultActions = mvc.perform(
            MockMvcRequestBuilders.post("/v1/api/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)

        ).andExpect(status().isBadRequest)
    }

    @Test
    fun post_save_test_password_validation_password_ok() {
        val title = "test title"
        val content = "test content"
        val password = "113433"
        val authorName = "test author"

        val postSaveDto = PostSaveDto(title=title, content=content)
        val requestJson = objectMapper.writeValueAsString(postSaveDto)

        val resultActions = mvc.perform(
            MockMvcRequestBuilders.post("/v1/api/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)

        ).andExpect(status().isOk)
    }

}