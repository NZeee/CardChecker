package com.example.cardchecker.utils

import android.text.Editable
import android.text.TextWatcher

class SimpleTextChangedListener(
    private val listener: (CharSequence?) -> Unit
) : TextWatcher {

    override fun afterTextChanged(s: Editable?) {
        // ignore
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        // ignore
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        listener.invoke(s)
    }
}
