package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemExpressionBinding

class HistoryAdapter(private val onOperationLongClick: (OperationUI) -> Unit, private val onOperationClick: (OperationUI) -> Unit,private var items: List<OperationUI> = listOf()) :
    RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>(){

        class HistoryViewHolder(val binding: ItemExpressionBinding) :
                RecyclerView.ViewHolder(binding.root)




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {


        return HistoryViewHolder(ItemExpressionBinding.inflate(
                LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {

        holder.itemView.setOnClickListener{
            onOperationClick(items[holder.adapterPosition])
        }
        holder.itemView.setOnLongClickListener(
            object : View.OnLongClickListener {
            override fun onLongClick(v: View?) : Boolean{
                onOperationLongClick(items[holder.adapterPosition])
            return true
            }
        })

        holder.binding.textExpression.text = items[position]?.expression
        holder.binding.textResult.text = items[position]?.result
    }

    override fun getItemCount(): Int {

        return items.size
    }

    fun updateItems(items: ArrayList<OperationUI>){
        this.items = items
        notifyDataSetChanged()
    }
}