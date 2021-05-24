package com.example.happycustomer1;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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
        View view = inflater.inflate(R.layout.fragment_profile_sign_up_next1, container, false);
        // here you have the reference of your button
        Button nextButton = (Button)view.findViewById(R.id.signup_next_button);
        Button loginButton = (Button)view.findViewById(R.id.signup_login_button);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity appCompatActivity = (AppCompatActivity) view.getContext();
                appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.wrapper, new ProfileLoginFragment()).commit();
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity appCompatActivity = (AppCompatActivity) view.getContext();
                appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.wrapper, new ProfileSignUpNext2Fragment()).commit();
            }
        });

        return view;
    }
}