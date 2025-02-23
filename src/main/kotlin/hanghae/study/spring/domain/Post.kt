package hanghae.study.spring.domain

import jakarta.persistence.*

@Entity
class Post (

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "post_id")
    val id: Long? = null,

    @Column(length = 120)
    val title: String,

    @Column(columnDefinition = "TEXT")
    val content: String,

    @Column(length = 16)
    val password: String,

    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.DETACH])
    @JoinColumn(name = "user_id")
    val user: User? = null

): BaseTimeEntity() {}