package backend.db

import org.springframework.data.annotation.Id
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

@Entity
class User(

        //USER ID
        @get:Id @get:GeneratedValue
        var userId: Long? = null,

        //USERNAME
        @get:NotBlank @get:Size(max = 128)
        var username: String,

        //USER MAIL
        @get:NotBlank @get:Size(max = 128)
        var mail: String,

        //USER ADDRESS
        @get:NotBlank @get:Size(max = 128)
        var address: String

//PREVIOUSLY PURCHASED TICKETS

//SPECIAL DISCOUNTS

//SUBSCRIPTIONS

)