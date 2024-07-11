package com.example.touristapplicationbyhighhopes;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TouristSpotMapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String spotName;
    private TextView tvWeather;
    private final String API_KEY = "9cc205598cfb4bf879ca92c252efd1e6";  // Replace with your OpenWeatherMap API key
    private LatLng touristSpotLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tourist_spot_map);

        // Get data from intent
        spotName = getIntent().getStringExtra("spotName");

        // Initialize views
        tvWeather = findViewById(R.id.tvWeather);
        Button btnDirections = findViewById(R.id.btnDirections);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Set click listener for directions button
        btnDirections.setOnClickListener(v -> {
            if (touristSpotLocation != null) {
                launchGoogleMapsForDirections(touristSpotLocation);
            } else {
                Toast.makeText(TouristSpotMapActivity.this, "Location not available.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker and move the camera
        touristSpotLocation = getTouristSpotLocation(spotName);
        if (touristSpotLocation != null) {
            mMap.addMarker(new MarkerOptions().position(touristSpotLocation).title(spotName));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(touristSpotLocation, 15));
            fetchWeatherData(touristSpotLocation.latitude, touristSpotLocation.longitude);
        }
    }

    private LatLng getTouristSpotLocation(String spotName) {
        // Implement this method to return the LatLng for each tourist spot
        switch (spotName) {
            case "Kaamulan Park":
                return new LatLng(8.1463, 125.1280); // Replace with actual coordinates
            case "Monastery of Transfiguration":
                return new LatLng(8.1865, 125.0697); // Replace with actual coordinates
            case "Malaybalay city Plaza":
                return new LatLng(8.1561, 125.1292); // Replace with actual coordinates
            case "Mt Capistrano":
                return new LatLng(8.0521, 125.1467); // Replace with actual coordinates
            case "Capitol Grounds":
                return new LatLng(8.1578, 125.1273); // Replace with actual coordinates
            case "Dalwangan Centennial Marker":
                return new LatLng(8.2186, 125.0953); // Replace with actual coordinates
            case "Ereccion Del Pueblo":
                return new LatLng(8.1576, 125.1284); // Replace with actual coordinates
            case "Nasuli Spring":
                return new LatLng(8.1068, 125.0887); // Replace with actual coordinates
            case "Two Trees":
                return new LatLng(8.2000, 125.1150); // Replace with actual coordinates
            case "Mt Kitanglad":
                return new LatLng(8.1193, 125.0578); // Replace with actual coordinates
            default:
                return null;
        }
    }

    private void fetchWeatherData(double lat, double lon) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WeatherService service = retrofit.create(WeatherService.class);
        Call<WeatherResponse> call = service.getCurrentWeather(lat, lon, API_KEY, "metric");
        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    WeatherResponse weatherResponse = response.body();
                    String weatherInfo = "Temp: " + weatherResponse.main.temp + "°C\n" +
                            "Description: " + weatherResponse.weather[0].description;
                    tvWeather.setText(weatherInfo);
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                tvWeather.setText("Failed to get weather data.");
            }
        });
    }

    private void launchGoogleMapsForDirections(LatLng destination) {
        String uri = "http://maps.google.com/maps?daddr=" + destination.latitude + "," + destination.longitude;
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        intent.setPackage("com.google.android.apps.maps");

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            // Try a generic map intent as a fallback
            intent.setPackage(null);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            } else {
                // Log the package name and show a message if not resolved
                Log.e("TouristSpotMapActivity", "Google Maps is not installed or is disabled. Package: com.google.android.apps.maps");
                Toast.makeText(this, "Google Maps is not installed or is disabled.", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
