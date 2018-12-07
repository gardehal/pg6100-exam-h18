package backend

import javax.persistence.*
import javax.validation.constraints.Size
import javax.validation.constraints.NotBlank

@Entity
class User(
        @get:NotBlank @get:Size(max = 128)
        var username: String,

        @get:NotBlank @get:Size(max = 128)
        var mail: String,

        @get:NotBlank @get:Size(max = 128)
        var address: String,

        @get:Id @get:GeneratedValue
        var id: Long? = null

        // PREVIOUSLY PURCHASED TICKETS
        // SPECIAL DISCOUNTS
        // SUBSCRIPTIONS
)