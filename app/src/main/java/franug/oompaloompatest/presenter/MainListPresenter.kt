package franug.oompaloompatest.presenter

import android.app.Activity
import franug.oompaloompatest.presenter.interfaces.IMainListPresenter
import franug.oompaloompatest.retrofit.RetrofitClient
import franug.oompaloompatest.view.interfaces.IMainListActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainListPresenter : IMainListPresenter {
    private var view: IMainListActivity? = null

    override fun attachView(view: Activity) {
        this.view = view as IMainListActivity
    }

    override fun detachView() {
        this.view = null
    }

    override suspend fun getList(page: Int) {
        val apiService = RetrofitClient.create(view as Activity)
        try {
            val oompaLoompas = apiService.getOompaLoompas(page)
            withContext(Dispatchers.Main) {
                view?.showList(oompaLoompas.results)
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                view?.showError()
            }
        }
    }
}