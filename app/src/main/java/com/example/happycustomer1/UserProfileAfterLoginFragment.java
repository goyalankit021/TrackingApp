package com.example.happycustomer1;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //Variable
    TextView temp;

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
        View view1 = inflater.inflate(R.layout.fragment_profile_login, container, false);

        //Hooks
        temp=(TextView)view1.findViewById(R.id.temp);
        SessionManager sessionManager=new SessionManager(view1.getContext());
        HashMap<String,String> userDetails=sessionManager.getUserDetailFromSession();

        String fullName=userDetails.get(SessionManager.KEY_FULLNAME);
        temp.setText(fullName);

        return view1;
    }
}