package franug.oompaloompatest.presenter

import franug.oompaloompatest.model.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("napptilus/oompa-loompas")
    suspend fun getOompaLoompas(@Query("page") page: Int): ApiResponse
}