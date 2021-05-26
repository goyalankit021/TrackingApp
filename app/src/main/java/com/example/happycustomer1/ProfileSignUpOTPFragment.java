package com.example.happycustomer1;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileSignUpOTPFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileSignUpOTPFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //Custom Variables
    PinView pin_view;
    String codeBySystem;
    Button verify_button;
    TextView otp_description_text;
    ImageView close_button;

    //Varibles
    androidx.appcompat.widget.Toolbar toolbarTop;
    TextView mTitle;

    //Data of user
    String signup_fullname,signup_username,signup_email,signup_password,gender,date,phoneNo,whatToDO;

    public ProfileSignUpOTPFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileSignUpOTPFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileSignUpOTPFragment newInstance(String param1, String param2) {
        ProfileSignUpOTPFragment fragment = new ProfileSignUpOTPFragment();
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
        View view1 = inflater.inflate(R.layout.fragment_profile_sign_up_o_t_p, container, false);

        //Hooks
        pin_view=(PinView)view1.findViewById(R.id.pin_view);
        verify_button=(Button)view1.findViewById(R.id.verify_button);
        otp_description_text=(TextView)view1.findViewById(R.id.otp_description_text);
        close_button=(ImageView)view1.findViewById(R.id.close_button);

        //To change color and heading on toolbar
        toolbarTop = (Toolbar) getActivity().findViewById(R.id.toolbar);
        mTitle = (TextView)toolbarTop.findViewById(R.id.toolbar_title);
        mTitle.setText("PROFILE");

        //To change color
        toolbarTop.setBackgroundColor(Color.parseColor("#FFFFFF"));


        //Back to home screen on close btton
        close_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity appCompatActivity = (AppCompatActivity) view.getContext();
                appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.wrapper, new ProfileFragment()).commit();
            }
        });



        //Data From Previous Fragment
        Bundle bundle=this.getArguments();

        //Getting every value from previous files
        signup_fullname=bundle.getString("signup_fullname");
        signup_username=bundle.getString("signup_username");
        signup_email=bundle.getString("signup_email");
        signup_password=bundle.getString("signup_password");
        gender=bundle.getString("gender");
        date=bundle.getString("date");
        phoneNo=bundle.getString("phoneNo");
        whatToDO=bundle.getString("whatToDO");



        //Set data in Text view, might give eroor
        String description="Enter one time password sent on\n"+phoneNo;
        otp_description_text.setText(description);


        //Function for sending code to user
        sendVerificationCodeToUser(phoneNo);

        verify_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code=pin_view.getText().toString();
                if(!code.isEmpty()){
                    verifyCode(code);
                }
                else
                {
                    Toast.makeText(getContext(),"Please enter otp and press verify",Toast.LENGTH_SHORT).show();
                }

            }
        });

        return view1;
    }

    private void sendVerificationCodeToUser(String phoneNo) {
        PhoneAuthProvider.verifyPhoneNumber(
                PhoneAuthOptions
                        .newBuilder(FirebaseAuth.getInstance())
                        .setActivity(getActivity())
                        .setPhoneNumber(phoneNo)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setCallbacks(mCallbacks)
                        .build());       // OnVerificationStateChangedCallbacks

    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks =
            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                @Override
                public void onCodeSent(@NonNull @NotNull String s, @NotNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
//                    super.onCodeSent(s, forceResendingToken);
                    codeBySystem = s;
                    Log.i("CODE_SENT", "Code success sent"+s);
                }

                @Override
                public void onVerificationCompleted( @NotNull PhoneAuthCredential phoneAuthCredential) {
                    String code = phoneAuthCredential.getSmsCode();
                    if (code != null) {
                        pin_view.setText(code);
                        verifyCode(code);
                    }
                    Log.i("CODE_SENT", "verification complete"+code);
                }

                @Override
                public void onVerificationFailed(@NotNull FirebaseException e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.i("CODE_SENT", "verification failed");
                }
            };

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeBySystem, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        //Migh give errror because of (Executor) https://www.youtube.com/watch?v=u0ZpjFZCXus
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener((Executor) this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //Verification completed successfully here Either
                            // store the data or do whatever desire

                            if (whatToDO.equals("updateData")) {
                                updateOldUsersData();
                            } else if (whatToDO.equals("createNewUser")) {
                                storeNewUserData();
                            }
                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(getContext(), "Verification Not Completed! Try again.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    private void updateOldUsersData() {
        Bundle bundle=new Bundle();
        bundle.putString("phoneNo",phoneNo);

        SetNewPasswordFragment fragment= new SetNewPasswordFragment();
        fragment.setArguments(bundle);

        //getView() might create error
        AppCompatActivity appCompatActivity = (AppCompatActivity) getView().getContext();
        appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.wrapper, fragment).commit();

        //Finish this activity here like add back to stack
    }

    private void storeNewUserData() {
        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        DatabaseReference reference = rootNode.getReference("Users");

        //Create helperclass reference and store data using firebase
        UserHelperClass addNewUser = new UserHelperClass(signup_fullname,signup_username,signup_email,signup_password,gender,date,phoneNo);
        reference.child(phoneNo).setValue(addNewUser);

        //Taking him to Profile
        AppCompatActivity appCompatActivity = (AppCompatActivity) getContext();
        appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.wrapper, new UserProfileAfterLoginFragment()).commit();
        //We will also create a Session here in next videos to keep the user logged In
/*Have to do code here by myseld
        startActivity(new Intent(getApplicationContext(), RetailerDashboard.class));
        finish();*/
    }
}