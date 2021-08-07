package com.example.w_app_1.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.w_app_1.MainActivity;
import com.example.w_app_1.R;

public class TemperatureFragment extends Fragment {

    private RadioGroup radioTemperatureGroup;
    private RadioButton radioTemperatureButton;
    private Button btnDisplay;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_temperature, container, false);
        initViews(view);
        initListeners(view);
        return view;
    }

    private void initViews(View view) {
        radioTemperatureGroup = (RadioGroup) view.findViewById(R.id.radioTemperature);
        btnDisplay = (Button) view.findViewById(R.id.btnDisplay);
    }

    private void initListeners(final View view) {
        btnDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int checkedRadioButtonId = radioTemperatureGroup.getCheckedRadioButtonId();
                radioTemperatureButton = (RadioButton) view.findViewById(checkedRadioButtonId);

                Intent intent = new Intent(getContext(), MainActivity.class);
                intent.putExtra("temp_type", radioTemperatureButton.getText());
                startActivity(intent);
//                Toast.makeText(getContext(), String.valueOf(radioTemperatureButton.getText()), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
