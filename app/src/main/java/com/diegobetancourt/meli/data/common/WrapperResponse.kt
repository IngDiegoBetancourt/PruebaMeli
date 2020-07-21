package com.diegobetancourt.meli.data.common

import com.diegobetancourt.meli.data.repository.products.remote.retrofit.ErrorResponseModel
import com.diegobetancourt.meli.data.repository.products.remote.retrofit.ProductApiContract
import com.squareup.moshi.Moshi
import okhttp3.ResponseBody
import retrofit2.Response


/**
 * This file contains the classes for wrap remote responses data and errors
 */

//Class for manage generical response
open class WrapperResponse<T> (
    var success: Boolean = false,
    var data: T? = null,
    var error: CatchedError? = null,
    var code: Int = 0
){
    fun body(): T{
        return data!!
    }
}

//Class for manage generical error
data class CatchedError (
    var type: Int = 0,
    var message: String = "Ha ocurrido un error"
)

//Enum typical errors
enum class ErrorTemplate(var type: Int,
                         var message: String){

    UNDEFINED_ERROR(type = 0,message = "Ha ocurrido un error"),
    COMMUNICATION_ERROR(type = 1,message = "No hay conexion a internet"),
    REPO_ERROR(type = 2,message = "Ha ocurrio un error en durante la manipulacion"),
    HTTP_ERROR(type = 3,message = "La petición ha fallado");

    fun toError():CatchedError{
        return CatchedError(type,message)
    }

}

//Class for manage generical response of retrofit request
class WrapperFromRequest<T>(response: Response<T>) : WrapperResponse<T>(){

    init{
            if(response.isSuccessful){
                success = true
                data = response.body()!!
                code = response.code()
            }else{
                success = false
                error = getErrorFromBody(response.errorBody())
                code = response.code()
            }
    }

    fun getDefaultError(): CatchedError{
        return ErrorTemplate.HTTP_ERROR.toError()
    }

     fun getErrorFromBody(errorBody: ResponseBody?): CatchedError {
        return if (errorBody != null) {
            val catchedError = CatchedError()
            val moshi = Moshi.Builder().build()
            val errorAdapter = moshi.adapter(ErrorResponseModel::class.java)
            val error: ErrorResponseModel? = errorAdapter.fromJson(errorBody.source())
            error?.let {
                catchedError.message = it.message
                catchedError.type = ErrorTemplate.HTTP_ERROR.type
                catchedError
            }?: getDefaultError()
        } else {
            getDefaultError()
        }
    }

}

//Class for obtains cached error from exceptions
class WrapperFromException<T>(exception: Throwable) : WrapperResponse<T>(){

    init{
            exception.printStackTrace()
            success = false

            when(exception){
                is java.net.UnknownHostException -> error = ErrorTemplate.COMMUNICATION_ERROR.toError()
                else -> error = ErrorTemplate.UNDEFINED_ERROR.toError()
            }

    }
}