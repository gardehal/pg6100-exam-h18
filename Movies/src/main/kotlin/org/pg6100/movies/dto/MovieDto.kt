package org.pg6100.movies.dto

import io.swagger.annotations.ApiModelProperty
import java.time.ZonedDateTime


data class MovieDto(
        @ApiModelProperty("The id of the movie")
        var movieId: String? = null,

        @ApiModelProperty("The title of the movie")
        var title: String? = null,

        @ApiModelProperty("The name of the director")
        var director: String? = null,

        @ApiModelProperty("The movie category")
        var category: String? = null,

        @ApiModelProperty("Premiere date of the movie")
        var screeningFromTime: ZonedDateTime? = null,

        @ApiModelProperty("End of screening time of the movie")
        var screeningToTime: ZonedDateTime? = null
)