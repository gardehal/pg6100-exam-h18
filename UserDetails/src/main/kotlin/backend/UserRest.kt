package backend

import com.google.common.base.Throwables
import backend.db.UserRepository
import backend.dto.DtoConverter
import backend.dto.UserDto
import io.swagger.annotations.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.ConstraintViolationException


const val ID_PARAM = "The numeric id of the user"
const val BASE_JSON = "application/json;charset=UTF-8"
const val USER_JSON = "application/vnd.user+json;charset=UTF-8;version=2"


@Api(value = "/user", description = "Handling of creating and retrieving user")
@RequestMapping(
        path = ["/user"],
        produces = [USER_JSON, BASE_JSON]
)
@RestController
class UserRest {

    @Autowired
    private lateinit var crud: UserRepository

    @Value("\${server.servlet.context-path}")
    private lateinit var contextPath: String

    @ApiOperation("Get all the users")
    @GetMapping
    fun get(@ApiParam("The country name")
            @RequestParam("username", required = false)
            username: String?,
            //
            @ApiParam("The id of the author who wrote the user")
            @RequestParam("mail", required = false)
            mail: String?,
            //
            @ApiParam("The id of the author who wrote the user")
            @RequestParam("address", required = false)
            address: String?

    ): ResponseEntity<List<UserDto>> {

        val list = if (username.isNullOrBlank() && mail.isNullOrBlank() && address.isNullOrBlank()) {
            crud.findAll()
        } else if (!username.isNullOrBlank()) {
            crud.findAllByUsername(username!!)
        } else if (!mail.isNullOrBlank()) {
            crud.findAllByMail(mail!!)
        } else {
            crud.findAllByAddress(address!!)
        }

        return ResponseEntity.ok(DtoConverter.transform(list))
    }


    @ApiOperation("Create a user")
    @PostMapping(consumes = [USER_JSON, BASE_JSON])
    @ApiResponse(code = 201, message = "The id of newly created user")
    fun createUser(
            @ApiParam("Text of user, plus author id and country. Should not specify id or creation time")
            @RequestBody
            dto: UserDto)
            : ResponseEntity<Long> {

        if (!(dto.id.isNullOrEmpty() && dto.userId.isNullOrEmpty()))
        {
            //Cannot specify id for a newly generated user
            return ResponseEntity.status(400).build()
        }
        if (dto.username == null || dto.mail == null || dto.address == null)
        {
            return ResponseEntity.status(400).build()
        }

        val id: Long?
        try {
            id = crud.createUser(dto.username!!, dto.mail!!, dto.address!!)
        } catch (e: Exception) {
            if (Throwables.getRootCause(e) is ConstraintViolationException) {
                return ResponseEntity.status(400).build()
            }
            throw e
        }

        return ResponseEntity.status(201).body(id)
    }

    @ApiOperation("Get a single user specified by id")
    @GetMapping(path = ["/{id}"])
    fun getUser(@ApiParam(ID_PARAM)
                @PathVariable("id")
                pathId: String?)
            : ResponseEntity<UserDto> {

        val id: Long
        try {
            id = pathId!!.toLong()
        } catch (e: Exception) {

            return ResponseEntity.status(404).build()
        }

        val dto = crud.findById(id).orElse(null) ?: return ResponseEntity.status(404).build()

        return ResponseEntity.ok(DtoConverter.transform(dto))
    }


    @ApiOperation("Update an existing user")
    @PutMapping(path = ["/{id}"], consumes = [(MediaType.APPLICATION_JSON_VALUE)])
    fun update(
            @ApiParam(ID_PARAM)
            @PathVariable("id")
            pathId: String?,
            //
            @ApiParam("The user that will replace the old one. Cannot change its id though.")
            @RequestBody
            dto: UserDto
    ): ResponseEntity<Any> {
        val dtoId: Long
        try {
            dtoId = getUserId(dto)!!.toLong()
        } catch (e: Exception) {

            return ResponseEntity.status(404).build()
        }

        if (getUserId(dto) != pathId)
        {
            return ResponseEntity.status(409).build()
        }

        if (!crud.existsById(dtoId))
        {
            return ResponseEntity.status(404).build()
        }

        if (dto.username == null || dto.mail == null || dto.address== null)
        {
            return ResponseEntity.status(400).build()
        }

        try {
            crud.update(dtoId, dto.username!!, dto.mail!!, dto.address!!)
        } catch (e: Exception) {
            if (Throwables.getRootCause(e) is ConstraintViolationException) {
                return ResponseEntity.status(400).build()
            }
            throw e
        }

        return ResponseEntity.status(204).build()
    }


    @ApiOperation("Update the text content of an existing user")
    @PutMapping(path = ["/{id}/username"], consumes = [(MediaType.TEXT_PLAIN_VALUE)])
    fun updateUsername(
            @ApiParam(ID_PARAM)
            @PathVariable("id")
            id: Long?,
            //
            @ApiParam("The new text which will replace the old one")
            @RequestBody
            username: String
    ): ResponseEntity<Any> {
        if (id == null) {
            return ResponseEntity.status(400).build()
        }

        if (!crud.existsById(id)) {
            return ResponseEntity.status(404).build()
        }

        try {
            crud.updateUsername(id, username)
        } catch (e: Exception) {
            if (Throwables.getRootCause(e) is ConstraintViolationException) {
                return ResponseEntity.status(400).build()
            }
            throw e
        }

        return ResponseEntity.status(204).build()
    }


    @ApiOperation("Delete a user with the given id")
    @DeleteMapping(path = ["/{id}"])
    fun delete(@ApiParam(ID_PARAM)
               @PathVariable("id")
               pathId: String?): ResponseEntity<Any> {

        val id: Long
        try {
            id = pathId!!.toLong()
        } catch (e: Exception) {
            return ResponseEntity.status(400).build()
        }

        if (!crud.existsById(id)) {
            return ResponseEntity.status(404).build()
        }

        crud.deleteById(id)
        return ResponseEntity.status(204).build()
    }


    /**
     * Code used to keep backward compatibility
     */
    private fun getUserId(dto: UserDto): String? {

        if (dto.userId != null) {
            return dto.userId
        } else {
            return dto.id
        }
    }
}