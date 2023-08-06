package franug.oompaloompatest.presenter

import android.app.Activity
import franug.oompaloompatest.presenter.interfaces.IDetailPresenter
import franug.oompaloompatest.retrofit.RetrofitClient
import franug.oompaloompatest.view.interfaces.IDetailActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DetailPresenter: IDetailPresenter {
    private var view: IDetailActivity? = null
    override suspend fun getDetails(id: Int) {
        try {
            val apiService = RetrofitClient.create(context = view as Activity)
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

    override fun attachView(view: Activity) {
        this.view = view as IDetailActivity
    }

    override fun detachView() {
        this.view = null
    }
}