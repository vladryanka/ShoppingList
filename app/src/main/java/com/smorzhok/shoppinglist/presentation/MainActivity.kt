package com.smorzhok.shoppinglist.presentation

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.smorzhok.shoppinglist.R
import com.smorzhok.shoppinglist.ShopApp
import com.smorzhok.shoppinglist.databinding.ActivityMainBinding
import com.smorzhok.shoppinglist.domain.ShopItem
import javax.inject.Inject
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity(), ShopItemFragment.OnEditingFinishedListener {
    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ShopListAdapter

    @Inject
    lateinit var viewModelFactory: ShopViewModelFactory

    private val component by lazy {
        (application as ShopApp).component
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setRV()
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        viewModel.shopList.observe(this) {
            adapter.submitList(it)
        }
        binding.floatingActionButton.setOnClickListener {
            if (isOnePaneMode()) {
                val intent = ShopItemActivity.newIntentAddMode(this)
                startActivity(intent)
            } else {
                launchFragment(ShopItemFragment.newInstanceAddItem())
            }
        }
        thread {
            val cursor = contentResolver.query(
                Uri.parse("content://com.smorzhok.shoppinglist/shop_item"), null,
                null, null
            )
            while(cursor?.moveToNext() == true){

                val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                val name = cursor.getString(cursor.getColumnIndexOrThrow("name"))
                val count = cursor.getInt(cursor.getColumnIndexOrThrow("count"))
                val enabled = cursor.getInt(cursor.getColumnIndexOrThrow("enabled")) > 0
                val shopItem = ShopItem(
                    id = id,
                    name = name,
                    count = count,
                    enabled = enabled
                )
                Log.d("MainActivity", "Cursor ${shopItem}")
            }
            cursor?.close()
        }

    }

    private fun isOnePaneMode(): Boolean {
        return binding.fragmentContainer == null
    }

    private fun launchFragment(fragment: ShopItemFragment) {
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContainer, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun setRV() {
        adapter = ShopListAdapter()
        with(binding.recyclerView) {
            this.adapter = this@MainActivity.adapter
            recycledViewPool.setMaxRecycledViews(
                ShopListAdapter.IS_ENABLED,
                ShopListAdapter.MAX_POOL_SIZE
            )
            recycledViewPool.setMaxRecycledViews(
                ShopListAdapter.IS_DISABLED,
                ShopListAdapter.MAX_POOL_SIZE
            )
        }

        adapter.onShopItemShortClickListener = {
            val intent = ShopItemActivity.newIntentEditMode(this, it.id)
            startActivity(intent)
        }
        setupLongClickListener()
        setupShortClickListener()
        setUpSwipeListener()

    }

    private fun setupLongClickListener() {
        adapter.onShopItemLongClickListener = {
            viewModel.changeEnableState(it)
        }
    }

    private fun setupShortClickListener() {
        adapter.onShopItemShortClickListener = {
            if (isOnePaneMode()) {
                val intent = ShopItemActivity.newIntentEditMode(this, it.id)
                startActivity(intent)
            } else {
                launchFragment(ShopItemFragment.newInstanceEditItem(it.id))
            }
        }
    }

    private fun setUpSwipeListener() {
        val simpleCallBack = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = adapter.currentList[viewHolder.adapterPosition]
                //viewModel.deleteShopItem(item)
                thread {
                    contentResolver.delete(
                        Uri.parse("content://com.smorzhok.shoppinglist/shop_item"),
                        null,
                        arrayOf(item.id.toString())
                    )
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(simpleCallBack)
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)

    }

    override fun onEditingFinished() {
        Toast.makeText(this@MainActivity, "Success", Toast.LENGTH_SHORT).show()
        supportFragmentManager.popBackStack()
    }
}
