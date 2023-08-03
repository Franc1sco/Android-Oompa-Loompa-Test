package franug.oompaloompatest.presenter

interface IMainListPresenter: IBasePresenter {
    suspend fun getList(userId: Int)
}