package com.sihan.comfortzone.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.firebase.database.GenericTypeIndicator
import com.sihan.comfortzone.R
import com.sihan.comfortzone.database.CategoryProduct
import com.sihan.comfortzone.database.DataManager
import com.sihan.comfortzone.domains.MyStack
import com.sihan.comfortzone.domains.Product
import com.sihan.comfortzone.repositories.OnProductListener
import com.sihan.comfortzone.utils.ProductAdapter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CategoryProductFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CategoryProductFragment : Fragment(), OnProductListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var categoryIndicator: TextView
    private lateinit var categoryProductView: RecyclerView
    private lateinit var stack: MyStack<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_category_product, container, false)
        bindWidgets(view!!)
        prepareProductView()
        return view
    }

    private fun prepareProductView() {
        val categoryProduct = CategoryProduct("/products/", "category", stack.peek()!!)
        val productList: MutableList<Product> = mutableListOf()
        val productAdapter = activity?.let { ProductAdapter(it, productList, this) }
        categoryProductView.adapter = productAdapter
        categoryProduct.addListener(productAdapter!!)
    }

    private fun bindWidgets(view: View) {
        @Suppress("UNCHECKED_CAST")
        stack = this.arguments!!.getSerializable("stack") as MyStack<String>
        categoryIndicator = view.findViewById(R.id.category_indicator)
        categoryIndicator.text = stack.peek()
        categoryProductView = view.findViewById(R.id.category_product)
        categoryProductView.layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CategoryProductFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CategoryProductFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onProductClicked(product: Product) {
        val bundle = Bundle()
        stack.push("singleProductFragment")
        bundle.putSerializable("product", product)
        bundle.putSerializable("stack", stack)
        loadFragment(SingleProductViewFragment(), bundle)
    }

    private fun loadFragment(fragment: Fragment, bundle: Bundle) {
        // load fragment
        fragment.arguments = bundle
        val manager = activity!!.supportFragmentManager.beginTransaction()
        manager.replace(R.id.fragment_holder, fragment)
        manager.addToBackStack(null)
        manager.commit()
    }
}