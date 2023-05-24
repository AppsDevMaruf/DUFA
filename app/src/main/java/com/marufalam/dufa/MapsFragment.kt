package com.marufalam.dufa


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import coil.load
import coil.transform.CircleCropTransformation
import com.bumptech.glide.Glide
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*
import com.marufalam.dufa.data.models.locations.ResponseUserLocation
import com.marufalam.dufa.databinding.FragmentMapsBinding
import com.marufalam.dufa.utils.*
import com.marufalam.dufa.utils.Constants.IMG_PREFIX
import com.marufalam.dufa.viewmodel.DashboardViewModel
import dagger.hilt.android.AndroidEntryPoint
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.URL
import java.util.*


@AndroidEntryPoint
class MapsFragment : BaseFragment<FragmentMapsBinding>(), OnMapReadyCallback {
    private val mapsViewModel by viewModels<DashboardViewModel>()
    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    private val permissionId = 2
    private var mapFrag: SupportMapFragment? = null
    private var myGoogleMap: GoogleMap? = null
    private var lat = 23.6850
    private var log = 90.3563
    private var markerList = ArrayList<ResponseUserLocation.AllMember>()


    override fun getFragmentView(): Int {
        return R.layout.fragment_maps
    }

    override fun configUi() {
        mapsViewModel.userLocationsVM()
        MapsInitializer.initialize(requireActivity(), MapsInitializer.Renderer.LATEST) {
            mapFrag = binding.map.getFragment()
            mapFrag?.getMapAsync(this)
        }
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
                            geocoder.getFromLocation(
                                location.latitude,
                                location.longitude,
                                1
                            ) as List<Address>
                        lat = list[0].latitude
                        log = list[0].longitude
                        Log.i("TAG", "getLocation: ${list[0].latitude}\n${list[0].longitude}")
                    }
                }
            } else {
                Toast.makeText(requireActivity(), "Please turn on location", Toast.LENGTH_LONG)
                    .show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermissions()
        }
    }


    @SuppressLint("PotentialBehaviorOverride")
    override fun onMapReady(googleMap: GoogleMap) {

        mapsViewModel.userLocationsVMLD.observe(this) {
            binding.progressBar.gone()
            when (it) {
                is NetworkResult.Success -> {
                    val userInfo = it.data?.user
                    markerList = it.data?.allMembers as ArrayList<ResponseUserLocation.AllMember>
                    myGoogleMap = googleMap
                    markerList.forEach { markerData ->
                        markerData.latitude?.let { it1 ->
                            markerData.longitude?.let { it2 ->
                                LatLng(
                                    it1,
                                    it2
                                )
                            }
                        }
                            ?.let { it2 ->
                                if (Build.VERSION.SDK_INT > 9) {
                                    val policy = ThreadPolicy.Builder().permitAll().build()
                                    StrictMode.setThreadPolicy(policy)
                                }
                                if (markerData.imagePath != null && markerData.imagePath != "") {
                                    val profileImg = IMG_PREFIX + markerData.imagePath
                                    googleMap.addMarker(
                                        MarkerOptions()
                                            .position(it2)
                                            .anchor(0.5f, 0.5f)
                                            .title(markerData.name)
                                            .snippet(markerData.phone)
                                            .icon(BitmapDescriptorFactory.fromBitmap(Glide.with(requireActivity()).asBitmap().load(profileImg).submit().get()))
                                            )
                                }

                            }
                        userInfo?.latitude?.let { latitude -> lat = latitude }
                        userInfo?.longitude?.let { longitude -> log = longitude }
                        googleMap.animateCamera(
                            CameraUpdateFactory.newLatLngZoom(
                                LatLng(lat, log),
                                10f
                            )
                        )

                        googleMap.setInfoWindowAdapter(object : GoogleMap.InfoWindowAdapter {
                            override fun getInfoWindow(marker: Marker): View? {
                                marker.title = marker.title
                                marker.snippet = marker.snippet
                                //todo set Icon
                                return null
                            }

                            @SuppressLint("MissingInflatedId")
                            override fun getInfoContents(marker: Marker): View? {
                                val v: View =
                                    layoutInflater.inflate(R.layout.custom_info_window, null, false)
                                val name = v.findViewById<TextView>(R.id.title)
                                val phone = v.findViewById<TextView>(R.id.snippet)
                                val profile =
                                    v.findViewById<CircleImageView>(R.id.mapUserProfilePic)
                                name.text = marker.title
                                phone.text = marker.snippet
                                /*  if (markerData.imagePath != null && markerData.imagePath != "") {
                                      val profileUrl = Constants.IMG_PREFIX + markerData.imagePath
                                      profile.load(profileUrl) {
                                          crossfade(true)
                                          placeholder(R.drawable.avatar_placeholder)
                                          transformations(CircleCropTransformation())
                                      }
                                  }*/

                                return v
                            }
                        })

                        googleMap.setOnInfoWindowClickListener { info ->
                            val dialIntent = Intent(Intent.ACTION_DIAL)
                            dialIntent.data = Uri.parse("tel:" + info.snippet)
                            requireActivity().startActivity(dialIntent)
                        }
                        googleMap.addCircle(
                            CircleOptions()
                                .center(LatLng(lat, log))
                                .radius(10000.0)
                                .strokeWidth(8F)
                                .strokeColor(Color.BLUE)
                                .fillColor(Color.TRANSPARENT)
                        )
                        googleMap.uiSettings.isZoomControlsEnabled
                        googleMap.uiSettings.isZoomGesturesEnabled


                    }

                }
                is NetworkResult.Error -> {

                }
                is NetworkResult.Loading -> {
                    binding.progressBar.show()
                }
            }
        }


    }

    private fun bitmapDescriptorFromUrl(imgUrl: String): Bitmap {
        val url = URL(imgUrl)
        return BitmapFactory.decodeStream(url.openConnection().getInputStream())
    }


}