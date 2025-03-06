package hanghae.study.spring.domain

import com.fasterxml.jackson.annotation.JsonManagedReference
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
    var password: String,

    @Column(length = 20)
    var authorName: String,

    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.DETACH])
    @JsonManagedReference
    @JoinColumn(name = "member_id")
    val member: Member? = null,

    @JsonManagedReference
    @OneToMany(mappedBy = "post", cascade = [CascadeType.ALL])
    var comments : MutableSet<Post> = mutableSetOf(),


    ): BaseTimeEntity() {

    fun update(postUpdateDto: PostUpdateDto) {
        this.title = postUpdateDto.title
        this.content = postUpdateDto.content
        this.authorName = postUpdateDto.authorName
    }

}