package `in`.ev.cakelist.ui.home

import `in`.ev.cakelist.BR
import `in`.ev.cakelist.R
import `in`.ev.cakelist.databinding.BottomSheetDescriptionBinding
import `in`.ev.cakelist.databinding.FragmentCakeHomeBinding
import `in`.ev.cakelist.domain.model.Cake
import `in`.ev.cakelist.ui.MainActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CakeHomeFragment : Fragment() {
    private val cakeHomeViewModel:CakeHomeViewModel by viewModels()

    private lateinit var homeLayoutbinding: FragmentCakeHomeBinding
    private lateinit var bottomSheetDialog: BottomSheetDialog
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
        observeData()
    }

    private fun initRecycerView() {
        homeLayoutbinding.rvPosts.layoutManager = GridLayoutManager(context, 1)
    }


    private fun initDataBinding() {
        homeLayoutbinding.setVariable(BR.viewModel,cakeHomeViewModel)
        homeLayoutbinding.executePendingBindings()
    }

    private fun observeData() {
        cakeHomeViewModel.stateHomeEvents.observe(viewLifecycleOwner, {
            when (it) {
                is HomeNavigation.openDescriptionDialpg -> {
                        openDescriptionBottomSheet(it.cake)
                }
            }
        })
    }

    private fun openDescriptionBottomSheet(cake: Cake) {
        activity?.let {
            bottomSheetDialog = BottomSheetDialog(it)
            val bindingSheet = DataBindingUtil.inflate<BottomSheetDescriptionBinding>(
                layoutInflater,
                R.layout.bottom_sheet_description,
                null,
                false)
            bottomSheetDialog.setContentView(bindingSheet.root)
            val bottomSheetViewModel = BottomSheetViewModel(cake, ::onOkClick)
            bindingSheet.setVariable(BR.bottomSheetViewModel, bottomSheetViewModel )
            bottomSheetDialog.show()
            }
        }
     private fun onOkClick() {
        bottomSheetDialog.dismiss()
    }
}