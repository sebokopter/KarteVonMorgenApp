package de.heilsen.kartevonmorgen.core.entity;

import lombok.NonNull;
import lombok.Value;

@Value
public class Id {
    @NonNull
    String value;
}
