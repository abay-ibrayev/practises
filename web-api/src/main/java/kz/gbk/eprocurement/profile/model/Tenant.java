package kz.gbk.eprocurement.profile.model;

import kz.gbk.eprocurement.profile.model.id.TenantId;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tenants")
public class Tenant {

    @EmbeddedId
    private TenantId id;

    @Enumerated(EnumType.STRING)
    private TenantRole role;

    private String name;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private User createdBy;

    private boolean active = true;

    private boolean hidden = false;

    Tenant() {
    }

    public Tenant(TenantId id) {
        this.id = id;
    }

    public Tenant(TenantId id, String name) {
        this.id = id;
        this.name = name;
    }

    public TenantId getId() {
        return id;
    }

    public void setId(TenantId id) {
        this.id = id;
    }

    public TenantRole getRole() {
        return role;
    }

    public void setRole(TenantRole role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }
}
