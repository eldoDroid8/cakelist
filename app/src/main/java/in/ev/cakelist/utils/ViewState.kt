package `in`.ev.cakelist.utils

import `in`.ev.cakelist.domain.model.Error


sealed class ViewState<out T: Any> {
    data class Failure(val throwable: Error) : ViewState<Nothing>()
}



