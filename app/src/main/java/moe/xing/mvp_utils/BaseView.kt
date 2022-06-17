package moe.xing.mvp_utils

import androidx.annotation.StringRes


/**
 * Created by Qi Xingchen on 2022-6-17.
 */
interface BaseView<T : BasePresenter> {

    fun setPresenter(presenter: T)

    /**
     * 显示消息
     *
     * @param message 消息文字
     */
    fun showMessage(message: String?)

    fun showMessage(e: Throwable?)

    fun showMessage(@StringRes message: Int)

    fun showMessage(@StringRes message: Int, message2: String?)

    fun showProgressDialog()

    fun showProgressDialog(title: String?)

    fun showProgressDialog(title: String?, now: Int?, max: Int?)

    fun dismissProgressDialog()

}