package com.deny.ibitur

class Greeting {
    fun greeting(): String {
        return "Hello, ${Platform().platform}!"
    }
}