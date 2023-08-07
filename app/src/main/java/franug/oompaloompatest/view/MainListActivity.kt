package franug.oompaloompatest.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import franug.oompaloompatest.R
import franug.oompaloompatest.adapter.OompaLoompaAdapter
import franug.oompaloompatest.databinding.ActivityMainBinding
import franug.oompaloompatest.model.OompaLoompa
import franug.oompaloompatest.view.interfaces.IMainListActivity
import franug.oompaloompatest.presenter.interfaces.IMainListPresenter
import franug.oompaloompatest.presenter.MainListPresenter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainListActivity : AppCompatActivity(), IMainListActivity {

    private lateinit var binding: ActivityMainBinding
    private var listDataAdapter = ArrayList<OompaLoompa>()
    private var adapter: OompaLoompaAdapter? = null
    private var presenter: IMainListPresenter = MainListPresenter()
    private var currentPage = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter.attachView(this)

        binding.recyclerView.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL, false
        )

        setPageViews()
        setupFilters()
        showLoadingScreen(true)
        lifecycleScope.launch(Dispatchers.IO) {
            presenter.getList(currentPage)
        }

        binding.ivOrderNext.setOnClickListener {
            showLoadingScreen(true)
            lifecycleScope.launch(Dispatchers.IO) {
                presenter.getList(++currentPage)
            }
        }
        binding.ivOrderPrevious.setOnClickListener {
            showLoadingScreen(true)
            lifecycleScope.launch(Dispatchers.IO) {
                presenter.getList(--currentPage)
            }
        }
    }

    private fun setupFilters() {
        binding.etGenderFilter.addTextChangedListener {
            presenter.getFilteredList(listDataAdapter, binding.etGenderFilter.text.toString(), binding.etProfessionFilter.text.toString())
        }
        binding.etProfessionFilter.addTextChangedListener {
            presenter.getFilteredList(listDataAdapter, binding.etGenderFilter.text.toString(), binding.etProfessionFilter.text.toString())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun showList(loompaList: Array<OompaLoompa>) {
        listDataAdapter.clear()
        listDataAdapter.addAll(loompaList)
        setPageViews()
        presenter.getFilteredList(listDataAdapter, binding.etGenderFilter.text.toString(), binding.etProfessionFilter.text.toString())
    }

    override fun showFilteredList(loompaList: Array<OompaLoompa>) {
        val listFilteredData = ArrayList<OompaLoompa>()
        listFilteredData.addAll(loompaList)
        binding.recyclerView.setHasFixedSize(true)
        adapter = OompaLoompaAdapter(listFilteredData, context = this)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.scrollToPosition(0)
        if (listFilteredData.size == 0) {
            Toast.makeText(this, getString(R.string.no_results), Toast.LENGTH_SHORT).show()
        }
        showLoadingScreen(false)
    }

    override fun showError()
    {
        listDataAdapter.clear()
        adapter = OompaLoompaAdapter(listDataAdapter, context = this)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.scrollToPosition(0)
        showLoadingScreen(false)
        setPageViews()
        Toast.makeText(this, getString(R.string.load_error), Toast.LENGTH_SHORT).show()
    }

    private fun showLoadingScreen(visibleLoading: Boolean) {
        if (visibleLoading) {
            binding.progressBar.visibility = View.VISIBLE
            binding.clHeader.visibility = View.GONE
            binding.clContent.visibility = View.GONE
            binding.clHeaderFilter.visibility = View.GONE
        } else {
            binding.progressBar.visibility = View.GONE
            binding.clHeader.visibility = View.VISIBLE
            binding.clContent.visibility = View.VISIBLE
            binding.clHeaderFilter.visibility = View.VISIBLE
        }
    }

    private fun setPageViews() {
        binding.tvCurrentPage.text = currentPage.toString()
        if (currentPage > 1) binding.ivOrderPrevious.visibility = View.VISIBLE
        else binding.ivOrderPrevious.visibility = View.GONE
    }
}