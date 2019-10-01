package com.kkalfas.sample.commonui

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kkalfas.sample.posts.BR
import kotlin.properties.Delegates

interface DiffUtilCompareCallback<Type> {
    fun areItemsTheSame(t1: Type, t2: Type): Boolean
}

abstract class CommonAdapter<Type : Any> :
    RecyclerView.Adapter<CommonAdapter.ViewHolder>(),
    DiffUtilCompareCallback<Type> {

    var items: List<Type> by Delegates.observable(emptyList()) { _, old, new ->
        selfNotify(old, new, this)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    class ViewHolder(private val dataBinding: ViewDataBinding) : RecyclerView.ViewHolder(dataBinding.root) {
        fun <VM> bind(viewModel: VM) {
            this.dataBinding.setVariable(BR.viewModel, viewModel)
            this.dataBinding.executePendingBindings()
        }
    }

    private fun RecyclerView.Adapter<ViewHolder>.selfNotify(
        old: List<Type>,
        new: List<Type>,
        callback: DiffUtilCompareCallback<Type>
    ) {
        DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return callback.areItemsTheSame(old[oldItemPosition], new[newItemPosition])
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return old[oldItemPosition] == new[newItemPosition]
            }

            override fun getOldListSize() = old.size
            override fun getNewListSize() = new.size
        }).dispatchUpdatesTo(this)
    }
}