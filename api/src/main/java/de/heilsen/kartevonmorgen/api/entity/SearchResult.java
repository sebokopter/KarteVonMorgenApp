package de.heilsen.kartevonmorgen.api.entity;

import java.util.List;

import lombok.Value;

@Value
public class SearchResult {
    List<EntryLocation> visible;
    List<EntryLocation> invisible;
}
