package com.example.happycustomer1;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;

import org.jetbrains.annotations.NotNull;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileLoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileLoginFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //Xml Files variables
    Button login_button, create_account_button, forget_password_button;
    ImageView login_back_button;
    CountryCodePicker login_country_code_picker;
    TextInputLayout login_phone_number, login_password;
    RelativeLayout progressBar;

    //Varibles
    androidx.appcompat.widget.Toolbar toolbarTop;
    TextView mTitle;

    public ProfileLoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileLoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileLoginFragment newInstance(String param1, String param2) {
        ProfileLoginFragment fragment = new ProfileLoginFragment();
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
        View view1 = inflater.inflate(R.layout.fragment_profile_login, container, false);

        //Changing color of toolbar
        //To change color and heading on toolbar
        toolbarTop = (Toolbar) getActivity().findViewById(R.id.toolbar);
        mTitle = (TextView)toolbarTop.findViewById(R.id.toolbar_title);
        mTitle.setText("profile");

        //To change color
        toolbarTop.setBackgroundColor(Color.parseColor("#ffe400"));

        //Hooks
        login_button = (Button) view1.findViewById(R.id.login_button);
        forget_password_button = (Button) view1.findViewById(R.id.forget_password_button);
        login_country_code_picker = (CountryCodePicker) view1.findViewById(R.id.login_country_code_picker);
        login_phone_number = (TextInputLayout) view1.findViewById(R.id.login_phone_number);
        login_password = (TextInputLayout) view1.findViewById(R.id.login_password);
        progressBar = (RelativeLayout) view1.findViewById(R.id.progressBar);
        create_account_button = (Button) view1.findViewById(R.id.create_account_button);
        login_back_button=(ImageView) view1.findViewById(R.id.login_back_button);


        //Click on back button replace with previous fragment
        login_back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity appCompatActivity = (AppCompatActivity) view.getContext();
                appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.wrapper, new ProfileFragment()).commit();
            }
        });

        //Click on will take you to signup screen
        create_account_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity appCompatActivity = (AppCompatActivity) view.getContext();
                appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.wrapper, new ProfileSignUpFragment()).commit();
            }
        });

        //Click on login button
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!isConnected(getContext())) {
                    showCustomDialog(view);
                }

                if (!validateFields()) {
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                //get data
                String _phoneNumber = login_phone_number.getEditText().getText().toString().trim();
                String _password = login_password.getEditText().getText().toString().trim();

                if (_phoneNumber.charAt(0) == '0') {
                    _phoneNumber = _phoneNumber.substring(1);
                }
                String _completePhoneNumber = "+" + login_country_code_picker.getSelectedCountryCode() + _phoneNumber;

                //Database Query may be error because of this word phoneNo
                Query checkUser = FirebaseDatabase.getInstance().getReference("Users").orderByChild("phoneNo").equalTo(_completePhoneNumber);
                checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            login_phone_number.setError(null);
                            login_phone_number.setErrorEnabled(false);

                            String systemPassword = snapshot.child(_completePhoneNumber).child("signup_password").getValue(String.class);
                            if (systemPassword.equals(_password)) {
                                login_password.setError(null);
                                login_password.setErrorEnabled(false);

                                String _fullname = snapshot.child(_completePhoneNumber).child("signup_fullname").getValue(String.class);
                                String _email = snapshot.child(_completePhoneNumber).child("signup_email").getValue(String.class);
                                String _username = snapshot.child(_completePhoneNumber).child("signup_username").getValue(String.class);
                                String _phoneNo = snapshot.child(_completePhoneNumber).child("phoneNo").getValue(String.class);
                                String _password = snapshot.child(_completePhoneNumber).child("signup_password").getValue(String.class);
                                String _gender = snapshot.child(_completePhoneNumber).child("gender").getValue(String.class);
                                String _date = snapshot.child(_completePhoneNumber).child("date").getValue(String.class);


                                //Create a session might be error because of view
                                SessionManager sessionManager = new SessionManager(view.getContext());
                                sessionManager.createLoginSession(_fullname, _username, _email, _password, _gender, _date, _phoneNo);

                                AppCompatActivity appCompatActivity1 = (AppCompatActivity) view.getContext();
                                appCompatActivity1.getSupportFragmentManager().beginTransaction().replace(R.id.wrapper, new UserProfileAfterLoginFragment()).commit();


                                Toast.makeText(view.getContext(), "Successfully Signed In", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);


                            } else {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(getContext(), "Password does not match!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(getContext(), "No such user exist!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });


        forget_password_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity appCompatActivity = (AppCompatActivity) view.getContext();
                appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.wrapper, new ForgetPasswordFragment()).commit();
            }
        });

        return view1;
    }

    private void showCustomDialog(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Please connect to Internet")
                .setCancelable(false)
                .setPositiveButton("Connect", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Call Another page
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        AppCompatActivity appCompatActivity = (AppCompatActivity) view.getContext();
                        appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.wrapper, new HomeFragment()).commit();
                    }
                });
    }

    private boolean isConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifiConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if ((wifiConn != null && wifiConn.isConnected()) || (mobileConn != null && mobileConn.isConnected())) {
            return true;
        } else {
            return false;
        }
    }

    private boolean validateFields() {
        String _phoneNumber = login_phone_number.getEditText().getText().toString().trim();
        String _password = login_password.getEditText().getText().toString().trim();

        if (_phoneNumber.isEmpty()) {
            login_phone_number.setError("Phone number can not be empty");
            login_phone_number.requestFocus();
            return false;
        } else if (_password.isEmpty()) {
            login_password.setError("Password can not be empty");
            login_password.requestFocus();
            return false;
        } else {
            login_phone_number.setError(null);
            login_phone_number.setErrorEnabled(false);

            login_password.setError(null);
            login_password.setErrorEnabled(false);
            return true;
        }
    }
}