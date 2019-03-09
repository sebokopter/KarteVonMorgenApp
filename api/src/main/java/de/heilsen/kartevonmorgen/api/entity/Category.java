package de.heilsen.kartevonmorgen.api.entity;

import lombok.Value;

@Value
public class Category {
    String id;
    String name;
    long version;
    long created;
}
