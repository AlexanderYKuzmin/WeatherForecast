package com.kuzmin.weatherforecast.ui.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.kuzmin.weatherforecast.R
import com.kuzmin.weatherforecast.databinding.FragmentSettingsBinding
import com.kuzmin.weatherforecast.domain.model.servicable.ApiKeyResult.Error
import com.kuzmin.weatherforecast.domain.model.servicable.ApiKeyResult.Success
import com.kuzmin.weatherforecast.extensions.showToast
import com.kuzmin.weatherforecast.ui.viewmodels.SettingsFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : Fragment(), OnClickListener {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private val settingsViewModel: SettingsFragmentViewModel by viewModels()

    private lateinit var onSettingsButtonClickListener: OnSettingsButtonClickListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnSettingsButtonClickListener) {
            onSettingsButtonClickListener = context
        } else throw RuntimeException("Activity must implement OnDeviceItemClickListener")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        settingsViewModel.getApiKey()


        binding.btnOk.setOnClickListener(this)
        binding.btnCancel.setOnClickListener(this)

        settingsViewModel.apiKeyResult.observe(viewLifecycleOwner) {
            when(it) {
                is Success -> {
                    binding.etApi.setText(it.apiKey)
                }
                is Error -> {
                    requireActivity().showToast(it.throwable.message.toString())
                }
            }
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            binding.btnOk.id -> {
                val apiKey = binding.etApi.text.toString()
                if (isKeyValid(apiKey)) {
                    settingsViewModel.saveApiKey(apiKey)
                    requireActivity().showToast(getString(R.string.api_key_updated))
                } else {
                    requireActivity().showToast(getString(R.string.invalid_api_key))
                }
            }

            binding.btnCancel.id -> {}
        }
        onSettingsButtonClickListener.onSettingsFinished()
    }

    private fun isKeyValid(apiKey: String): Boolean {
        return apiKey.isNotEmpty() && apiKey.length == 32
    }

    interface OnSettingsButtonClickListener {
        fun onSettingsFinished()
    }
}