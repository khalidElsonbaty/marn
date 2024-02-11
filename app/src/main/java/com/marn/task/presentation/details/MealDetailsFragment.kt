package com.marn.task.presentation.details


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.marn.task.R
import com.marn.task.databinding.FragmentMealDetailsBinding
import com.marn.task.domain.entity.Category
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MealDetailsFragment : Fragment(), ClickHandler {

    //use data binding for attach xml view with fragment in kotlin
    private lateinit var binding: FragmentMealDetailsBinding
    private val viewModel: MealDetailsViewModel by viewModels()
    private var category : Category? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            //receive bundle from list fragment
            category = MealDetailsFragmentArgs.fromBundle(requireArguments()).category
        } catch (_: Exception) {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMealDetailsBinding.inflate(inflater, container, false)
        binding.handler = this
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        viewModel._categoryDetails.value = category
        return binding.root
    }


    override fun onShare() {
            binding.root.findNavController().navigate(R.id.shareFragment, Bundle(1).apply {
                putParcelable("category", viewModel.categoryDetails.value) })

    }

    override fun onBack() {
        //back press from stack of navigation(route)
        findNavController().popBackStack()
    }

}