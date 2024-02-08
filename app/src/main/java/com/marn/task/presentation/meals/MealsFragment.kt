package com.marn.task.presentation.meals

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.marn.task.R
import com.marn.task.databinding.FragmentMealsBinding
import com.marn.task.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class MealsFragment : Fragment() {

    //use data binding for attach xml view with fragment in kotlin
    private lateinit var binding: FragmentMealsBinding
    private val viewModel: MealsViewModel by viewModels()
    private var mealsAdapter: MealsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMealsBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //init methods for setup view and retrieve data from server
        setUi()
        assignObservable()
        callList()
    }


    private fun setUi() {
        //adapter initialization
        mealsAdapter = MealsAdapter()
        val linearLayoutManager = LinearLayoutManager(activity)
        binding.rvCategory.apply {
            layoutManager = linearLayoutManager
            setItemViewCacheSize(100)
            adapter = mealsAdapter
        }
    }

    private fun assignObservable() {
        //assign observable from service response
        lifecycleScope.launch {
            viewModel.categories.collect {
                mealsAdapter?.submitList(it?.categories)
            }
        }

    }

    private fun callList() {
        //call api service
        viewModel.getMealsCategories()
    }
}