package de.heilsen.kartevonmorgen.api.entity;

import lombok.Value;

@Value
public class EntryLocation {
    String id;
    double lat;
    double lng;
}
