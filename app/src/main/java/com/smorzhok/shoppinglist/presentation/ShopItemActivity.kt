package com.smorzhok.shoppinglist.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputLayout
import com.smorzhok.shoppinglist.R
import com.smorzhok.shoppinglist.data.ShopItem

class ShopItemActivity : AppCompatActivity() {
    private lateinit var textInputLayoutCount: TextInputLayout
    private lateinit var textInputLayoutName: TextInputLayout
    private lateinit var textEditName: EditText
    private lateinit var textEditCount: EditText
    private lateinit var buttonSave: Button
    private lateinit var viewModel: ShopItemViewModel
    private var screenMode = EXTRA_MODE_UNKNOWN
    private var shopItemId = ShopItem.UNDEFINED_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item)
        parseIntent()
        viewModel = ViewModelProvider(this)[ShopItemViewModel::class.java]
        initViews()
        addTextChangeListeners()
        launchRightMode()
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.errorInputCount.observe(this) {
            val message = if (it) {
                getString(R.string.error_input_count)
            } else {
                null
            }
            textInputLayoutCount.error = message
        }
        viewModel.errorInputName.observe(this) {
            val message = if (it) {
                getString(R.string.error_input_name)
            } else {
                null
            }
            textInputLayoutName.error = message
        }
        viewModel.isSaved.observe(this) {
            finish()
        }
    }

    private fun launchRightMode() {
        when (screenMode) {
            EXTRA_EDIT -> launchEditMode()
            EXTRA_ADD -> launchAddMode()
        }
    }

    private fun addTextChangeListeners() {
        textEditName.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetValueInputName()
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })
        textEditCount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetValueInputCount()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    private fun launchEditMode() {
        viewModel.getItemByID(shopItemId)
        viewModel.shopItem.observe(this) {
            textEditName.setText(it.name)
            textEditCount.setText(it.count.toString())
        }
        buttonSave.setOnClickListener {
            viewModel.editItem(textEditName.text?.toString(), textEditCount.text?.toString())
        }
    }

    private fun launchAddMode() {
        buttonSave.setOnClickListener {
            viewModel.addItem(textEditName.text?.toString(), textEditCount.text?.toString())
        }
    }

    private fun parseIntent() {
        if (!intent.hasExtra(EXTRA_MODE)) {
            throw RuntimeException("Param screen mode is absent")
        }
        val mode = intent.getStringExtra(EXTRA_MODE)
        if (mode != EXTRA_EDIT && mode != EXTRA_ADD) {
            throw RuntimeException("Unknown screen mode $mode")
        }
        screenMode = mode
        if (screenMode == EXTRA_EDIT) {
            if (!intent.hasExtra(EXTRA_SHOP_ITEM_ID)) {
                throw RuntimeException("Param shop item id is absent")
            }
            shopItemId = intent.getIntExtra(EXTRA_SHOP_ITEM_ID, ShopItem.UNDEFINED_ID)
        }
    }

    private fun initViews() {
        textEditName = findViewById(R.id.textEditName)
        textEditCount = findViewById(R.id.textEditCount)
        buttonSave = findViewById(R.id.buttonSave)
        textInputLayoutCount = findViewById(R.id.textInputLayoutCount)
        textInputLayoutName = findViewById(R.id.textInputLayoutName)
    }

    companion object {
        private const val EXTRA_MODE = "extra_mode"
        private const val EXTRA_SHOP_ITEM_ID = "extra_shop_item_id"
        private const val EXTRA_ADD = "extra_add"
        private const val EXTRA_EDIT = "extra_edit"
        private const val EXTRA_MODE_UNKNOWN = ""
        fun newIntentAddMode(context: Context): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_MODE, EXTRA_ADD)
            return intent
        }

        fun newIntentEditMode(context: Context, id: Int): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_MODE, EXTRA_EDIT)
            intent.putExtra(EXTRA_SHOP_ITEM_ID, id)
            return intent
        }
    }
}