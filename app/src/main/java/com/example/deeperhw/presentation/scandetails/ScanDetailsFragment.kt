package com.example.deeperhw.presentation.scandetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.deeperhw.R
import com.example.deeperhw.databinding.FragmentScanDetailsBinding
import com.example.deeperhw.presentation.util.extensions.swapCoordinates
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.data.geojson.GeoJsonLayer
import com.google.maps.android.data.geojson.GeoJsonPolygonStyle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.json.JSONObject

private const val PROPERTY_KEY_DEPTH = "depth"

@AndroidEntryPoint
class ScanDetailsFragment : Fragment(R.layout.fragment_scan_details), OnMapReadyCallback {

    private lateinit var binding: FragmentScanDetailsBinding
    private lateinit var googleMap: GoogleMap
    private val args: ScanDetailsFragmentArgs by navArgs()
    private val viewModel: ScanDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentScanDetailsBinding.inflate(inflater, container, false).also {
        binding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupEvents()
        prepareMap()
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        viewModel.load(args.scanId)
    }

    private fun prepareMap() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun setupEvents() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.scanDetails.collectLatest {
                        buildMap(it)
                    }
                }

                launch {
                    viewModel.onError.collectLatest {
                        Toast.makeText(requireContext(), R.string.error, Toast.LENGTH_LONG).show()
                        findNavController().popBackStack()
                    }
                }

                launch {
                    viewModel.isLoading.collectLatest {
                        binding.loadingIndicator.isVisible = it
                    }
                }
            }
        }
    }

    private fun buildMap(scanDetails: JSONObject) {
        if (!this::googleMap.isInitialized) return

        with(GeoJsonLayer(googleMap, scanDetails)) {
            modifyPolygons(this)
            moveCamera(this)
            this.addLayerToMap()
        }
    }

    private fun modifyPolygons(layer: GeoJsonLayer) {
        layer.features.forEach {
            val depth = it.getProperty(PROPERTY_KEY_DEPTH)
            it.polygonStyle = GeoJsonPolygonStyle().apply {
                this.fillColor = getColorByDepth(depth)
                this.strokeColor = getColorByDepth(depth)
                this.strokeWidth = 1F
            }
        }
    }

    private fun moveCamera(layer: GeoJsonLayer) {
        layer.map.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                LatLngBounds(
                    layer.boundingBox.southwest.swapCoordinates(),
                    layer.boundingBox.northeast.swapCoordinates()
                ).center, 14f
            )
        )
    }

    @ColorInt
    private fun getColorByDepth(depth: String): Int = requireContext().getColor(
        with (depth.toDouble()) {
            when {
                this < 1 -> R.color.red
                this < 2 -> R.color.orange
                this < 4 -> R.color.yellow
                this < 6 -> R.color.green
                else -> R.color.blue
            }
        }
    )
}
