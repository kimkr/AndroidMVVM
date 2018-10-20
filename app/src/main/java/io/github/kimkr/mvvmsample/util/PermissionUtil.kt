package io.github.kimkr.mvvmsample.util

import android.Manifest
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.DialogFragment
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import io.github.kimkr.mvvmsample.R

class PermissionUtil {

    companion object {
        fun requestPermission(activity: AppCompatActivity, requestId: Int,
                              permission: String, finishActivity: Boolean) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                PermissionUtil.RationaleDialog.newInstance(requestId, finishActivity)
                        .show(activity.supportFragmentManager, "dialog")
            } else {
                ActivityCompat.requestPermissions(activity, arrayOf(permission), requestId)
            }
        }

        fun isPermissionGranted(grantPermissions: Array<String>, grantResults: IntArray,
                                permission: String): Boolean {
            for (i in grantPermissions.indices) {
                if (permission == grantPermissions[i]) {
                    return grantResults[i] == PackageManager.PERMISSION_GRANTED
                }
            }
            return false
        }
    }

    class PermissionDeniedDialog : DialogFragment() {

        private var finishActivity = false

        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            finishActivity = arguments!!.getBoolean(ARGUMENT_FINISH_ACTIVITY)
            return AlertDialog.Builder(activity)
                    .setMessage(R.string.location_permission_denied)
                    .setPositiveButton(android.R.string.ok, null)
                    .create()
        }

        override fun onDismiss(dialog: DialogInterface?) {
            super.onDismiss(dialog)
            if (finishActivity) {
                Toast.makeText(activity, R.string.location_permission_denied,
                        Toast.LENGTH_SHORT).show()
                activity!!.finish()
            }
        }

        companion object {

            private val ARGUMENT_FINISH_ACTIVITY = "finish"

            fun newInstance(finishActivity: Boolean): PermissionDeniedDialog {
                val arguments = Bundle()
                arguments.putBoolean(ARGUMENT_FINISH_ACTIVITY, finishActivity)
                val dialog = PermissionDeniedDialog()
                dialog.arguments = arguments
                return dialog
            }
        }
    }

    class RationaleDialog : DialogFragment() {

        private var mFinishActivity = false

        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            val arguments = arguments
            val requestCode = arguments!!.getInt(ARGUMENT_PERMISSION_REQUEST_CODE)
            mFinishActivity = arguments.getBoolean(ARGUMENT_FINISH_ACTIVITY)

            return AlertDialog.Builder(activity)
                    .setMessage(R.string.location_permission_request_title)
                    .setPositiveButton(android.R.string.ok, DialogInterface.OnClickListener { dialog, which ->
                        // After click on Ok, request the permission.
                        ActivityCompat.requestPermissions(activity!!,
                                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                                requestCode)
                        // Do not finish the Activity while requesting permission.
                        mFinishActivity = false
                    })
                    .setNegativeButton(android.R.string.cancel, null)
                    .create()
        }

        override fun onDismiss(dialog: DialogInterface?) {
            super.onDismiss(dialog)
            if (mFinishActivity) {
                Toast.makeText(activity,
                        R.string.location_permission_request_desc,
                        Toast.LENGTH_SHORT)
                        .show()
                activity!!.finish()
            }
        }

        companion object {
            private val ARGUMENT_PERMISSION_REQUEST_CODE = "requestCode"
            private val ARGUMENT_FINISH_ACTIVITY = "finish"

            fun newInstance(requestCode: Int, finishActivity: Boolean): RationaleDialog {
                val arguments = Bundle()
                arguments.putInt(ARGUMENT_PERMISSION_REQUEST_CODE, requestCode)
                arguments.putBoolean(ARGUMENT_FINISH_ACTIVITY, finishActivity)
                val dialog = RationaleDialog()
                dialog.arguments = arguments
                return dialog
            }
        }
    }
}