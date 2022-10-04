package Interface
import Beans.Post
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PlaceHolderApi {
    // consumir los datos de la api
    @GET("posts")
    fun getListado(): Call<List<Post>>

    @GET("posts/{id}")
    fun getById(@Path("id") id: Int): Call<Post>

    @GET("posts")
    fun getByUserId(@Query("userId") userId: Int): Call<List<Post>>

}