package `in`.ev.cakelist.ui.home

import `in`.ev.cakelist.BR
import `in`.ev.cakelist.databinding.FragmentCakeHomeBinding
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CakeHomeFragment : Fragment() {
    private val cakeHomeViewModel:CakeHomeViewModel by viewModels()

    private lateinit var homeLayoutbinding: FragmentCakeHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeLayoutbinding = FragmentCakeHomeBinding.inflate(inflater, container, false)
        homeLayoutbinding.lifecycleOwner = viewLifecycleOwner
        return homeLayoutbinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initDataBinding()
        initRecycerView()
    }

    private fun initRecycerView() {
        homeLayoutbinding.rvPosts.layoutManager = GridLayoutManager(context, 1)
    }


    private fun initDataBinding() {
        homeLayoutbinding.setVariable(BR.viewModel,cakeHomeViewModel)
        homeLayoutbinding.executePendingBindings()
    }

}