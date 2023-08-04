package franug.oompaloompatest.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import franug.oompaloompatest.databinding.ItemMainScreenAdapterBinding
import franug.oompaloompatest.model.OompaLoompa
import franug.oompaloompatest.view.DetailActivity


class OompaLoompaAdapter(private var listData: ArrayList<OompaLoompa>, private var context: Context) :
    RecyclerView.Adapter<OompaLoompaAdapter.ViewHolderData>() {

    // el metodo onCreateViewHolder es el que se ejecuta cuando se crea un nuevo viewholder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderData {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemListBinding: ItemMainScreenAdapterBinding =
            ItemMainScreenAdapterBinding.inflate(layoutInflater, parent, false)
        return ViewHolderData(itemListBinding)
    }

    // el metodo onBindViewHolder es el que se ejecuta cuando se carga un viewholder y es donde se cargan los datos
    override fun onBindViewHolder(holder: ViewHolderData, position: Int) {
        val oompaLoompa: OompaLoompa = listData[position]
        holder.itemListBinding.tvName.text = oompaLoompa.first_name
        holder.itemListBinding.tvStatus.text = oompaLoompa.last_name
        if (oompaLoompa.image.isNullOrEmpty().not()) Glide.with(context).load(oompaLoompa.image).into(holder.itemListBinding.ivGameImage)

        holder.itemView.setOnClickListener { view ->
            // iniciar la actividad de DetailActivity pasarle el id del oompaLoompa
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("ID", oompaLoompa.id)
            context.startActivity(intent)
            //Toast.makeText(context, "Clicked on item ${oompaLoompa.first_name}", Toast.LENGTH_SHORT).show()
        }
    }

    // el metodo getItemCount es el que se ejecuta cuando se necesita saber la cantidad de elementos que tiene el recyclerView
    override fun getItemCount(): Int {
        return listData.size
    }

    // el xml que le cargamos a la vista
    class ViewHolderData(var itemListBinding: ItemMainScreenAdapterBinding) :
        RecyclerView.ViewHolder(itemListBinding.root) {
    }

}
