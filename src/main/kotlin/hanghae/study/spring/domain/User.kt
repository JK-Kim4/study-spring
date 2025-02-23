package hanghae.study.spring.domain

import jakarta.persistence.*

@Entity
class User (
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name= "user_id")
    val id: Long? = null,

    @Column(length = 20)
    val name: String,

    @Column(length = 32)
    val password: String,

    @OneToMany(cascade = [(CascadeType.ALL)])
    val posts: MutableSet<Post> = mutableSetOf(),

): BaseTimeEntity() {}


