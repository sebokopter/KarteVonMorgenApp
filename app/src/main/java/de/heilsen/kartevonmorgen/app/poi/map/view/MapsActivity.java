package de.heilsen.kartevonmorgen.app.poi.map.view;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;
import java.util.List;

import de.heilsen.kartevonmorgen.app.R;
import de.heilsen.kartevonmorgen.app.poi.map.model.repo.MarkerOptionsRxRepo;
import de.heilsen.kartevonmorgen.app.poi.map.viewmodel.MapViewModel;
import de.heilsen.kartevonmorgen.app.poi.map.viewmodel.MapViewModelFactory;
import de.heilsen.kartevonmorgen.data.PoiGateway;

import static java.util.Objects.requireNonNull;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleMap.OnCameraMoveStartedListener {

    private MapViewModel viewModel;
    private GoogleMap googleMap;
    private List<Marker> currentlyVisibleMarkers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        viewModel = constructViewModel();
        requireNonNull(mapFragment).getMapAsync(this);
    }

    private MapViewModel constructViewModel() {
        MarkerOptionsRxRepo markerOptionsRxRepo =
                new MarkerOptionsRxRepo(PoiGateway.ofdbPoiRepository("https://api.ofdb.io/v0/"));
        MapViewModelFactory mapViewModelFactory = new MapViewModelFactory(markerOptionsRxRepo);
        return ViewModelProviders.of(this, mapViewModelFactory).get(MapViewModel.class);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        this.googleMap.setOnCameraMoveStartedListener(this);
        viewModel.cameraUpdate().observe(this, googleMap::moveCamera);
        viewModel.visibleRegion().setValue(googleMap.getProjection().getVisibleRegion());
        viewModel.markerOptions().observe(this, markerOptions -> {
            if (markerOptions != null) {
                MarkersDiffUtil.sync(currentlyVisibleMarkers, markerOptions, googleMap::addMarker);
            }
        });
        viewModel.errorState().observe(this, error -> {
            if (error != null) {
                Snackbar.make(findViewById(R.id.map), error.getErrorMessage(), Snackbar.LENGTH_LONG);
            }
        });
    }

    @Override
    public void onCameraMoveStarted(int reason) {
        if (reason == REASON_GESTURE) {
            viewModel.visibleRegion().setValue(googleMap.getProjection().getVisibleRegion());
        }
    }
}
