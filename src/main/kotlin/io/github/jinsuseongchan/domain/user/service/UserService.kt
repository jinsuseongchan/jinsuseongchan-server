package io.github.jinsuseongchan.domain.user.service

import io.github.jinsuseongchan.domain.user.domain.User
import io.github.jinsuseongchan.domain.user.repository.UserRepository
import io.github.jinsuseongchan.global.config.auth.UserDetailImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    @Autowired private val userRepository: UserRepository,
    @Autowired private val bCryptPasswordEncoder: BCryptPasswordEncoder): UserDetailsService {
    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        val user: User = userRepository.findByUsername(username)?: throw UsernameNotFoundException("아이디나 비밀번호가 일치하지 않습니다.")
        return UserDetailImpl(user)
    }

    fun saveUser(user: User): User {
        val encodePassword: String = bCryptPasswordEncoder.encode(user.password)
        val encodeUser: User = User(user.username, encodePassword, user.nickName, user.gender, user.birthday, user.authority)
        return userRepository.save(encodeUser)
    }
}