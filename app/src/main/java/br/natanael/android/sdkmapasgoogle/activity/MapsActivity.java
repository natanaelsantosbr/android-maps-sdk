package br.natanael.android.sdkmapasgoogle.activity;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import br.natanael.android.sdkmapasgoogle.R;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
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

        //Mudar a exibicao do mapa
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        //Adicionar Latitude e Longitude
        LatLng meuEndereco = new LatLng(-15.8668565, -48.0644553);


        //Adicionar evento de cliques
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mMap.addMarker(
                        new MarkerOptions()
                                .position(latLng)
                                .title("Meu Endereço")
                                .title("local")
                                .snippet("Descricao")
                        .icon( (
                        BitmapDescriptorFactory.fromResource(R.drawable.icone_loja)
                        )));
            }
        });


        mMap.addMarker(
                new MarkerOptions()
                .position(meuEndereco)
                .title("Meu Endereço")
//        .icon(
//                BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
//        .icon(
//                BitmapDescriptorFactory.fromResource(R.drawable.icone_loja)
//        )
        );

        //2,0 ate 21,00
        mMap.moveCamera(
                CameraUpdateFactory.newLatLngZoom(meuEndereco, 15)

        );

    }
}
