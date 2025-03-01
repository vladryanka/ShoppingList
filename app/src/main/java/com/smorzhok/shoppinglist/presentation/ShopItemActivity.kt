package com.smorzhok.shoppinglist.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.smorzhok.shoppinglist.R
import com.smorzhok.shoppinglist.domain.ShopItem

class ShopItemActivity : AppCompatActivity(), ShopItemFragment.OnEditingFinishedListener {
    private var screenMode = EXTRA_MODE_UNKNOWN
    private var shopItemId = ShopItem.UNDEFINED_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item)
        parseIntent()
        if (savedInstanceState == null)
            launchRightMode()
    }

    private fun launchRightMode() {
        val fragment = when (screenMode) {
            EXTRA_EDIT -> ShopItemFragment.newInstanceEditItem(shopItemId)
            EXTRA_ADD -> ShopItemFragment.newInstanceAddItem()
            else -> throw RuntimeException("Unknown screen mode $screenMode")
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.shop_item_container, fragment)
            .commit()
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

    override fun onEditingFinished() {
        finish()
    }
}