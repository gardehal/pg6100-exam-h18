package backend.dto

import backend.db.User

class DtoConverter {

    companion object {

        fun transform(entity: User): UserDto {

            return UserDto(
                    userId = entity.id?.toString(),
                    username = entity.username,
                    mail = entity.mail,
                    address = entity.address
            )
        }

        fun transform(entities: Iterable<User>): List<UserDto> {
            return entities.map { transform(it) }
        }
    }
}