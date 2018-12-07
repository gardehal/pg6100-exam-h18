package backend.dto

import io.swagger.annotations.ApiModelProperty
import java.lang.Deprecated

data class UserDto(

        @ApiModelProperty("The id of the news")
        var userId: String? = null,

        @ApiModelProperty("The id of the author that wrote/created this news")
        var username: String? = null,

        @ApiModelProperty("The text of the news")
        var mail: String? = null,

        @ApiModelProperty("The text of the news")
        var address: String? = null


) {
    @ApiModelProperty("Deprecated. Use newsId instead")
    @Deprecated
    var id: String? = null
}