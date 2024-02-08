package com.marn.task.presentation.details

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.marn.task.R
import com.marn.task.databinding.FragmentMealDetailsBinding
import com.marn.task.domain.entity.Category
import com.marn.task.utils.ToastUtils.showToast
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
    val discoveryReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            when (intent.action) {
                BluetoothDevice.ACTION_FOUND -> {
                    // Device found
                    val device: BluetoothDevice? = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                    // Check if this is the device you want to connect to
                }
            }
        }
    }

    override fun onShare() {
        val bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()
        if (bluetoothAdapter == null) {
            // Device doesn't support Bluetooth
            requireContext().showToast(getString(R.string.device_doesn_t_support_bluetooth))
        } else {
            if (!bluetoothAdapter.isEnabled) {
                // Bluetooth is not enabled, prompt the user to enable it
                requireContext().showToast(getString(R.string.bluetooth_is_not_enabled))

            } else {
                // Bluetooth is enabled, proceed with further steps

            }
        }
    }

    override fun onBack() {
        //back press from stack of navigation(route)
        findNavController().popBackStack()
    }

}