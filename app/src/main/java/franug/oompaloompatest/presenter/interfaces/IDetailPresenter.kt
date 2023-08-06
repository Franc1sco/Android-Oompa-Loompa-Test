package franug.oompaloompatest.presenter.interfaces

interface IDetailPresenter: IBasePresenter {
    suspend fun getDetails(id: Int)
}