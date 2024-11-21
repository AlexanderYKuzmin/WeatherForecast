package com.kuzmin.weatherforecast.ui

import android.annotation.SuppressLint
import android.location.Location
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.kuzmin.weatherforecast.R
import com.kuzmin.weatherforecast.R.string.failed_get_location
import com.kuzmin.weatherforecast.R.string.location_is_not_available
import com.kuzmin.weatherforecast.databinding.ActivityMainBinding
import com.kuzmin.weatherforecast.domain.model.forecast.Coord
import com.kuzmin.weatherforecast.domain.model.servicable.ForecastOperationResult
import com.kuzmin.weatherforecast.domain.model.servicable.ForecastOperationResult.Error
import com.kuzmin.weatherforecast.domain.model.servicable.ForecastOperationResult.GetForecastDataFromServerSuccess
import com.kuzmin.weatherforecast.domain.model.servicable.ForecastOperationResult.GetLocationSuccess
import com.kuzmin.weatherforecast.domain.model.servicable.ForecastOperationResult.Loading
import com.kuzmin.weatherforecast.domain.model.servicable.ForecastOperationResult.LocationSavedDatastoreSuccess
import com.kuzmin.weatherforecast.extensions.checkNetworkConnection
import com.kuzmin.weatherforecast.extensions.hasRequiredRuntimePermissions
import com.kuzmin.weatherforecast.extensions.requestLocationPermission
import com.kuzmin.weatherforecast.extensions.showExceptionMessage
import com.kuzmin.weatherforecast.extensions.showToast
import com.kuzmin.weatherforecast.ui.fragments.SettingsFragment
import com.kuzmin.weatherforecast.ui.viewmodels.MainActivityViewModel
import com.kuzmin.weatherforecast.ui.viewmodels.MainActivityViewModel.AppState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), SettingsFragment.OnSettingsButtonClickListener {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private val mainActivityViewModel: MainActivityViewModel by viewModels()

    private val navController: NavController by lazy {
        findNavController(R.id.fragment_container_view_tag)
    }

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private lateinit var  connectivityManager: ConnectivityManager

    //private val networkAvailability = MutableLiveData(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

        setSupportActionBar(binding.toolbar)

        connectivityManager = getSystemService(ConnectivityManager::class.java) as ConnectivityManager
        //connectivityManager.requestNetwork(getNetworkRequest(), networkCallback)

        checkNetworkConnection(connectivityManager)

        val startFragmentBundle = bundleOf()
        navController.setGraph(navController.graph, startFragmentBundle)

        setupSearchView()

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        checkLocationPermissionAndGetLocation()

        launchForecastPagerFragment()

        mainActivityViewModel.appState.observe(this) { state ->
            if (state.title != getString(R.string.app_name)) {
                mainActivityViewModel.getLocationByCityName(state.title)
            }
            updateTitle(state)
        }

        mainActivityViewModel.forecastOperationResult.observe(this) { result ->
            handleForecastOperationResult(result)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.mm_search -> {
                if (binding.searchView.visibility == View.GONE) {
                    binding.searchView.visibility = View.VISIBLE
                    binding.searchView.isIconified = false
                } else {
                    binding.searchView.visibility = View.GONE
                }
            }
            R.id.mm_properties -> {
                launchForecastSettingsFragment()
            }
        }
        return true
    }

    private fun updateTitle(state: AppState) {
        val title = binding.toolbar.getChildAt(0) as TextView
        if (state.title.isEmpty()) {
            title.text = getString(R.string.app_name)
        } else {
            title.text = state.title
        }
    }

    private fun handleForecastOperationResult(result: ForecastOperationResult) {
        binding.pbLoading.visibility = View.GONE

        when (result) {
            is Error -> {
                showExceptionMessage(
                    result.handleError(this.resources),
                    result.throwable
                )
            }

            is GetForecastDataFromServerSuccess -> {
                if (navController.currentDestination?.id == R.id.forecastStartFragment) {
                    launchForecastPagerFragment()
                }
            }

            is GetLocationSuccess -> {
                mainActivityViewModel.storeLocationData(
                    result.location,
                    mainActivityViewModel.appState.value?.title ?: getString(R.string.app_name)
                    )
                mainActivityViewModel.loadDataFromNetwork(result.location)
            }

            is LocationSavedDatastoreSuccess -> {
                mainActivityViewModel.loadDataFromNetwork(result.location)
            }

            is Loading -> {
                binding.pbLoading.visibility = View.VISIBLE
            }
        }
    }

    private fun setupSearchView() {
        with(binding.searchView) {
            isSubmitButtonEnabled = true
            queryHint = getString(R.string.search_hint)

            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    mainActivityViewModel.handleSearchQuery(query)
                    visibility = View.GONE
                    return true
                }
                override fun onQueryTextChange(newText: String?): Boolean { return false }
            })

            setOnCloseListener {
                visibility = View.GONE
                true
            }
        }
    }

    private fun launchForecastPagerFragment() {
        navController.navigate(R.id.forecastPagerFragment)
    }

    @SuppressLint("RestrictedApi")
    private fun launchForecastSettingsFragment() {
        if (navController.currentDestination?.id != R.id.forecastStartFragment) {
            navController.popBackStack(R.id.forecastStartFragment, false)
        }

        navController.navigate(R.id.settingsFragment)
    }

    override fun onSettingsFinished() {
        navController.navigate(R.id.forecastPagerFragment)
    }

    private fun checkLocationPermissionAndGetLocation() {
        if (hasRequiredRuntimePermissions()) {
            val locationData = getLastKnownLocation()
            if (locationData != null) {
                mainActivityViewModel.storeLocationData(
                    locationData,
                    mainActivityViewModel.appState.value?.title ?: getString(R.string.app_name)
                )
            } else mainActivityViewModel.loadDataFromStorage()
        } else {
            requestLocationPermission()
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLastKnownLocation(): Coord? {
        var locationData: Coord? = null
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                location?.let {
                    locationData = Coord(it.latitude, it.longitude)
                } ?: showToast(getString(location_is_not_available))
            }
            .addOnFailureListener {
                showToast(getString(failed_get_location))
            }
        return locationData
    }
}