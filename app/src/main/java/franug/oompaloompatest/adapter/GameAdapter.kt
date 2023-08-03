package franug.oompaloompatest.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import franug.oompaloompatest.databinding.ItemMainScreenAdapterBinding
import franug.oompaloompatest.model.OompaLoompa

class GameAdapter(private var listData: ArrayList<OompaLoompa>, private var context: Context) :
    RecyclerView.Adapter<GameAdapter.ViewHolderData>() {

    // el metodo onCreateViewHolder es el que se ejecuta cuando se crea un nuevo viewholder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderData {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemListBinding: ItemMainScreenAdapterBinding =
            ItemMainScreenAdapterBinding.inflate(layoutInflater, parent, false)
        return ViewHolderData(itemListBinding)
    }

    // el metodo onBindViewHolder es el que se ejecuta cuando se carga un viewholder y es donde se cargan los datos
    override fun onBindViewHolder(holder: ViewHolderData, position: Int) {
        val game: OompaLoompa = listData[position]
        holder.itemListBinding.tvName.text = game.first_name
        holder.itemListBinding.tvStatus.text = game.last_name
        if (game.image.isNullOrEmpty().not()) Glide.with(context).load(game.image).into(holder.itemListBinding.ivGameImage)
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
