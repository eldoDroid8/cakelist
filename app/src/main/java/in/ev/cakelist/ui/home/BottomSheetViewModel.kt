package `in`.ev.cakelist.ui.home

import `in`.ev.cakelist.domain.model.Cake
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class BottomSheetViewModel(cake: Cake, val callBack: () -> Unit) {
    val title: LiveData<String> = MutableLiveData(cake.title)
    val description: LiveData<String> = MutableLiveData(cake.desc)

    fun onOkClick() {
        callBack.invoke()
    }
}