package franug.oompaloompatest.presenter

import franug.oompaloompatest.model.ApiResponse

interface IMainListActivity {
    fun applyList(gameList: ApiResponse)
}