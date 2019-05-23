package com.example.networkingtask.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.networkingtask.R
import com.example.networkingtask.adapter.JobsRecyclerAdapter
import com.example.networkingtask.viewmodel.JobsListViewModel
import com.google.android.material.snackbar.Snackbar

/**
 * [Fragment] subclass that displays list of jobs in [RecyclerView].
 *
 * @author Alexander Gorin
 */
class JobsListFragment : Fragment() {

    private val viewModel: JobsListViewModel by lazy {
        ViewModelProviders.of(this).get(JobsListViewModel::class.java)
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var frameLayout: FrameLayout
    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerViewAdapter: JobsRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.jobs_list_fragment, container, false).apply {
            recyclerView = findViewById(R.id.jobs_recycler_view)
            progressBar = findViewById(R.id.progress_bar)
            frameLayout = findViewById(R.id.frame_layout)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerViewAdapter = JobsRecyclerAdapter()
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(
                DividerItemDecoration(
                    recyclerView.context,
                    DividerItemDecoration.VERTICAL
                )
            )
            adapter = recyclerViewAdapter
        }

        with(viewModel) {
            isLoading.observe(this@JobsListFragment, Observer {
                progressBar.isVisible = it
            })
            loadData().observe(this@JobsListFragment, Observer { jobs ->
                jobs?.let {
                    recyclerViewAdapter.setJobs(it)
                }
            })
            isConnected.observe(this@JobsListFragment, Observer {
                if (!it) {
                    Snackbar.make(frameLayout, getString(R.string.error), Snackbar.LENGTH_SHORT).show()
                }
            })
        }
    }

    companion object {
        fun newInstance() = JobsListFragment()
    }
}