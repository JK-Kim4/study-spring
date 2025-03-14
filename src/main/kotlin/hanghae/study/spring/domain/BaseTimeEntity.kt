package hanghae.study.spring.domain

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@EntityListeners(AuditingEntityListener::class)
@MappedSuperclass
abstract class BaseTimeEntity(

    @Column(name = "created_at")
    var createdAt: LocalDateTime? = LocalDateTime.now(),

    @Column(name = "modified_at")
    var modifiedAt: LocalDateTime? = LocalDateTime.now(),
)