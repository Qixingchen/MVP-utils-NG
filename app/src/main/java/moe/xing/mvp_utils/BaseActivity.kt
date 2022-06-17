package moe.xing.mvp_utils

import android.R
import android.app.ProgressDialog
import android.os.Build
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import moe.xing.baseutils.utils.LogHelper
import moe.xing.baseutils.utils.TextHelper


/**
 * Created by Qi Xingchen on 2022-6-17.
 */
open class BaseActivity : AppCompatActivity() {

    lateinit var mActivity: AppCompatActivity
    var mDialog: ProgressDialog? = null
    var active = false

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        mActivity = this
    }

    fun showProgressDialog(message: String, now: Int?, max: Int?) {
        if (now == null && mDialog != null) {
            mDialog!!.dismiss()
            mDialog = null
        }
        if (mDialog == null) {
            mDialog = ProgressDialog(mActivity)
        }
        if (now != null && max != null) {
            if (mDialog!!.isIndeterminate) {
                mDialog!!.dismiss()
                mDialog = ProgressDialog(mActivity)
            }
            mDialog!!.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
            if (mDialog!!.max == 100 && max != 100) {
                mDialog!!.max = max
            }
            mDialog!!.progress = now
        }

        if (!mDialog!!.isShowing) {
            val params = mDialog!!.window!!.attributes
            params.dimAmount = 0f
            mDialog!!.window!!.attributes = params
            if (TextUtils.isEmpty(title)) {
                title = "加载中..."
            }
            mDialog!!.setTitle(title)
            mDialog!!.setCancelable(false)
            mDialog!!.setButton(
                AlertDialog.BUTTON_NEGATIVE, "取消显示进度框"
            ) { _, _ ->
                mDialog!!.dismiss()
                mDialog = null
            }
            mDialog!!.show()
        }
    }

    fun showProgressDialog() {
        showProgressDialog("")
    }

    fun showProgressDialog(message: String) {
        showProgressDialog(message, null, null)
    }

    fun dismissProgressDialog() {
        if (mDialog != null) {
            mDialog!!.dismiss()
            mDialog = null
        }
    }

    fun showMessage(message: String?) {
        val viewGroup = (findViewById<View>(R.id.content) as ViewGroup).getChildAt(0) as ViewGroup
        LogHelper.Snackbar(viewGroup, message)
    }

    fun showMessage(e: Throwable) {
        val message: String = if (TextHelper.isVisible(e.localizedMessage)) {
            e.localizedMessage ?: "未知错误"
        } else {
            e.toString()
        }
        showMessage(message)
    }

    fun showMessage(@StringRes message: Int) {
        showMessage(getString(message))
    }

    fun showMessage(@StringRes message: Int, message2: String) {
        showMessage(getString(message) + message2)
    }

    /**
     * 设置暗色状态栏图标
     *
     * @param needDark `true` 需要暗色图标
     */
    fun setDarkStatusIcon(needDark: Boolean) {
        val decorView = window.decorView
        var vis = decorView.systemUiVisibility
        vis = if (needDark) {
            vis or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        } else {
            vis and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
        }
        decorView.systemUiVisibility = vis
    }


}