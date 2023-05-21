package com.marufalam.dufa

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.marufalam.dufa.data.models.map.MarkerData
import com.marufalam.dufa.databinding.FragmentMapsBinding
import java.util.*

class MapsFragment : BaseFragment<FragmentMapsBinding>(), OnMapReadyCallback {
    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    private val permissionId = 2
    private var mapFrag: SupportMapFragment? = null
    private var myGoogleMap: GoogleMap? = null
    private var lat=23.6850
    private var log=90.3563

    private val markerList = ArrayList<MarkerData>()


    override fun getFragmentView(): Int {
        return R.layout.fragment_maps
    }

    override fun configUi() {
        mapFrag = binding.map.getFragment()
        mapFrag?.getMapAsync(this)
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        getLocation()
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =
            requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }
    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                requireActivity(),
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                requireActivity(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }
    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ),
            permissionId
        )
    }
    @SuppressLint("MissingSuperCall")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == permissionId) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getLocation()
            }
        }
    }
    @SuppressLint("MissingPermission", "SetTextI18n")
    private fun getLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.lastLocation.addOnCompleteListener(requireActivity()) { task ->
                    val location: Location? = task.result
                    if (location != null) {
                        val geocoder = Geocoder(requireActivity(), Locale.getDefault())
                        //myGoogleMap!!.isMyLocationEnabled=true
                        val list: List<Address> =
                            geocoder.getFromLocation(location.latitude, location.longitude, 1) as List<Address>
                        lat = list[0].latitude
                        log = list[0].longitude

                        Log.i("TAG", "getLocation: ${list[0].latitude}\n${list[0].longitude}")
                    }
                }
            } else {
                Toast.makeText(requireActivity(), "Please turn on location", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermissions()
        }
    }



    override fun onMapReady(googleMap: GoogleMap) {
        myGoogleMap?.clear()
        myGoogleMap = googleMap
        val myLocation = LatLng(23.6850, 90.3563)
        markerList.add(MarkerData(22.8246, 91.1017,"Noakhali","sub-noakhali",R.drawable.occupation))
        markerList.add(MarkerData(23.4607, 91.1809,"Comilla","sub-farmGate",R.drawable.occupation))
        markerList.add(MarkerData(22.3569, 91.7832,"Chattogram","sub-Chattogram",R.drawable.occupation))
        val markerLayout = layoutInflater.inflate(R.layout.marker_layout, null, false)
        //val userImg = markerLayout.findViewById<ImageView>(R.id.userImg)
        val bitmap = Bitmap.createScaledBitmap(
            viewToBitmap(markerLayout)!!, 128,128,false)
        markerList.forEach{ markerData ->
            googleMap.addMarker(MarkerOptions()
                .position(LatLng(markerData.latitude, markerData.longitude))
                .anchor(0.5f, 0.5f)
                .title(markerData.title)
                .snippet(markerData.snippets)
                .icon(BitmapDescriptorFactory.fromBitmap(bitmap)))
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myLocation,9f))
            googleMap.uiSettings.isZoomControlsEnabled

        }

/*        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        val dhaka = LatLng(23.8103, 90.4125)
        val farmGate = LatLng(-23.7561, 90.3872)*/



       /* val bitmap = Bitmap.createScaledBitmap(
            viewToBitmap(markerLayout)!!, 128,128,false)
        val bitmap2 = Bitmap.createScaledBitmap(
            viewToBitmap(markerLayout)!!,128,128,false)
        val bitmap3 = Bitmap.createScaledBitmap(
            viewToBitmap(markerLayout)!!,128,128,false)

        val markerIcon = BitmapDescriptorFactory.fromBitmap(bitmap)
        myGoogleMap!!.addMarker(MarkerOptions().position(dhaka).icon(markerIcon))
        val markerIcon2 = BitmapDescriptorFactory.fromBitmap(bitmap2)
        myGoogleMap!!.addMarker(MarkerOptions().position(sydney).icon(markerIcon2))
        val markerIcon3 = BitmapDescriptorFactory.fromBitmap(bitmap3)
        myGoogleMap!!.addMarker(MarkerOptions().position(farmGate).icon(markerIcon3))
*/
/*




        val sydneyMarker = MarkerOptions()
            .position(sydney)
            .title("Marker in Sydney")
        val dhakaMarker = MarkerOptions()
            .position(dhaka)
            .title("Marker in Sydney")
        val farmGateMarker = MarkerOptions()
            .position(farmGate)
            .title("Marker in Sydney")
        //set custom icon
        sydneyMarker.icon(BitmapFromVector(requireActivity(), R.drawable.baseline_location_on_24))
        //add marker
        myGoogleMap!!.addMarker(sydneyMarker)
        myGoogleMap!!.addMarker(dhakaMarker)
        myGoogleMap!!.addMarker(farmGateMarker)
*/

       // myGoogleMap!!.moveCamera(CameraUpdateFactory.newLatLng(sydney))

    }

    private fun viewToBitmap(markerLayout: View?): Bitmap? {
        markerLayout!!.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
        val bitmap = Bitmap.createBitmap(
            markerLayout.measuredWidth,
            markerLayout.measuredHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        markerLayout.layout(0, 0, requireView().measuredWidth, requireView().measuredHeight)
        markerLayout.draw(canvas)
        return bitmap
    }

    private fun BitmapFromVector( vectorResId: Int): BitmapDescriptor? {
        //drawable generator
        val vectorDrawable: Drawable = ContextCompat.getDrawable(requireActivity(), vectorResId)!!
        vectorDrawable.setBounds(
            0,
            0,
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight
        )
        //bitmap generator
        val bitmap: Bitmap =
            Bitmap.createBitmap(
                vectorDrawable.intrinsicWidth,
                vectorDrawable.intrinsicHeight,
                Bitmap.Config.ARGB_8888
            )

        //canvas generate
        //pass bitmap in canvas constructor
        val canvas = Canvas(bitmap)
        //pass canvas in drawable
        vectorDrawable.draw(canvas)
        //return BitmapDescriptorFactory
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }
}