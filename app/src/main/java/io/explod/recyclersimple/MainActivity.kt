package io.explod.recyclersimple

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

class MainActivity : AppCompatActivity(), Adapter.Listener {

    val dataLoader = DataLoader()

    val adapter by lazy(LazyThreadSafetyMode.NONE) {
        // lazily create the adapter, making sure the listener is set
        val adapter = Adapter()
        adapter.setListener(this)
        adapter
    }

    val recycler: RecyclerView by lazy(LazyThreadSafetyMode.NONE) {
        // lazy findViewById
        findViewById<RecyclerView>(R.id.recycler_data)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(this)
    }


    override fun onResume() {
        super.onResume()
        dataLoader.loadData()
    }

    override fun onItemClicked(data: AdapterData, position: Int) {
        // our listener updates the data and notifies the adapter of the change
        data.checked = !data.checked
        adapter.notifyItemChanged(position)
    }

    inner class DataLoader {

        fun loadData() {
            val data = doExpensiveWorkInBackground()
            onDataReady(data)
        }

        private fun doExpensiveWorkInBackground(): List<AdapterData> {
            return (0..1000)
                    .map { id ->
                        AdapterData(id.toLong(), id.toString(16), id % 3 == 0)
                    }
        }

        private fun onDataReady(data: List<AdapterData>) {
            adapter.setAdapterData(data)
        }

    }

}
