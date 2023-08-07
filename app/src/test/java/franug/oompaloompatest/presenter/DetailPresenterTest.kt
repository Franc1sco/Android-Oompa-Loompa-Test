package franug.oompaloompatest.presenter

import franug.oompaloompatest.view.DetailActivity
import io.mockk.MockKAnnotations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@OptIn(ExperimentalCoroutinesApi::class)
class DetailPresenterTest {
    private var mockView = mock(DetailActivity::class.java)

    private var presenter = DetailPresenter()

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
            presenter.getDetails(1)
        }.run {
            verify(mockView).showError()
        }
    }
}