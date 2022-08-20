package com.svapp.rsplash.ui.random

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.svapp.rsplash.databinding.FragmentRandomPictureBinding

/**
 * A simple [Fragment] subclass.
 * Used for display random picture.
 */
class RandomPictureFragment : Fragment() {

    private var _binding: FragmentRandomPictureBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRandomPictureBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}