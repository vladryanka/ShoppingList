package com.smorzhok.shoppinglist.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.smorzhok.shoppinglist.R

class MainActivity : AppCompatActivity(), ShopItemFragment.OnEditingFinishedListener {
    private lateinit var viewModel: MainViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ShopListAdapter
    private lateinit var button: FloatingActionButton
    private var shopItemContainer: FragmentContainerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        shopItemContainer = findViewById(R.id.fragmentContainer)
        button = findViewById(R.id.floatingActionButton)
        setRV()
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.shopList.observe(this) {
            adapter.submitList(it)
        }
        button.setOnClickListener {
            if(isOnePaneMode()){
                val intent = ShopItemActivity.newIntentAddMode(this)
                startActivity(intent)
            }
            else {
                launchFragment(ShopItemFragment.newInstanceAddItem())
            }

        }
    }

    private fun isOnePaneMode(): Boolean{
        return shopItemContainer == null
    }

    private fun launchFragment(fragment:ShopItemFragment){
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContainer,fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun setRV() {
        adapter = ShopListAdapter()
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.adapter = adapter
        recyclerView.recycledViewPool.setMaxRecycledViews(
            ShopListAdapter.IS_ENABLED,
            ShopListAdapter.MAX_POOL_SIZE
        )
        recyclerView.recycledViewPool.setMaxRecycledViews(
            ShopListAdapter.IS_DISABLED,
            ShopListAdapter.MAX_POOL_SIZE
        )
        adapter.onShopItemShortClickListener ={
            val intent = ShopItemActivity.newIntentEditMode(this,it.id)
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
                viewModel.deleteShopItem(item)
            }
        }
        val itemTouchHelper = ItemTouchHelper(simpleCallBack)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    override fun onEditingFinished() {
        Toast.makeText(this@MainActivity, "Success", Toast.LENGTH_SHORT).show()
        supportFragmentManager.popBackStack()
    }
}
