package com.example.project;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.project.Id.Id;

public class Fragment_buspass extends Fragment {

    TextView name,category;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragmen
        View root = inflater.inflate(R.layout.fragment_buspass, container,false);

        name = root.findViewById(R.id.name);
        category = root.findViewById(R.id.category);

        requireActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);

        name.setText(Id.fname + "\n" + Id.lname);
        category.setText(Id.category);
        return root;
}

    @Override
    public void onResume() {
        super.onResume();
        name.setText(Id.fname + "\n" + Id.lname);
        category.setText(Id.category);
    }

    @Override
    public void onPause() {
        super.onPause();
        name.setText(Id.fname + "\n" + Id.lname);
        category.setText(Id.category);
    }
}