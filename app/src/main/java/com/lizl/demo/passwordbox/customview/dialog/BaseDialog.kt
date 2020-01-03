package com.lizl.demo.passwordbox.customview.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import com.lizl.demo.passwordbox.R


abstract class BaseDialog(context: Context) : Dialog(context, R.style.GlobalDialogStyle)
{
    protected val TAG = javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(getDialogLayoutResId(), null)
        setContentView(view)

        initView()
    }

    override fun onStart()
    {
        super.onStart()

        // 设置Dialog宽度
        val params = window?.attributes ?: return
        val display = context.resources.displayMetrics
        val min = display.heightPixels.coerceAtMost(display.widthPixels)
        // 宽度设置为宽高最小值的80%（兼容横屏）
        params.width = (min * 0.8).toInt()
        window?.attributes = params
    }

    abstract fun getDialogLayoutResId(): Int

    abstract fun initView()
}