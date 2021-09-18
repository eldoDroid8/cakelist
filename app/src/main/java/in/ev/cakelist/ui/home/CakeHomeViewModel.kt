package `in`.ev.cakelist.ui.home

import `in`.ev.cakelist.domain.model.Cake
import `in`.ev.cakelist.domain.model.Response
import `in`.ev.cakelist.domain.usecase.GetCakeListUsecase
import `in`.ev.cakelist.utils.ViewState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CakeHomeViewModel @Inject constructor(
    private val getCakeListUsecase: GetCakeListUsecase
) : ViewModel() {
    private val homeNavEvents = MutableLiveData<HomeNavigation>()
    val stateHomeEvents: LiveData<HomeNavigation> = homeNavEvents
    private val cakeList = MutableLiveData<List<Cake>>()
    val isRefreshing = MutableLiveData(false)
    val observableList: LiveData<List<Cake>> = this.cakeList
    val itemSelected: RecyclerviewItemSelected
        get() = this::cakeSelected
    private val cakeListLiveData: MutableLiveData<ViewState<List<Cake>>> = MutableLiveData()
    val stateNav: LiveData<ViewState<List<Cake>>> = cakeListLiveData

    init {
        getCakeList()
    }

    private fun getCakeList() {
        viewModelScope.launch{
            getCakeListUsecase.execute().collect { response ->
                when (response) {
                    is Response.Loading -> {
                        setRefreshing(true)
                    }
                    is Response.ApiCallSuccess -> {
                        setRefreshing(false)
                        response.data?.let {
                            cakeList.apply {
                                value = it
                            }
                        }
                    }
                    is Response.ApiCallError -> {
                        setRefreshing(false)
                        cakeListLiveData.value = ViewState.Failure(response.error)
                    }
                }
            }
        }
    }

    fun refreshList() {
        setRefreshing(true)
        getCakeList()
    }

    private fun setRefreshing(refresh: Boolean) {
        isRefreshing.value = refresh
    }

    private fun cakeSelected(cake: Cake) {
        homeNavEvents.value = HomeNavigation.OpenDescriptionDialpg(cake)
    }
}

sealed class HomeNavigation {
    data class OpenDescriptionDialpg(val cake: Cake) : HomeNavigation()
}
