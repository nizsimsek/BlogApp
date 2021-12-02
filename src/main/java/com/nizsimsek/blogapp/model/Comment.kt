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
    var likes: Long = 0,
    var isLiked: Boolean? = false,
    val createdDate: LocalDateTime = LocalDateTime.now(),
    val updatedDate: LocalDateTime? = LocalDateTime.now(),

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    val author: User,

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "post_id", referencedColumnName = "id")
    val post: Post,

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "comment_likes",
        joinColumns = [JoinColumn(name = "comment_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")]
    )
    val likedUserList: List<User> = emptyList(),
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
        return this::class.simpleName + "(id = $id , content = $content , likes = $likes , isLiked = $isLiked , createdDate = $createdDate , updatedDate = $updatedDate , author = $author , post = $post )"
    }
}