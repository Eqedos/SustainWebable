package com.example.sustainwebable;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;


public class FragmentThree extends Fragment {
    private Button buttonToLogin;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_three, container, false);
        buttonToLogin = v.findViewById(R.id.buttonToLogin);
        buttonToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LoginRegPage.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        return v;
    }
}