package org.pg6100.movies

import java.time.ZonedDateTime
import javax.persistence.*
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Entity
class MovieEntity(
    @get:NotNull @get:Size(max = 128)
    var title: String,

    @get:NotNull @get:Size(max = 128)
    var director: String,

    @get:NotNull @get:Size(max = 32)
    var category: String,

    @get:NotNull
    var screeningFromTime: ZonedDateTime,

    @get:NotNull
    var screeningToTime: ZonedDateTime,

    @get:Id @get:GeneratedValue
    var movieId: Long? = null
)