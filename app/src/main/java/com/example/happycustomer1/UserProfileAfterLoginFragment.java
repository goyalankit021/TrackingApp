package com.example.happycustomer1;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserProfileAfterLoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserProfileAfterLoginFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    TextView username,gender,bmi_value,bmi_category;
    EditText height,weight;
    Button bmi_button;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    //Varibles
    androidx.appcompat.widget.Toolbar toolbarTop;
    TextView mTitle;

    public UserProfileAfterLoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserProfileAfterLoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserProfileAfterLoginFragment newInstance(String param1, String param2) {
        UserProfileAfterLoginFragment fragment = new UserProfileAfterLoginFragment();
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
        View view1 = inflater.inflate(R.layout.fragment_user_profile_after_login, container, false);

        //Hooks
        /*temp=(TextView)view1.findViewById(R.id.temp);
        SessionManager sessionManager=new SessionManager(view1.getContext());
        HashMap<String,String> userDetails=sessionManager.getUserDetailFromSession();

        String fullName=userDetails.get(SessionManager.KEY_FULLNAME);
        temp.setText(fullName);

        Firebas*/

        //Toolbar
        toolbarTop = (Toolbar) getActivity().findViewById(R.id.toolbar);
        mTitle = (TextView)toolbarTop.findViewById(R.id.toolbar_title);
        mTitle.setText("profile");


        //Text Field for Name
        username=(TextView)view1.findViewById(R.id.username);

        //Textfield for gender
        gender=(TextView)view1.findViewById(R.id.Gender);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("userLoginSession", Context.MODE_PRIVATE);
        String fullNameSH = sharedPreferences.getString("fullName","Unknown");
        String genderSH = sharedPreferences.getString("gender","Unknown");
        username.setText(fullNameSH);
        gender.setText(genderSH);


        //After this code is complete
        height=(EditText)view1.findViewById(R.id.height);
        weight=(EditText)view1.findViewById(R.id.weight);
        bmi_button=(Button)view1.findViewById(R.id.emi_button);

        bmi_category=(TextView)view1.findViewById(R.id.bmi_catergory);
        bmi_value=(TextView)view1.findViewById(R.id.bmi_catergory);

        bmi_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAndPrintBmiValueAndCategory();
            }
        });

        return view1;
    }

    private void checkAndPrintBmiValueAndCategory() {
        String heightVal=height.getText().toString().trim();
        String weightVal=weight.getText().toString().trim();

        if(heightVal==null||weightVal==null||heightVal.isEmpty()||weightVal.isEmpty()){
            showErrorToast();
            return;
        }
        else
        {
            for(int i=0;i<heightVal.length();i++)
            {
                if(!Character.isDigit(heightVal.charAt(i))){
                    showErrorToast();
                    return;
                }
            }
            for(int i=0;i<weightVal.length();i++)
            {
                if(!Character.isDigit(weightVal.charAt(i))){
                    showErrorToast();
                    return;
                }
            }
        }

        float weight_val_int=Float.valueOf(weightVal);
        float height_val_int=Float.valueOf(weightVal);
        float val=(weight_val_int/(height_val_int*height_val_int))*10000;
        bmi_value.setText(String.valueOf(val));


        if(val<18.5){
            bmi_category.setText("Underweight");
        }else if(val>=18.5&&val<=24.9)
        {
            bmi_category.setText("Normal");
        }else if(val>=25&&val<=29.9){
            bmi_category.setText("Overweight");
        }
        else
        {
            bmi_category.setText("Obesity");
        }



    }

    private void showErrorToast() {
        Toast.makeText(getContext(),"Please enter values correctly",Toast.LENGTH_SHORT).show();
    }
}