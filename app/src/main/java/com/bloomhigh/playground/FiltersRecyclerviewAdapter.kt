package com.bloomhigh.playground

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

class FiltersRecyclerviewAdapter : RecyclerView.Adapter<FiltersRecyclerviewAdapter.FilerVH>() {

    class FilerVH(view: View): RecyclerView.ViewHolder(view) {
        val image = view.findViewById<ImageView>(R.id.image)
        val name = view.findViewById<TextView>(R.id.name)
        val indicator = view.findViewById<View>(R.id.indicator)
    }

    private var selectedEffectIndex = 0

    fun selectEffect(index: Int) {
        notifyItemChanged(selectedEffectIndex, selectedEffectIndex)
        selectedEffectIndex = index
        notifyItemChanged(selectedEffectIndex, selectedEffectIndex)
    }

    private var callback: OnEffectSelectedListener? = null

    fun setOnEffectSelectedListener(listener: OnEffectSelectedListener) {
        callback = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilerVH {
        return FilerVH(LayoutInflater.from(parent.context).inflate(
            R.layout.item_filter, parent, false
        ))
    }

    override fun getItemCount(): Int {
        return Effects.size
    }

    override fun onBindViewHolder(holder: FilerVH, position: Int, payloads: MutableList<Any>) {
        if(payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            if(selectedEffectIndex == position) {
                holder.indicator.visibility = View.VISIBLE
            } else {
                holder.indicator.visibility = View.INVISIBLE
            }
        }
    }

    override fun onBindViewHolder(holder: FilerVH, position: Int) {
        val effect = Effects[position]

        holder.itemView.setOnClickListener {
            callback?.onEffectSelected(position)
            selectEffect(position)
        }

        if(selectedEffectIndex == position) {
            holder.indicator.visibility = View.VISIBLE
        } else {
            holder.indicator.visibility = View.INVISIBLE
        }

        holder.name.text = effect.name
        Glide.with(holder.itemView)
            .load(R.drawable.photo)
            .transform(
                RoundedCorners(10),
                GPPTransformation(effect)
            )
            .into(holder.image)
    }
}