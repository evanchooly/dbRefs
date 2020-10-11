package com.foo;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import dev.morphia.annotations.Reference;

import java.util.Objects;
import java.util.UUID;

@Entity(value = "Dummy", useDiscriminator = false)
public class Dummy {
    @Id
    private final UUID id;

    @Reference(value = "dummyRef", ignoreMissing = true)
    private final DummyRef dummyRef;

    protected Dummy() {
        id = null;
        dummyRef = null;
    }

    public Dummy(UUID id, DummyRef dummyRef) {
        this.id = id;
        this.dummyRef = dummyRef;
    }

    public DummyRef getDummyRef() {
        return dummyRef;
    }

    public UUID getId() {
        return id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dummyRef);
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }
        if (!(o instanceof Dummy)) {
            return false;
        }
        Dummy dummy = (Dummy) o;
        return Objects.equals(id, dummy.id) &&
               Objects.equals(dummyRef, dummy.dummyRef);
    }
}