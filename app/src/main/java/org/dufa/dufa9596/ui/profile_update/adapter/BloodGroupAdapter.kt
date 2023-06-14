package org.dufa.dufa9596.ui.profile_update.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.dufa.dufa9596.R
import org.dufa.dufa9596.data.models.search.blood.Bloodgroup
import org.dufa.dufa9596.interfaces.BloodGroupSelectListener

class BloodGroupAdapter(
    private var bloodGroupSelectListener: BloodGroupSelectListener,
    private var bloodGroupList: List<Bloodgroup>,
    var context: Context
) :
    RecyclerView.Adapter<BloodGroupAdapter.ViewHolder>() {

    // creating a variable for array list and context.


    // method for filtering our recyclerview items.
    fun filterList(filterList: ArrayList<Bloodgroup>) {
        // below line is to add our filtered
        // list in our course array list.
        bloodGroupList = filterList
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
        val model: Bloodgroup = bloodGroupList[position]
        holder.name.text = model.name
        holder.icon.setImageResource(R.drawable.blood)



        holder.itemView.setOnClickListener {
            bloodGroupSelectListener.selectedBloodGroup(model)


        }


    }

    override fun getItemCount(): Int {
        // returning the size of array list.
        return bloodGroupList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // creating variables for our views.
        var name: TextView
        var icon: ImageView

        init {
            // initializing our views with their ids.
            name = itemView.findViewById(R.id.searchByItemTv)
            icon = itemView.findViewById(R.id.itemIcon)


        }
    }

    // creating a constructor for our variables.

}
