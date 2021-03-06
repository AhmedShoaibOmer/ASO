package aso.mo.asoplayer.main.navigation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import aso.mo.asoplayer.databinding.ItemNavBinding
import aso.mo.asoplayer.main.common.callbacks.OnItemClickListener
import aso.mo.asoplayer.main.common.dragSwipe.OnStartDragListener


class NavAdapter(
    private var items: List<NavItem>?,
    private val dragStartListener: OnStartDragListener,
    private val onItemClickListener: OnItemClickListener
) :
    RecyclerView.Adapter<NavViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NavViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemBinding = ItemNavBinding.inflate(inflater, parent, false)
        return NavViewHolder(itemBinding, dragStartListener, onItemClickListener)
    }

    override fun getItemCount(): Int {
        return items?.size ?: 0
    }

    override fun onBindViewHolder(viewHolder: NavViewHolder, position: Int) {
        viewHolder.bind(items?.get(position))
    }

    fun updateItems(items: List<NavItem>, fromPosition: Int? = null, toPosition: Int? = null) {
        this.items = items
        if (fromPosition != null && toPosition != null)
            notifyItemMoved(fromPosition, toPosition)
        else notifyDataSetChanged()
    }

}