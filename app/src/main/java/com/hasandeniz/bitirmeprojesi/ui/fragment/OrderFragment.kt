package com.hasandeniz.bitirmeprojesi.ui.fragment

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import com.hasandeniz.bitirmeprojesi.R
import com.hasandeniz.bitirmeprojesi.data.entity.Food
import com.hasandeniz.bitirmeprojesi.databinding.FragmentOrderBinding
import com.hasandeniz.bitirmeprojesi.ui.adapter.FoodAdapter
import com.hasandeniz.bitirmeprojesi.ui.viewmodel.OrderViewModel
import com.hasandeniz.bitirmeprojesi.ui.viewmodel.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderFragment : Fragment(),SearchView.OnQueryTextListener {
    private lateinit var binding : FragmentOrderBinding
    private lateinit var adapter: FoodAdapter
    private var foodList = arrayListOf<Food>()
    private val viewModel : OrderViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_order, container, false)

        (activity as AppCompatActivity).setSupportActionBar(binding.mainToolbar)


        viewModel.foodList.observe(viewLifecycleOwner){ state->

            when (state.status){
                Status.SUCCESS -> {
                    showFoods(state.data as List<Food>)
                }
                Status.LOADING -> {
                    showLoading()
                }
                Status.ERROR -> {
                    showError(state.message)
                }
            }
        }

        showSearchView()


        return binding.root
    }


    private fun showSearchView(){
        requireActivity().addMenuProvider(object : MenuProvider{
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.toolbar_menu,menu)

                val item = menu.findItem(R.id.action_ara)
                val searchView = item.actionView as SearchView
                searchView.setOnQueryTextListener(this@OrderFragment)
            }
            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return false
            }
        }, viewLifecycleOwner,Lifecycle.State.RESUMED)
    }

    private fun showError(message: String?) {
        binding.errorMessage = message
        binding.rvMain.visibility = View.GONE
        binding.circularProgressMain.visibility = View.GONE
        binding.ivErrorMain.visibility = View.VISIBLE
        binding.tvErrorMain.visibility = View.VISIBLE
    }

    private fun showLoading() {
        binding.rvMain.visibility = View.GONE
        binding.ivErrorMain.visibility = View.GONE
        binding.tvErrorMain.visibility = View.GONE
        binding.circularProgressMain.visibility = View.VISIBLE
    }

    private fun showFoods(data: List<Food>){
        binding.circularProgressMain.visibility = View.GONE
        binding.ivErrorMain.visibility = View.GONE
        binding.tvErrorMain.visibility = View.GONE
        binding.rvMain.visibility = View.VISIBLE

        if (foodList.isEmpty())
            foodList.addAll(data)
        adapter = FoodAdapter(requireContext(),data,viewModel)
        binding.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadFoods()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String): Boolean {
        val filteredList = ArrayList<Food>()
        for (food in foodList){
            val foodName = food.yemek_adi.lowercase()
            if (foodName.contains(newText)){
                filteredList.add(food)
            }
        }
        adapter.setFilter(filteredList)
        return true
    }

}