package com.foo;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;

import java.util.Objects;
import java.util.UUID;

@Entity(value = "DummyRef", noClassnameStored = true)
public class DummyRef {
    @Id
    private final Uid id;

    protected DummyRef() {
        id = null;
    }

    public DummyRef(Uid id) {
        this.id = id;
    }

    public Uid getId() {
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