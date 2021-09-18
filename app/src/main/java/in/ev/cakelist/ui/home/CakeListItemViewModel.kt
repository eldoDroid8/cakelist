package `in`.ev.cakelist.ui.home

import `in`.ev.cakelist.domain.model.Cake
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class CakeListItemViewModel(val cake: Cake, private val itemSelected:
RecyclerviewItemSelected?) {
    val imageUrl: LiveData<String> = MutableLiveData(cake.image)
    val label: LiveData<String> = MutableLiveData(cake.title)

    fun onCakeSelected() {
        itemSelected?.invoke(cake)
    }

}