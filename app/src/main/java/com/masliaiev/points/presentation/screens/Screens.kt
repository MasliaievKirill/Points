package com.masliaiev.points.presentation.screens

sealed class Screens(val route: String) {

    object SplashScreen : Screens("splash_screen")
    object Login : Screens("login")
    object SignUp : Screens("sing_up")
    object AppMap : Screens("app_map")
    object PointsList : Screens("points_list")
    object Profile : Screens("profile/{${loginArgument}}") {
        fun getRouteWithArgument(argument: String): String {
            return "profile/$argument"
        }
    }

    object AddPoint : Screens("add_point/{${loginArgument}}/{${latitude}}/{${longitude}}") {
        fun getRouteWithArgument(login: String, latitude: Double, longitude: Double): String {
            return "add_point/$login/$latitude/$longitude"
        }
    }

    companion object {
        const val loginArgument = "login"
        const val latitude = "latitude"
        const val longitude = "longitude"
    }

}