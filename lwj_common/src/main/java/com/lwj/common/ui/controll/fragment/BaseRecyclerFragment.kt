package com.lwj.common.ui.controll.fragment

import androidx.viewbinding.ViewBinding

abstract class BaseRecyclerFragment<T: ViewBinding>:  BaseDbFragment<T>(){
    override var isReuse: Boolean = false
}