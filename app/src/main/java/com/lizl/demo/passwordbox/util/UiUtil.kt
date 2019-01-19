package com.lizl.demo.passwordbox.util

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.View
import android.view.ViewTreeObserver
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import com.lizl.demo.passwordbox.R


/**
 * UI相关工具类
 */
class UiUtil
{
    companion object
    {
        /**
         * 复制内容到剪切板
         */
        fun copyTextToClipboard(context: Context, text: String)
        {
            val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clipData = ClipData.newPlainText("text", text)
            clipboardManager.primaryClip = clipData

            ToastUtil.showToast(R.string.notify_success_to_copy)
        }

        /**
         * 根据Fragment显示方向获取对应的动画
         */
        fun getFragmentTransactionAnnotation(showFromDirection: Int): IntArray
        {
            return when (showFromDirection)
            {
                Constant.FRAGMENT_SHOW_DIRECTION_RIGHT  -> intArrayOf(R.anim.slide_right_in, R.anim.slide_left_out, R.anim.slide_left_in, R.anim.slide_right_out)
                Constant.FRAGMENT_SHOW_DIRECTION_LEFT   -> intArrayOf(R.anim.slide_left_in, R.anim.slide_right_out, R.anim.slide_right_in, R.anim.slide_left_out)
                Constant.FRAGMENT_SHOW_DIRECTION_TOP    -> intArrayOf(R.anim.slide_top_in, R.anim.slide_bottom_out, R.anim.slide_bottom_in, R.anim.slide_top_out)
                Constant.FRAGMENT_SHOW_DIRECTION_BOTTOM -> intArrayOf(R.anim.slide_bottom_in, R.anim.slide_top_out, R.anim.slide_top_in, R.anim.slide_top_out)
                else                                    -> intArrayOf(R.anim.slide_right_in, R.anim.slide_left_out, R.anim.slide_left_in, R.anim.slide_right_out)
            }
        }

        /**
         * 隐藏输入法
         */
        fun hideInputKeyboard(view: View)
        {
            val manager = UiApplication.getInstance().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            if (manager.isActive)
            {
                manager.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
            }
        }

        /**
         * 退回到桌面
         */
        fun backToLauncher()
        {
            val intent = Intent(Intent.ACTION_MAIN)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.addCategory(Intent.CATEGORY_HOME)
            UiApplication.getInstance().startActivity(intent)
        }

        /**
         * 跳转到APP详情界面（用于获取权限）
         */
        fun goToAppDetailPage()
        {
            val intent = Intent()
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.action = "android.settings.APPLICATION_DETAILS_SETTINGS"
            intent.data = Uri.fromParts("package", UiApplication.getInstance().packageName, null)
            UiApplication.getInstance().startActivity(intent)
        }

        /**
         * 去除TextView默认换行样式，自定义换行
         */
        fun clearTextViewAutoWrap(tv: TextView)
        {
            tv.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener
            {
                override fun onGlobalLayout()
                {
                    tv.viewTreeObserver.removeOnGlobalLayoutListener(this)
                    val newText = autoSplitText(tv)
                    tv.text = newText
                }
            })
        }

        /**
         * 自定义TextView换行
         */
        private fun autoSplitText(tv: TextView): String
        {
            val rawText = tv.text.toString() //原始文本
            val tvPaint = tv.paint //paint，包含字体等信息
            val tvWidth = (tv.width - tv.paddingLeft - tv.paddingRight).toFloat() //控件可用宽度

            //将原始文本按行拆分
            val rawTextLines = rawText.split("\n".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val sb = StringBuilder()
            for (rawTextLine in rawTextLines)
            {
                if (tvPaint.measureText(rawTextLine) <= tvWidth)
                {
                    //如果整行宽度在控件可用宽度之内，就不处理了
                    sb.append(rawTextLine)
                }
                else
                {
                    //如果整行宽度超过控件可用宽度，则按字符测量，在超过可用宽度的前一个字符处手动换行
                    var lineWidth = 0f
                    var cnt = 0
                    while (cnt != rawTextLine.length)
                    {
                        val ch = rawTextLine[cnt]
                        lineWidth += tvPaint.measureText(ch.toString())
                        if (lineWidth <= tvWidth)
                        {
                            sb.append(ch)
                        }
                        else
                        {
                            sb.append("\n")
                            lineWidth = 0f
                            --cnt
                        }
                        ++cnt
                    }
                }
                sb.append("\n")
            }

            //把结尾多余的\n去掉
            if (!rawText.endsWith("\n"))
            {
                sb.deleteCharAt(sb.length - 1)
            }
            return sb.toString()
        }
    }
}