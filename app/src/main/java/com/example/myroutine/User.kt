package com.example.myroutine

/*denk auch an den User im DBHelper*/
data class User(val name: String, val bday: String) {
    companion object {
        var currenUser: User? = null
    }
}