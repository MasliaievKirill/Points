package com.masliaiev.points.helpers

sealed class Response {

    object Success : Response()
    class Error(val massage: String) : Response()
}
