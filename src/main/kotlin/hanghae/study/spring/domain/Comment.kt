package hanghae.study.spring.domain

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*
import lombok.ToString
import org.hibernate.annotations.BatchSize

@Entity
@ToString
class Comment(
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name= "comment_id")
    val id: Long? = null,

    @BatchSize(size = 100)
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    val post: Post,

    @BatchSize(size = 100)
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    val member: Member,

    @Column(length = 32)
    val content: String,
    ) : BaseTimeEntity() {
}