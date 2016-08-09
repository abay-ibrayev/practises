package kz.gbk.eprocurement.profile.model.id;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import java.io.Serializable;
import java.util.UUID;

@Embeddable
public class TenantId implements Serializable {

    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @GeneratedValue(generator = "uuid2")
    @Column(name = "id", unique = true, columnDefinition = "VARCHAR(36)")
    private java.util.UUID id;

    TenantId() {
    }

    TenantId(java.util.UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    private void setId(UUID id) {
        this.id = id;
    }

    public static TenantId newId() {
        return new TenantId(UUID.randomUUID());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TenantId tenantId = (TenantId) o;

        return id != null ? id.equals(tenantId.id) : tenantId.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
