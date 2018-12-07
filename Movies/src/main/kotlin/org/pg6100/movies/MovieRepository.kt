package org.pg6100.movies

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.time.ZonedDateTime
import javax.persistence.EntityManager

@Repository
interface MoviesRepositoryInterface : CrudRepository<MovieEntity, Long>, MoviesRepositoryCustom
{
    fun findAllByTitle(title: String): Iterable<MovieEntity>

    fun findAllByCategory(category: String): Iterable<MovieEntity>

    fun findAllByDirector(director: String): Iterable<MovieEntity>
}

@Transactional
interface MoviesRepositoryCustom {

    fun createMovie(title: String, director: String, category: String,
        screeningFromTime: ZonedDateTime, screeningToTime: ZonedDateTime): Long

    fun updateTitle(movieId: Long, title: String): Boolean

    fun update(moviesId: Long, title: String, director: String, category: String,
        screeningFromTime: ZonedDateTime, screeningToTime: ZonedDateTime): Boolean
}

@Repository
@Transactional
class MoviesRepository: MoviesRepositoryCustom
{
    @Autowired
    private lateinit var em: EntityManager

    override fun createMovie(title: String, director: String, category: String,
        screeningFromTime: ZonedDateTime, screeningToTime: ZonedDateTime): Long
    {
        val entity = MovieEntity(title, director, category, screeningFromTime, screeningToTime)
        em.persist(entity)
        return entity.movieId!!
    }

    override fun updateTitle(movieId: Long, title: String): Boolean
    {
        val news = em.find(MovieEntity::class.java, movieId) ?: return false
        news.title = title
        return true
    }

    override fun update(moviesId: Long, title: String, director: String, category: String,
        screeningFromTime: ZonedDateTime, screeningToTime: ZonedDateTime): Boolean
    {
        val movie = em.find(MovieEntity::class.java, moviesId) ?: return false
        movie.title = title
        movie.director = director
        movie.category = category
        movie.screeningFromTime = screeningFromTime
        movie.screeningToTime = screeningToTime
        return true
    }
}