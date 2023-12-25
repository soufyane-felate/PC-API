package com.example.apisqlite






import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import java.time.LocalDate

class MainActivity : AppCompatActivity() {
    private lateinit var productAdapter: ProductAdapter
    private lateinit var productList: List<Product>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.RecyclerView1)
        recyclerView.layoutManager = LinearLayoutManager(this)

        fetchData()
    }

    private fun fetchData() {
        val url = "https://run.mocky.io/v3/047e7d73-8ae1-4212-be4e-e573235993f7"

        val request = JsonArrayRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                val gson = Gson()
                val productList = mutableListOf<Product>()

                for (i in 0 until response.length()) {
                    val jsonObject = response.getJSONObject(i)
                    val product = Product(
                        jsonObject.getString("nom"),
                        jsonObject.getString("systeme_exploitation"),
                        ProductUtils.parseDateString(jsonObject.getString("date_fin_os")),
                        jsonObject.getString("prix"),
                        jsonObject.getString("image")
                    )
                    productList.add(product)
                }

                val dbHelper = DataBase_Product(this)
                dbHelper.updateProducts(productList)

                updateRecyclerView(productList)
            },
            Response.ErrorListener { error ->
                error.printStackTrace()

                val dbHelper = DataBase_Product(this)
                productList = dbHelper.getAllProducts()
                updateRecyclerView(productList)
            })

        Volley.newRequestQueue(this).add(request)
    }


    private fun updateRecyclerView(products: List<Product>) {
        productList = products
        productAdapter = ProductAdapter(this, productList)
        val recyclerView: RecyclerView = findViewById(R.id.RecyclerView1)
        recyclerView.adapter = productAdapter
    }
}
