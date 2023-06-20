package com.example.Sseudam

import java.io.Serializable

class Comunity(
    var title: String,
    var contents: String
): Serializable {
    constructor(): this("","")

}