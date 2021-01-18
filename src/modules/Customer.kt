package com.example.modules

import kotlinx.serialization.Serializable

@Serializable
data class Customer(val username: String, val email: String, val bio: String, val city: String,
                    val uuid: String, val regDate: String, val magicNo: Int)
