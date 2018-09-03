package com.lisheny.mystationtest;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textview);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= 23) {
            //如果用户并没有同意该权限
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                //申请权限
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 100);
            } else {
                LocationUtil.getCurrentLocation(MainActivity.this, callBack);
            }
        }
    }

    private LocationUtil.LocationCallBack callBack = new LocationUtil.LocationCallBack() {
        @Override
        public void onSuccess(Location location) {
            textView.append("经度: " + location.getLongitude() + " 纬度: " + location.getLatitude()+ "\n");
        }

        @Override
        public void onFail(String msg) {
            textView.append(msg + "\n");
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                LocationUtil.getCurrentLocation(MainActivity.this, callBack);
            } else {
                textView.append("权限没获取！！！"+ "\n");
            }
        }
    }
}
