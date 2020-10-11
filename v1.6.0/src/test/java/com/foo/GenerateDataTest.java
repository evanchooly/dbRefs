package com.foo;

import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
import dev.morphia.Datastore;
import dev.morphia.Morphia;
import dev.morphia.mapping.Mapper;
import dev.morphia.mapping.MapperOptions;
import dev.morphia.query.Query;
import org.bson.UuidRepresentation;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.UUID;

public class GenerateDataTest {
    private static final String TEST_DB_NAME = "morphia_test";
    private static com.mongodb.MongoClient mongoClient;
    private MongoDatabase database;
    private Datastore datastore;
    private Morphia morphia;

    public GenerateDataTest() {
        MongoClientOptions options = MongoClientOptions.builder()
                                                       .uuidRepresentation(UuidRepresentation.JAVA_LEGACY)
                                                       .build();
        mongoClient = new com.mongodb.MongoClient(new ServerAddress("localhost"), options);
        database = mongoClient.getDatabase(TEST_DB_NAME);
        morphia = new Morphia(new Mapper(MapperOptions.legacy().build()));
        datastore = morphia.createDatastore(mongoClient, this.database.getName());
    }

    @Test
    public void generate() {
        database.drop();
        morphia.map(Media.class, MediaItem.class, Dummy.class, DummyRef.class);
        UUID uuid = UUID.fromString("8a51c09b-843b-4b76-ac33-db3701f180f4");

        Media media = new Media();
        media.mediaItem = new MediaItem();
        media.mediaItem.id = uuid;

        datastore.save(List.of(media.mediaItem, media));

        Query<Media> query = datastore.find(Media.class);
        Media first = query.first();
        Assert.assertEquals(first.mediaItem.id, uuid);

        Dummy dummy = new Dummy(uuid, new DummyRef(uuid));
        datastore.save(dummy.getDummyRef(), dummy);

        Dummy loaded = datastore.find(Dummy.class).first();
        Assert.assertEquals(loaded.getDummyRef(), dummy.getDummyRef());
    }
}