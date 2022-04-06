package jcb.bb.jowdi.Adapter

import android.view.View

interface StringOnClick {
    fun onAdapterClick(positon: Int)
    fun onItemLongClick(position: Int, v: View?)
}