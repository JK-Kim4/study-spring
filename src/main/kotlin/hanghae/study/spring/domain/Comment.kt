package hanghae.study.spring.domain

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*
import lombok.ToString

@Entity
@ToString
class Comment(
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name= "comment_id")
    val id: Long? = null,

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    val post: Post,

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    val member: Member,

    @Column(length = 32)
    val content: String,
    ) : BaseTimeEntity() {
}