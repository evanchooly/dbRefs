package com.foo;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import dev.morphia.annotations.Reference;
import org.bson.types.ObjectId;

import java.util.Objects;

@Entity(value = "media", noClassnameStored = true)
public class Media {
    @Id
    private ObjectId id;
    @Reference
    MediaItem mediaItem;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Media)) {
            return false;
        }
        Media media = (Media) o;
        return Objects.equals(id, media.id) &&
               Objects.equals(mediaItem, media.mediaItem);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, mediaItem);
    }
}
