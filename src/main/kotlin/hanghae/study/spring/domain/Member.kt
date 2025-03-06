package hanghae.study.spring.domain

import com.fasterxml.jackson.annotation.JsonManagedReference
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

    @Enumerated(EnumType.STRING)
    val role: Role? = Role.CLIENT,

    @JsonManagedReference
    @OneToMany(mappedBy = "member", cascade = [(CascadeType.ALL)])
    val posts: MutableSet<Post> = mutableSetOf(),

    ): BaseTimeEntity() {}


