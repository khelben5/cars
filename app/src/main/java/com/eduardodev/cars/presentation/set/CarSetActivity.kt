package com.eduardodev.cars.presentation.set

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.eduardodev.cars.R
import com.eduardodev.cars.presentation.list.CarListFragment
import com.eduardodev.cars.presentation.map.CarMapFragment
import com.eduardodev.cars.presentation.model.*
import kotlinx.android.synthetic.main.activity_car_set.*
import org.jetbrains.anko.getStackTraceString
import org.jetbrains.anko.longToast

private const val TAG_LIST_FRAGMENT = "listFragment"
private const val TAG_MAP_FRAGMENT = "mapFragment"
private const val STATE_SELECTED_ITEM_ID = "selectedItemId"

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
        model.getCars().observe(this, Observer { resource -> resource?.let { updateUI(it) } })
    }

    override fun onResume() {
        super.onResume()
        carSetBottomNavigation.setOnNavigationItemSelectedListener {
            onNavigationItemSelected(it.itemId)
        }
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
}