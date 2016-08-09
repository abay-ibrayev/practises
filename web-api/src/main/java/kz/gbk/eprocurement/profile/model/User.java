package kz.gbk.eprocurement.profile.model;

import kz.gbk.eprocurement.profile.model.id.TenantId;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User extends BaseUser {

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "tenant_id"))
    private TenantId tenantId;

    private String jobPosition;

    User() {
    }

    public User(TenantId tenantId) {
        this.tenantId = Objects.requireNonNull(tenantId);
    }

    public TenantId getTenantId() {
        return tenantId;
    }

    private void setTenantId(TenantId tenantId) {
        this.tenantId = tenantId;
    }

    public String getJobPosition() {
        return jobPosition;
    }

    public void setJobPosition(String jobPosition) {
        this.jobPosition = jobPosition;
    }
}
