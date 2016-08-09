package kz.gbk.eprocurement.profile.model;

import kz.gbk.eprocurement.profile.model.id.UserId;

import javax.persistence.*;

@MappedSuperclass
public class BaseUser {
    @EmbeddedId
    private UserId id;

    @Column(nullable = false, unique = true)
    private String username;

    private String password;

    private String fullName;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    private boolean active = true;

    public UserId getId() {
        return id;
    }

    public void setId(UserId id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
