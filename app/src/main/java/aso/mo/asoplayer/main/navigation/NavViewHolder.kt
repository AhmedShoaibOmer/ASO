package aso.mo.asoplayer.main.navigation

import android.util.TypedValue
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView
import aso.mo.asoplayer.R
import aso.mo.asoplayer.databinding.ItemNavBinding
import aso.mo.asoplayer.main.common.callbacks.OnItemClickListener
import aso.mo.asoplayer.main.common.dragSwipe.ItemTouchHelperViewHolder
import aso.mo.asoplayer.main.common.dragSwipe.OnStartDragListener
import kotlinx.android.synthetic.main.item_nav.view.*


class NavViewHolder(
    private val itemBinding: ItemNavBinding,
    private val dragStartListener: OnStartDragListener,
    private val onItemClickListener: OnItemClickListener
) :
    RecyclerView.ViewHolder(itemBinding.root), ItemTouchHelperViewHolder {

    init {
        itemView.clipToOutline = true
        itemView.rippleView.setOnClickListener {
            onItemClickListener.onItemClick(adapterPosition)
        }

        itemView.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                dragStartListener.onStartDrag(this)
            }
            false
        }
    }

    fun bind(navItem: NavItem?) {
        itemBinding.item = navItem
        itemBinding.executePendingBindings()
    }

    override fun onItemSelected() {
        itemView.rippleView.setBackgroundResource(R.drawable.nav_item_dragging)
    }

    override fun onItemClear() {
        val outValue = TypedValue()
        itemView.context.theme.resolveAttribute(
            android.R.attr.selectableItemBackground,
            outValue,
            true
        )
        itemView.rippleView.setBackgroundResource(outValue.resourceId)
    }
}