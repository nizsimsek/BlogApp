package com.nizsimsek.blogapp.model

import org.hibernate.Hibernate
import org.hibernate.annotations.GenericGenerator
import java.time.LocalDateTime
import javax.persistence.*

@Entity
data class Post @JvmOverloads constructor(

        @Id
        @GeneratedValue(generator = "UUID")
        @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
        val id: String? = "",
        val title: String,
        val content: String,
        val like: Long = 0,
        val dislike: Long = 0,
        val createdDate: LocalDateTime = LocalDateTime.now(),
        val updatedDate: LocalDateTime? = LocalDateTime.now(),

        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "author_id", referencedColumnName = "id")
        val author: User,

        @ManyToMany(fetch = FetchType.LAZY)
        @JoinTable(
                name = "post_tags",
                joinColumns = [JoinColumn(name = "post_id", referencedColumnName = "id")],
                inverseJoinColumns = [JoinColumn(name = "tag_id", referencedColumnName = "id")]
        )
        val tagList: List<Tag>,

        @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
        val commentList: List<Comment>? = emptyList()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Post

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(title = $title , content = $content , like = $like , dislike = $dislike , createdDate = $createdDate , updatedDate = $updatedDate , author = $author )"
    }
}