package franug.oompaloompatest.view.interfaces

import franug.oompaloompatest.model.OompaLoompa

interface IMainListActivity {
    fun showList(loompaList: Array<OompaLoompa>)
    fun showError()
}