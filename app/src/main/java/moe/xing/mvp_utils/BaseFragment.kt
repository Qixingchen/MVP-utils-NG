package moe.xing.mvp_utils

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.annotation.StringRes
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment


/**
 * Created by Qi Xingchen on 2022-6-17.
 */
abstract class BaseFragment : Fragment() {

    lateinit var mRootView: View
    lateinit var mContext: Context
    var mActionBar: ActionBar? = null
    lateinit var mFragment: Fragment
    var viewExisted = false


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mRootView = CreateView(inflater, container, savedInstanceState)
        ViewFound(mRootView)
        viewExisted = true
        return mRootView
    }

    abstract fun CreateView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View

    abstract fun ViewFound(view: View): Void

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context

        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (parentFragmentManager.backStackEntryCount > 0) {
                        parentFragmentManager.popBackStack()
                    } else {
                        activity?.finish()
                    }
                }
            })
    }

    protected abstract fun getTitle(): String

    protected abstract fun setTitle(title: String)

    override fun onResume() {
        super.onResume()
        mActionBar = (activity as AppCompatActivity?)?.supportActionBar
        val title = getTitle()
        setTitle(title)
    }

    open fun showMessage(message: String?) {
        (activity as BaseActivity?)!!.showMessage(message)
    }

    open fun showMessage(e: Throwable?) {
        (activity as BaseActivity?)!!.showMessage(e!!)
    }

    open fun showMessage(@StringRes message: Int) {
        (activity as BaseActivity?)!!.showMessage(message)
    }

    open fun showMessage(@StringRes message: Int, message2: String?) {
        (activity as BaseActivity?)!!.showMessage(message, message2!!)
    }

    open fun showProgressDialog() {
        showProgressDialog("")
    }

    open fun showProgressDialog(title: String?) {
        showProgressDialog(title, null, null)
    }

    open fun showProgressDialog(title: String?, now: Int?, max: Int?) {
        (activity as BaseActivity?)!!.showProgressDialog(title!!, now, max)
    }

    open fun dismissProgressDialog() {
        (activity as BaseActivity?)!!.dismissProgressDialog()
    }

    open fun onBackPressedSupport() {
        activity?.onBackPressed()
    }
}