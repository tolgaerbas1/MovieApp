package com.whocanfly.movieapp.extensions

import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.whocanfly.movieapp.BR

object RecyclerViewBindings {

    @BindingAdapter("data")
    fun <T> setRecyclerViewProperties(recyclerView: RecyclerView, data: T) {
        if (recyclerView.adapter is BindableAdapter<*>) {
            (recyclerView.adapter as BindableAdapter<T>).setData(data)
        }
    }

    interface OnItemClickListener {
        fun onItemClick(view: View, vararg data: Any)
    }

    interface LayoutSelector {
        fun onSelectLayout(item: Any?): Int
    }

    interface ItemIdGetter {
        fun onGetItemId(data: List<*>, position: Int): Long
    }

    @JvmStatic
    @BindingAdapter(
        value = [
            "entries",
            "layout",
            "onItemClick",
            "itemIdGetter",
            "lifecycleOwner",
            "numItems",
            "fastBind"
        ], requireAll = false
    )
    fun RecyclerView.entries(
        array: List<Any>?,
        layoutId: Int,
        listener: OnItemClickListener?,
        itemIdGetter: ItemIdGetter?,
        lifecycleOwner: LifecycleOwner?,
        numItems: Int?,
        fastBind: Boolean
    ) {
        entries(array, object : LayoutSelector {
            override fun onSelectLayout(item: Any?): Int {
                return layoutId
            }
        }, listener, itemIdGetter, lifecycleOwner, numItems, fastBind)
    }

    @JvmStatic
    @BindingAdapter(
        value = [
            "entries",
            "layout",
            "onItemClick",
            "itemIdGetter",
            "lifecycleOwner",
            "numItems",
            "fastBind"
        ], requireAll = false
    )
    fun RecyclerView.entries(
        array: ArrayList<Any>?,
        layoutId: Int,
        listener: OnItemClickListener?,
        itemIdGetter: ItemIdGetter?,
        lifecycleOwner: LifecycleOwner?,
        numItems: Int?,
        fastBind: Boolean
    ) {
        entries(array, object : LayoutSelector {
            override fun onSelectLayout(item: Any?): Int {
                return layoutId
            }
        }, listener, itemIdGetter, lifecycleOwner, numItems, fastBind)
    }

    @JvmStatic
    @BindingAdapter(
        value = ["entries", "layout", "onItemClick", "itemIdGetter", "lifecycleOwner", "numItems", "fastBind"],
        requireAll = false
    )
    fun RecyclerView.entries(
        array: List<Any>?,
        layoutSelector: LayoutSelector,
        listener: OnItemClickListener?,
        itemIdGetter: ItemIdGetter?,
        lifecycleOwner: LifecycleOwner?,
        numItems: Int?,
        fastBind: Boolean
    ) {
        val a = adapter
        if (a != null && a is RecyclerViewAdapter) {
            a.setData(array)
        } else {
            adapter = RecyclerViewAdapter(
                array?.toMutableList() ?: mutableListOf(),
                layoutSelector,
                listener,
                itemIdGetter,
                lifecycleOwner,
                numItems ?: -1,
                fastBind
            )
        }
    }

    @JvmStatic
    @BindingAdapter(
        value = ["entries", "layout", "onItemClick", "itemIdGetter", "lifecycleOwner", "numItems", "fastBind", "tabLayout"],
        requireAll = false
    )
    fun ViewPager2.entries(
        array: List<Any>?,
        layoutId: Int,
        listener: OnItemClickListener?,
        itemIdGetter: ItemIdGetter?,
        lifecycleOwner: LifecycleOwner?,
        numItems: Int?,
        fastBind: Boolean,
        tabLayout: TabLayout?
    ) {
        adapter = RecyclerViewAdapter(
            array?.toMutableList() ?: mutableListOf(),
            object : LayoutSelector {
                override fun onSelectLayout(item: Any?): Int {
                    return layoutId
                }
            },
            listener,
            itemIdGetter,
            lifecycleOwner,
            numItems ?: -1,
            fastBind
        )
        if (tabLayout != null)
            TabLayoutMediator(tabLayout, this) { _, _ -> }.attach()
    }

    internal class RecyclerViewAdapter(
        private val data: MutableList<Any>,
        private val layoutSelector: RecyclerViewBindings.LayoutSelector,
        private val listener: RecyclerViewBindings.OnItemClickListener?,
        private val itemIdGetter: RecyclerViewBindings.ItemIdGetter?,
        private val viewLifecycleOwner: LifecycleOwner?,
        private var numItems: Int,
        private val fastBind: Boolean
    ) : RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder>() {

        init {
            setHasStableIds(itemIdGetter != null)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
            return RecyclerViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    viewType,
                    parent,
                    false
                ),
                listener,
                itemIdGetter
            )
        }

        private fun getIndexFromPosition(dataSize: Int, position: Int): Int {
            return if (numItems == dataSize) position else if (position != 0) position % dataSize else position
        }

        override fun getItemViewType(position: Int): Int {
            return when (data.size) {
                0 -> layoutSelector.onSelectLayout(null)
                else -> layoutSelector.onSelectLayout(
                    data[getIndexFromPosition(
                        data.size,
                        position
                    )]
                )
            }
        }

        fun setData(data: List<Any>?) {
            this.data.clear()
            if (data != null) {
                this.data.addAll(data)
            }
            notifyDataSetChanged()
        }

        fun getData() = data

        override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
            if (data.size != 0) {
                val index = getIndexFromPosition(data.size, position)
                holder.bind(data[index], index)
            }
        }

        override fun getItemCount(): Int {
            return if (numItems == -1) data.size else numItems
        }

        fun getActualItemCount(): Int {
            return data.size
        }

        override fun getItemId(position: Int): Long {
            return itemIdGetter?.onGetItemId(data, getIndexFromPosition(data.size, position))
                ?: super.getItemId(position)
        }

        fun removeItemAt(index: Int): Any? {
            return if (data is ArrayList) {
                val item = data.removeAt(index)
                notifyItemRemoved(index)
                item
            } else {
                null
            }
        }

        fun restoreItem(index: Int, item: Any) {
            if (data is ArrayList) {
                data.add(index, item)
                notifyItemInserted(index)
            }
        }

        inner class RecyclerViewHolder(
            private val binding: ViewDataBinding,
            private val listener: RecyclerViewBindings.OnItemClickListener?,
            private val itemIdGetter: RecyclerViewBindings.ItemIdGetter?
        ) : RecyclerView.ViewHolder(binding.root) {

            fun bind(item: Any?, index: Int) {
//            binding.setVariable(BR.index, index)
                binding.setVariable(BR.item, item)
                binding.setVariable(BR.listener, listener)
//            binding.setVariable(BR.itemIdGetter, itemIdGetter)
//                binding.setVariable(BR.lifecycleOwner, viewLifecycleOwner)
                binding.lifecycleOwner = viewLifecycleOwner
                if (fastBind) {
                    binding.executePendingBindings()
                }
            }
        }
    }

    interface BindableAdapter<T> {
        fun setData(data: T)
    }
}