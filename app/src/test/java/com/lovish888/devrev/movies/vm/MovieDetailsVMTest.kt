package com.lovish888.devrev.movies.vm

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.lovish888.devrev.movies.types.Movie
import com.lovish888.devrev.networking.ApiRequest
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.*

@ExperimentalCoroutinesApi
class MovieDetailsVMTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

    @MockK
    private lateinit var movieObserver: Observer<Movie>

    private lateinit var movieDetailsVM: MovieDetailsVM

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
        movieDetailsVM = MovieDetailsVM()
        movieDetailsVM.movie.observeForever(movieObserver)
    }

    @After
    fun tearDown() {
        movieDetailsVM.movie.removeObserver(movieObserver)
        Dispatchers.resetMain()
    }

    @Test
    fun `fetchMovieDetails - success`() = runTest {
        val movie = Movie(id = 1, title = "Test Movie")

        mockkObject(ApiRequest)
        coEvery {
            ApiRequest.get(
                any<String>(),
                any<(Movie) -> Unit>(),
                any<(Throwable) -> Unit>()
            )
        } coAnswers {
            val onSuccess = it.invocation.args[1] as (Movie) -> Unit
            onSuccess(movie)
        }

        movieDetailsVM.fetchMovieDetails(1)

        coVerify { movieObserver.onChanged(movie) }
        unmockkObject(ApiRequest)
    }

    @Test
    fun `fetchMovieDetails - failure`() = runTest {
        val errorMessage = "Error fetching movie details"

        mockkObject(ApiRequest)
        coEvery {
            ApiRequest.get(
                any<String>(),
                any<(Movie) -> Unit>(),
                any<(Throwable) -> Unit>()
            )
        } coAnswers {
            val onFailure = it.invocation.args[2] as (Throwable) -> Unit
            onFailure(Exception(errorMessage))
        }

        movieDetailsVM.fetchMovieDetails(1)

        coVerify(exactly = 0) { movieObserver.onChanged(any()) }
        unmockkObject(ApiRequest)
    }
}
