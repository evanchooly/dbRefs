package com.foo;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import dev.morphia.Datastore;
import dev.morphia.Morphia;
import dev.morphia.mapping.MapperOptions;
import dev.morphia.query.Query;
import org.bson.UuidRepresentation;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.UUID;

import static com.mongodb.MongoClientSettings.builder;

public class ReproducerTest {
    protected static final String TEST_DB_NAME = "morphia_test";
    protected static MongoClient mongoClient;
    protected MongoDatabase database;
    protected Datastore datastore;

    public ReproducerTest() {
        MapperOptions mapperOptions = MapperOptions.builder()
                                                   .uuidRepresentation(UuidRepresentation.JAVA_LEGACY)
                                                   .build();
        mongoClient = MongoClients.create(builder()
                                              .uuidRepresentation(mapperOptions.getUuidRepresentation())
                                              .build());
        this.database = mongoClient.getDatabase(TEST_DB_NAME);
        this.datastore = Morphia.createDatastore(mongoClient, database.getName());
    }

    @Test
    public void reproduce() {
        datastore.getMapper().map(Media.class, MediaItem.class);
        UUID id = UUID.fromString("8a51c09b-843b-4b76-ac33-db3701f180f4");

        Media media = datastore.find(Media.class).first();
        Assert.assertNotNull(media);
        Assert.assertNotNull(media.mediaItem);
        Assert.assertEquals(media.mediaItem.id, id);

        Dummy dummy = datastore.find(Dummy.class).first();
        Assert.assertNotNull(dummy);
        Assert.assertNotNull(dummy.getDummyRef());
        Assert.assertEquals(dummy.getDummyRef().getId(), id);
    }
}
