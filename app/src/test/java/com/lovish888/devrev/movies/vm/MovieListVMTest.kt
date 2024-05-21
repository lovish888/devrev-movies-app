package com.lovish888.devrev.movies.vm

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.lovish888.devrev.movies.types.Movie
import com.lovish888.devrev.movies.types.MovieListResponse
import com.lovish888.devrev.movies.util.GET_POPULAR_MOVIES_URL
import com.lovish888.devrev.networking.ApiRequest
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull

@ExperimentalCoroutinesApi
class MovieListVMTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

    @MockK
    private lateinit var moviesObserver: Observer<List<Movie>>

    private lateinit var movieListVM: MovieListVM

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
        movieListVM = MovieListVM()
        movieListVM.movies.observeForever(moviesObserver)
    }

    @After
    fun tearDown() {
        movieListVM.movies.removeObserver(moviesObserver)
        Dispatchers.resetMain()
    }

    @Test
    fun `fetchMovies - success`() = runTest {
        val movies = List(20) { Movie(id = it, title = "Movie $it") }
        val movieListResponse = MovieListResponse(results = movies)

        mockkObject(ApiRequest)

        coEvery {
            ApiRequest.get<MovieListResponse>(
                any<String>(),
                any<(MovieListResponse) -> Unit>(),
                any<(Throwable) -> Unit>()
            )
        } coAnswers {
            val onSuccess: (MovieListResponse) -> Unit = args[1] as (MovieListResponse) -> Unit
            onSuccess.invoke(movieListResponse)
        }

        movieListVM.fetchMovies("popular")

        // Advance time to ensure the coroutine has completed
        testScheduler.advanceUntilIdle()

        // Verify the observer was updated
        coVerify { moviesObserver.onChanged(movies) }

        // Verify the LiveData value
        assertEquals(20, movieListVM.movies.value?.size)

        unmockkObject(ApiRequest)
    }

    @Test
    fun `fetchMovies - failure`() = runTest {
        val errorMessage = "Error fetching movies"

        mockkObject(ApiRequest)
        coEvery {
            ApiRequest.get(
                any<String>(),
                any<(MovieListResponse) -> Unit>(),
                any<(Throwable) -> Unit>()
            )
        } coAnswers {
            val onFailure = it.invocation.args[2] as (Throwable) -> Unit
            onFailure(Exception(errorMessage))
        }

        movieListVM.fetchMovies("latest")

        coVerify(exactly = 0) { moviesObserver.onChanged(any()) }
        unmockkObject(ApiRequest)
    }
}
