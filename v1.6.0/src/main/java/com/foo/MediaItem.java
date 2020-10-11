package com.foo;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;

import java.util.Objects;
import java.util.UUID;

@Entity(value = "MediaItems", noClassnameStored = true)
public class MediaItem {
    @Id
    UUID id;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MediaItem)) {
            return false;
        }
        MediaItem mediaItem = (MediaItem) o;
        return Objects.equals(id, mediaItem.id) &&
               Objects.equals(name, mediaItem.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    String name;
}
