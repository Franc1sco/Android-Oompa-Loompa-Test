package franug.oompaloompatest.view

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.bumptech.glide.Glide
import franug.oompaloompatest.AndroidApplication
import franug.oompaloompatest.databinding.ActivityDetailBinding
import franug.oompaloompatest.model.OompaLoompa
import franug.oompaloompatest.presenter.DetailPresenter
import franug.oompaloompatest.presenter.interfaces.IDetailPresenter
import franug.oompaloompatest.utils.ConnectivityReceiver
import franug.oompaloompatest.view.interfaces.IDetailActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class DetailActivity: AppCompatActivity(), IDetailActivity {

    private lateinit var binding: ActivityDetailBinding
    private var presenter: IDetailPresenter = DetailPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intentFilter = IntentFilter(ConnectivityReceiver.NETWORK_AVAILABLE_ACTION)
        LocalBroadcastManager.getInstance(this).registerReceiver(object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                val isConnected = intent.getBooleanExtra(ConnectivityReceiver.IS_NETWORK_AVAILABLE, false)
                AndroidApplication.getInstance?.connected = isConnected
            }
        }, intentFilter)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter.attachView(this)
        val id = intent.getIntExtra("ID", 0)
        showLoadingScreen(true)
        lifecycleScope.launch(Dispatchers.IO) {
            presenter.getDetails(id)
        }

    }

    override fun showDetails(oompaLoompa: OompaLoompa) {
        binding.nameTextView.text = oompaLoompa.first_name + " " + oompaLoompa.last_name
        binding.professionTextView.text = "Profesión: " + oompaLoompa.profession
        binding.emailTextView.text = "Email: " + oompaLoompa.email
        binding.ageTextView.text = "Edad: " + oompaLoompa.age.toString()
        binding.countryTextView.text = "País: " + oompaLoompa.country
        binding.heightTextView.text = "Altura: " + oompaLoompa.height.toString()
        binding.colorTextView.text = "Color favorito: " + oompaLoompa.favorite?.color
        binding.foodTextView.text = "Comida favorita: " + oompaLoompa.favorite?.food
        binding.genderTextView.text = "Género: " + oompaLoompa.gender
        Glide.with(this).load(oompaLoompa.image).into(binding.profileImageView)
        showLoadingScreen(false)
        binding.songLyricsTextViewShow.setOnClickListener {
            // show dialog
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Letra de canción favorita")
            builder.setMessage(oompaLoompa.favorite?.song)
            builder.setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            builder.show()
        }


    }

    override fun showError() {
        showLoadingScreen(false)
        Toast.makeText(this, "Error al cargar la lista", Toast.LENGTH_SHORT).show()
    }

    private fun showLoadingScreen(visibleLoading: Boolean) {
        if (visibleLoading) {
            binding.progressBar.visibility = View.VISIBLE
            binding.clContent.visibility = View.GONE
        } else {
            binding.progressBar.visibility = View.GONE
            binding.clContent.visibility = View.VISIBLE
        }
    }
}