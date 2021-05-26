package com.example.happycustomer1;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SetNewPasswordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SetNewPasswordFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //Custom Variables
    Button set_new_password_btn;
    TextInputLayout new_password,confirm_password;
    RelativeLayout forgetProgressBarNew;
    String phoneNo;

    public SetNewPasswordFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SetNewPasswordFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SetNewPasswordFragment newInstance(String param1, String param2) {
        SetNewPasswordFragment fragment = new SetNewPasswordFragment();
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
        View view = inflater.inflate(R.layout.fragment_set_new_password, container, false);

        new_password=(TextInputLayout) view.findViewById(R.id.new_password);
        confirm_password=(TextInputLayout)view.findViewById(R.id.confirm_password);
        set_new_password_btn=(Button)view.findViewById(R.id.set_new_password_btn);
        forgetProgressBarNew=(RelativeLayout)view.findViewById(R.id.forgetProgressBarNew);

        //Data From Previous Fragment
        Bundle bundle=this.getArguments();
        phoneNo=bundle.getString("phoneNo");

        set_new_password_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isConnected(getContext())){
                    showCustomDialog(view);
                    return;
                }

                if(!validatePassword() | !validateConfirmPassword())
                {
                    return;
                }
                forgetProgressBarNew.setVisibility(View.VISIBLE);

                String _newPassword=new_password.getEditText().getText().toString().trim();

                //Upload data in firebase and in sessions
                DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Users");
                reference.child(phoneNo).child("signup_password").setValue(new_password);

                AppCompatActivity appCompatActivity = (AppCompatActivity) view.getContext();
                appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.wrapper, new ForgetPasswordSuccessMessageFragment()).commit();


            }
        });

        return view;
    }

    private boolean validatePassword() {
        String val = new_password.getEditText().getText().toString().trim();
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
            new_password.setError("Field can not be empty");
            return false;
        } else if (!val.matches(checkPassword)) {
            new_password.setError("Password should contain 4 characters!");
            return false;
        } else {
            new_password.setError(null);
            new_password.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateConfirmPassword(){
        String val = confirm_password.getEditText().getText().toString().trim();
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
            confirm_password.setError("Field can not be empty");
            return false;
        } else if (!val.matches(checkPassword)) {
            confirm_password.setError("Password should contain 4 characters!");
            return false;
        } else if(!new_password.equals(confirm_password)){
            confirm_password.setError("Both password does not match!");
            return false;
        }
        else {
            confirm_password.setError(null);
            confirm_password.setErrorEnabled(false);
            return true;
        }
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
}