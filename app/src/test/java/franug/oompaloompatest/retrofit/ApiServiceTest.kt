package franug.oompaloompatest.retrofit

import com.google.gson.Gson
import franug.oompaloompatest.model.ApiResponse
import franug.oompaloompatest.model.OompaLoompa
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiServiceTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var apiService: ApiService

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        apiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `getOompaLoompas success`() = runBlocking {
        val oompaLoompaList = Array(1) { OompaLoompa() }
        val mockResponse = ApiResponse(1, 1, oompaLoompaList)// Crea un objeto ApiResponse de prueba
        mockWebServer.enqueue(MockResponse().setBody(Gson().toJson(mockResponse)))

        val response = apiService.getOompaLoompas(1)

        assert(response == mockResponse)
    }
}