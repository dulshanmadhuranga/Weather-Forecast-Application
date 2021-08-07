package com.example.w_app_1.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.w_app_1.MainActivity;
import com.example.w_app_1.R;

public class CityFragment extends Fragment {

    private EditText editTextCity;
    private Button buttonSubmit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_city, container, false);
        initViews(view);
        initListeners();
        return view;
    }

    private void initViews(View view) {
        editTextCity = (EditText) view.findViewById(R.id.edit_text_city);
        buttonSubmit = (Button) view.findViewById(R.id.buttonSub);
    }

    private void initListeners() {
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                intent.putExtra("city", editTextCity.getText().toString());
                startActivity(intent);
//                System.out.println(editTextCity.getText());
            }
        });
    }
}
