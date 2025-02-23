package hanghae.study.spring.domain

import jakarta.persistence.*

@Entity
class Member(
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name= "member_id")
    val id: Long? = null,

    @Column(length = 20)
    val name: String,

    @Column(length = 32)
    val password: String,

    @OneToMany(mappedBy = "member", cascade = [(CascadeType.ALL)])
    val posts: MutableSet<Post> = mutableSetOf(),

    ): BaseTimeEntity() {}


