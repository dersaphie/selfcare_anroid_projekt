package api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


interface PexelApi {

        @Headers("Authorization: 563492ad6f91700001000001bd0e748994414fcb845b9ab40da21faf")
        @GET("v1/collections")
        fun getPexels(): Single<PexelsWrapper>

}
