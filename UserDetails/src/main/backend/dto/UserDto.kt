package backend.dto

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import java.lang.Deprecated

@ApiModel("A numeric counter, with name")
data class UserDto(


        // USERNAME
        @ApiModelProperty("Username of the user")
        var username: String? = null,

        // USER MAIL
        @ApiModelProperty("The mail of the user")
        var mail: String? = null,

        //USER ADRESS
        @ApiModelProperty("The address of the user")
        var address: String? = null,

        // USER ID
        @ApiModelProperty("Unique userId of user")
        var userId: String? = null

        //TODO PREVIOUSLY PURCHASED TICKETS
        //TODO SPECIAL DISCOUNTS
        //SUBSCRIPTIONS
)