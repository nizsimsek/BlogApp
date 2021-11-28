package com.nizsimsek.blogapp.model

import org.hibernate.Hibernate
import org.hibernate.annotations.GenericGenerator
import java.time.LocalDateTime
import javax.persistence.*

@Entity
data class Comment @JvmOverloads constructor(

        @Id
        @GeneratedValue(generator = "UUID")
        @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
        val id: String? = "",
        val content: String,
        val like: Long = 0,
        val dislike: Long = 0,
        val createdDate: LocalDateTime = LocalDateTime.now(),
        val updatedDate: LocalDateTime? = LocalDateTime.now(),

        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "author_id", referencedColumnName = "id")
        val author: User,

        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "post_id", referencedColumnName = "id")
        val post: Post
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Comment

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(content = $content , like = $like , dislike = $dislike , createdDate = $createdDate , updatedDate = $updatedDate , author = $author , post = $post )"
    }
}