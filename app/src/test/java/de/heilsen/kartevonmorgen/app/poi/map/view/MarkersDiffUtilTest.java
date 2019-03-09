package de.heilsen.kartevonmorgen.app.poi.map.view;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.codehaus.groovy.runtime.DefaultGroovyMethods.any;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

//TODO: make me work without mockito
class MarkersDiffUtilTest {
//
//    private final Marker returnedMarker = null;
//    private final LatLng returnedPosition = new LatLng(1, 2);
//    private List<Marker> markerList;
//    private List<MarkerOptions> markerOptionsList;
//    private MarkersDiffUtil.OnMarkerAddedCallback onMarkerAddedCallback;
//
//    @BeforeEach
//    void setUp() {
//        markerList = new ArrayList<>();
//        markerOptionsList = new ArrayList<>();
//        onMarkerAddedCallback = markerOptions -> null;
//    }
//
//    @Test
//    void markerIsAdded() {
//        markerOptionsList.add(new MarkerOptions().title("title"));
//
//        MarkersDiffUtil.addNewMarker(
//                markerList,
//                markerOptionsList,
//                onMarkerAddedCallback);
//
//        assertThat(markerList.size(), is(1));
//    }
//
//    @Test
//    void onMarkerAddedIsCalled() {
//        markerOptionsList.add(new MarkerOptions().position(returnedPosition));
//        doReturn(returnedMarker).when(onMarkerAddedCallback).add(any(MarkerOptions.class));
//
//        MarkersDiffUtil.addNewMarker(
//                markerList,
//                markerOptionsList,
//                onMarkerAddedCallback);
//
//        verify(onMarkerAddedCallback).add(any(MarkerOptions.class));
//    }
//
//    @Test
//    void removeMarkerFromList() {
//        doReturn(returnedPosition).when(returnedMarker).getPosition();
//        markerList.add(returnedMarker);
//
//        MarkersDiffUtil.removeMarker(markerList, markerOptionsList);
//
//        verify(returnedMarker).remove();
//    }
//
//    @Test
//    void syncAddsMarker() {
//        MarkerOptions markerOptions = new MarkerOptions().title("title");
//        markerOptionsList.add(markerOptions);
//        doReturn(returnedMarker).when(onMarkerAddedCallback).add(markerOptions);
//
//        MarkersDiffUtil.sync(markerList, markerOptionsList, onMarkerAddedCallback);
//
//        assertThat(markerList.size(), is(1));
//        assertThat(markerOptionsList.size(), is(1));
//        verify(onMarkerAddedCallback).add(markerOptions);
//    }
//
//    @Test
//    void syncRemovesMarker() {
//        markerList.add(returnedMarker);
//
//        MarkersDiffUtil.sync(markerList, markerOptionsList, onMarkerAddedCallback);
//
//        assertThat(markerList.size(), is(0));
//        assertThat(markerOptionsList.size(), is(0));
//        verify(returnedMarker).remove();
//    }
//
//    @Test
//    void noSyncWhenNoDifferenceInTitle() {
//        markerList.add(returnedMarker);
//        doReturn(returnedPosition).when(returnedMarker).getPosition();
//        markerOptionsList.add(new MarkerOptions().position(new LatLng(1,2)));
//
//        MarkersDiffUtil.sync(markerList, markerOptionsList, onMarkerAddedCallback);
//
//        assertThat(markerList.size(), is(1));
//        assertThat(markerOptionsList.size(), is(1));
//        verify(returnedMarker, never()).remove();
//        verify(onMarkerAddedCallback, never()).add(any(MarkerOptions.class));
//    }
}