package com.example.cardchecker.di

import com.example.cardchecker.MainActivity
import com.example.checker.parser.CardInfoApi
import com.example.checker.parser.CardInfoParser
import com.example.checker.validation.CardValidator

object MainActivityInjector {

    fun inject(activity: MainActivity) {
        activity.cardValidator = CardValidator()

        val api = CardInfoApi("https://lookup.binlist.net/")
        activity.cardInfoParser = CardInfoParser(api)
    }
}
