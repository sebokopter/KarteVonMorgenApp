package de.heilsen.kartevonmorgen.api.entity;

import java.util.ArrayList;
import java.util.List;

import de.heilsen.kartevonmorgen.core.entity.Category;
import de.heilsen.kartevonmorgen.core.entity.Coordinate;
import de.heilsen.kartevonmorgen.core.entity.Id;
import de.heilsen.kartevonmorgen.core.entity.Poi;
import de.heilsen.kartevonmorgen.core.entity.Rating;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Entry {
    String id;
    long created;
    long version;
    String title;
    String description;
    double lat;
    double lng;
    String street;
    String zip;
    String city;
    String country;
    String email;
    String telephone;
    String homepage;
    List<String> categories;
    List<String> tags;
    List<String> ratings;
    String license;
    String image_url;
    String image_link_url;

    public static Poi toPoi(Entry apiEntry) {
        return Poi.builder()
                .id(new Id(apiEntry.getId()))
                .created(apiEntry.getCreated())
                .title(apiEntry.getTitle())
                .description(apiEntry.getDescription())
                .coordinate(new Coordinate(apiEntry.getLat(), apiEntry.getLng()))
                .categories(mapCategories(apiEntry.getCategories()))
                .tags(apiEntry.getTags())
                .ratings(mapRatings(apiEntry.getRatings()))
                .build();
    }

    private static List<de.heilsen.kartevonmorgen.core.entity.Category> mapCategories(List<String> apiCategories) {
        List<de.heilsen.kartevonmorgen.core.entity.Category> categories = new ArrayList<>();
        for (String category : apiCategories) {
            categories.add(new Category(category, category)); //TODO: convert api category to core category
        }
        return categories;
    }

    private static List<Rating> mapRatings(List<String> apiRatings) {
        List<Rating> ratings = new ArrayList<>();
        for (String apiRating : apiRatings) {
            ratings.add(new Rating(apiRating));
        }
        return ratings;
    }

}
