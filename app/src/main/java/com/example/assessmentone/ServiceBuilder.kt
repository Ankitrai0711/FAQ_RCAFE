package com.example.assessmentone
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Headers

class ServiceBuilder {

        companion object{
            private var baseUrl = "https://restaurant-api.reciproci.com/"
            private val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

            val headerInterceptor=object:Interceptor{

                override fun intercept(chain: Interceptor.Chain): Response {
                    var headers=HashMap<String,String>()
                    headers["Content-Type"] = "application/json"
                    headers["DEVICE_ID"] = "930cb5749ebcdd68"
                    headers["Accept-Language"] = "EN"
                    headers["DEVICE_TYPE"] = "Android"
                    headers["APP_VERSION"] = "2.1.8.9"
                    headers["COUNTRY"] = "India"
                    headers["CITY"] = "Noida"
                    headers["Authorization"] = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJrV1BwaEtCaW9PcWFrL1h1YjdxcjZBPT0iLCJpYXQiOjE3MzU2MTg4MDAsImV4cCI6MTczNTY0NzYwMH0.ecL0JYIGCnArZUZBaA5dXBlTslvvZnF422kO6TNlI1c"
                    headers["GUEST_USER_TOKEN"] = ""
                    headers["DEVICE_DETAILS"] = "uL41/zMDxpBRlnnpbixRIOmFHZGIklmdGp6ZAIhTzByq7HJSXEaOyYDB2FK2WFnTWwPBEC6eV3ZKzUZEuBIb+NgflA9PpA1ct75H33VPW9R/NeXun80m+pOTW4n42oFdgR7Dm8+TIA9l1FPmMYoJpIWVBXVoN9B/um0XfV1rj3ICrOFvZdQnVV6l63T5mkDWYece8d+l26+P9t2w3JUlBy6bkE0x6D6iFug1rTaAs21G2yHr6JLa6ilVBwv80W04pagPDowiktRzQhxc6kUjEg=="
                    var request=chain.request()
                    request=request.newBuilder().apply {
                        headers.forEach{(key, value) -> addHeader(key, value) }
                    }.build()
                    val response=chain.proceed(request)
                    return response
                }
            }
            val okHttp = OkHttpClient.Builder().addInterceptor(logger).addInterceptor(headerInterceptor)
            val retrofit = Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).client(
                okHttp.build()).build()

            fun <T>buildServices(service : Class<T>):T{
                return retrofit.create(service)
            }

        }

    }
