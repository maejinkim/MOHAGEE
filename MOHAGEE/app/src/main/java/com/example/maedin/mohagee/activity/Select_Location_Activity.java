package com.example.maedin.mohagee.activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.maedin.mohagee.R;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import android.app.AlertDialog;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Select_Location_Activity extends FragmentActivity implements OnMapReadyCallback,
        View.OnClickListener {
    private GoogleMap mMap;
    private Marker currentMarker = null; // 지정 위치 마커
    private Marker selectMarker = null;
    private MarkerOptions curmark;
    private Double CurrentLng;
    private Double CurrentLat;
    private Geocoder geocoder;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 2002;
    private Button select_location_button;
    private Location CurrentLoc;

    final Context context = this;
    private Double pickedLng;
    private Double pickedLat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_location_activity_layout);

        select_location_button = (Button) findViewById(R.id.select_location_button);
        select_location_button.setOnClickListener(this);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_activity_select_location);
        mapFragment.getMapAsync(this);

        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment_select_location);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                Log.d("younho", place.getLatLng().toString());
                if(selectMarker != null)
                    selectMarker.remove();

                pickedLng = place.getLatLng().longitude;
                pickedLat = place.getLatLng().latitude;
                curmark = new MarkerOptions().position(place.getLatLng());

                mMap.clear();
                selectMarker = mMap.addMarker(curmark);
                mMap.moveCamera(CameraUpdateFactory.newLatLng(place.getLatLng()));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(), 15));
                // TODO: Get info about the selected place.
                System.out.println("Place: " + place.getName());
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                System.out.println("An error occurred: " + status);
            }
        });


    }
    public String getAddress(LatLng latLng) { // 좌표 -> 주소 변환
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = null;

        try {

            addresses = geocoder.getFromLocation(
                    latLng.latitude,
                    latLng.longitude,
                    1
            );
        } catch (IOException e) {
            return "주소 변환 불가";
        } catch (IllegalArgumentException e) {
            return "잘못된 GPS 좌표";
        }

        if (addresses == null || addresses.size() == 0) {
            return "주소 식별 불가";
        }
        Address address = addresses.get(0);
        return address.getAddressLine(0).toString();
    }

    public void setCurrentLoc(Location location) { // 위치 지정

        if (currentMarker != null) {
            currentMarker.remove();
        }

        if (location != null) {
            Log.d("younho", "MY_LOC");
            LatLng currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());
            CurrentLng = location.getLongitude();
            CurrentLat = location.getLatitude();

            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(currentLatLng);
            markerOptions.title("내 위치");
            markerOptions.snippet(getAddress(currentLatLng));
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE));
            markerOptions.draggable(true);

            currentMarker = this.mMap.addMarker(markerOptions);

            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15));

            return;
        }

        // 위치를 찾을 수 없는 경우
        Log.d("younho", "Default");
        LatLng SEOUL = new LatLng(37.55, 126.99);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(SEOUL);
        markerOptions.title("서울");
        markerOptions.snippet(getAddress(SEOUL));
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE));

        currentMarker = mMap.addMarker(markerOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(SEOUL, 10));
    }


    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;
        geocoder = new Geocoder(this);
        mMap.getUiSettings().setCompassEnabled(true); // 나침반 설정


        if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }

        // 맵 터치 이벤트 구현 //
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng point) {
                if(selectMarker != null)
                {
                    selectMarker.remove();
                }

                pickedLng = point.longitude;
                pickedLat = point.latitude;
                curmark = new MarkerOptions().position(point);

                selectMarker = mMap.addMarker(curmark);
                selectMarker.setTitle(getAddress(point));


            }
        });

        // Add a marker in Sydney and move the camera
        setCurrentLoc(CurrentLoc);



        if (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)) { // 권한 있는 경우

            Log.d("younho", "enter_granted");
            googleMap.setMyLocationEnabled(true);

            googleMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() { // 현재 위치 버튼 클릭 시
                @Override
                public boolean onMyLocationButtonClick() {
                    Location location = mMap.getMyLocation();
                    setCurrentLoc(location);

                    LatLng latLng = new LatLng(currentMarker.getPosition().latitude, currentMarker.getPosition().longitude);
                    return true;
                }
            });

            mMap.getUiSettings().setRotateGesturesEnabled(false);

        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }


    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.select_location_button:
                if(selectMarker == null)
                {
                    Toast.makeText(context, "장소를 입력해 주세요!", Toast.LENGTH_SHORT).show();
                    break;
                }
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setTitle("어디서 모하지?");
                alertDialogBuilder
                        .setMessage("입력하신 위치로 설정하시겠습니까?")
                        .setCancelable(false)
                        .setPositiveButton("예",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Fragment fragment = getSupportFragmentManager().findFragmentByTag("search_fragment");
                                        //Bundle bundle = new Bundle();
                                        //bundle.putDouble("Lat", pickedLat);
                                        //bundle.putDouble("Lng", pickedLng);
                                        //fragment.setArguments(bundle);
                                        Intent data = new Intent();
                                        data.putExtra("Lat",pickedLat);
                                        data.putExtra("Lng", pickedLng);
                                        LatLng latlng = new LatLng(pickedLat, pickedLng);
                                        data.putExtra("Addr", getAddress(latlng));

                                        String str = String.valueOf(pickedLat);
                                        Log.d("place", str);
                                        setResult(0, data);
                                        Select_Location_Activity.this.finish();
                                    }
                                })
                        .setNegativeButton("취소",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        selectMarker.remove();
                                        dialogInterface.cancel();
                                    }
                                });
                AlertDialog alertDialog = alertDialogBuilder.create();

                alertDialog.show();
                break;
        }
    }



}
