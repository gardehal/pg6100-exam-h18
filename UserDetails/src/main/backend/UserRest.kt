package backend

import backend.db.UserRepository
import backend.dto.UserDto
import com.google.common.base.Throwables
import backend.dto.DtoConverter
import io.swagger.annotations.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.ConstraintViolationException
import org.springframework.web.util.UriComponentsBuilder
import java.lang.Deprecated


const val ID_PARAM = "The numeric userId of user"
const val BASE_JSON = "application/json;charset=UTF-8"
const val USER_JSON = "application/vnd.user+json;charset=UTF-8;version=2"

@Api(value = "/users", description = "Handling of creating and retrieving users")
@RequestMapping(
        path = ["/users"], // when the url is "<base>/users", then this class will be used to handle it
        produces = [USER_JSON, BASE_JSON]
)
@RestController
class UserRestApi {


    @Autowired
    private lateinit var crud: UserRepository

    @Value("\${server.servlet.context-path}")
    private lateinit var contextPath : String

    @ApiOperation("Get all the users")
    @GetMapping
    fun get(@ApiParam("The userId of user")
            @RequestParam("userId", required = false)
            userId: String?,
            //
            @ApiParam("The username of user")
            @RequestParam("username", required = false)
            username: String?,
            //
            @ApiParam("The mail of the user")
            @RequestParam("mail", required = false)
            mail: String?,
            //
            @ApiParam("The address of the user")
            @RequestParam("address", required = false)
            address: String?

    ): ResponseEntity<List<UserDto>> {


        val list = if (userId.isNullOrBlank() && username.isNullOrBlank()
                && mail.isNullOrBlank() && address.isNullOrBlank())
        {
            crud.findAll()
        }
        else if (!username.isNullOrBlank())
        {
            crud.findAllByUsername(username!!)
        }
        else if (!address.isNullOrBlank())
        {
            crud.findAllByAddress(address!!)
        }
        else {
            crud.findAllByMail(mail!!)
        }

        return ResponseEntity.ok(DtoConverter.transform(list))
    }


    @ApiOperation("Create a user")
    @PostMapping(consumes = [USER_JSON, BASE_JSON])
    @ApiResponse(code = 201, message = "The userId of created user")
    fun createUser(
            @ApiParam("Id of user, username of user, mail of user, address of user")
            @RequestBody
            dto: UserDto)
            : ResponseEntity<Long> {

        if (!(dto.userId.isNullOrEmpty() && dto.userId.isNullOrEmpty()))
        {
            //Cannot specify userId for a newly generated user
            return ResponseEntity.status(400).build()
        }

        if (dto.userId == null || dto.username == null || dto.mail == null || dto.address == null)
        {
            return ResponseEntity.status(400).build()
        }

        val id: Long?
        try
        {
            id = crud.createUser(dto.userId!!, dto.username!!, dto.mail!!, dto.address!!)
        }
        catch (e: Exception)
        {
            if(Throwables.getRootCause(e) is ConstraintViolationException)
            {
                return ResponseEntity.status(400).build()
            }
            throw e
        }

        return ResponseEntity.status(201).body(id)
    }

    /*
        In the following, we changed the URL from "/news/userId/{userId}"  to "/news/{userId}"
     */
    @ApiOperation("Get a single user specified by userId")
    @GetMapping(path = ["/{userId}"])
    fun getUser(@ApiParam(ID_PARAM)
                @PathVariable("userId")
                pathId: String?)
            : ResponseEntity<UserDto> {

        val id: Long
        try
        {
            id = pathId!!.toLong()
        }
        catch (e: Exception)
        {
            /*
                invalid userId. But here we return 404 instead of 400,
                as in the API we defined the userId as string instead of long
             */
            return ResponseEntity.status(404).build()
        }

        val dto = crud.findById(id).orElse(null) ?: return ResponseEntity.status(404).build()

        return ResponseEntity.ok(DtoConverter.transform(dto))
    }


    @ApiOperation("Update an existing user")
    @PutMapping(path = ["/{userId}"], consumes = [(MediaType.APPLICATION_JSON_VALUE)])
    fun update(
            @ApiParam(ID_PARAM)
            @PathVariable("userId")
            pathId: String?,
            //
            @ApiParam("The user that will replace the old one. Cannot change its userId though.")
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

        if (dto.userId == null || dto.username == null || dto.mail == null || dto.address == null)
        {
            return ResponseEntity.status(400).build()
        }

        try {
            crud.update(dtoId, dto.userId!!, dto.username!!, dto.mail!!, dto.address!!)
        } catch (e: Exception) {
            if(Throwables.getRootCause(e) is ConstraintViolationException) {
                return ResponseEntity.status(400).build()
            }
            throw e
        }

        return ResponseEntity.status(204).build()
    }


    @ApiOperation("Update username of an existing user")
    @PutMapping(path = ["/{userId}/user"], consumes = [(MediaType.TEXT_PLAIN_VALUE)])
    fun updateUser(
            @ApiParam(ID_PARAM)
            @PathVariable("userId")
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
            if(Throwables.getRootCause(e) is ConstraintViolationException) {
                return ResponseEntity.status(400).build()
            }
            throw e
        }

        return ResponseEntity.status(204).build()
    }


    @ApiOperation("Delete a user with the given userId")
    @DeleteMapping(path = ["/{userId}"])
    fun delete(@ApiParam(ID_PARAM)
               @PathVariable("userId")
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
            return dto.userId
        }
    }

    @ApiOperation("Get a single user specified by userId")
    @ApiResponses(ApiResponse(code = 301, message = "Deprecated URI. Moved permanently."))
    @GetMapping(path = ["/userId/{userId}"])
    @Deprecated
    fun deprecatedGetById(@ApiParam(ID_PARAM)
                          @PathVariable("userId")
                          pathId: String?)
            : ResponseEntity<UserDto> {

        return ResponseEntity
                .status(301)
                .location(UriComponentsBuilder.fromUriString("$contextPath/user/$pathId").build().toUri()
                ).build()
    }

    @ApiOperation("Update an existing user")
    @ApiResponses(ApiResponse(code = 308, message = "Deprecated URI. Moved permanently."))
    @PutMapping(path = ["/userId/{userId}"], consumes = [(MediaType.APPLICATION_JSON_VALUE)])
    @Deprecated
    fun deprecatedUpdate(
            @ApiParam(ID_PARAM)
            @PathVariable("userId")
            pathId: String?,
            //
            @ApiParam("The user that will replace the old one. Cannot change its userId though.")
            @RequestBody
            dto: UserDto
    ): ResponseEntity<Any> {

        return ResponseEntity
                .status(308)
                .location(UriComponentsBuilder.fromUriString("$contextPath/user/$pathId").build().toUri()
                ).build()
    }


    @ApiOperation("Delete a user with the given userId")
    @ApiResponses(ApiResponse(code = 308, message = "Deprecated URI. Moved permanently."))
    @DeleteMapping(path = ["/userId/{userId}"])
    @Deprecated
    fun deprecatedDelete(@ApiParam(ID_PARAM)
                         @PathVariable("userId")
                         pathId: String?): ResponseEntity<Any> {

        return ResponseEntity
                .status(308)
                .location(UriComponentsBuilder.fromUriString("$contextPath/user/$pathId").build().toUri()
                ).build()
    }
}

