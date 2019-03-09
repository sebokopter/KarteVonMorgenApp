package de.heilsen.kartevonmorgen.core.entity;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class CoordinateTest {
    @Test
    void ctor_positiveLatitude_inRange() {
        Coordinate coordinate = new Coordinate(45, 0);
        assertThat(coordinate, is(new Coordinate(45, 0)));
        assertThat(coordinate, instanceOf(Coordinate.class));
    }

    @Test
    void ctor_negativeLatitude_inRange() {
        Coordinate coordinate = new Coordinate(-45, 0);
        assertThat(coordinate, is(new Coordinate(-45, 0)));
        assertThat(coordinate, instanceOf(Coordinate.class));
    }

    @Test
    void ctor_negativeLatitude_outOfRange() {
        Coordinate expected = new Coordinate(-90, 0);

        Coordinate actual = new Coordinate(-100, 0);

        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    void ctor_positiveLatitude_outOfRange_maxOf90() {
        Coordinate expected = new Coordinate(90, 0);

        Coordinate actual = new Coordinate(100, 0);

        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    void ctor_longitude_inRange() {
        Coordinate longitude = new Coordinate(0, 0);
        assertThat(longitude, is(instanceOf(Coordinate.class)));
        assertThat(longitude, is(new Coordinate(0, 0)));
    }

    @Test
    void ctor_negativeLongitude_outsideRange_isWrapped() {
        Coordinate expected = new Coordinate(0, 160);

        Coordinate actual = new Coordinate(0, -200);

        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    void ctor_positiveLongitude_outsideRange_isWrapped() {
        Coordinate expected = new Coordinate(0, -60);

        Coordinate actual = new Coordinate(0, 300);

        assertThat(actual, is(equalTo(expected)));
    }
}