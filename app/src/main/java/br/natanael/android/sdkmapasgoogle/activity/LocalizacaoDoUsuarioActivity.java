package br.natanael.android.sdkmapasgoogle.activity;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import br.natanael.android.sdkmapasgoogle.R;
import br.natanael.android.sdkmapasgoogle.helper.Permissoes;

public class LocalizacaoDoUsuarioActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private String[] permissoes = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
    };

    private LocationManager locationManager;
    private LocationListener locationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_localizacao_do_usuario);

        Permissoes.validarPermissoes(permissoes, this, 1);

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

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);


        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                //Mudar a exibicao do mapa
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

                Double latitude = location.getLatitude();
                Double longitude = location.getLongitude();

                //Adicionar Latitude e Longitude
                LatLng meuEndereco = new LatLng(latitude, longitude);


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

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                //eh quando o status do servico muda. Ex: Permissoes

            }

            @Override
            public void onProviderEnabled(String provider) {
                //eh quando o usuario habilitar o servico de localizacao

            }

            @Override
            public void onProviderDisabled(String provider) {
                //eh quando o usuario desativar o servico de localizacao
            }
        };

        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                0,
                0,
                locationListener

        );




    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        for (int permissaoResultado : grantResults) {
            if (permissaoResultado == PackageManager.PERMISSION_DENIED) {
                alertaValidacaoPermissao();
            } else if (permissaoResultado == PackageManager.PERMISSION_GRANTED) {
                //Recuperar localização do usuario

                if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }


            }
        }

    }

    private void alertaValidacaoPermissao() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permissões Negadas!");
        builder.setMessage("Para utilizar o app é necessário aceitas as condições");
        builder.setCancelable(false);
        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();

            }
        });

        AlertDialog dialog  = builder.create();
        dialog.show();
    }
}
