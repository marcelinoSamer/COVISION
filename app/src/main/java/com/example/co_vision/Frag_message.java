package com.example.co_vision;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.SharedPreferences;
import com.google.android.material.textfield.TextInputLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Frag_message extends Fragment {

    public static String user_login_data = "com.example.co_vision.logindata";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fag_prof, container, false);
        TextInputLayout usernametv = view.findViewById(R.id.usernameedtprof);
        TextInputLayout firstnametv = view.findViewById(R.id.firstnameedtprof);
        TextInputLayout lastnametv = view.findViewById(R.id.lastnameedtprof);
        TextInputLayout agetv = view.findViewById(R.id.editAgeedt);
        TextInputLayout emailtv = view.findViewById(R.id.editEmailedt);

        SharedPreferences getdata = this.getActivity().getSharedPreferences(user_login_data, Context.MODE_PRIVATE);


        String Sage = getdata.getString("age","0");

        usernametv.getEditText().setText(getdata.getString("username","empty"));
        firstnametv.getEditText().setText(getdata.getString("firstname","empty"));
        lastnametv.getEditText().setText(getdata.getString("lastname","empty"));
        agetv.getEditText().setText(Sage);
        emailtv.getEditText().setText(getdata.getString("email","empty"));


        return view;
    }
}
