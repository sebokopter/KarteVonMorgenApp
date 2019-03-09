package de.heilsen.kartevonmorgen.app.poi.map.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import de.heilsen.kartevonmorgen.app.poi.map.model.repo.MarkerOptionsRxRepo;

public class MapViewModelFactory implements ViewModelProvider.Factory {

    private final MarkerOptionsRxRepo markerOptionsRxRepo;

    public MapViewModelFactory(MarkerOptionsRxRepo markerOptionsRxRepo) {
        this.markerOptionsRxRepo = markerOptionsRxRepo;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        MapViewModel viewModel = new MapViewModel(markerOptionsRxRepo);
        if (modelClass.isAssignableFrom(viewModel.getClass())) {
            return (T) viewModel;
        }
        throw new IllegalArgumentException("Unknown class name");
    }
}
