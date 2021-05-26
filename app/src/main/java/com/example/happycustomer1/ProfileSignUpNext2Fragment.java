package com.example.happycustomer1;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;
import com.hbb20.CountryCodePicker;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileSignUpNext2Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileSignUpNext2Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //Custom Variables
    CountryCodePicker country_code_picker;
    TextInputLayout signup_phone_number;
    Button signup_login_button,signup_next_button;

    public ProfileSignUpNext2Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileSignUpNext2Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileSignUpNext2Fragment newInstance(String param1, String param2) {
        ProfileSignUpNext2Fragment fragment = new ProfileSignUpNext2Fragment();
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
        View view = inflater.inflate(R.layout.fragment_profile_sign_up_next2, container, false);

        //Hooks
        country_code_picker=(CountryCodePicker)view.findViewById(R.id.country_code_picker);
        signup_phone_number=(TextInputLayout)view.findViewById(R.id.signup_phone_number);
        signup_login_button=(Button)view.findViewById(R.id.signup_login_button);
        signup_next_button=(Button)view.findViewById(R.id.signup_next_button);

        //Data From Previous Fragment
        Bundle bundle=this.getArguments();

        signup_next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!validatePhoneNumber()){
                    return;
                }

                //Getting values of fields
                String _getUserEnteredPhoneNumber=signup_phone_number.getEditText().getText().toString().trim();
                String _phoneNo="+"+country_code_picker.getFullNumber()+_getUserEnteredPhoneNumber;

                //Making bundle to send data to next fragment
                bundle.putString("phoneNo",_phoneNo);
                bundle.putString("whatToDO","createNewUser");

                ProfileSignUpOTPFragment fragment2= new ProfileSignUpOTPFragment();
                fragment2.setArguments(bundle);

                AppCompatActivity appCompatActivity = (AppCompatActivity) view.getContext();
                appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.wrapper, fragment2).commit();

            }
        });

        return view;
    }

    private boolean validatePhoneNumber() {
        String val = signup_phone_number.getEditText().getText().toString().trim();
        String checkspaces = "\\A\\w{1,20}\\z";
        if (val.isEmpty()) {
            signup_phone_number.setError("Enter valid phone number");
            return false;
        } else if (!val.matches(checkspaces)) {
            signup_phone_number.setError("No White spaces are allowed!");
            return false;
        }
        else if (val.length()<10) {
            signup_phone_number.setError("Enter valid phone number");
            return false;
        }
        else {
            signup_phone_number.setError(null);
            signup_phone_number.setErrorEnabled(false);
            return true;
        }
    }

}