package cu.sld.hluciavolleylistvikotlin

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import cu.sld.hluciavolleylistvikotlin.R
import com.squareup.picasso.Picasso
import java.util.ArrayList

class ListAdapter(private val context: Context, private val dataModelArrayList: ArrayList<DataModel>) : BaseAdapter() {

    override fun getViewTypeCount(): Int {
        return count
    }

    override fun getItemViewType(position: Int): Int {

        return position
    }

    override fun getCount(): Int {
        return dataModelArrayList.size
    }

    override fun getItem(position: Int): Any {
        return dataModelArrayList[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val holder: ViewHolder

        if (convertView == null) {
            holder = ViewHolder()
            val inflater = context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inflater.inflate(R.layout.lv_player, null, true)

            holder.iv = convertView!!.findViewById<View>(R.id.iv) as ImageView
            holder.tvname = convertView.findViewById<View>(R.id.name) as TextView
            holder.tvcountry = convertView.findViewById<View>(R.id.country) as TextView
            holder.tvcity = convertView.findViewById<View>(R.id.city) as TextView

            convertView.tag = holder
        } else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = convertView.tag as ViewHolder
        }

        Picasso.get().load(dataModelArrayList[position].getimgURLs()).into(holder.iv)
        holder.tvname!!.text = "Name: " + dataModelArrayList[position].getNames()
        holder.tvcountry!!.text = "Country: " + dataModelArrayList[position].getCountrys()
        holder.tvcity!!.text = "City: " + dataModelArrayList[position].getCitys()

        return convertView
    }

    private inner class ViewHolder {

         var tvname: TextView? = null
         var tvcountry: TextView? = null
         var tvcity: TextView? = null
        var iv: ImageView? = null
    }

}
