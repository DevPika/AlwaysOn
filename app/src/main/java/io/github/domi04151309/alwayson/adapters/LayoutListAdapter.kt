package io.github.domi04151309.alwayson.adapters

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getColor
import androidx.core.graphics.ColorUtils
import androidx.recyclerview.widget.RecyclerView
import io.github.domi04151309.alwayson.R

class LayoutListAdapter(
    private val drawables: Array<Int>,
    private val titles: Array<String>,
    private val onItemClickListener: OnItemClickListener,
) : RecyclerView.Adapter<LayoutListAdapter.ViewHolder>() {
    companion object {
        private const val SELECTED_BACKGROUND_ALPHA = 64
    }

    private var selectedItem = -1

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.layout_list_item, parent, false),
        )
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int,
    ) {
        holder.view.setOnClickListener {
            setSelectedItem(position)
            onItemClickListener.onItemClick(position)
        }

        if (position == selectedItem) {
            holder.view.findViewById<LinearLayout>(R.id.linearLayout)
                .setBackgroundColor(
                    ColorUtils.setAlphaComponent(
                        getColor(holder.view.context, R.color.colorAccent),
                        SELECTED_BACKGROUND_ALPHA,
                    ),
                )
        } else {
            holder.view.findViewById<LinearLayout>(R.id.linearLayout).background =
                getAttr(holder.view.context)
        }
        holder.view.findViewById<ImageView>(R.id.drawable)
            .setImageDrawable(ContextCompat.getDrawable(holder.view.context, drawables[position]))
        holder.view.findViewById<TextView>(R.id.title).text = titles[position]
    }

    override fun getItemCount(): Int = drawables.size

    internal fun setSelectedItem(position: Int) {
        val lastSelected = selectedItem
        selectedItem = position
        if (lastSelected != -1) notifyItemChanged(lastSelected)
        notifyItemChanged(selectedItem)
    }

    private fun getAttr(context: Context): Drawable? {
        val value = TypedValue()
        context.theme.resolveAttribute(
            androidx.appcompat.R.attr.selectableItemBackground,
            value,
            true,
        )
        return ContextCompat.getDrawable(context, value.resourceId)
    }
}
