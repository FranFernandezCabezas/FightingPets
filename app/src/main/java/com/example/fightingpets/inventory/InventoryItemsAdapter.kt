package com.example.fightingpets.inventory

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fightingpets.InventoryItem
import com.example.fightingpets.R

class InventoryItemsAdapter(var items: ArrayList<InventoryItem>, private val onClickListener: OnClickListener) : RecyclerView.Adapter<InventoryItemsAdapter.ItemsViewHolder>() {

    init {
        this.items = items
    }

    class ItemsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var icon: ImageView
        private var name: TextView
        private var function: TextView
        private var valor: TextView
        private var quantity: TextView


        init {
            icon = itemView.findViewById(R.id.item_imageView)
            name = itemView.findViewById(R.id.item_name_textView)
            function = itemView.findViewById(R.id.item_function_textView)
            valor = itemView.findViewById(R.id.item_valor_textView)
            quantity = itemView.findViewById(R.id.num_items_textView)
        }

        fun bindTarjeta(item: InventoryItem)  {
            icon.setImageResource(item.icon)
            name.text = item.name
            valor.text = item.valor.toString()
            function.text = item.function.value
            quantity.text = item.quantity.toString()
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ItemsViewHolder {
        val itemView = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_list_item, viewGroup, false)
        return ItemsViewHolder(itemView)
    }

    override fun onBindViewHolder(viewHolder: ItemsViewHolder, pos: Int) {
        val item = items[pos]
        viewHolder.itemView.setOnClickListener {
            onClickListener.onClick(item)
        }
        viewHolder.bindTarjeta(item)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class OnClickListener(val clickListener: (item: InventoryItem) -> Unit) {
        fun onClick(item: InventoryItem) = clickListener(item)
    }
}