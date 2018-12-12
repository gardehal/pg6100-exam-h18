package backend

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Bean
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

@Suppress("DEPRECATION")
@SpringBootApplication(scanBasePackages = ["backend"])
@EnableJpaRepositories(basePackages = ["backend"])
@EntityScan(basePackages = ["backend"])
@EnableSwagger2
open class UserApplication {

    @Bean
    open fun swaggerApi(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .paths(PathSelectors.any())
                .build()
    }

    private fun apiInfo(): ApiInfo {
        return ApiInfoBuilder()
                .title("API for REST User")
                .description("Some description")
                .version("2.0.0") // Note the change in version
                .build()
    }
}

/*
    If you run this directly, you can then check the Swagger documentation at:

    http://localhost:8080/userrest/api/swagger-ui.html
 */

fun main(args: Array<String>) {
    SpringApplication.run(UserApplication::class.java, *args)
}