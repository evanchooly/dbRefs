package com.foo;

import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
import dev.morphia.Datastore;
import dev.morphia.Morphia;
import dev.morphia.mapping.Mapper;
import dev.morphia.mapping.MapperOptions;
import org.bson.UuidRepresentation;
import org.testng.Assert;
import org.testng.annotations.Test;

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
        final Uid uuid_dummy = new Uid("8a51c09b-843b-4b76-ac33-db3701f180f4");
        final Uid uuid_dummy_ref = new Uid("8a51c09b-843b-4b76-ac33-db3701f180f5");

        final Dummy dummy = new Dummy(uuid_dummy, new DummyRef(uuid_dummy_ref));
        datastore.save(dummy.getDummyRef());
        datastore.save(dummy);

        final Dummy loaded = datastore.find(Dummy.class).first();
        Assert.assertEquals(loaded.getDummyRef(), dummy.getDummyRef());
    }
}