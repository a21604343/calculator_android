package com.example.myapplication

import android.graphics.drawable.BitmapDrawable
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.example.myapplication.databinding.FragmentMapBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.LocationSource
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng

import java.util.*
import javax.xml.transform.Source


class MapFragment : Fragment(), OnLocationChangedListener {

    private lateinit var binding: FragmentMapBinding
    private lateinit var viewModel : CalculatorViewModel

    private lateinit var geocoder : Geocoder
    private var map: GoogleMap? = null



    private val TAG = MapFragment::class.java.simpleName


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.i(TAG, "create view")
        val view = inflater.inflate(R.layout.fragment_map,container,false)

        binding = FragmentMapBinding.bind(view)

        geocoder = Geocoder(context,Locale.getDefault())

        binding.map.onCreate(savedInstanceState)
        binding.map.getMapAsync {
                map -> this.map = map
            FusedLocation.registerListener(this)
        }

        viewModel = ViewModelProvider(activity as ViewModelStoreOwner).get(CalculatorViewModel::class.java)
        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Mapa Incêndios"

        return binding.root
    }

    override fun onStart(){
        super.onStart()
        Log.i(TAG, "start view")

        //val bitmap = (resources.getDrawable(R.drawable.mapa_portugal) as BitmapDrawable).bitmap
        //listaHistorico = viewModel.getList()
        // binding.mapaTugz.setImageBitmap(bitmap)

    }

    override fun onLocationChanged(latitude: Double, longitude: Double) {
        placeCamera(latitude,longitude)
        placeCityName(latitude,longitude)
    }

    override fun onResume() {
        super.onResume()
        binding.map.onResume()
    }

    private fun placeCamera(latitude : Double, longitude : Double){
        var cameraPosition = CameraPosition.Builder()
            .target(LatLng(latitude,longitude))
            .zoom(12f)
            .build()
        map?.animateCamera(
            CameraUpdateFactory.newCameraPosition(cameraPosition)
        )
    }

    private fun placeCityName(latitude: Double, longitude: Double){
        val address = geocoder.getFromLocation(latitude,longitude,5)
        val location = address.first{
            it.locality != null && it.locality.isNotEmpty()
        }
        binding.tvCityName.text = location.locality
    }

    override fun onDestroy() {
        super.onDestroy()
        FusedLocation.unregisterListener(this)
    }


}