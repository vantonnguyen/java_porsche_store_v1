package vn.tonnguyen.porsche_store_v1.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import vn.tonnguyen.porsche_store_v1.model.Staff;

import java.util.Collection;
import java.util.List;

public class CustomStaffDetails implements UserDetails {
    private final Staff staff;

    public CustomStaffDetails(Staff staff) {
        this.staff = staff;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + staff.getRole()));
    }

    @Override public String getPassword() { return staff.getPassword(); }
    @Override public String getUsername() { return staff.getUsername(); }
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
}

