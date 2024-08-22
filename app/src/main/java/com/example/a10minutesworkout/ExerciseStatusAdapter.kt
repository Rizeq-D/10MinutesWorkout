package com.example.a10minutesworkout

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.a10minutesworkout.databinding.ItemExerciseStatusBinding

class ExerciseStatusAdapter(val items:ArrayList<ExerciseModel>)
    : RecyclerView.Adapter<ExerciseStatusAdapter.ViewHolder>(){

    class ViewHolder(binding : ItemExerciseStatusBinding)
        : RecyclerView.ViewHolder(binding.root) {

            val tvItem = binding.tvItem

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemExerciseStatusBinding.inflate(
            LayoutInflater.from(parent.context), parent, false))
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model : ExerciseModel = items[position]
        holder.tvItem.text = model.getId().toString()

        when{
            model.getIsSelected() -> {
                holder.tvItem.background =
                    ContextCompat.getDrawable(holder.itemView.context,
                        R.drawable.item_circular_color_background_selected)
                holder.tvItem.setTextColor(Color.parseColor("#FFE91809"))
            }

            model.getIsCompleted() -> {
                    holder.tvItem.background =
                        ContextCompat.getDrawable(holder.itemView.context,
                            R.drawable.item_circular_color_background_completed)
                    holder.tvItem.setTextColor(Color.parseColor("#f8cc1b"))
            }

            else -> {
                holder.tvItem.background =
                    ContextCompat.getDrawable(holder.itemView.context,
                        R.drawable.item_circular_color_background)
                holder.tvItem.setTextColor(Color.parseColor("#42826c"))
            }
        }

    }
    override fun getItemCount(): Int {
        return items.size
    }
}