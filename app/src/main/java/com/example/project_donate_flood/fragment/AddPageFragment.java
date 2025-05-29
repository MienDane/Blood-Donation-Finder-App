package com.example.project_donate_flood.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.widget.Spinner;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;


import com.example.project_donate_flood.R;


public class AddPageFragment extends Fragment {

    private Spinner bloodTypeSpinner; // Declare the Spinner

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_page, container, false);
        bloodTypeSpinner = view.findViewById(R.id.bloodTypeSpinner); // Now find the Spinner after setting the content view

        String[] bloodTypes = getResources().getStringArray(R.array.blood_type);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, bloodTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bloodTypeSpinner.setAdapter(adapter);
        return view;


    }
}