package com.foo;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;

import java.util.UUID;

@Entity(value = "MediaItems", useDiscriminator = false)
public class MediaItem {
    @Id
    UUID id;
    String name;
}
