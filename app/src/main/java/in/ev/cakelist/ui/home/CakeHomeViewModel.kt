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
    val observableList: LiveData<List<Cake>> = this.cakeList
    val showShimmerAnimation = MutableLiveData(true)
    val itemSelected: RecyclerviewItemSelected
        get() = this::cakeSelected
    private val characterLiveData: MutableLiveData<ViewState<List<Cake>>> = MutableLiveData()
    val stateNav: LiveData<ViewState<List<Cake>>> = characterLiveData

    init {
        getCakeList()
    }

    fun getCakeList() {
        viewModelScope.launch{
            getCakeListUsecase.execute().collect { response ->
                when (response) {
                    is Response.Loading -> {
                        adjustShimmerVisibility(response.loading)
                    }
                    is Response.ApiCallSuccess -> {
                        response.data?.let {
                            characterLiveData.value = ViewState.Success(it)
                            cakeList.apply {
                                value = it
                            }
                        }
                    }
                    is Response.ApiCallError -> {
                        characterLiveData.value = ViewState.Failure(response.error)
                    }
                }
            }
        }
    }


    private fun adjustShimmerVisibility(visibility: Boolean) {
        showShimmerAnimation.value = visibility
    }

    private fun cakeSelected(cake: Cake) {
        homeNavEvents.value = HomeNavigation.NavigateToDetail(cake)
    }
}

sealed class HomeNavigation {
    data class NavigateToDetail(val cake: Cake) : HomeNavigation()
    object OpenFilterDialog : HomeNavigation()
}
