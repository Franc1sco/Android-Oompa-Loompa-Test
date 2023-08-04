package franug.oompaloompatest.view.interfaces

import franug.oompaloompatest.model.OompaLoompa

interface IDetailActivity {
    fun showDetails(oompaLoompa: OompaLoompa)
    fun showError()
}