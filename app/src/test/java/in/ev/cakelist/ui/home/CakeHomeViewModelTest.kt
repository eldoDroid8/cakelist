package `in`.ev.cakelist.ui.home

import `in`.ev.cakelist.domain.model.Cake
import `in`.ev.cakelist.domain.model.Error
import `in`.ev.cakelist.domain.model.Response
import `in`.ev.cakelist.domain.usecase.GetCakeListUseCaseImpl
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class CakeHomeViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private val dispatcher = TestCoroutineDispatcher()

    @MockK
    private lateinit var useCase: GetCakeListUseCaseImpl

    private lateinit var viewModel: CakeHomeViewModel

    private val cake = Cake(
        desc = "sadsa",
        title = "scsd",
        image = "https://images.amcnetworks.com/amc.com/wp-content/uploads/2015/04/cast_bb_700x1000_walter-white-lg.jpg"

    )
    private val cakes = mutableListOf(cake)
    private val errorResponse = Error(status_message = "service error")
    private val networkResultSuccess = Response.ApiCallSuccess<List<Cake>>(cakes)

    private val networkResultError = Response.ApiCallError((errorResponse))

    private val networkLoadingT = Response.Loading<Boolean>(true)
    private val networkLoadingF = Response.Loading<Boolean>(false)

    private val flowSuccess = flowOf(networkResultSuccess)
    private val flowFailure = flowOf(networkResultError)
    private val flowLoadingFalse = flowOf(networkLoadingF)
    private val flowLoadingTrue = flowOf(networkLoadingT)


    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun reset() {
        Dispatchers.resetMain()
    }


    @Test
    fun given_Usecase_Get_CakeList_When_Get_Cakes_Then_Api_Success() = runBlockingTest {
        coEvery {
            useCase.execute()
        } returns flowSuccess
        viewModel = CakeHomeViewModel(useCase)
        flowSuccess.collect { data ->
            data.data?.isNotEmpty()?.let { assert(it) }
        }
    }

    @Test
    fun given_Usecase_Get_CakeList_When_Get_Cakes_Then_Api_Error() = runBlockingTest {
        coEvery {
            useCase.execute()
        } returns flowFailure
        viewModel = CakeHomeViewModel(useCase)
        flowFailure.collect { data ->
            assert(data.error.status_message.equals("service error"))
        }
    }

    @Test
    fun given_Repository_Get_Emit_True_When_Get_CakeList_Then_True() =
        runBlockingTest {
            coEvery {
                useCase.execute()
            } returns flowSuccess
            viewModel = CakeHomeViewModel(useCase)
            flowSuccess.map {
                flowLoadingTrue
            }
            flowLoadingTrue.collect { data ->
                assert(data.loading)
            }
        }

    @Test
    fun given_Repository_Get_Emit_False_When_Get_CakeList_Then_False() =
        runBlockingTest {
            coEvery {
                useCase.execute()
            } returns flowSuccess
            viewModel = CakeHomeViewModel(useCase)
            flowSuccess.map {
                flowLoadingFalse
            }
            flowLoadingTrue.collect { data ->
                assert(data.loading)
            }
        }

}