package com.foo;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;

import java.util.Objects;
import java.util.UUID;

@Entity(value = "DummyRef", useDiscriminator = false)
public class DummyRef {
    @Id
    private final UUID id;

    protected DummyRef() {
        id = null;
    }

    public DummyRef(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DummyRef)) {
            return false;
        }
        DummyRef dummyRef = (DummyRef) o;
        return Objects.equals(id, dummyRef.id);
    }
}