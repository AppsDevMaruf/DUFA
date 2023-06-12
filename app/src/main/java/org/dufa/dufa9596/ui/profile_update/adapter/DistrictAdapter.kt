package org.dufa.dufa9596.ui.profile_update.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.dufa.dufa9596.R
import org.dufa.dufa9596.data.models.get_districts.District
import org.dufa.dufa9596.interfaces.DistrictSelectListener

class DistrictAdapter(
    var districtSelectListener: DistrictSelectListener,
    private var districtList: List<District>,
    var context: Context
) :
    RecyclerView.Adapter<DistrictAdapter.ViewHolder>() {

    // creating a variable for array list and context.


    // method for filtering our recyclerview items.
    fun filterList(filterList: ArrayList<District>) {
        // below line is to add our filtered
        // list in our course array list.
        districtList = filterList
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
        val model: District = districtList[position]
        holder.name.text = model.name
        holder.itemView.setOnClickListener {
            districtSelectListener.selectedDistrict(model)


        }


    }

    override fun getItemCount(): Int {
        // returning the size of array list.
        return districtList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // creating variables for our views.
        lateinit var name: TextView

        init {
            // initializing our views with their ids.
            name = itemView.findViewById(R.id.searchByItemTv)


        }
    }

}
