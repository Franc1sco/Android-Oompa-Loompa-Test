package franug.oompaloompatest.presenter

import android.app.Activity
import franug.oompaloompatest.presenter.interfaces.IDetailPresenter
import franug.oompaloompatest.retrofit.RetrofitClient
import franug.oompaloompatest.view.interfaces.IDetailActivity
import franug.oompaloompatest.view.interfaces.IMainListActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailPresenter: IDetailPresenter {
    private var view: IDetailActivity? = null
    override fun getDetails(id: Int) {
        val apiService = RetrofitClient.create(context = view as Activity)

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val oompaLoompas = apiService.getOompaLoompasDetail(id)
                withContext(Dispatchers.Main) {
                    view?.showDetails(oompaLoompas)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    view?.showError()
                }
            }
        }
    }

    override fun attachView(view: Activity) {
        this.view = view as IDetailActivity
    }

    override fun detachView() {
        this.view = null
    }
}