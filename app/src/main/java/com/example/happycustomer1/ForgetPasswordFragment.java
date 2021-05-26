package com.example.happycustomer1;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

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
 * Use the {@link ForgetPasswordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ForgetPasswordFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //Custom Variables
    CountryCodePicker forget_country_code_picker;
    TextInputLayout forget_phone_number;
    Button forget_password_next_btn;
    RelativeLayout forgetProgressBar;

    public ForgetPasswordFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ForgetPasswordFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ForgetPasswordFragment newInstance(String param1, String param2) {
        ForgetPasswordFragment fragment = new ForgetPasswordFragment();
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
        View view1 = inflater.inflate(R.layout.fragment_forget_password, container, false);

        //Hooks
        forget_country_code_picker=(CountryCodePicker)view1.findViewById(R.id.forget_country_code_picker);
        forget_phone_number=(TextInputLayout)view1.findViewById(R.id.forget_phone_number);
        forget_password_next_btn=(Button)view1.findViewById(R.id.forget_password_next_btn);
        forgetProgressBar=(RelativeLayout)view1.findViewById(R.id.forgetProgressBar);

        forget_password_next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isConnected(getContext())){
                    showCustomDialog(view);
                    return;
                }

                if (!validateFields()) {
                    return;
                }
                forgetProgressBar.setVisibility(View.VISIBLE);
                //get data
                String _phoneNumber = forget_phone_number.getEditText().getText().toString().trim();

                if (_phoneNumber.charAt(0) == '0') {
                    _phoneNumber = _phoneNumber.substring(1);
                }
                final String _completePhoneNumber = "+" + forget_country_code_picker.getFullNumber() + _phoneNumber;

                //Check whether user exits or not
                Query checkUser= FirebaseDatabase.getInstance().getReference("Users").orderByChild("phoneNo").equalTo(_completePhoneNumber);
                checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            forget_phone_number.setError(null);
                            forget_phone_number.setErrorEnabled(false);

                            Bundle bundle=new Bundle();
                            bundle.putString("signup_fullname","Old User");
                            bundle.putString("signup_username","Old User");
                            bundle.putString("signup_email","Old User");
                            bundle.putString("signup_password","Old User");
                            bundle.putString("gender","Old User");
                            bundle.putString("date","Old User");
                            bundle.putString("phoneNo",_completePhoneNumber);
                            bundle.putString("whatToDO","updateData");

                            ProfileSignUpOTPFragment fragment= new ProfileSignUpOTPFragment();
                            fragment.setArguments(bundle);

                            AppCompatActivity appCompatActivity = (AppCompatActivity) view.getContext();
                            appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.wrapper, fragment).commit();

                            forgetProgressBar.setVisibility(View.GONE);
                        }
                        else{
                            forgetProgressBar.setVisibility(View.GONE);
                            forget_phone_number.setError("No such user exist!");
                            forget_phone_number.requestFocus();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });

            }
        });

        return view1;
    }

    private void showCustomDialog(View view) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
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
                    }
                });
    }

    private boolean isConnected(Context context) {
        ConnectivityManager connectivityManager= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifiConn=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileConn=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if((wifiConn!=null&& wifiConn.isConnected())||(mobileConn!=null&&mobileConn.isConnected())){
            return true;
        }
        else{
            return false;
        }
    }

    private boolean validateFields() {
        String _phoneNumber = forget_phone_number.getEditText().getText().toString().trim();

        if (_phoneNumber.isEmpty()) {
            forget_phone_number.setError("Phone number can not be empty");
            forget_phone_number.requestFocus();
            return false;
        } else {
            forget_phone_number.setError(null);
            forget_phone_number.setErrorEnabled(false);
            return true;
        }
    }
}