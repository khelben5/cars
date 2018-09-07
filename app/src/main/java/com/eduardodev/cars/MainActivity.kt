package com.eduardodev.cars

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.eduardodev.cars.list.CarListFragment

const val TAG_CAR_LIST_FRAGMENT = "carListFragment"

class MainActivity : AppCompatActivity() {

    private val carListFragment: Fragment? by lazy {
        supportFragmentManager.findFragmentByTag(TAG_CAR_LIST_FRAGMENT)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addCarListFragmentIfNeeded()
    }

    private fun addCarListFragmentIfNeeded() {
        if (carListFragment == null) addCarListFragment()
    }

    private fun addCarListFragment() {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.mainContainer, createCarListFragment(), TAG_CAR_LIST_FRAGMENT)
                .commit()
    }

    private fun createCarListFragment() = CarListFragment.newInstance()
}