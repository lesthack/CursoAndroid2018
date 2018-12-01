package jorgeluis.mapascurso;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MapaActivity extends AppCompatActivity
        implements OnMapReadyCallback, GoogleMap.OnMapClickListener, LocationListener,
        Response.Listener<String>, Response.ErrorListener {

    private GoogleMap mMap;
    private LocationManager locationManager;
    private String best_provider;
    private boolean banRED = false;
    private boolean banGPS = false;
    //private LatLng posactual;
    private LatLng latlng;
    private int n=0;

    private RequestQueue qSolicitudes;
    private Marker[] arrMarcas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);

        if(getIntent().getExtras().containsKey("latlng")){
            Log.i("Dev", "Si hay");
            latlng = (LatLng) getIntent().getExtras().get("latlng");
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        qSolicitudes = Volley.newRequestQueue(this);
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

        if(latlng != null){
            //latlng = new LatLng(20.5392052, -100.828269);
            //mMap.addMarker(new MarkerOptions().position(latlng).title("Marker in Sydney"));
            //mMap.moveCamera(CameraUpdateFactory.newLatLng(latlng));
            crearMarca(latlng);
        }

        mMap.setOnMapClickListener(this);

        miPosicion();

        //mMap.setMapStyle(new MapStyleOptions(getResources().getString(R.string.style_json)));
        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.style_json));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_mapa, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.mapa_hibrido:
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                break;
            case R.id.mapa_relieve:
                mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                break;
            case R.id.mapa_satellite:
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                break;
            default:
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapClick(LatLng pos) {
        crearMarca(pos);
    }

    private void crearMarca(LatLng pos){
        mMap.addMarker(
                new MarkerOptions()
                        .position(pos)
                        .title("Point " + String.valueOf(n++))
                        .snippet("Algo mas")
                //.icon(icon)
        );
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(pos));

        CameraPosition cam = new CameraPosition.Builder().target(pos).zoom(15).build();
        CameraUpdate camUpd = CameraUpdateFactory.newCameraPosition(cam);
        mMap.animateCamera(camUpd);
    }

    private void miPosicion(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            return;
        }
        if(locationManager==null){
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Criteria criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_COARSE);
            banGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            banRED = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            best_provider = locationManager.getBestProvider(criteria, true);
        }

        Location posicion=null;

        // Mi metod es usando el best_provider
        /*locationManager.requestLocationUpdates(best_provider, 1000, 10, this);
        posicion = locationManager.getLastKnownLocation(best_provider);
        if(posicion != null){
            latlng = new LatLng(posicion.getAltitude(), posicion.getLongitude());
            crearMarca(latlng);
        }*/

        if(banRED){
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 10, this);
            posicion = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }
        if(banGPS){
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 10, this);
            posicion = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }
        if(posicion != null){
            latlng = new LatLng(posicion.getLatitude(), posicion.getLongitude());
            crearMarca(latlng);
            lugaresCercanos();
        }


    }

    @Override
    public void onLocationChanged(Location location) {
        LatLng tlatlng = new LatLng(location.getLatitude(), location.getLongitude());
        /*if(tlatlng!=latlng){
            latlng = tlatlng;
            crearMarca(latlng);
        }*/
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    private void lugaresCercanos(){
        String url = "http://places.demo.api.here.com/places/v1/discover/explore?at=" + String.valueOf(latlng.latitude) + "%2C" + String.valueOf(latlng.longitude) + "&app_id=DemoAppId01082013GAL&app_code=AJKnXv84fjrb0KIHawS0Tg&tf=plain&pretty=true%22&fbclid=IwAR3mQgMJV1T3uWjKyGRgraiMsy_8BnNLlDY8rpG98WsDerjMqjE11kUtWTg#";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, this, this);
        qSolicitudes.add(stringRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(String response) {
        try {
            JSONObject json_data = (JSONObject) new JSONObject(response).get("results");
            JSONArray items = json_data.getJSONArray("items");
            arrMarcas = new Marker[items.length()];
            for(int i=0; i<items.length(); i++){
                JSONObject itemJson = items.getJSONObject(i);
                LatLng marcalatlng = new LatLng(itemJson.getJSONArray("position").getDouble(0), itemJson.getJSONArray("position").getDouble(1));
                arrMarcas[i] = mMap.addMarker(
                  new MarkerOptions().position(marcalatlng).title(itemJson.getString("title"))
                );
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

