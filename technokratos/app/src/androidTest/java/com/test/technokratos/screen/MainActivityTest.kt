package com.test.technokratos.screen

import org.junit.Test

class MainActivityTest {
    private var phone: String = ""

    @Test
    fun onCreate() {
        phone = "0000"
        callToNumber()
    }

    @Test
    fun callToNumber() {
        print(phone)
    }
}