package org.tsdes.advanced.frontend.sparest.backend

import io.restassured.RestAssured
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.not
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner
import org.tsdes.advanced.frontend.sparest.backend.db.Book
import org.tsdes.advanced.frontend.sparest.backend.db.BookRepository
import org.tsdes.advanced.frontend.sparest.dto.BookDto

@ActiveProfiles("test")
@RunWith(SpringRunner::class)
@SpringBootTest(classes = [(SpaRestBackendApplication::class)],
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SpaRestBackendApplicationTest
{
    @LocalServerPort
    protected var port = 0

    @Autowired
    protected lateinit var repository: MovieRepository

    @Before
    @After
    fun clean()
    {
        RestAssured.baseURI = "http://localhost"
        RestAssured.port = port
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails()
    }
}