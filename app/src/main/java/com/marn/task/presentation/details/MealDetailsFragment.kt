package com.marn.task.presentation.details

import android.Manifest
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.marn.task.R
import com.marn.task.databinding.FragmentMealDetailsBinding
import com.marn.task.domain.entity.Category
import com.marn.task.utils.ToastUtils.showToast
import dagger.hilt.android.AndroidEntryPoint
import java.io.InputStream
import java.io.OutputStream
import java.util.UUID

@AndroidEntryPoint
class MealDetailsFragment : Fragment(), ClickHandler {

    //use data binding for attach xml view with fragment in kotlin
    private lateinit var binding: FragmentMealDetailsBinding
    private val viewModel: MealDetailsViewModel by viewModels()
    private var category : Category? = null
    var bluetoothAdapter: BluetoothAdapter?=null
    var socket: BluetoothSocket? = null
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
        setBroadCastReceiver()
        return binding.root
    }

    private fun setBroadCastReceiver() {
        val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
        activity?.registerReceiver(discoveryReceiver, filter)
    }

    private val discoveryReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            when (intent.action) {
                BluetoothDevice.ACTION_FOUND -> {
                    // Device found
                    val device: BluetoothDevice? = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                    // Check if this is the device you want to connect to
                    sendDataFromSender(device)

                }
            }
        }
    }
    private val BLUETOOTH_PERMISSION_REQUEST_CODE = 1001  // Define a request code for the permission request
    override fun onShare() {
        //Check Bluetooth Availability
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        checkBluetoothPermission()
    }

    override fun onBack() {
        //back press from stack of navigation(route)
        findNavController().popBackStack()
    }
    // Define an ActivityResultLauncher in your activity or fragment
    private val requestDiscoverableLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // Handle the result here
            val data: Intent? = result.data
            // Process the data as needed
        } else {
            // Handle the case where the activity result is not OK
        }
    }

    private fun sendDataFromSender(device: BluetoothDevice?) {

        val uuid: UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB") // Standard SerialPortService ID
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.BLUETOOTH_CONNECT
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.BLUETOOTH_CONNECT,), BLUETOOTH_PERMISSION_REQUEST_CODE)

            return
        } else {
            // the device you want to connect to
            socket = device?.createRfcommSocketToServiceRecord(uuid)
            socket?.connect()
            sendData()
            receiveData()
        }

    }

    private fun receiveData() {
        val inputStream: InputStream = socket?.inputStream!!
        val buffer = ByteArray(1024)
        val bytesRead: Int = inputStream.read(buffer)
        val message = String(buffer, 0, bytesRead)
        requireContext().showToast(message)
    }

    private fun sendData() {
        val outputStream: OutputStream? = socket?.outputStream
        outputStream?.write(viewModel.categoryDetails.value?.strCategory?.toByteArray())

    }

    val DISCOVERABLE_DURATION = 300 // 300 seconds (5 minutes)
    private fun setDiscoveringDevices() {
        val discoverableIntent = Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE)
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, DISCOVERABLE_DURATION)
         // Launch the discoverable activity using the ActivityResultLauncher
        requestDiscoverableLauncher.launch(discoverableIntent)
    }


    // Check if the app has Bluetooth permissions
    private fun checkBluetoothPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted, request the permission
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.BLUETOOTH,android.Manifest.permission.BLUETOOTH_SCAN), BLUETOOTH_PERMISSION_REQUEST_CODE)
        } else {
            // Permission has already been granted, proceed with Bluetooth operations
            // Your Bluetooth-related code here
            if (bluetoothAdapter == null) {
                // Device doesn't support Bluetooth
                requireContext().showToast(getString(R.string.device_doesn_t_support_bluetooth))
            } else {
                if (!bluetoothAdapter?.isEnabled!!) {
                    // Bluetooth is not enabled, prompt the user to enable it
                    requireContext().showToast(getString(R.string.bluetooth_is_not_enabled))

                } else {
                    // Bluetooth is enabled, proceed with further steps
                    bluetoothAdapter?.startDiscovery()
                    setDiscoveringDevices()
                }
            }

        }
    }
    // Handle the result of the permission request
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            BLUETOOTH_PERMISSION_REQUEST_CODE -> {
                // If request is cancelled, the result arrays are empty
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission granted, proceed with Bluetooth operations
                    // Your Bluetooth-related code here

                } else {
                    // Permission denied, handle the scenario where Bluetooth functionality is restricted
                    // Notify the user or take appropriate action
                }
                return
            }
        }
    }

    override fun onDestroyView() {
        // Unregister the BroadcastReceiver to avoid memory leaks
        activity?.unregisterReceiver(discoveryReceiver)
        socket?.close()
        super.onDestroyView()
    }
}