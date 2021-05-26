package com.example.happycustomer1;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileSignUpNext1Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileSignUpNext1Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    //User Defined Variables
    RadioGroup radio_group;
    RadioButton selectedAge;
    DatePicker age_picker;

    ImageView signup_back_button;

    //Varibles
    androidx.appcompat.widget.Toolbar toolbarTop;
    TextView mTitle;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileSignUpNext1Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileSignUpNext1Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileSignUpNext1Fragment newInstance(String param1, String param2) {
        ProfileSignUpNext1Fragment fragment = new ProfileSignUpNext1Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view1 = inflater.inflate(R.layout.fragment_profile_sign_up_next1, container, false);
        // here you have the reference of your button
        Button nextButton = (Button)view1.findViewById(R.id.signup_next_button);
        Button loginButton = (Button)view1.findViewById(R.id.signup_login_button);
        signup_back_button=(ImageView)view1.findViewById(R.id.signup_back_button);


        //To change color and heading on toolbar
        toolbarTop = (Toolbar) getActivity().findViewById(R.id.toolbar);
        mTitle = (TextView)toolbarTop.findViewById(R.id.toolbar_title);
        mTitle.setText("PROFILE");
        //To change color
        toolbarTop.setBackgroundColor(Color.parseColor("#ffe400"));


        //Sending back to profile on pressing back
        signup_back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity appCompatActivity = (AppCompatActivity) view.getContext();
                appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.wrapper, new ProfileSignUpFragment()).commit();
            }
        });

        //XML File hooks
        radio_group=(RadioGroup)view1.findViewById(R.id.radio_group);
        age_picker=(DatePicker)view1.findViewById(R.id.age_picker);


        //Data From Previous Fragment
        Bundle bundle=this.getArguments();
        /*
        String signup_fullname=bundle.getString("signup_fullname");
        String signup_email=bundle.getString("signup_email");
        String signup_username=bundle.getString("signup_username");
        String signup_password=bundle.getString("signup_password");*/



        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!validateGender()){
                    return;
                }

                //Stroing data
                selectedAge=view1.findViewById(radio_group.getCheckedRadioButtonId());
                String _gender=selectedAge.getText().toString();
                int day=age_picker.getDayOfMonth();
                int month=age_picker.getMonth();
                int year=age_picker.getYear();
                String date=day+"/"+month+"/"+year;

                //Making bundle to send data to next fragment
                bundle.putString("gender",_gender);
                bundle.putString("date",date);

                ProfileSignUpNext2Fragment fragment2= new ProfileSignUpNext2Fragment();
                fragment2.setArguments(bundle);


                AppCompatActivity appCompatActivity = (AppCompatActivity) view.getContext();
                appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.wrapper, fragment2).commit();
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity appCompatActivity = (AppCompatActivity) view.getContext();
                appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.wrapper, new ProfileLoginFragment()).commit();
            }
        });






        return view1;
    }

    private boolean validateGender() {
        if (radio_group.getCheckedRadioButtonId() == -1) {
            Toast.makeText(getContext(), "Please Select Gender", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }
}