package org.dufa.dufa9596

import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.location.LocationRequest
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint
import org.dufa.dufa9596.databinding.FragmentGetLocationBinding
@AndroidEntryPoint
class GetLocationFragment :BaseFragment<FragmentGetLocationBinding>() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback
    override fun getFragmentView(): Int {
        return R.layout.fragment_get_location
    }

    override fun configUi() {

        binding.getLocation.setOnClickListener {

        }
    }

    private fun checkLocation() {
        val manager =
            requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            showAlertLocation()
        }
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
    }

    private fun showAlertLocation() {
        val dialog = AlertDialog.Builder(requireActivity())
        dialog.setMessage("Your location settings is set to Off, Please enable location to use this application")
        dialog.setPositiveButton("Settings") { _, _ ->
            val myIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivity(myIntent)
        }
        dialog.setNegativeButton("Cancel") { _, _ ->
            requireActivity().finish()
        }
        dialog.setCancelable(false)
        dialog.show()
    }



    // Stop location updates
    private fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    // Stop receiving location update when activity not visible/foreground
    override fun onPause() {
        super.onPause()
        stopLocationUpdates()
    }

    // Start receiving location update when activity  visible/foreground
    override fun onResume() {
        super.onResume()

    }
}



