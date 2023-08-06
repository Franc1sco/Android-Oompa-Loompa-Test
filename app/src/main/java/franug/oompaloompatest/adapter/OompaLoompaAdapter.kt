package franug.oompaloompatest.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import franug.oompaloompatest.databinding.ItemMainScreenAdapterBinding
import franug.oompaloompatest.model.OompaLoompa
import franug.oompaloompatest.view.DetailActivity


class OompaLoompaAdapter(private var listData: ArrayList<OompaLoompa>, private var context: Context) :
    RecyclerView.Adapter<OompaLoompaAdapter.ViewHolderData>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderData {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemListBinding: ItemMainScreenAdapterBinding =
            ItemMainScreenAdapterBinding.inflate(layoutInflater, parent, false)
        return ViewHolderData(itemListBinding)
    }

    override fun onBindViewHolder(holder: ViewHolderData, position: Int) {
        val oompaLoompa: OompaLoompa = listData[position]
        holder.itemListBinding.tvName.text = oompaLoompa.first_name + " " + oompaLoompa.last_name
        holder.itemListBinding.tvProfession.text = "Profesión: " + oompaLoompa.profession
        holder.itemListBinding.tvGender.text = "Género: " + oompaLoompa.gender
        if (oompaLoompa.image.isNullOrEmpty().not()) {
            Glide.with(context).load(oompaLoompa.image).into(holder.itemListBinding.ivGameImage)
        }

        holder.itemView.setOnClickListener { view ->
            // iniciar la actividad de DetailActivity pasarle el id del oompaLoompa
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("ID", oompaLoompa.id)
            context.startActivity(intent)
            //Toast.makeText(context, "Clicked on item ${oompaLoompa.first_name}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    class ViewHolderData(var itemListBinding: ItemMainScreenAdapterBinding) :
        RecyclerView.ViewHolder(itemListBinding.root) {
    }

}
