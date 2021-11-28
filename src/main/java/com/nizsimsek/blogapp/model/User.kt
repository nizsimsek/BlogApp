package com.nizsimsek.blogapp.model

import org.hibernate.Hibernate
import org.hibernate.annotations.GenericGenerator
import javax.persistence.*

@Entity
data class User @JvmOverloads constructor (

        @Id
        @GeneratedValue(generator = "UUID")
        @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
        val id: String? = "",
        val username: String,
        val email: String,
        val firstName: String,
        val lastName: String,
        val password: String,

        @OneToMany(mappedBy = "author", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
        val postList: List<Post>? = emptyList(),

        @OneToMany(mappedBy = "author", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
        val commentList: List<Comment>? = emptyList()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as User

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(username = $username , email = $email , firstName = $firstName , lastName = $lastName , password = $password )"
    }
}