package de.heilsen.kartevonmorgen.app.poi.map;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import de.heilsen.kartevonmorgen.app.poi.map.view.MarkersDiffUtil;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

class MarkersDiffUtilTest {

    private final Marker returnedMarker = mock(Marker.class);
    private final LatLng returnedPosition = new LatLng(1, 2);
    private List<Marker> markerList;
    private List<MarkerOptions> markerOptionsList;
    private MarkersDiffUtil.OnMarkerAddedCallback onMarkerAddedCallback;

    @BeforeEach
    void setUp() {
        markerList = new ArrayList<>();
        markerOptionsList = new ArrayList<>();
        onMarkerAddedCallback = mock(MarkersDiffUtil.OnMarkerAddedCallback.class);
    }

    @Test
    void markerIsAdded() {
        markerOptionsList.add(new MarkerOptions().title("title"));
        doReturn(returnedMarker).when(onMarkerAddedCallback).add(any(MarkerOptions.class));

        MarkersDiffUtil.addNewMarker(
                markerList,
                markerOptionsList,
                onMarkerAddedCallback);

        assertThat(markerList.size(), is(1));
    }

    @Test
    void onMarkerAddedIsCalled() {
        markerOptionsList.add(new MarkerOptions().position(returnedPosition));
        doReturn(returnedMarker).when(onMarkerAddedCallback).add(any(MarkerOptions.class));

        MarkersDiffUtil.addNewMarker(
                markerList,
                markerOptionsList,
                onMarkerAddedCallback);

        verify(onMarkerAddedCallback).add(any(MarkerOptions.class));
    }

    @Test
    void removeMarkerFromList() {
        doReturn(returnedPosition).when(returnedMarker).getPosition();
        markerList.add(returnedMarker);

        MarkersDiffUtil.removeMarker(markerList, markerOptionsList);

        verify(returnedMarker).remove();
    }

    @Test
    void syncAddsMarker() {
        MarkerOptions markerOptions = new MarkerOptions().title("title");
        markerOptionsList.add(markerOptions);
        doReturn(returnedMarker).when(onMarkerAddedCallback).add(markerOptions);

        MarkersDiffUtil.sync(markerList, markerOptionsList, onMarkerAddedCallback);

        assertThat(markerList.size(), is(1));
        assertThat(markerOptionsList.size(), is(1));
        verify(onMarkerAddedCallback).add(markerOptions);
    }

    @Test
    void syncRemovesMarker() {
        markerList.add(returnedMarker);

        MarkersDiffUtil.sync(markerList, markerOptionsList, onMarkerAddedCallback);

        assertThat(markerList.size(), is(0));
        assertThat(markerOptionsList.size(), is(0));
        verify(returnedMarker).remove();
    }

    @Test
    void noSyncWhenNoDifferenceInTitle() {
        markerList.add(returnedMarker);
        doReturn(returnedPosition).when(returnedMarker).getPosition();
        markerOptionsList.add(new MarkerOptions().position(new LatLng(1,2)));

        MarkersDiffUtil.sync(markerList, markerOptionsList, onMarkerAddedCallback);

        assertThat(markerList.size(), is(1));
        assertThat(markerOptionsList.size(), is(1));
        verify(returnedMarker, never()).remove();
        verify(onMarkerAddedCallback, never()).add(any(MarkerOptions.class));
    }
}