package com.lizl.passwordbox.util

import android.app.Dialog
import android.content.Context
import com.lizl.passwordbox.custom.dialog.DialogAccountInfo
import com.lizl.passwordbox.custom.dialog.DialogInput
import com.lizl.passwordbox.custom.dialog.DialogOperationConfirm
import com.lizl.passwordbox.custom.dialog.DialogOperationList
import com.lizl.passwordbox.mvvm.model.AccountModel
import com.lizl.passwordbox.mvvm.model.OperationModel

object DialogUtil
{
    private var dialog: Dialog? = null

    fun showAccountInfoDialog(context: Context, accountModel: AccountModel)
    {
        showDialog(DialogAccountInfo(context, accountModel))
    }

    fun showInputDialog(context: Context, title: String, editHint: String, inputCompletedCallback: (String) -> Unit)
    {
        showDialog(DialogInput(context, title, editHint, inputCompletedCallback))
    }

    fun showOperationConfirmDialog(context: Context, title: String, notify: String, operationConfirmCallback: () -> Unit)
    {
        showDialog(DialogOperationConfirm(context, title, notify, operationConfirmCallback))
    }

    fun showOperationListDialog(context: Context, operationList: List<OperationModel>)
    {
        showDialog(DialogOperationList(context, operationList))
    }

    private fun showDialog(dialog: Dialog)
    {
        this.dialog?.dismiss()
        this.dialog = dialog
        this.dialog?.show()
    }

    fun dismissDialog()
    {
        dialog?.dismiss()
    }
}