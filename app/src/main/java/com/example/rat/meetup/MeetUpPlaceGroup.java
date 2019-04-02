package com.example.rat.meetup;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MeetUpPlaceGroup extends AppCompatActivity implements View.OnClickListener{

    TextView name;
    boolean locationPermission = false;
    double userLng=0.0, userLat=0.0;
    String key;
    String sender,receiver;
    Button placeButton;
    ImageButton back;

    RecyclerView recyclerView;
    MeetUpPlaceAdapter adapter;
    List<Address> addresses;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meet_up_place);


        init();
        if(locationPermission){
            getDeviceLoaction();
        }else {
            getLocationPermission();
        }

    }

    private void getData(){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("groups/"+key+"/places");

        String id = sender;
        myRef.child(id).setValue(new GettingAddress(userLng,userLat));
    }

    private void init() {
        name = (TextView)findViewById(R.id.name);
        key = getIntent().getStringExtra("key");
        sender = getIntent().getStringExtra("sender");

        receiver = getIntent().getStringExtra("receiver");


        placeButton = (Button)findViewById(R.id.placeButton);
        placeButton.setOnClickListener(this);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);

        back = (ImageButton)findViewById(R.id.back);
        back.setOnClickListener(this);

    }

    private void getLocationPermission() {
        String[] permissions = {android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION};
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationPermission = true;
                getDeviceLoaction();
            } else {
                ActivityCompat.requestPermissions(this,
                        permissions,
                        1234);
            }
        } else {
            ActivityCompat.requestPermissions(this,
                    permissions,
                    1234);
        }
    }

    private void getDeviceLoaction() {
        FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        try {
            if (locationPermission) {
                Task location = fusedLocationProviderClient.getLastLocation();
                Toast.makeText(this, "Getting Device Location", Toast.LENGTH_SHORT).show();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            Location currentLocation = (Location) task.getResult();
                            userLat = currentLocation.getLatitude();
                            userLng = currentLocation.getLongitude();
                            calculation();

                            //Toast.makeText(MainActivity.this, , Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        } catch (SecurityException e) {

        }
    }
    void calculation(){
        getData();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.placeButton){
            showNewPlace();
        }if(v.getId() == R.id.back){
            finish();
        }
    }

    private void showNewPlace() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("groups/"+key+"/places");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int size = 0;
                for(DataSnapshot snapshot : dataSnapshot.getChildren())size++;

                Point[] points = new Point[size];
                int i=0;
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    GettingAddress address = snapshot.getValue(GettingAddress.class);
                    points[i] = new Point(address.getUsrLng(),address.getUsrLat());
                    i++;
                }

                QuickHull hull = new QuickHull();
                List<Point> items = hull.findConvexHull(points);
                try {
                    giveDistance(items);
                } catch (IOException e) {

                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    void giveDistance(List<Point>items) throws IOException {
        double co_x = 0.0;
        double co_y = 0.0;

        if(items.size() ==1){
            co_x = items.get(0).x;
            co_y = items.get(0).y;
        }else{

            double min_x = 200.0,min_y=200.0;
            double max_x = 0, max_y=0;
            for(int i=0;i<items.size();i++){
                System.out.println(items.get(i).x +" "+items.get(i).y);
                min_x = minimum(items.get(i).x, min_x);
                min_y = minimum(items.get(i).y, min_y);
                max_x = maximum(items.get(i).x, max_x);
                max_y = maximum(items.get(i).y, max_y);
            }
            co_x = (min_x+max_x)/2;
            co_y = (min_y+max_y)/2;
        }

        getNewAddress(co_x,co_y);
    }

    private void  getNewAddress(double usrLng, double usrLat) throws IOException {

        Geocoder geocoder;

        LocationManager locationManager;

        geocoder = new Geocoder(this, Locale.getDefault());

        addresses = geocoder.getFromLocation(usrLat,usrLng,5);


        adapter = new MeetUpPlaceAdapter(addresses,MeetUpPlaceGroup.this);
        final LinearLayoutManager mLayoutManager= new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);

    }

    private double maximum(double a,double b){
        double num;

        if(a>b){
            num = a;
        }
        else{
            num = b;
        }
        return num;
    }
    private double minimum(double x, double min_x) {
        double num;

        if(x<min_x){
            num = x;
        }
        else{
            num = min_x;
        }
        return num;
    }
}
