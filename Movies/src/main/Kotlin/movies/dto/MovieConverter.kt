package movies.dto

import movies.MovieEntity

class MovieConverter
{
    companion object
    {
        fun transform(entity: MovieEntity): MovieDto
        {
            return MovieDto(
                    movieId = entity.movieId?.toString(),
                    title = entity.title,
                    director = entity.director,
                    category = entity.category,
                    screeningFromTime = entity.screeningFromTime,
                    screeningToTime = entity.screeningToTime
            )
        }

        fun transform(entities: Iterable<MovieEntity>): List<MovieDto>
        {
            return entities.map { transform(it) }
        }
    }
}