package com.dformula.helper.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.dformula.helper.databinding.FragmentMenu3Binding;


public class Fragment_menu3 extends Fragment {

    private FragmentMenu3Binding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMenu3Binding.inflate(inflater,container,false);



        return binding.getRoot();
    }


}