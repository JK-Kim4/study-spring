package hanghae.study.spring.domain

import hanghae.study.spring.api.dto.PostUpdateDto
import jakarta.persistence.*
import lombok.ToString

@Entity
@ToString
class Post (

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "post_id")
    val id: Long? = null,

    @Column(length = 120)
    var title: String,

    @Column(columnDefinition = "TEXT")
    var content: String,

    @Column(length = 16)
    val password: String,

    @Column(length = 20)
    var authorName: String,

    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.DETACH])
    @JoinColumn(name = "member_id")
    var member: Member? = null

): BaseTimeEntity() {

    fun update(postUpdateDto: PostUpdateDto) {
        this.title = postUpdateDto.title
        this.content = postUpdateDto.content
        this.authorName = postUpdateDto.authorName
    }

}