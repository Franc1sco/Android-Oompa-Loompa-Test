package franug.oompaloompatest.presenter.interfaces

import android.app.Activity

interface IBasePresenter {
    fun attachView(view: Activity)
    fun detachView()
}