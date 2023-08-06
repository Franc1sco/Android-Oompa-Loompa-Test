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
import franug.oompaloompatest.R
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
        val name = oompaLoompa.firstName + " " + oompaLoompa.lastName
        binding.tvName.text = name
        binding.tvProfession.text = getString(R.string.profession, oompaLoompa.profession)
        binding.tvEmail.text = getString(R.string.email, oompaLoompa.email)
        binding.tvAge.text = getString(R.string.age, oompaLoompa.age)
        binding.tvCountry.text = getString(R.string.country, oompaLoompa.country)
        binding.tvHeight.text = getString(R.string.height, oompaLoompa.height)
        binding.tvColor.text = getString(R.string.favorite_color, oompaLoompa.favorite?.color)
        binding.tvFood.text = getString(R.string.favorite_food, oompaLoompa.favorite?.food)
        binding.tvGender.text = getString(R.string.gender, oompaLoompa.gender)
        Glide.with(this).load(oompaLoompa.image).into(binding.profileImageView)
        showLoadingScreen(false)
        binding.tvSongLyricsShow.setOnClickListener {
            // show dialog
            val builder = AlertDialog.Builder(this)
            builder.setTitle(R.string.favorite_song)
            builder.setMessage(oompaLoompa.favorite?.song)
            builder.setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            builder.show()
        }


    }

    override fun showError() {
        showLoadingScreen(false)
        Toast.makeText(this, getString(R.string.load_error), Toast.LENGTH_SHORT).show()
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