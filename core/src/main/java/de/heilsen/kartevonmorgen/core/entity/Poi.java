package de.heilsen.kartevonmorgen.core.entity;

import java.util.Collection;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Poi {
    Id id;
    long created;
    String title;
    String description;
    Coordinate coordinate;
    Collection<Category> categories;
    Collection<String> tags;
    Collection<Rating> ratings;
}