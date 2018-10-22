package com.dumi.svq_ver10.ui.splash

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.pm.PackageManager
import android.support.v4.content.ContextCompat
import com.dumi.svq_ver10.R
import com.dumi.svq_ver10.ui.BaseActivity
import com.dumi.svq_ver10.ui.location.LocationActivity
import com.dumi.svq_ver10.ui.login.LoginActivity
import com.dumi.svq_ver10.util.PermissionUtil
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : BaseActivity() {

    private var permissionDenied = false

    override fun getLayout(): Int {
        return R.layout.activity_splash
    }

    override fun useDataBinding(): Boolean {
        return false
    }

    override fun onResume() {
        super.onResume()
        if (permissionDenied) {
            showMissingPermissionError()
        } else {
            animateViewComponent(iv_splash, listOf(R.anim.rotation, R.anim.fadein),
                    0, 2000, this::onCompleteAnim)
        }
    }

    private fun onCompleteAnim() {
        if (isLocationPermitted()) {
            navigateTo(LocationActivity::class.java)
        } else {
            requestLocationPermission()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                            grantResults: IntArray) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return
        }
        if (PermissionUtil.isPermissionGranted(permissions, grantResults,
                        ACCESS_FINE_LOCATION)) {
            navigateTo(LoginActivity::class.java)
        } else {
            permissionDenied = true
        }
    }

    private fun isLocationPermitted(): Boolean {
        return ContextCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestLocationPermission() {
        PermissionUtil.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                ACCESS_FINE_LOCATION, false)
    }

    private fun showMissingPermissionError() {
        PermissionUtil.PermissionDeniedDialog
                .newInstance(true).show(supportFragmentManager, "dialog")
    }

    companion object {
        private val TAG = SplashActivity::class.java.simpleName
        private val LOCATION_PERMISSION_REQUEST_CODE = 1
    }
}