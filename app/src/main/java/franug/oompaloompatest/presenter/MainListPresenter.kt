package franug.oompaloompatest.presenter

import android.app.Activity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainListPresenter : IMainListPresenter {
    private var view: IMainListActivity? = null
    val retrofit = Retrofit.Builder()
        .baseUrl("https://2q2woep105.execute-api.eu-west-1.amazonaws.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService = retrofit.create(ApiService::class.java)

    // metodo que se llama cuando se crea el presenter y se le pasa la vista
    override fun attachView(view: Activity) {
        this.view = view as IMainListActivity
    }

    // metodo que se llama cuando se destruye el presenter y se limpia la vista
    override fun detachView() {
        this.view = null
    }

    override suspend fun getList(userId: Int) {
        val list = apiService.getOompaLoompas(1)
        withContext(Dispatchers.Main) {
            view?.applyList(list)
        }
    }
}