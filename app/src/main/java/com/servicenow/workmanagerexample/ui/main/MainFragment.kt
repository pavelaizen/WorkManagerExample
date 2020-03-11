package com.servicenow.workmanagerexample.ui.main

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.servicenow.workmanagerexample.ColorManager
import com.servicenow.workmanagerexample.NitzanWorker
import com.servicenow.workmanagerexample.R
import com.servicenow.workmanagerexample.databinding.MainFragmentBinding
import java.time.Duration
import java.util.concurrent.TimeUnit

class MainFragment : Fragment(), View.OnClickListener {
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = MainFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        ColorManager.color.observe(viewLifecycleOwner, Observer { color ->
            val viewColor = binding.colorView.background as? ColorDrawable
            val currentColor = viewColor?.color ?: Color.WHITE
            ValueAnimator.ofObject(ArgbEvaluator() , currentColor, color).apply {
                duration = 500
                addUpdateListener {
                    binding.colorView.setBackgroundColor(it.animatedValue as Int)
                }
                start()
            }
        })

        binding.btnBlue.setOnClickListener { changeColor(Color.BLUE) }
        binding.btnGreen.setOnClickListener { changeColor(Color.GREEN) }
        binding.btnRed.setOnClickListener { changeColor(Color.RED) }
        binding.btnYellow.setOnClickListener { changeColor(Color.YELLOW) }


    }

    override fun onClick(v: View?) {

    }

    private fun changeColor(color: Int) {
        val request = OneTimeWorkRequestBuilder<NitzanWorker>()
            .setInputData(workDataOf("color" to color))
            .build()
        WorkManager.getInstance(requireContext())
            .beginUniqueWork("SequentialRequests", ExistingWorkPolicy.APPEND, request).enqueue()
    }


}
