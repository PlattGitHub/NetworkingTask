package com.example.networkingtask.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.networkingtask.R

/**
 * Launcher Activity that displays [JobsListFragment]
 *
 * @author Alexander Gorin
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(
                    R.id.fragment_container,
                    JobsListFragment.newInstance()
                ).commit()
        }
    }
}
