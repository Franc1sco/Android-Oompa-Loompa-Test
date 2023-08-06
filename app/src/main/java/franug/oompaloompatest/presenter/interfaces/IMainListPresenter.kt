package franug.oompaloompatest.presenter.interfaces

interface IMainListPresenter: IBasePresenter {
    suspend fun getList(page: Int)
}