package hanghae.study.spring.domain

import com.fasterxml.jackson.annotation.JsonManagedReference
import hanghae.study.spring.api.dto.PostUpdateDto
import jakarta.persistence.*
import lombok.ToString
import org.hibernate.annotations.BatchSize

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

    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.DETACH])
    @JsonManagedReference
    @JoinColumn(name = "member_id")
    var member: Member? = null,

    @BatchSize(size = 100)
    @JsonManagedReference
    @OneToMany(mappedBy = "post", cascade = [CascadeType.ALL])
    var comments : MutableList<Comment>? = mutableListOf(),

    ): BaseTimeEntity() {

    fun update(postUpdateDto: PostUpdateDto) {
        this.title = postUpdateDto.title
        this.content = postUpdateDto.content
    }

}