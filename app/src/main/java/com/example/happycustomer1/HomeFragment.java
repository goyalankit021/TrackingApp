package com.example.happycustomer1;

import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.happycustomer1.ConstantFragments.CoachDetailsFragment;
import com.example.happycustomer1.ConstantFragments.FirstAidFragment;
import com.example.happycustomer1.ConstantFragments.GymChestAndTricepsFragment;
import com.example.happycustomer1.ConstantFragments.HomeBicepsAndBackFragment;

import org.jetbrains.annotations.NotNull;


import static android.content.Context.MODE_PRIVATE;
import static android.content.Context.SENSOR_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements SensorEventListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private SensorManager sensorManager=null;
    private Sensor mStepCounter=null;
    private boolean running = false;
    private float totalSteps=0f;
    private float previousTotalSteps = 0f;
    private TextView steps;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //Button from xml
    Button bicep_button,coach_button,firstaid_button,trace_button,chest_button;

    //Varibles
    androidx.appcompat.widget.Toolbar toolbarTop;
    TextView mTitle;

    public HomeFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View view1 = inflater.inflate(R.layout.fragment_home, container, false);

        toolbarTop = (Toolbar) getActivity().findViewById(R.id.toolbar);
        mTitle = (TextView)toolbarTop.findViewById(R.id.toolbar_title);
        mTitle.setText("Dashboard");


        //Creating hooks
        bicep_button=(Button)view1.findViewById(R.id.bicep_button);
        coach_button=(Button)view1.findViewById(R.id.coach_button);
        firstaid_button=(Button)view1.findViewById(R.id.firstaid_button);
        trace_button=(Button)view1.findViewById(R.id.trace_button);
        chest_button=(Button)view1.findViewById(R.id.chest_button);


        bicep_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity appCompatActivity1 = (AppCompatActivity) view.getContext();
                appCompatActivity1.getSupportFragmentManager().beginTransaction().replace(R.id.wrapper, new HomeBicepsAndBackFragment()).addToBackStack(null).commit();
            }
        });

        coach_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity appCompatActivity1 = (AppCompatActivity) view.getContext();
                appCompatActivity1.getSupportFragmentManager().beginTransaction().replace(R.id.wrapper, new CoachDetailsFragment()).addToBackStack(null).commit();
            }
        });

        firstaid_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity appCompatActivity1 = (AppCompatActivity) view.getContext();
                appCompatActivity1.getSupportFragmentManager().beginTransaction().replace(R.id.wrapper, new FirstAidFragment()).addToBackStack(null).commit();
            }
        });

        trace_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity appCompatActivity1 = (AppCompatActivity) view.getContext();
                appCompatActivity1.getSupportFragmentManager().beginTransaction().replace(R.id.wrapper, new TrackMeFragment()).addToBackStack(null).commit();
            }
        });

        chest_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity appCompatActivity1 = (AppCompatActivity) view.getContext();
                appCompatActivity1.getSupportFragmentManager().beginTransaction().replace(R.id.wrapper, new GymChestAndTricepsFragment()).addToBackStack(null).commit();
            }
        });


        return view1;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        steps = getView().findViewById(R.id.steps);
        loadData();
        resetSteps();

        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        sensorManager = (SensorManager) getActivity().getSystemService(SENSOR_SERVICE);
        if(sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)!=null){
            mStepCounter=sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        }else {
            steps.setText("NULL");
        }
    }

    private void resetSteps() {
        steps.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                previousTotalSteps=totalSteps;
                if(mStepCounter!=null) {
                    steps.setText(String.valueOf(0));
                }
                saveData();
                return true;
            }
        });
    }

    private void saveData() {

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MySharedPref",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat("previousSteps",previousTotalSteps);
        editor.apply();
    }
    private void loadData(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MySharedPref",MODE_PRIVATE);
        previousTotalSteps = sharedPreferences.getFloat("previousSteps",0f);


    }
    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor == mStepCounter){
            totalSteps = (int) event.values[0];
            steps.setText(String.valueOf((int)totalSteps- (int)previousTotalSteps));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onResume() {
        super.onResume();
        if(sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)!=null){
            sensorManager.registerListener(this,mStepCounter,SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)!=null){
            sensorManager.unregisterListener(this,mStepCounter);
        }
    }
}