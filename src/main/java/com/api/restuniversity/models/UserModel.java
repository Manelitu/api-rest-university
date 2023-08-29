package com.api.restuniversity.models;


import com.api.restuniversity.enums.Roles;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
@Data
public class UserModel implements UserDetails, Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(nullable = false)
    private String name;
    @Email
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @Column(nullable = false)
    private Roles roles;
    @Column(nullable = true)
    private Boolean active = true;

    @Column(name="created_at", nullable = false)
    private LocalDateTime createdAt;
    @Column(name="updated_at", nullable = true, updatable = true)
    private LocalDateTime updatedAt = null;
    @Column(name="deleted_at", nullable = true, updatable = true)
    private LocalDateTime deletedAt = null;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.roles == Roles.ADMIN) return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
        else if (this.roles == Roles.STUDENT) return List.of(new SimpleGrantedAuthority("ROLE_STUDENT"));
        else if (this.roles == Roles.PROFESSOR) return List.of(new SimpleGrantedAuthority("ROLE_PROFESSOR"));
        else return List.of(new SimpleGrantedAuthority("ROLE_COORDINATOR"));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
