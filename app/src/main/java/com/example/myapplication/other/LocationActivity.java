package com.example.myapplication.other;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.myLib.MyRequestPermission;

//DDMS测试,Sdk\tools\monitor.bat
public class LocationActivity extends AppCompatActivity implements MyRequestPermission.GrantedCallback{
    MyRequestPermission myRequestPermission;
    LocationManager locationManager;
    MyLocationListener myLocationListener;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        myRequestPermission = new MyRequestPermission("android.permission.ACCESS_FINE_LOCATION",
                this,this);
        myRequestPermission.register();
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        myLocationListener = new MyLocationListener(this);
    }

    public void onButton1Click(View view){
        myRequestPermission.beginRequest();
    }

    public void onButton2Click(View view){
        locationManager.removeUpdates(myLocationListener);
    }

    @Override
    public void grantedProcess() {
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, myLocationListener);
    }
}

class MyLocationListener implements LocationListener {

    Context context;
    public MyLocationListener(Context context){
        this.context = context;
    }
    @Override
    public void onLocationChanged(Location location) {
        Log.i("GpsLocation","您当前位置的经度为" + location.getLongitude());
        Log.i("GpsLocation","您当前位置的纬度为" + location.getLatitude());
    }
    @Override
    public void onProviderDisabled(String provider) {
        Log.i("GpsLocation", "provider被关闭! ");
        Toast.makeText(context,"GPS 没打开",Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onProviderEnabled(String provider) {
        Log.i("GpsLocation", "provider被开启! ");
        Toast.makeText(context,"开始定位",Toast.LENGTH_SHORT).show();

    }

    //no use
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        //当provider的状态在OUT_OF_SERVICE、TEMPORARILY_UNAVAILABLE 和 AVAILABLE 之间发生变化时调用
        Log.i("GpsLocation", "provider状态发生改变!");
    }
}
