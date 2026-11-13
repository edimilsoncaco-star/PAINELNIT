package com.nexus.panel.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.nexus.panel.R
import com.nexus.panel.views.ParticleView
import java.util.Random

class HomeFragment : Fragment() {

    private lateinit var particleView: ParticleView
    private lateinit var tvStatus: TextView
    private lateinit var tvPing: TextView
    private lateinit var tvVersion: TextView
    private val handler = Handler(Looper.getMainLooper())
    private val rand = Random()
    private var running = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_home, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        particleView = view.findViewById(R.id.particleView)
        tvStatus     = view.findViewById(R.id.tvStatus)
        tvPing       = view.findViewById(R.id.tvPing)
        tvVersion    = view.findViewById(R.id.tvVersion)

        tvVersion.text = "NEXUS v3.1 · BUILD 3109"
        tvStatus.text  = "● SISTEMA ONLINE"
        startPingSimulation()
    }

    private fun startPingSimulation() {
        running = true
        val r = object : Runnable {
            override fun run() {
                if (!running) return
                val ping = rand.nextInt(30) + 8
                tvPing.text = "PING: ${ping}ms"
                handler.postDelayed(this, 1500)
            }
        }
        handler.post(r)
    }

    override fun onDestroyView() {
        running = false
        handler.removeCallbacksAndMessages(null)
        super.onDestroyView()
    }
}
