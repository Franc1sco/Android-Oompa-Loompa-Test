package franug.oompaloompatest.presenter

import franug.oompaloompatest.view.MainListActivity
import io.mockk.MockKAnnotations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner


@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class MainListPresenterTest {
    private var mockView = mock(MainListActivity::class.java)

    private var presenter = MainListPresenter()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        presenter.attachView(mockView)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun onAfter() {
        Dispatchers.resetMain()
    }

    @Test
    fun `should showError`() {
        runBlocking {
            presenter.getList(1)
        }.run {
            verify(mockView).showError()
        }
    }

}