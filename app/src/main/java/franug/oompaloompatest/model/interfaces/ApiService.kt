package franug.oompaloompatest.model.interfaces

import franug.oompaloompatest.model.ApiResponse
import franug.oompaloompatest.model.OompaLoompa
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("napptilus/oompa-loompas")
    suspend fun getOompaLoompas(@Query("page") page: Int): ApiResponse

    @GET("napptilus/oompa-loompas/{id}")
    suspend fun getOompaLoompasDetail(@Path("id") id: Int): OompaLoompa
}