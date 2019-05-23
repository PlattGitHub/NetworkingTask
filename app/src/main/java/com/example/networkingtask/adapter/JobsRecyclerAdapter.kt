package com.example.networkingtask.adapter

import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.networkingtask.R
import com.example.networkingtask.model.JobGitHub

/**
 * Simple [RecyclerView.Adapter] to display [JobGitHub] list.
 */
class JobsRecyclerAdapter : RecyclerView.Adapter<JobsRecyclerAdapter.JobsViewHolder>() {

    private var list = emptyList<JobGitHub>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobsViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.jobs_list_item, parent, false)
        return JobsViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: JobsViewHolder, position: Int) {
        val job = list[holder.adapterPosition]
        holder.apply {
            companyName.text = job.company
            jobTitle.text = job.title
            jobLocation.text = job.location

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                jobDescription.text = Html.fromHtml(job.description, Html.FROM_HTML_MODE_LEGACY);
            } else {
                jobDescription.text = Html.fromHtml(job.description);
            }
        }
    }

    fun setJobs(newList: List<JobGitHub>) {
        list = newList
        notifyDataSetChanged()
    }

    inner class JobsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val companyName: TextView = itemView.findViewById(R.id.company_name)
        val jobTitle: TextView = itemView.findViewById(R.id.job_title)
        val jobDescription: TextView = itemView.findViewById(R.id.job_description)
        val jobLocation: TextView = itemView.findViewById(R.id.job_location)
    }
}