package org.pg6100.movies

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

/**
 * Created by arcuri82 on 06-Jul-17.
 */

@SpringBootApplication(scanBasePackages = ["org.pg6100"])
@EnableJpaRepositories(basePackages = ["org.pg6100"])
@EntityScan(basePackages = ["org.pg6100.movies"])
@EnableSwagger2
class MovieApplication
{
    @Bean
    fun swaggerApi(): Docket
    {
        return Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .paths(PathSelectors.any())
                .build()
    }

    private fun apiInfo(): ApiInfo
    {
        return ApiInfoBuilder()
                .title("API for Movies")
                .description("")
                .version("1.0.0")
                .build()
    }
}

fun main(args: Array<String>)
{
    SpringApplication.run(MovieApplication::class.java, *args)
}
