package franug.oompaloompatest.presenter.interfaces

import franug.oompaloompatest.model.OompaLoompa

interface IMainListPresenter: IBasePresenter {
    suspend fun getList(page: Int)
    fun getFilteredList(adapter: ArrayList<OompaLoompa>, gender: String, profession: String)
}