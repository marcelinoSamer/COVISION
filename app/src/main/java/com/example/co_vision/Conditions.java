package com.example.co_vision;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Conditions extends Fragment {

    public static String user_login_data = "com.example.co_vision.logindata";
    FirebaseDatabase userDB;
    DatabaseReference DBreference;
    RadioButton coughYes, coughNo, feverYes, feverNo,
            smellYes, smellNo, oxyNo, oxyYes,
            muscleYes, muscleNo;
    RadioGroup cough, fever, smell, o2, muscle;
    Button save;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_condintions, container, false);
        //RadioButtons
        coughYes = view.findViewById(R.id.coughYes);//
        coughNo = view.findViewById(R.id.coughNo);
        feverYes = view.findViewById(R.id.feverYes);//
        feverNo = view.findViewById(R.id.feverNo);
        smellYes = view.findViewById(R.id.smellYes);//
        smellNo = view.findViewById(R.id.smellNo);
        oxyYes = view.findViewById(R.id.oxyYes);//
        oxyNo = view.findViewById(R.id.oxyNo);
        muscleYes = view.findViewById(R.id.muscleYes);//
        muscleNo = view.findViewById(R.id.muscleNo);

        // RadioGroups
        cough = view.findViewById(R.id.cough);
        fever = view.findViewById(R.id.fever);
        smell = view.findViewById(R.id.smell);
        o2 = view.findViewById(R.id.o2);
        muscle = view.findViewById(R.id.muscle);

        //save changes button
        save = view.findViewById(R.id.addSymp);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //check if all button are checked
                if (cough.getCheckedRadioButtonId() == -1 || smell.getCheckedRadioButtonId() == -1
                        || fever.getCheckedRadioButtonId() == -1 || o2.getCheckedRadioButtonId() == -1
                        || muscle.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(getContext(), "Please fill all of the data", Toast.LENGTH_SHORT).show();

                //check the condition of the user
                } else {
                    if ((smellNo.isChecked() && coughYes.isChecked() && feverYes.isChecked() && oxyYes.isChecked() && muscleYes.isChecked())
                            || (smellNo.isChecked() && coughYes.isChecked() && feverYes.isChecked())) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(Conditions.this.getActivity());


                        //user is infected
                        SharedPreferences.Editor prf = getActivity().getSharedPreferences("com.example.co_vision.logindata", 0).edit();
                        prf.putString("state", "Infected");
                        prf.commit();
                        SharedPreferences getusername = getActivity().getSharedPreferences("com.example.co_vision.logindata", 0);
                        String Susername = getusername.getString("username", "no such user");
                        userDB = FirebaseDatabase.getInstance("FIREBASE_DATABASE_LINK");
                        DBreference = userDB.getReference("userdata");
                        DBreference.child(Susername).child("state").setValue("Infected");
                        builder.setMessage("You are most probably Infected, Contact your doctor.")
                                .setCancelable(false)
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {


                                    }
                                });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();


                    } else if (smellNo.isChecked() || coughYes.isChecked() || feverYes.isChecked() || oxyYes.isChecked() || muscleYes.isChecked()) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(Conditions.this.getActivity());

                        //user is suspected
                        SharedPreferences.Editor prf = getActivity().getSharedPreferences("com.example.co_vision.logindata", 0).edit();
                        prf.putString("state", "Suspected");
                        prf.commit();
                        SharedPreferences getusername = getActivity().getSharedPreferences("com.example.co_vision.logindata", 0);
                        String Susername = getusername.getString("username", "no such user");
                        userDB = FirebaseDatabase.getInstance("FIREBASE_DATABASE_LINK");
                        DBreference = userDB.getReference("userdata");
                        DBreference.child(Susername).child("state").setValue("Suspected");
                        builder.setMessage("You are suspected to have COVID-19, Please stay home.")
                                .setCancelable(false)
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {


                                    }
                                });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(Conditions.this.getActivity());


                        //user is safe
                        SharedPreferences.Editor prf = getActivity().getSharedPreferences("com.example.co_vision.logindata", 0).edit();
                        prf.putString("state", "Safe");
                        prf.commit();
                        SharedPreferences getusername = getActivity().getSharedPreferences("com.example.co_vision.logindata", 0);
                        String Susername = getusername.getString("username", "no such user");
                        userDB = FirebaseDatabase.getInstance("FIREBASE_DATABASE_LINK");
                        DBreference = userDB.getReference("userdata");
                        DBreference.child(Susername).child("state").setValue("Safe");
                        builder.setMessage("You are not Infected, Dont forget to wear your face mask")
                                .setCancelable(false)
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                    }
                                });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }
                }
            }
        });

        return view;
    }
}
