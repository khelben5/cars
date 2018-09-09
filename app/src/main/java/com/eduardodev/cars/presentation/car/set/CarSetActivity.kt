package com.eduardodev.cars.presentation.car.set

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.eduardodev.cars.R
import com.eduardodev.cars.presentation.car.list.CarListFragment
import com.eduardodev.cars.presentation.car.map.CarMapFragment
import com.eduardodev.cars.presentation.model.*
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import kotlinx.android.synthetic.main.activity_car_set.*
import org.jetbrains.anko.getStackTraceString
import org.jetbrains.anko.longToast

private const val TAG_LIST_FRAGMENT = "listFragment"
private const val TAG_MAP_FRAGMENT = "mapFragment"
private const val STATE_SELECTED_ITEM_ID = "selectedItemId"
private const val REQUEST_CODE_API_ERROR_DIALOG = 1

class CarSetActivity : AppCompatActivity() {

    private val model by lazy { ViewModelProviders.of(this)[CarSetViewModel::class.java] }
    private val listFragment: Fragment? by lazy {
        supportFragmentManager.findFragmentByTag(TAG_LIST_FRAGMENT)
    }
    private val mapFragment: Fragment? by lazy {
        supportFragmentManager.findFragmentByTag(TAG_MAP_FRAGMENT)
    }
    private var selectedItemId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car_set)
        savedInstanceState?.let { selectedItemId = it.getInt(STATE_SELECTED_ITEM_ID) }
        model.cars.observe(this, Observer { resource -> resource?.let { updateUI(it) } })
    }

    override fun onResume() {
        super.onResume()
        carSetBottomNavigation.setOnNavigationItemSelectedListener {
            onNavigationItemSelected(it.itemId)
        }
        checkForGooglePlayServices()
    }

    override fun onPause() {
        super.onPause()
        carSetBottomNavigation.setOnNavigationItemSelectedListener(null)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(STATE_SELECTED_ITEM_ID, carSetBottomNavigation.selectedItemId)
    }

    private fun updateUI(resource: Resource) {
        when (resource) {
            is Success -> onSuccessResource()
            is Failure -> onFailureResource(resource)
            Progress -> onProgressResource()
            ConnectionFailure -> onConnectionFailureResource()
        }
    }

    private fun onSuccessResource() {
        showProgress(false)
        carSetBottomNavigation.visibility = View.VISIBLE
        carSetBottomNavigation.selectedItemId = selectedItemId ?: R.id.itemList
    }

    private fun onFailureResource(failure: Failure) {
        showProgress(false)
        Log.e(javaClass.simpleName, failure.exception.getStackTraceString())
        longToast(R.string.failure_generic)
    }

    private fun onProgressResource() {
        showProgress(true)
    }

    private fun onConnectionFailureResource() {
        showProgress(false)
        longToast(R.string.failure_connection)
    }

    private fun showProgress(inProgress: Boolean) {
        carSetProgress.visibility = if (inProgress) View.VISIBLE else View.GONE
    }

    private fun onNavigationItemSelected(itemId: Int) = when (itemId) {
        R.id.itemList -> {
            onListSelected()
            true
        }
        R.id.itemMap -> {
            onMapSelected()
            true
        }
        else -> {
            Log.e(javaClass.simpleName, "unknown selection")
            false
        }
    }

    private fun onListSelected() {
        addListFragmentIfNeeded()
    }

    private fun onMapSelected() {
        addMapFragmentIfNeeded()
    }

    private fun addListFragmentIfNeeded() {
        if (listFragment == null) addListFragment()
    }

    private fun addMapFragmentIfNeeded() {
        if (mapFragment == null) addMapFragment()
    }

    private fun addListFragment() {
        supportFragmentManager.beginTransaction()
                .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                .replace(R.id.carSetFragmentContainer, createListFragment(), TAG_LIST_FRAGMENT)
                .commit()
    }

    private fun addMapFragment() {
        supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left)
                .replace(R.id.carSetFragmentContainer, createMapFragment(), TAG_MAP_FRAGMENT)
                .commit()
    }

    private fun createListFragment() = CarListFragment.newInstance()

    private fun createMapFragment() = CarMapFragment.newInstance()

    private fun checkForGooglePlayServices() {
        val googleApiAvailability = GoogleApiAvailability.getInstance()
        val availability = googleApiAvailability.isGooglePlayServicesAvailable(this)
        if (availability == ConnectionResult.SUCCESS) return
        googleApiAvailability
                .getErrorDialog(this, availability, REQUEST_CODE_API_ERROR_DIALOG)
                .show()
    }
}