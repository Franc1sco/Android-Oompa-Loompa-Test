package franug.oompaloompatest.view

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import franug.oompaloompatest.AndroidApplication
import franug.oompaloompatest.adapter.OompaLoompaAdapter
import franug.oompaloompatest.databinding.ActivityMainBinding
import franug.oompaloompatest.model.OompaLoompa
import franug.oompaloompatest.view.interfaces.IMainListActivity
import franug.oompaloompatest.presenter.interfaces.IMainListPresenter
import franug.oompaloompatest.presenter.MainListPresenter
import franug.oompaloompatest.utils.ConnectivityReceiver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity(), IMainListActivity {

    private lateinit var binding: ActivityMainBinding
    private var listDataAdapter = ArrayList<OompaLoompa>()
    private var adapter: OompaLoompaAdapter? = null
    private var presenter: IMainListPresenter = MainListPresenter()
    private var currentPage = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intentFilter = IntentFilter(ConnectivityReceiver.NETWORK_AVAILABLE_ACTION)
        LocalBroadcastManager.getInstance(this).registerReceiver(object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                val isConnected = intent.getBooleanExtra(ConnectivityReceiver.IS_NETWORK_AVAILABLE, false)
                AndroidApplication.getInstance?.connected = isConnected
            }
        }, intentFilter)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter.attachView(this)

        binding.recyclerView.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL, false
        )

        showLoadingScreen(true)
        lifecycleScope.launch(Dispatchers.IO) {
            presenter.getList(currentPage)
        }

        binding.ivOrderNext.setOnClickListener {
            showLoadingScreen(true)
            lifecycleScope.launch(Dispatchers.IO) {
                presenter.getList(++currentPage)
            }
            binding.ivCurrentPage.text = currentPage.toString()
            if (currentPage > 1) binding.ivOrderPrevious.visibility = View.VISIBLE
        }
        binding.ivOrderPrevious.setOnClickListener {
            showLoadingScreen(true)
            lifecycleScope.launch(Dispatchers.IO) {
                presenter.getList(--currentPage)
            }
            binding.ivCurrentPage.text = currentPage.toString()
            if (currentPage == 1) binding.ivOrderPrevious.visibility = View.GONE
        }
    }

    private fun showLoadingScreen(visibleLoading: Boolean) {
        if (visibleLoading) {
            binding.progressBar.visibility = View.VISIBLE
            binding.clEnd.visibility = View.GONE
            binding.clSubTitle.visibility = View.GONE
        } else {
            binding.progressBar.visibility = View.GONE
            binding.clEnd.visibility = View.VISIBLE
            binding.clSubTitle.visibility = View.VISIBLE
        }
    }

    override fun showList(loompaList: Array<OompaLoompa>) {
        showLoadingScreen(false)
        //binding.recyclerView.setHasFixedSize(true)
        listDataAdapter.clear()
        listDataAdapter.addAll(loompaList)
        adapter = OompaLoompaAdapter(listDataAdapter, context = this)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.setOnClickListener {
            AdapterView.OnItemClickListener { _, _, position, _ ->
                Toast.makeText(this, "Item con nombre ${loompaList[position].first_name}", Toast.LENGTH_SHORT).show()
            }
        }
        // mover el scroll al principio
        binding.recyclerView.scrollToPosition(0)
    }

    override fun showError() {
        showLoadingScreen(false)
        Toast.makeText(this, "Error al cargar la lista", Toast.LENGTH_SHORT).show()
    }
}