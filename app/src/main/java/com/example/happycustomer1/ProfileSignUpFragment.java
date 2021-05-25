package com.example.happycustomer1;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileSignUpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileSignUpFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    //Custom Variables
    TextInputLayout signup_fullname, signup_username, signup_email, signup_password;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileSignUpFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileSignUpFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileSignUpFragment newInstance(String param1, String param2) {
        ProfileSignUpFragment fragment = new ProfileSignUpFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_sign_up, container, false);
        // here you have the reference of your button
        Button nextButton = (Button) view.findViewById(R.id.signup_next_button);
        Button loginButton = (Button) view.findViewById(R.id.signup_login_button);




        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Code for sending data from one fragment to another
                Bundle bundle=new Bundle();


                AppCompatActivity appCompatActivity = (AppCompatActivity) view.getContext();
                appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.wrapper, new ProfileLoginFragment()).commit();
            }
        });

        //Hooks for text fields
        signup_email = (TextInputLayout) view.findViewById(R.id.signup_email);
        signup_fullname = (TextInputLayout) view.findViewById(R.id.signup_fullname);
        signup_username = (TextInputLayout) view.findViewById(R.id.signup_username);
        signup_password = (TextInputLayout) view.findViewById(R.id.signup_password);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validateFullName() | !validateUsername() | !validateEmail() | !validatePassword()) {
                    return;
                }

                //Code for sending data from one file to other
                Bundle bundle=new Bundle();
                bundle.putString("signup_fullname",signup_fullname.getEditText().getText().toString().trim());
                bundle.putString("signup_username",signup_username.getEditText().getText().toString().trim());
                bundle.putString("signup_email",signup_email.getEditText().getText().toString().trim());
                bundle.putString("signup_password",signup_password.getEditText().getText().toString().trim());

                ProfileSignUpNext1Fragment fragment= new ProfileSignUpNext1Fragment();
                fragment.setArguments(bundle);

                AppCompatActivity appCompatActivity = (AppCompatActivity) view.getContext();
                appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.wrapper, fragment).commit();
            }
        });

        return view;
    }

    private boolean validateFullName() {
        String val = signup_fullname.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            signup_fullname.setError("Field can not be empty");
            return false;
        } else {
            signup_fullname.setError(null);
            signup_fullname.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateUsername() {
        String val = signup_username.getEditText().getText().toString().trim();
        String checkspaces = "\\A\\w{1,20}\\z";

        if (val.isEmpty()) {
            signup_username.setError("Field can not be empty");
            return false;
        } else if (val.length() > 20) {
            signup_username.setError("Username is too large!");
            return false;
        } else if (!val.matches(checkspaces)) {
            signup_username.setError("No White spaces are allowed!");
            return false;
        } else {
            signup_username.setError(null);
            signup_username.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateEmail() {
        String val = signup_email.getEditText().getText().toString().trim();
        String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+.+[a-z]+";

        if (val.isEmpty()) {
            signup_email.setError("Field can not be empty");
            return false;
        } else if (!val.matches(checkEmail)) {
            signup_email.setError("Invalid Email!");
            return false;
        } else {
            signup_email.setError(null);
            signup_email.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePassword() {
        String val = signup_password.getEditText().getText().toString().trim();
        String checkPassword = "^" +
                //"(?=.*[0-9])" +         //at least 1 digit
                //"(?=.*[a-z])" +         //at least 1 lower case letter
                //"(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                //"(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
                "$";

        if (val.isEmpty()) {
            signup_password.setError("Field can not be empty");
            return false;
        } else if (!val.matches(checkPassword)) {
            signup_password.setError("Password should contain 4 characters!");
            return false;
        } else {
            signup_password.setError(null);
            signup_password.setErrorEnabled(false);
            return true;
        }
    }

}