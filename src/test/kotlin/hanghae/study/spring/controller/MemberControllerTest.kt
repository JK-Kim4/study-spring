package hanghae.study.spring.controller

import com.fasterxml.jackson.databind.ObjectMapper
import hanghae.study.spring.api.dto.MemberSaveDto
import hanghae.study.spring.api.dto.MemberSigninDto
import hanghae.study.spring.common.jwt.JwtUtil
import hanghae.study.spring.domain.Role
import hanghae.study.spring.service.MemberService
import org.junit.jupiter.api.Assertions
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
class MemberControllerTest {

    @Autowired
    lateinit var memberService: MemberService

    @Autowired
    lateinit var jwtUtil: JwtUtil

    @Autowired
    lateinit var mvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Test
    fun signup_test(){
        val name = "testmember"
        val password = "1234"
        val memberSaveDto = MemberSaveDto(name = name, password = password, role = Role.CLIENT)

        val requestJson = objectMapper.writeValueAsString(memberSaveDto)

        val resultActions = mvc.perform(
            MockMvcRequestBuilders.post("/v1/api/member/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)

        ).andExpect(status().isOk)

        Assertions.assertTrue(resultActions.andReturn().response.contentAsString.contains("success"))
    }

    @Test
    fun signin_test(){
        val name = "testmember"
        val password = "1234"
        memberService.signup(MemberSaveDto(name = name, password = password, role = Role.CLIENT))
        val memberSigninDto = MemberSigninDto(name = name, password = password)


        val requestJson = objectMapper.writeValueAsString(memberSigninDto)

        val resultActions = mvc.perform(
            MockMvcRequestBuilders.post("/v1/api/member/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)

        ).andExpect(status().isOk)

        val token = resultActions.andReturn().response.getHeader("Authorization")

        if (token != null) {
            println("valid result = " + jwtUtil.validateToken(token) )
        }


        Assertions.assertTrue(resultActions.andReturn().response.contentAsString.contains("success"))
    }

}