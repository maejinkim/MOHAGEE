package com.example.maedin.mohagee.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.Manifest;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.maedin.mohagee.R;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class SearchActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 2002;
    private Geocoder geocoder;
    private Button button;
    private Location mLastKnownLocation;
    private EditText editText;
    private Marker currentMarker = null; // 지정 위치 마커
    private Double CurrentLat;
    private Double CurrentLng;
    private Location CurrentLoc;
    private boolean mPermissionDenied = false;

    private Location lastKnownLocation = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_activity_layout);
        //editText = (EditText) findViewById(R.id.editText);
        //button=(Button)findViewById(R.id.button);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_activity);
        mapFragment.getMapAsync(this);

        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                Location location = new Location("");
                location.setLatitude(place.getLatLng().latitude);
                location.setLongitude(place.getLatLng().longitude);
                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(place.getLatLng()).title(place.getName().toString()));
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


            /*if(circle != null)
                circle.remove();
            circle = this.googleMap.addCircle(new CircleOptions()
                    .center(currentLatLng)
                    .radius(radius)
                    .strokeColor(Color.parseColor("#884169e1"))
                    .fillColor(Color.parseColor("#5587cefa")));*/
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
                MarkerOptions mOptions = new MarkerOptions();
                // 마커 타이틀
                mOptions.title("마커 좌표");
                Double latitude = point.latitude; // 위도
                Double longitude = point.longitude; // 경도
                // 마커의 스니펫(간단한 텍스트) 설정
                mOptions.snippet(latitude.toString() + ", " + longitude.toString());
                // LatLng: 위도 경도 쌍을 나타냄
                mOptions.position(new LatLng(latitude, longitude));
                // 마커(핀) 추가
                googleMap.addMarker(mOptions);
            }
        });
        ////////////////////

        // 버튼 이벤트
//        button.setOnClickListener(new Button.OnClickListener(){
//            @Override
//            public void onClick(View v){
//                String str=editText.getText().toString();
//                List<Address> addressList = null;
//                try {
//                    // editText에 입력한 텍스트(주소, 지역, 장소 등)을 지오 코딩을 이용해 변환
//                    addressList = geocoder.getFromLocationName(
//                            str, // 주소
//                            10); // 최대 검색 결과 개수
//                }
//                catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//                System.out.println(addressList.get(0).toString());
//                // 콤마를 기준으로 split
//                String []splitStr = addressList.get(0).toString().split(",");
//                String address = splitStr[0].substring(splitStr[0].indexOf("\"") + 1,splitStr[0].length() - 2); // 주소
//                System.out.println(address);
//
//                String latitude = splitStr[10].substring(splitStr[10].indexOf("=") + 1); // 위도
//                String longitude = splitStr[12].substring(splitStr[12].indexOf("=") + 1); // 경도
//                System.out.println(latitude);
//                System.out.println(longitude);
//
//                // 좌표(위도, 경도) 생성
//                LatLng point = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
//                // 마커 생성
//                MarkerOptions mOptions2 = new MarkerOptions();
//                mOptions2.title("search result");
//                mOptions2.snippet(address);
//                mOptions2.position(point);
//                // 마커 추가
//                mMap.addMarker(mOptions2);
//                // 해당 좌표로 화면 줌
//                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point,15));
//            }
//        });
        ////////////////////

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

        }

        else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }


    }
}



