package franug.oompaloompatest.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import franug.oompaloompatest.R
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
        val name = oompaLoompa.firstName + " " + oompaLoompa.lastName
        holder.itemListBinding.tvName.text = name
        holder.itemListBinding.tvProfession.text = context.getString(R.string.profession, oompaLoompa.profession)
        holder.itemListBinding.tvGender.text = context.getString(R.string.gender, oompaLoompa.gender)
        if (oompaLoompa.image.isNullOrEmpty().not()) {
            Glide.with(context).load(oompaLoompa.image).into(holder.itemListBinding.ivGameImage)
        }

        holder.itemView.setOnClickListener { _ ->
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("ID", oompaLoompa.id)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    class ViewHolderData(var itemListBinding: ItemMainScreenAdapterBinding) : RecyclerView.ViewHolder(itemListBinding.root)
}
