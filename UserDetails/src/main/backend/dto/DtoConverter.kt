package backend.dto

import backend.db.User

object DtoConverter {

    fun transform(user: User) : UserDto{

        return UserDto(
                username = user.username,
                mail = user.mail,
                address = user.address,
                userId = user.userId.toString()
         ).apply {
            //here setup both ids, even if one is deprecated
            userId = user.userId?.toString()
        }
    }

    fun transform(users: Iterable<User>) : List<UserDto>{
        return users.map { transform(it) }
    }
}