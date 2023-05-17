package com.marufalam.dufa

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.getSystemService
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.marufalam.dufa.databinding.FragmentMapsBinding

class MapsFragment : BaseFragment<FragmentMapsBinding>(), OnMapReadyCallback {
    private var mapFrag: SupportMapFragment? = null
    private var myGoogleMap: GoogleMap? = null


    override fun getFragmentView(): Int {
        return R.layout.fragment_maps
    }

    override fun configUi() {
        mapFrag = binding.map.getFragment()
        mapFrag?.getMapAsync(this)
    }



    override fun onMapReady(googleMap: GoogleMap) {
        myGoogleMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        val dhaka = LatLng(23.8103, 90.4125)
        val farmGate = LatLng(-23.7561, 90.3872)

        val markerLayout = layoutInflater.inflate(R.layout.marker_layout, null, false)
        val userImg = markerLayout.findViewById<ImageView>(R.id.userImg)

        val bitmap = Bitmap.createScaledBitmap(
            viewToBitmap(markerLayout)!!,
            128,
            128,
            false
        )
        val bitmap2 = Bitmap.createScaledBitmap(
            viewToBitmap(markerLayout)!!,
            128,
            128,
            false
        )
        val bitmap3 = Bitmap.createScaledBitmap(
            viewToBitmap(markerLayout)!!,
            128,
            128,
            false
        )
        val markerIcon = BitmapDescriptorFactory.fromBitmap(bitmap)
        myGoogleMap!!.addMarker(MarkerOptions().position(dhaka).icon(markerIcon))
        val markerIcon2 = BitmapDescriptorFactory.fromBitmap(bitmap2)
        myGoogleMap!!.addMarker(MarkerOptions().position(sydney).icon(markerIcon2))
        val markerIcon3 = BitmapDescriptorFactory.fromBitmap(bitmap3)
        myGoogleMap!!.addMarker(MarkerOptions().position(farmGate).icon(markerIcon3))

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

    private fun BitmapFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
        //drawable generator
        val vectorDrawable: Drawable = ContextCompat.getDrawable(context, vectorResId)!!
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