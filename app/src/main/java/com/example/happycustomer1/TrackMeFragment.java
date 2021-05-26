package com.example.happycustomer1;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TrackMeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TrackMeFragment extends Fragment implements OnMapReadyCallback, LocationListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    MapView mapView;
    GoogleMap mMap;
    Location currLocation;
    Marker mCurrLocationMarker;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //Varibles
    androidx.appcompat.widget.Toolbar toolbarTop;
    TextView mTitle;

    public TrackMeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TrackMeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TrackMeFragment newInstance(String param1, String param2) {
        TrackMeFragment fragment = new TrackMeFragment();
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
        mTitle.setText("Trace Me");
        return inflater.inflate(R.layout.fragment_track_me, container, false);

    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        final AutoCompleteTextView autoCompleteTextView= (AutoCompleteTextView)getView().findViewById(R.id.destination);
//        autoCompleteTextView.setAdapter(new PlaceAutoSuggestAdapter(this,android.R.layout.simple_list_item_1));
//        autoCompleteTextView.setOnItemClickListener(new  AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Log.d("Address : ",autoCompleteTextView.getText().toString());
//                LatLng latLng=getLatLngFromAddress(autoCompleteTextView.getText().toString());
//                if(latLng!=null) {
//                    Log.d("Lat Lng : ", " " + latLng.latitude + " " + latLng.longitude);
//                    Address address=getAddressFromLatLng(latLng);
//                    if(address!=null) {
//                        Log.d("Address : ", "" + address.toString());
//                        Log.d("Address Line : ",""+address.getAddressLine(0));
//                        Log.d("Phone : ",""+address.getPhone());
//                        Log.d("Pin Code : ",""+address.getPostalCode());
//                        Log.d("Feature : ",""+address.getFeatureName());
//                        Log.d("More : ",""+address.getLocality());
//                    }
//                    else {
//                        Log.d("Adddress","Address Not Found");
//                    }
//                }
//                else {
//                    Log.d("Lat Lng","Lat Lng Not Found");
//                }
//            }
//        });
        mapView = getView().findViewById(R.id.mapView);
        if (mapView != null) {
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getActivity());
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }
    private LatLng getLatLngFromAddress(String address){

        Geocoder geocoder=new Geocoder(this.getActivity());
        List<Address> addressList;

        try {
            addressList = geocoder.getFromLocationName(address, 1);
            if(addressList!=null){
                Address singleaddress=addressList.get(0);
                LatLng latLng=new LatLng(singleaddress.getLatitude(),singleaddress.getLongitude());
                return latLng;
            }
            else{
                return null;
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    private Address getAddressFromLatLng(LatLng latLng){
        Geocoder geocoder=new Geocoder(this.getActivity());
        List<Address> addresses;
        try {
            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 5);
            if(addresses!=null){
                Address address=addresses.get(0);
                return address;
            }
            else{
                return null;
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        currLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }

        //Place current location marker
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        mCurrLocationMarker = mMap.addMarker(markerOptions);

        //move map camera
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,11));
    }
}