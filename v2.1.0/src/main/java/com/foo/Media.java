package com.foo;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import dev.morphia.annotations.Reference;
import org.bson.types.ObjectId;

@Entity(value = "media", useDiscriminator = false)
public class Media {
    @Id
    private ObjectId id;
    @Reference
    MediaItem mediaItem;
}
