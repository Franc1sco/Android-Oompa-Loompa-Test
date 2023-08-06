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

    // metodo que se llama cuando se crea el presenter y se le pasa la vista
    override fun attachView(view: Activity) {
        this.view = view as IMainListActivity
    }

    // metodo que se llama cuando se destruye el presenter y se limpia la vista
    override fun detachView() {
        this.view = null
    }

    override fun getList(page: Int) {
        val apiService = RetrofitClient.create(context = view as Activity)

        GlobalScope.launch(Dispatchers.IO) {
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
}