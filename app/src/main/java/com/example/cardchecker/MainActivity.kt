package com.example.cardchecker

import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cardchecker.di.MainActivityInjector
import com.example.cardchecker.extension.visible
import com.example.cardchecker.utils.SimpleTextChangedListener
import com.example.checker.parser.CardInfo
import com.example.checker.parser.CardInfoParser
import com.example.checker.validation.CardValidator
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        private const val CHECK_TASK_DEBOUNCE_MILLIS = 300L
    }

    // Dependencies
    lateinit var cardValidator: CardValidator
    lateinit var cardInfoParser: CardInfoParser

    private val handler = Handler()
    private val checkTask = Runnable { checkNumber() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        injectDependencies()
        configureView()
    }

    override fun onDestroy() {
        super.onDestroy()
        cardInfoParser.release()
    }


    private fun injectDependencies() {
        MainActivityInjector.inject(this)
    }

    private fun configureView() {
        cardNumberInput.addTextChangedListener(SimpleTextChangedListener {
            cardNumberInput.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_credit_card, 0, 0, 0)
            handler.removeCallbacks(checkTask)
            handler.postDelayed(checkTask, CHECK_TASK_DEBOUNCE_MILLIS)
        })
        cardNumberInput.requestFocus()
    }

    private fun checkNumber() {
        // validate card number
        val clearValue = cardNumberInput.text?.toString().orEmpty().replace(" ", "")
        val isValid = cardValidator.isValid(clearValue)

        // update state icon
        val iconRes = if (isValid) R.drawable.ic_check else R.drawable.ic_not_check
        cardNumberInput.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_credit_card, 0, iconRes, 0)

        if (isValid) {
            cardInfoProgress.visible(true)
            cardInfoParser.parseInfo(clearValue) { result ->
                runOnUiThread { handleCardInfoResult(result) }
            }
        } else {
            cardInfoLayout.visible(false)
        }
    }

    private fun handleCardInfoResult(result: Result<CardInfo>) {
        cardInfoProgress.visible(false)

        if (result.isSuccess) {
            cardInfoLayout.visible(true)
            val cardInfo = result.getOrNull() ?: return

            cardScheme.text = getString(R.string.card_scheme_template, cardInfo.scheme)
            cardScheme.visible(!cardInfo.scheme.isNullOrEmpty())

            cardType.text = getString(R.string.card_type_template, cardInfo.type)
            cardType.visible(!cardInfo.type.isNullOrEmpty())

            cardCountry.text = getString(R.string.card_country_template, cardInfo.countryName)
            cardCountry.visible(!cardInfo.countryName.isNullOrEmpty())

            cardBank.text = getString(R.string.card_bank_template, cardInfo.bankName)
            cardBank.visible(!cardInfo.bankName.isNullOrEmpty())
        } else {
            cardInfoLayout.visible(false)
            showMessage(result.exceptionOrNull()?.message.orEmpty())
        }
    }

    private fun showMessage(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}
