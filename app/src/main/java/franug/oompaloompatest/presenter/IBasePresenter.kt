package franug.oompaloompatest.presenter

import android.app.Activity

interface IBasePresenter {
    fun attachView(view: Activity)
    fun detachView()
}