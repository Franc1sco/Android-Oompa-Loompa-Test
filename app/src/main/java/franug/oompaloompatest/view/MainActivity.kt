package franug.oompaloompatest.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import franug.oompaloompatest.adapter.GameAdapter
import franug.oompaloompatest.databinding.ActivityMainBinding
import franug.oompaloompatest.model.ApiResponse
import franug.oompaloompatest.model.OompaLoompa
import franug.oompaloompatest.presenter.IMainListActivity
import franug.oompaloompatest.presenter.IMainListPresenter
import franug.oompaloompatest.presenter.MainListPresenter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity(), IMainListActivity {

    private lateinit var binding: ActivityMainBinding
    private var listDataAdapter = ArrayList<OompaLoompa>()
    private var adapter: GameAdapter? = null
    private var presenter: IMainListPresenter = MainListPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter.attachView(this)

        binding.recyclerView.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL, false
        )

        showLoadingScreen(true)
        lifecycleScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                presenter.getList(1)
            }
        }.invokeOnCompletion {
            showLoadingScreen(false)
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

    override fun applyList(gameList: ApiResponse) {
        binding.recyclerView.setHasFixedSize(true)
        listDataAdapter.clear()
        listDataAdapter.addAll(gameList.results)
        adapter = GameAdapter(listDataAdapter, context = this)
        binding.recyclerView.adapter = adapter
    }
}