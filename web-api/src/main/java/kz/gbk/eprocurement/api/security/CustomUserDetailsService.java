package kz.gbk.eprocurement.api.security;

import kz.gbk.eprocurement.profile.model.BaseUser;
import kz.gbk.eprocurement.profile.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        BaseUser user = userRepository.findOneByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User " + username + " not found");
        }

        return new RepositoryUserDetails(user);
    }

    private final static class RepositoryUserDetails implements UserDetails {

        private final BaseUser repoUser;

        public RepositoryUserDetails(BaseUser repoUser) {
            this.repoUser = repoUser;
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            if (repoUser.getRole() == null) {
                return Collections.emptyList();
            }

            return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + repoUser.getRole().name()));
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return repoUser.isActive();
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return repoUser.isActive();
        }

        @Override
        public String getPassword() {
            return repoUser.getPassword();
        }

        @Override
        public String getUsername() {
            return repoUser.getUsername();
        }
    }
}
