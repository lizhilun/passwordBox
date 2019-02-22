package com.lizl.demo.passwordbox.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lizl.demo.passwordbox.R
import com.lizl.demo.passwordbox.model.OperationItem
import kotlinx.android.synthetic.main.item_operation.view.*

class OperationListAdapter(context: Context, private var operationList: List<OperationItem>) : RecyclerView.Adapter<OperationListAdapter.ViewHolder>()
{
    private var layoutInflater: LayoutInflater? = null

    private var onOperationItemClickListener: OnOperationItemClickListener? = null

    init
    {
        layoutInflater = LayoutInflater.from(context)
    }

    override fun getItemCount(): Int = operationList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        return ViewHolder(layoutInflater!!.inflate(R.layout.item_operation, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        holder.bindViewHolder(operationList[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        fun bindViewHolder(operationItem: OperationItem)
        {
            itemView.tv_operation_name.text = operationItem.operationName

            itemView.setOnClickListener {
                onOperationItemClickListener?.onOperationItemClick()
                operationItem.operationItemCallBack.onOperationExecute()
            }
        }
    }

    interface OnOperationItemClickListener
    {
        fun onOperationItemClick()
    }

    fun setOnOperationItemClickListener(onOperationItemClickListener: OnOperationItemClickListener)
    {
        this.onOperationItemClickListener = onOperationItemClickListener
    }
}