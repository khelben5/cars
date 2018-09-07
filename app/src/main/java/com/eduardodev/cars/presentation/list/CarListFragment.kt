package com.eduardodev.cars.presentation.list

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.eduardodev.cars.R
import com.eduardodev.cars.presentation.model.*
import kotlinx.android.synthetic.main.fragment_car_list.*
import org.jetbrains.anko.getStackTraceString
import org.jetbrains.anko.support.v4.longToast


class CarListFragment : Fragment() {

    companion object {
        fun newInstance() = CarListFragment()
    }

    private val model by lazy { ViewModelProviders.of(this)[CarListViewModel::class.java] }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_car_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        carListRecyclerView.setHasFixedSize(true)
        model.getCars().observe(this, Observer { resource -> resource?.let { updateUI(it) } })
    }

    private fun updateUI(resource: Resource) {
        when (resource) {
            is Success -> showCars(resource.result)
            is Failure -> showFailure(resource.exception)
            is Process -> showProgress(true)
            ConnectionFailure -> showConnectionFailure()
        }
    }

    private fun showCars(result: Any) {
        showProgress(false)

        if (result !is List<*>) {
            showFailure(Exception("result is not a list"))
            return
        }

        val cars = result.mapNotNull { it as? Car }
        carListRecyclerView.adapter = CarListAdapter(cars)
    }

    private fun showFailure(exception: Exception) {
        showProgress(false)
        Log.e(javaClass.simpleName, exception.getStackTraceString())
        longToast(R.string.failure_generic)
    }

    private fun showConnectionFailure() {
        showProgress(false)
        longToast(R.string.failure_connection)
    }

    private fun showProgress(show: Boolean) {
        carListProgress.visibility = if (show) View.VISIBLE else View.GONE
    }
}