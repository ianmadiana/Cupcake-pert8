/*
 * Copyright (C) 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.cupcake

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.cupcake.databinding.FragmentFlavorBinding
import com.example.cupcake.databinding.FragmentToppingBinding
import com.example.cupcake.model.OrderViewModel

/**
 * [FlavorFragment] allows a user to choose a cupcake flavor for the order.
 */
class ToppingFragment : Fragment() {

    // Binding object instance corresponding to the fragment_flavor.xml layout
    // This property is non-null between the onCreateView() and onDestroyView() lifecycle callbacks,
    // when the view hierarchy is attached to the fragment.
    private var binding: FragmentToppingBinding? = null

    //properti delegasi
    private  val sharedViewModel: OrderViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentToppingBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            nextButton.setOnClickListener { goToNextScreen() }
        }

        //mengikat viewModel dengan sharedViewModel
        binding?.apply {
            //Mengamati LiveData pada aplikasi
            //siklus proses agar data dapat diamati
            lifecycleOwner = viewLifecycleOwner
            viewModel = sharedViewModel
            //blok kode di bawah berhubungan dengan layout pada onClick
            toppingFragment = this@ToppingFragment
        }
    }

    /**
     * Navigate to the next screen to choose pickup date.
     */
    //fungsi untuk berpindah antar fragment
    fun goToNextScreen() {
        //ketika user memilih topping untuk cupcake
        //maka akan dinavigasikan ke pickup date fragment
        findNavController().navigate(R.id.action_toppingFragment_to_pickupFragment)
    }

    /**
     * This fragment lifecycle method is called when the view hierarchy associated with the fragment
     * is being removed. As a result, clear out the binding object.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}