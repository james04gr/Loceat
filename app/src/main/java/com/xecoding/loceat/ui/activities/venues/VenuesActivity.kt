package com.xecoding.loceat.ui.activities.venues

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.xecoding.loceat.R
import com.xecoding.loceat.extension.toast
import com.xecoding.loceat.model.location.GeoCoderLocation
import com.xecoding.loceat.ui.adapter.CategoryRecyclerAdapter
import com.xecoding.loceat.viewmodels.VenuesViewModel
import com.xecoding.loceat.viewmodels.VenuesViewModel.Companion.EXTRA_LAT_LNG
import kotlinx.android.synthetic.main.activity_venues.*

class VenuesActivity : AppCompatActivity() {

    private val viewModel: VenuesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_venues)
        lifecycle.addObserver(viewModel)

        actionBar?.setDisplayHomeAsUpEnabled(true)

        val viewManager = LinearLayoutManager(this)
        val viewAdapter = CategoryRecyclerAdapter()

        categoryRecycler.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
        viewModel.address?.let {
            setActionTitle(it)
        }

        viewModel.venues.observe(this, Observer {
            it?.let {
                if (it.isNullOrEmpty()) {
                    toast("No venues found near to you!")
                    finish()
                } else {
                    loadingGroup.visibility = View.GONE
                    categoryRecycler.visibility = View.VISIBLE
                    viewAdapter.categoryGroups = it
                }
            }
        })
    }

    private fun setActionTitle(value: String) {
        this.supportActionBar?.title = value
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        fun startActivity(context: Context, geoCoderLocation: GeoCoderLocation) {
            context.startActivity(
                Intent(context, VenuesActivity::class.java).apply {
                    putExtra(EXTRA_LAT_LNG, geoCoderLocation)
                }
            )
        }
    }
}