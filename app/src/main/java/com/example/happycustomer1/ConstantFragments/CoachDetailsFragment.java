package com.example.happycustomer1.ConstantFragments;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.happycustomer1.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CoachDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CoachDetailsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //Varibles
    androidx.appcompat.widget.Toolbar toolbarTop;
    TextView mTitle;

    public CoachDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CoachDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CoachDetailsFragment newInstance(String param1, String param2) {
        CoachDetailsFragment fragment = new CoachDetailsFragment();
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
        toolbarTop = (Toolbar) getActivity().findViewById(R.id.toolbar);

        mTitle = (TextView)toolbarTop.findViewById(R.id.toolbar_title);
        mTitle.setText("COACH DETAILS");
        return inflater.inflate(R.layout.fragment_coach_details, container, false);
    }
}