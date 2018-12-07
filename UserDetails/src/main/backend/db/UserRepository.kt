package backend.db

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.time.ZonedDateTime
import javax.persistence.EntityManager


@Repository
interface UserRepository : CrudRepository<User, Long>,  UserRepositoryCustom
{
    fun findAllByUsername(username: String): MutableIterable<User>

    fun findAllByAddress(address: String): MutableIterable<User>

    fun findAllByMail(mail: String): MutableIterable<User>
}

@Transactional
interface UserRepositoryCustom
{
    fun createUser(userId: String, username: String, mail: String, address: String): Long?

    fun updateUsername(id: Long, username: String): Boolean

    fun update(dtoId: Long, userId: String, username: String, mail: String, address: String): Boolean
}

@Repository
@Transactional
class UserRepositoryImpl : UserRepositoryCustom
{
    @Autowired
    private lateinit var em: EntityManager

    override fun createUser(userId: Long, username: String, mail: String, address: String): Long {
        val entity = User(userId, username, mail, address)
        em.persist(entity)
        return entity.userId!!
    }

    override fun updateUsername(userId: Long, username: String): Boolean {
        val user = em.find(User::class.java, userId) ?: return false
        user.username = username
        return true
    }

    override fun update(userId: Long,
                        username: String,
                        mail: String,
                        address: String): Boolean {
        val user = em.find(User::class.java, userId) ?: return false
        user.userId = userId
        user.username = username
        user.mail = mail
        user.address = address
        return true
    }
}