package movies.api

import com.google.common.base.Throwables
import io.swagger.annotations.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.ConstraintViolationException
import javax.validation.Valid
import org.springframework.web.util.UriComponentsBuilder
import movies.MoviesRepository
import movies.MoviesRepositoryInterface
import movies.dto.MovieConverter
import movies.dto.MovieDto
import java.time.ZonedDateTime

const val ID_PARAM = "The numeric id of the movie"
const val MOVIES_JSON = "application/vnd.movies+json;charset=UTF-8;version=1"
const val BASE_JSON = "application/json;charset=UTF-8"

@Api(value = "/movies", description = "Handling of creating and retrieving movies")
@RequestMapping(
        path = ["/movies"],
        produces = [MOVIES_JSON, BASE_JSON]
)
@RestController
class MovieRestApi
{
    @Autowired
    private lateinit var crud: MoviesRepositoryInterface

    @Value("\${server.servlet.context-path}")
    private lateinit var contextPath : String

    @ApiOperation("Get all the movies")
    @GetMapping
    fun get(@ApiParam("The title of the movie")
            @RequestParam("title", required = false)
            title: String?,

            @ApiParam("The name of the director")
            @RequestParam("director", required = false)
            director: String?,

            @ApiParam("The category")
            @RequestParam("category", required = false)
            category: String?,

            @ApiParam("The start of the screening")
            @RequestParam("screeningFromTime", required = false)
            screeningFromTime: ZonedDateTime?,

            @ApiParam("The end of the screening")
            @RequestParam("screeningToTime", required = false)
            screeningToTime: ZonedDateTime?

    ): ResponseEntity<List<MovieDto>>
    {

        val list = if(title.isNullOrBlank() && director.isNullOrBlank()
                && category.isNullOrBlank()) //TODO screening?
        {
            crud.findAll()
        }
        else if(!title.isNullOrBlank())
        {
            crud.findAllByTitle(title!!)
        }
        else if(!director.isNullOrBlank())
        {
            crud.findAllByDirector(director!!)
        }
        else //if(!category.isNullOrBlank())
        {
            crud.findAllByCategory(category!!)
        }

        return ResponseEntity.ok(MovieConverter.transform(list))
    }

    @ApiOperation("Create a movie")
    @PostMapping(consumes = [MOVIES_JSON, BASE_JSON])
    @ApiResponse(code = 201, message = "The id of newly created movie")
    fun createNews(
            @ApiParam("Title, director, category, screeningFromTime, screeningToTime of movie")
            @RequestBody
            dto: MovieDto)
            : ResponseEntity<Long> {

        if (!dto.movieId.isNullOrEmpty())
        {
            //Cannot specify id for a newly generated news
            return ResponseEntity.status(400).build()
        }

        if (dto.title == null || dto.director == null || dto.category == null
                || dto.screeningFromTime == null || dto.screeningToTime == null)
        {
            return ResponseEntity.status(400).build()
        }

        val id: Long?
        try
        {
            id = crud.createMovie(dto.title!!, dto.director!!, dto.category!!
                    , dto.screeningFromTime!!, dto.screeningToTime!!)
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
        In the following, we changed the URL from "/news/id/{id}"  to "/news/{id}"
     */


    @ApiOperation("Get a single movie specified by id")
    @GetMapping(path = ["/{id}"])
    fun getNews(@ApiParam(ID_PARAM)
                @PathVariable("id")
                pathId: String?)
            : ResponseEntity<MovieDto> {

        val id: Long
        try
        {
            id = pathId!!.toLong()
        }
        catch (e: Exception) {
            return ResponseEntity.status(404).build()
        }

        //FIXME ???
        val dto = crud.findById(id).orElse(null) ?: return ResponseEntity.status(404).build()

        return ResponseEntity.ok(MovieConverter.transform(dto))
    }


    @ApiOperation("Update an existing movie entry")
    @PutMapping(path = ["/{id}"], consumes = [(MediaType.APPLICATION_JSON_VALUE)])
    fun update(
            @ApiParam(ID_PARAM)
            @PathVariable("id")
            pathId: String?,
            @ApiParam("The movie that will replace the old one. Cannot change its id though.")
            @RequestBody
            dto: MovieDto
    ): ResponseEntity<Any>
    {
        val dtoId: Long
        try
        {
            dtoId = getMovieId(dto)!!.toLong()
        }
        catch (e: Exception) {
            /*
                invalid id. But here we return 404 instead of 400,
                as in the API we defined the id as string instead of long
             */
            return ResponseEntity.status(404).build()
        }

        if (getMovieId(dto) != pathId)
        {
            // Not allowed to change the id of the resource (because set by the DB).
            // In this case, 409 (Conflict) sounds more appropriate than the generic 400
            return ResponseEntity.status(409).build()
        }

        if (!crud.existsById(dtoId))
        {
            //Here, in this API, made the decision to not allow to create a news with PUT.
            // So, if we cannot find it, should return 404 instead of creating it
            return ResponseEntity.status(404).build()
        }

        if (dto.title == null || dto.director == null || dto.category == null
                || dto.screeningFromTime == null || dto.screeningToTime == null)
        {
            return ResponseEntity.status(400).build()
        }

        try
        {
            crud.update(dtoId, dto.title!!, dto.director!!, dto.category!!,
                    dto.screeningFromTime!!, dto.screeningToTime!!)
        }
        catch (e: Exception)
        {
            if(Throwables.getRootCause(e) is ConstraintViolationException) {
                return ResponseEntity.status(400).build()
            }
            throw e
        }

        return ResponseEntity.status(204).build()
    }

    @ApiOperation("Update the title content of an existing movie")
    @PutMapping(path = ["/{id}/title"], consumes = [(MediaType.TEXT_PLAIN_VALUE)])
    fun updateText(
            @ApiParam(ID_PARAM)
            @PathVariable("id")
            id: Long?,
            @ApiParam("The new text which will replace the old one")
            @RequestBody
            title: String
    ): ResponseEntity<Any>
    {
        if (id == null)
        {
            return ResponseEntity.status(400).build()
        }

        if (!crud.existsById(id))
        {
            return ResponseEntity.status(404).build()
        }

        try
        {
            crud.updateTitle(id, title)
        }
        catch (e: Exception)
        {
            if(Throwables.getRootCause(e) is ConstraintViolationException)
            {
                return ResponseEntity.status(400).build()
            }
            throw e
        }

        return ResponseEntity.status(204).build()
    }

    @ApiOperation("Delete a movie with the given id")
    @DeleteMapping(path = ["/{id}"])
    fun delete(@ApiParam(ID_PARAM)
               @PathVariable("id")
               pathId: String?): ResponseEntity<Any>
    {

        val id: Long
        try
        {
            id = pathId!!.toLong()
        }
        catch (e: Exception)
        {
            return ResponseEntity.status(400).build()
        }

        if (!crud.existsById(id))
        {
            return ResponseEntity.status(404).build()
        }

        crud.delete(id)
        return ResponseEntity.status(204).build()
    }

    private fun getMovieId(dto: MovieDto): String?
    {
        return dto.movieId
    }
}