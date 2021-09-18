package `in`.ev.cakelist.ui.home

import `in`.ev.cakelist.R
import `in`.ev.cakelist.domain.model.Cake
import `in`.ev.cakelist.ui.widgets.recyclerview.BaseRecyclerAdapter
import android.widget.Filter
import android.widget.Filterable

class CakeListAdapter : BaseRecyclerAdapter<Cake, CakeListItemViewModel>(
    layoutId = R.layout.list_item_cake
) {
    private var itemSelected: RecyclerviewItemSelected ?= null
    override fun getItemCount(): Int {
        return getListItems().size
    }

    override fun getObjForPosition(position: Int): CakeListItemViewModel {
        return CakeListItemViewModel(getListItems()[position], itemSelected)
    }

    internal  fun setItemClickListener(clickListener: RecyclerviewItemSelected) {
        itemSelected = clickListener
    }

}

typealias  RecyclerviewItemSelected = (Cake) -> Unit