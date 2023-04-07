package com.cha.tp_searchplaceapp.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.cha.tp_searchplaceapp.activities.PlaceUrlActivity
import com.cha.tp_searchplaceapp.databinding.RecyclerListFragmentItemBinding
import com.cha.tp_searchplaceapp.model.Place

class PlaceListRecyclerAdapter(var context: Context, var documents: MutableList<Place>) : RecyclerView.Adapter<PlaceListRecyclerAdapter.VH>() {

    inner class VH(var binding: RecyclerListFragmentItemBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH = VH(RecyclerListFragmentItemBinding.inflate(LayoutInflater.from(context),parent,false))

    override fun getItemCount(): Int = documents.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        val place: Place = documents[position]

        holder.binding.tvPlaceName.text = place.place_name
//        if(place.road_address_name == "") holder.binding.tvAddress.text = place.address_name
//        else holder.binding.tvAddress.text = place.road_address_name
        holder.binding.tvAddress.text = if(place.road_address_name == "") place.address_name else place.road_address_name
        holder.binding.tvDistance.text = "${place.distance}m"

        holder.binding.root.setOnClickListener {
            // 웹 뷰 보여줄 예정. -> 액티비티 추가
            val intent: Intent = Intent(context, PlaceUrlActivity::class.java)
            intent.putExtra("place_url",place.place_url)
            context.startActivity(intent)
        }
    }
}