package fr.centralesupelec.sio.controller

import fr.centralesupelec.sio.data.MoviesRepository
import fr.centralesupelec.sio.data.PersonRepository
import fr.centralesupelec.sio.model.Movie
import fr.centralesupelec.sio.model.MovieGenre
import fr.centralesupelec.sio.model.Person
import org.springframework.data.domain.Example
import org.springframework.data.domain.ExampleMatcher
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.*
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@EnableOAuth2Client
@RestController
class MovieController(private val moviesRepository: MoviesRepository, private val personRepository: PersonRepository) {

    @GetMapping("/movies")
    fun getAll() = moviesRepository.findAll()

    @GetMapping("/directors")
    fun getDirectors(): Set<Person> {
        val movies = moviesRepository.findAll()

        var directors = setOf<Person>()
        movies.forEach { it -> directors += it.directors!! }
        return directors
    }

    @GetMapping("/actors")
    fun getActors(): Set<Person> {
        val movies = moviesRepository.findAll()

        var actors = setOf<Person>()
        movies.forEach { it -> actors += it.directors!! }
        return actors
    }

    @GetMapping("/genres")
    fun getgenres(): Set<String> {
        var genres = setOf<String>()
        MovieGenre.values().forEach { it -> genres += it.name }
        return genres
    }

    @GetMapping("/search")
    fun search(@RequestParam(value = "title", required = false) title: String?,
               @RequestParam(value = "genre", required = false) genre: String?,
               @RequestParam(value = "directors", required = false) directors: String?,
               @RequestParam(value = "actors", required = false) actors: String?,
               @RequestParam(value = "offset", required = false) offset: String?,
               @RequestParam(value = "limit", required = false) limit: String?): List<Movie> {

        val matcher = ExampleMatcher.matching()
                .withMatcher("title", contains().ignoreCase())

        var setDirectors = setOf<Person>()
        if (directors != null) {
            directors?.split(",").forEach { it -> setDirectors += personRepository.findById(it.toLong()).get() }
        }

        var setActors = setOf<Person>()
        if (actors != null) {
            actors?.split(",").forEach { it -> setActors += personRepository.findById(it.toLong()).get() }
        }

        val movieGenre = if (genre == null) genre else MovieGenre.valueOf(genre!!.toUpperCase())

        val result = moviesRepository.findAll(Example.of(Movie(title, movieGenre, null, null, null), matcher))

        var movies = listOf<Movie>()
        result.forEach { it -> movies += it }

        movies = if (setDirectors.isNotEmpty()) movies.filter { it.directors!!.containsAll(setDirectors) } else movies
        movies = if (setActors.isNotEmpty()) movies.filter { it.actors!!.containsAll(setActors) } else movies

        return movies
    }
}