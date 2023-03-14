package com.marufalam.dufa.ui.profile_update.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.marufalam.dufa.R
import com.marufalam.dufa.data.models.get_halls.Hall
import com.marufalam.dufa.data.models.search.blood.Bloodgroup
import com.marufalam.dufa.interfaces.BloodGroupSelectListener
import com.marufalam.dufa.interfaces.HallSelectListener

class HallAdapter(
    var hallSelectListener: HallSelectListener,
    private var hallList: ArrayList<Hall>,
    var context: Context
) :
    RecyclerView.Adapter<HallAdapter.ViewHolder>() {

    // creating a variable for array list and context.


    // method for filtering our recyclerview items.
    fun filterList(filterList: ArrayList<Hall>) {
        // below line is to add our filtered
        // list in our course array list.
        hallList = filterList
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // below line is to inflate our layout.
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_search, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // setting data to our views of recycler view.
        val model: Hall = hallList[position]
        holder.name.text = model.name


        holder.itemView.setOnClickListener {
            hallSelectListener.selectedHall(model)


        }


    }

    override fun getItemCount(): Int {
        // returning the size of array list.
        return hallList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // creating variables for our views.
        lateinit var name: TextView

        init {
            // initializing our views with their ids.
            name = itemView.findViewById(R.id.searchByItemTv)


        }
    }

    // creating a constructor for our variables.

}
