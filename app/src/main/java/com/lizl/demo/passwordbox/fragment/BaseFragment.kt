package com.lizl.demo.passwordbox.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lizl.demo.passwordbox.activity.MainActivity

abstract class BaseFragment : Fragment()
{
    protected var TAG = this.javaClass.simpleName

    var fragmentHasDestroyed = false

    override fun onCreate(savedInstanceState: Bundle?)
    {
        Log.d(TAG, "onCreate")
        super.onCreate(savedInstanceState)

        fragmentHasDestroyed = false
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        return inflater.inflate(getLayoutResId(), container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?)
    {
        super.onActivityCreated(savedInstanceState)

        initView()
    }

    override fun onStart()
    {
        Log.d(TAG, "onStart")
        super.onStart()
    }

    override fun onResume()
    {
        Log.d(TAG, "onResume")
        super.onResume()
    }

    override fun onPause()
    {
        Log.d(TAG, "onPause")
        super.onPause()
    }

    override fun onStop()
    {
        Log.d(TAG, "onStop")
        super.onStop()
    }

    override fun onDestroy()
    {
        Log.d(TAG, "onDestroy")
        super.onDestroy()

        fragmentHasDestroyed = true
    }

    abstract fun getLayoutResId(): Int

    abstract fun initView()

    abstract fun onBackPressed(): Boolean

    protected fun backToPreFragment()
    {
        (activity as MainActivity).backToPreFragment()
    }

    protected fun turnToFragment(fragment: BaseFragment)
    {
        (activity as MainActivity).turnToFragment(fragment)
    }

    protected fun turnToFragment(fragment: BaseFragment, bundle: Bundle)
    {
        (activity as MainActivity).turnToFragment(fragment, bundle)
    }
}