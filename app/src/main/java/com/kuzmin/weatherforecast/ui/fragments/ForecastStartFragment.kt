package com.kuzmin.weatherforecast.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kuzmin.weatherforecast.databinding.FragmentForecastStartBinding

class ForecastStartFragment : Fragment() {

    private var _binding: FragmentForecastStartBinding? = null
    private val binding get() = _binding!!

    private var onRefreshListener: OnRefreshListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnRefreshListener) {
            onRefreshListener = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentForecastStartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnRefreshStart.setOnClickListener {
            onRefreshListener?.onRefreshForecastStart()
        }
    }

    interface OnRefreshListener {
        fun onRefreshForecastStart()
    }
}