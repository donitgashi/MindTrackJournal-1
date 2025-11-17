package com.example.mindtrack

import android.app.Application
import com.example.mindtrack.di.Graph

class MindtrackApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Graph.provide(this)
    }
}