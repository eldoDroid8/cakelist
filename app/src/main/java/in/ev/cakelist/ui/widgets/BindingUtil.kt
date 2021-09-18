package `in`.ev.cakelist.ui.widgets

import `in`.ev.cakelist.domain.model.Cake
import `in`.ev.cakelist.ui.home.CakeListAdapter
import `in`.ev.cakelist.ui.home.RecyclerviewItemSelected
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView


@BindingAdapter("homeAdapter","itemClickListener", requireAll = false)
fun adddCakesToList(
    recyclerView: RecyclerView,
    homeData: MutableList<Cake>?,
    clickListener: RecyclerviewItemSelected
) {
    try {
        homeData?.let {
            val adapter =
                    CakeListAdapter()
            recyclerView.adapter = adapter
            adapter.setItemClickListener(clickListener)
            adapter.addRecylerViewListData(it)
        }
    } catch (e: ClassCastException) {
        e.printStackTrace()
    }
}

