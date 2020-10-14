package com.foo;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import dev.morphia.Datastore;
import dev.morphia.Morphia;
import dev.morphia.mapping.MapperOptions;
import dev.morphia.query.internal.MorphiaCursor;
import org.bson.UuidRepresentation;
import org.testng.Assert;
import org.testng.annotations.Test;

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
        datastore.getMapper().map(Dummy.class, DummyRef.class);

        generate();

        final MorphiaCursor<Dummy> dummyCursor = datastore.find(Dummy.class).iterator();
        final Dummy oldMorphiaVersion = dummyCursor.next();
        final Dummy newMorphiaVersion = dummyCursor.next();

        // cannot find reference document if the document is saved with the ol§d version
        Assert.assertNotNull(oldMorphiaVersion);
        Assert.assertNull(oldMorphiaVersion.getDummyRef());

        // finds reference document if the document is saved with the new version
        Assert.assertNotNull(newMorphiaVersion);
        Assert.assertNotNull(newMorphiaVersion.getDummyRef());
    }

    public void generate() {
        Uid uuid = new Uid("8a51c09b-843b-4b76-ac33-db3701f180f6");
        Uid uuid2 = new Uid("8a51c09b-843b-4b76-ac33-db3701f180f7");

        Dummy dummy = new Dummy(uuid, new DummyRef(uuid2));
        datastore.save(dummy.getDummyRef());
        datastore.save(dummy);
    }
}
