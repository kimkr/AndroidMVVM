package com.dumi.svq_ver10.ui.location

import android.databinding.ObservableArrayList
import android.databinding.ObservableList
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import com.dumi.svq_ver10.BR
import com.dumi.svq_ver10.R
import com.dumi.svq_ver10.persistence.repository.LocationRepository
import com.dumi.svq_ver10.ui.BaseActivity
import com.dumi.svq_ver10.ui.components.SingleDialog
import com.dumi.svq_ver10.ui.main.MainActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_location.*
import kotlinx.android.synthetic.main.layout_app_bar.*
import javax.inject.Inject

class LocationActivity : BaseActivity() {
    @field:[Inject]
    internal lateinit var viewModel: LocationViewModel
    @field:[Inject]
    internal lateinit var adapter: SearchResultAdapter
    @field:[Inject]
    internal lateinit var repository: LocationRepository

    private lateinit var map: GoogleMap

    private lateinit var markerPosition: LatLng

    override fun getLayout(): Int {
        return R.layout.activity_location
    }

    override fun useDataBinding(): Boolean {
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        binding.setVariable(BR.viewmodel, viewModel)
        initView()
    }

    private fun initView() {
        val mapFragment =
                supportFragmentManager.findFragmentById(R.id.fragment_map) as SupportMapFragment
        mapFragment.getMapAsync { map ->
            this.map = map
            viewModel.requestCurrentLocation()
        }
        btn_search.setOnClickListener { _ -> onClickSearch() }
        btn_gps.setOnClickListener { _ -> viewModel.requestCurrentLocation() }
        btn_close.setOnClickListener { _ -> et_address.clearFocus() }
        et_address.setOnEditorActionListener { _, action, _ ->
            when (action) {
                EditorInfo.IME_ACTION_SEARCH -> onClickSearch()
                else -> false
            }
            true
        }
        et_address.setOnFocusChangeListener { _, focused ->
            viewModel.mapVisible.set(!focused)
            if (!focused) hideKeyboard(et_address)
        }
        et_address.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable: Editable?) {
                if (editable.isNullOrEmpty()) {
                    viewModel.mapVisible.set(true)
                    hideKeyboard(et_address)
                } else {
                    viewModel.mapVisible.set(false)
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

        })
        viewModel.currentLocation.addOnListChangedCallback(
                object : ObservableList.OnListChangedCallback<ObservableArrayList<Double>>() {
                    override fun onChanged(sender: ObservableArrayList<Double>?) {
                    }

                    override fun onItemRangeRemoved(sender: ObservableArrayList<Double>?, positionStart: Int, itemCount: Int) {
                    }

                    override fun onItemRangeMoved(sender: ObservableArrayList<Double>?, fromPosition: Int, toPosition: Int, itemCount: Int) {
                    }

                    override fun onItemRangeInserted(list: ObservableArrayList<Double>?, positionStart: Int, itemCount: Int) {
                        focusCamera(map, list!![0], list!![1])
                    }

                    override fun onItemRangeChanged(list: ObservableArrayList<Double>?, positionStart: Int, itemCount: Int) {
                        focusCamera(map, list!![0], list!![1])
                    }
                }
        )
        lv_address_result.setOnItemClickListener { _, view, i, l ->
            var result = adapter.getItem(i)
            viewModel.setCurrentAddress(result.address)
            viewModel.updateViewState(ViewAction.CLICK_SEARCH_RESULT)
            focusCamera(map, result.lat, result.lng)
        }
        lv_address_result.adapter = adapter
        rl_choose_current.setOnClickListener { _ -> setCurrentLocation() }
    }

    private fun onClickSearch() {
        hideKeyboard(et_address)
        viewModel.updateViewState(ViewAction.START_SEARCH)
        val keyword = et_address.text
        if (!TextUtils.isEmpty(keyword))
            viewModel.searchNearbyPlaces(keyword.toString())
        else
            toast(R.string.location_empty_keyword)
    }

    private fun focusCamera(map: GoogleMap, lat: Double, lng: Double) {
        markerPosition = LatLng(lat, lng)
        map.addMarker(MarkerOptions().position(markerPosition))
        map.moveCamera(CameraUpdateFactory.newLatLng(markerPosition))
        map.animateCamera(CameraUpdateFactory.zoomTo(15f))
    }

    private fun setCurrentLocation() {
        repository.updateLocation(markerPosition.latitude, markerPosition.longitude)
                .andThen(repository.updateAddress(viewModel.currentAddress.get()!!))
                .subscribe {
                    val dialog = SingleDialog(this,
                            "나의 위치가 설정되었습니다.",
                            View.OnClickListener {
                                val settingInfoIntent = intent.getStringExtra("SettingInfoActivity")
                                if (settingInfoIntent != null) {
                                } else {
                                    navigateTo(MainActivity::class.java)
                                }
                            })
                    dialog.show()
                }
    }


    companion object {
        private val TAG = LocationActivity::class.java.simpleName
    }
}