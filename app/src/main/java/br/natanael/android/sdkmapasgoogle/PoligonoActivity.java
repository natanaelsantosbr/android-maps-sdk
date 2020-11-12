package br.natanael.android.sdkmapasgoogle;

import androidx.fragment.app.FragmentActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class PoligonoActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poligono);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //Adicionar a Latitude
        final LatLng meuEndereco = new LatLng(-15.8668565, -48.0644553);

        PolygonOptions polygonOptions = new PolygonOptions();
        polygonOptions.add(new LatLng(-15.8669208,-48.0630578));
        polygonOptions.add(new LatLng(-15.8668948,-48.0621343));
        polygonOptions.add(new LatLng(-15.8673473,-48.0619317));

        mMap.addPolygon(polygonOptions);

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                LatLng a = new LatLng(-15.8668565, -48.0644553);
                PolylineOptions polylineOptions = new PolylineOptions();
                polylineOptions.add(a);
                polylineOptions.add(latLng);
                polylineOptions.color(Color.BLUE);
                polylineOptions.width(20);

                mMap.addPolyline(polylineOptions);

            }
        });

        //Adicionar um Icone
        mMap.addMarker(new MarkerOptions()
        .position(meuEndereco)
        .title("Meu Endereço"));

        //Mover a camera
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(meuEndereco, 15));
    }
}
