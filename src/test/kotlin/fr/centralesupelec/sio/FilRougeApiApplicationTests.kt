package fr.centralesupelec.sio

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForObject
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApplicationTests(@Autowired private val restTemplate: TestRestTemplate) {

    @Test
    fun contextLoads() {
    }

//    @Test
//    fun findAll() {
//        val content = """[{"title":"Lord of the Rings: The Return of the King","genres":"FANTASY","id":1},{"title":"Star Wars VIII: The Last Jedi","genres":"SCIENCE_FICTION","id":2},{"title":"Kingsman 2: The Golden Circle","genres":"ACTION","id":3}]"""
//        Assertions.assertEquals(content, restTemplate.getForObject<String>("/movies"))
//    }
}
