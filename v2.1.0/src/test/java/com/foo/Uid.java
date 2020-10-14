package com.foo;

import dev.morphia.annotations.Embedded;

import java.util.UUID;

@Embedded(useDiscriminator = false)
public class Uid {
    final String uuid;

    /**
     * Constructor. Simply creates a random UUID.
     */
    public Uid() {
        this.uuid = UUID.randomUUID().toString();
    }

    public Uid(String uuid) {
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }

    public boolean equals(Object obj) {
        final boolean eq;
        if (this == obj) {
            eq = true;
        } else if (obj instanceof Uid) {
            Uid that = (Uid) obj;
            eq = that.uuid.equals(this.uuid);
        } else {
            eq = false;
        }

        return eq;
    }

    public int hashCode() {
        return this.uuid.hashCode();
    }
}
