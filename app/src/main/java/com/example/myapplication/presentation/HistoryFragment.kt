package com.example.myapplication.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.PaceApplication
import com.example.myapplication.R
import com.example.myapplication.data.AppDatabase
import com.example.myapplication.data.PaceDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class HistoryFragment : Fragment() {
    private lateinit var database: AppDatabase
    private lateinit var dao: PaceDao
    private lateinit var paceRv: RecyclerView
    private lateinit var paceAdapter: PaceAdapter
    private lateinit var paceActionManager: PaceActionManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = (requireActivity().application as PaceApplication).getDatabase()
        dao = database.paceDao()
        paceActionManager = PaceActionManager(dao)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_history, container, false)
        paceAdapter = PaceAdapter()
        paceRv = view.findViewById(R.id.rv_pace_list)
        paceRv.layoutManager = LinearLayoutManager(activity)
        paceRv.adapter = paceAdapter

        CoroutineScope(IO).launch {
            val paceList = dao.getAllPaces()
            println(paceList)

            withContext(Main) {
                paceAdapter.submitList(paceList)
            }
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Botão "Delete All" pelo ID
        val btnDeleteAll: Button = view.findViewById(R.id.btn_delete_all)

        // Clique para o botão "Delete All"
        btnDeleteAll.setOnClickListener {
            deleteAllPaceData()
        }
    }

    private fun deleteAllPaceData() {
        GlobalScope.launch(Dispatchers.IO) {
            paceActionManager.deleteAllPaces()

            // Após a exclusão, atualize a lista de paces
            val paceList = dao.getAllPaces()
            withContext(Dispatchers.Main) {
                paceAdapter.submitList(paceList)
            }
        }
    }

    companion object {
        fun newInstance() : HistoryFragment {
            return HistoryFragment()
        }
    }
}



