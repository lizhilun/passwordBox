package com.lizl.passwordbox.custom.view

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lizl.passwordbox.R
import com.lizl.passwordbox.adapter.TitleBarBtnListAdapter
import com.lizl.passwordbox.mvvm.model.TitleBarBtnModel

class CustomTitleBar(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : ConstraintLayout(context, attrs, defStyleAttr)
{
    private lateinit var backBtn: AppCompatImageView
    private lateinit var titleTextView: AppCompatTextView
    private lateinit var btnListView: RecyclerView

    private var onBackBtnClickListener: (() -> Unit)? = null

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    init
    {
        initView(context, attrs)
    }

    fun initView(context: Context, attrs: AttributeSet?)
    {
        backBtn = AppCompatImageView(context).apply {
            id = generateViewId()
            val padding = context.resources.getDimensionPixelOffset(R.dimen.toolbar_back_icon_padding)
            scaleType = ImageView.ScaleType.FIT_START
            setImageResource(R.drawable.ic_back)
            setPadding(0, padding, 0, padding)
            this@CustomTitleBar.addView(this)
        }

        titleTextView = AppCompatTextView(context).apply {
            id = generateViewId()
            setTextSize(TypedValue.COMPLEX_UNIT_PX, context.resources.getDimension(R.dimen.toolbar_title_text_size))
            setTextColor(ContextCompat.getColor(context, R.color.white))
            gravity = Gravity.CENTER_VERTICAL
            this@CustomTitleBar.addView(this)
        }

        btnListView = RecyclerView(context).apply {
            id = generateViewId()
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, true)
            adapter = TitleBarBtnListAdapter(emptyList())
            this@CustomTitleBar.addView(this)
        }

        val typeArray = context.obtainStyledAttributes(attrs, R.styleable.CustomTitleBar)
        backBtn.isVisible = typeArray.getBoolean(R.styleable.CustomTitleBar_backBtnVisible, true)
        titleTextView.text = typeArray.getString(R.styleable.CustomTitleBar_titleText)
        typeArray.recycle()

        val constraintSet = ConstraintSet()
        constraintSet.clone(this)

        constraintSet.constrainHeight(backBtn.id, LayoutParams.MATCH_PARENT)
        constraintSet.constrainWidth(backBtn.id, context.resources.getDimensionPixelOffset(R.dimen.toolbar_back_icon_size))
        constraintSet.connect(backBtn.id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START)

        constraintSet.constrainHeight(titleTextView.id, LayoutParams.MATCH_PARENT)
        constraintSet.constrainWidth(titleTextView.id, LayoutParams.WRAP_CONTENT)
        constraintSet.connect(titleTextView.id, ConstraintSet.START, backBtn.id, ConstraintSet.END)

        constraintSet.constrainHeight(btnListView.id, LayoutParams.MATCH_PARENT)
        constraintSet.constrainWidth(btnListView.id, LayoutParams.MATCH_CONSTRAINT)
        constraintSet.connect(btnListView.id, ConstraintSet.START, titleTextView.id, ConstraintSet.END)
        constraintSet.connect(btnListView.id, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END)

        constraintSet.applyTo(this)

        backBtn.setOnClickListener { onBackBtnClickListener?.invoke() }
    }

    fun setOnBackBtnClickListener(onBackBtnClickListener: () -> Unit)
    {
        this.onBackBtnClickListener = onBackBtnClickListener
    }

    fun setBtnList(btnList: List<TitleBarBtnModel.BaseModel>)
    {
        (btnListView.adapter as TitleBarBtnListAdapter).replaceData(btnList)
    }

    fun setBackBtnVisible(visible: Boolean)
    {
        backBtn.isVisible = visible
    }

    fun setTitleText(text: String)
    {
        titleTextView.text = text
    }
}