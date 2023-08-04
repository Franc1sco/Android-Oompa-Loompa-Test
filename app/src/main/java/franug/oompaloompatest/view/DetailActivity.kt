package franug.oompaloompatest.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import franug.oompaloompatest.databinding.ActivityDetailBinding
import franug.oompaloompatest.model.OompaLoompa
import franug.oompaloompatest.presenter.DetailPresenter
import franug.oompaloompatest.presenter.interfaces.IDetailPresenter
import franug.oompaloompatest.view.interfaces.IDetailActivity

class DetailActivity: AppCompatActivity(), IDetailActivity {

    private lateinit var binding: ActivityDetailBinding
    private var presenter: IDetailPresenter = DetailPresenter()
    private var isTextExpanded = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter.attachView(this)
        val id = intent.getIntExtra("ID", 0)
        showLoadingScreen(true)
        presenter.getDetails(id)

    }

    override fun showDetails(oompaLoompa: OompaLoompa) {
        binding.nameTextView.text = oompaLoompa.first_name + " " + oompaLoompa.last_name
        binding.tvColorViewInfo.text = oompaLoompa.favorite.color
        binding.tvFoodViewInfo.text = oompaLoompa.favorite.food
        binding.expandTv.text = oompaLoompa.favorite.song
        if (oompaLoompa.image.isNullOrEmpty().not()) Glide.with(this).load(oompaLoompa.image).into(binding.profileImageView)
        showLoadingScreen(false)
    }

    override fun showError() {
        showLoadingScreen(false)
        Toast.makeText(this, "Error al cargar la lista", Toast.LENGTH_SHORT).show()
    }

    private fun showLoadingScreen(visibleLoading: Boolean) {
        if (visibleLoading) {
            binding.progressBar.visibility = View.VISIBLE
            binding.llContent.visibility = View.GONE
        } else {
            binding.progressBar.visibility = View.GONE
            binding.llContent.visibility = View.VISIBLE
        }
    }
}