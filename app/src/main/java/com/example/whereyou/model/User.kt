package com.example.whereyou.model

import com.parse.ParseObject

class User : ParseObject() {
    lateinit var username : String
    lateinit var password : String
}