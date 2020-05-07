package org.tec.auth.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.tec.auth.AuthApplication;
import org.tec.auth.entity.OAuthUser;
import org.tec.auth.enums.Role;

import javax.transaction.Transactional;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Transactional
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes={AuthApplication.class})
public class OAuthUserRepositoryTest {

    @Autowired
    OAuthUserRepository oAuthUserRepository;

    @Test
    public void testCustomer() {
        Optional<OAuthUser> oAuthUser = oAuthUserRepository.findByUsername("demo@outboundengine.com");

        assertTrue(oAuthUser.isPresent());

        OAuthUser actual = oAuthUser.get();
        assertTrue(actual.isAccountNonExpired());
        assertTrue(actual.isAccountNonLocked());
        assertTrue(actual.isCredentialsNonExpired());
        assertTrue(actual.isEnabled());
        assertFalse(actual.getAuthorities().isEmpty());
        assertTrue(actual.getAuthorities().contains(new SimpleGrantedAuthority(Role.CUSTOMER.name())));
    }

    @Test
    public void testCustomerManager() {
        Optional<OAuthUser> oAuthUser = oAuthUserRepository.findByUsername("agency@outboundengine.com");

        assertTrue(oAuthUser.isPresent());

        OAuthUser actual = oAuthUser.get();
        assertTrue(actual.isAccountNonExpired());
        assertTrue(actual.isAccountNonLocked());
        assertTrue(actual.isCredentialsNonExpired());
        assertTrue(actual.isEnabled());
        assertFalse(actual.getAuthorities().isEmpty());
        assertTrue(actual.getAuthorities().contains(new SimpleGrantedAuthority(Role.CUSTOMER_MANAGER.name())));
    }

    @Test
    public void testSupport() {
        Optional<OAuthUser> oAuthUser = oAuthUserRepository.findByUsername("amanda@outboundengine.com");

        assertTrue(oAuthUser.isPresent());

        OAuthUser actual = oAuthUser.get();
        assertTrue(actual.isAccountNonExpired());
        assertTrue(actual.isAccountNonLocked());
        assertTrue(actual.isCredentialsNonExpired());
        assertTrue(actual.isEnabled());
        assertFalse(actual.getAuthorities().isEmpty());
        assertTrue(actual.getAuthorities().contains(new SimpleGrantedAuthority(Role.SUPPORT.name())));
    }

    @Test
    public void testAdmin() {
        Optional<OAuthUser> oAuthUser = oAuthUserRepository.findByUsername("admin@outboundengine.com");

        assertTrue(oAuthUser.isPresent());

        OAuthUser actual = oAuthUser.get();
        assertTrue(actual.isAccountNonExpired());
        assertTrue(actual.isAccountNonLocked());
        assertTrue(actual.isCredentialsNonExpired());
        assertTrue(actual.isEnabled());
        assertFalse(actual.getAuthorities().isEmpty());
        assertTrue(actual.getAuthorities().contains(new SimpleGrantedAuthority(Role.ADMIN.name())));
    }

    @Test
    public void testManager() {
        Optional<OAuthUser> oAuthUser = oAuthUserRepository.findByUsername("frank@outboundengine.com");

        assertTrue(oAuthUser.isPresent());

        OAuthUser actual = oAuthUser.get();
        assertTrue(actual.isAccountNonExpired());
        assertTrue(actual.isAccountNonLocked());
        assertTrue(actual.isCredentialsNonExpired());
        assertTrue(actual.isEnabled());
        assertFalse(actual.getAuthorities().isEmpty());
        assertTrue(actual.getAuthorities().contains(new SimpleGrantedAuthority(Role.MANAGER.name())));
    }

    @Test
    public void testSuperUser() {
        Optional<OAuthUser> oAuthUser = oAuthUserRepository.findByUsername("admin@outboundengine.com");

        assertTrue(oAuthUser.isPresent());

        OAuthUser actual = oAuthUser.get();
        assertTrue(actual.isAccountNonExpired());
        assertTrue(actual.isAccountNonLocked());
        assertTrue(actual.isCredentialsNonExpired());
        assertTrue(actual.isEnabled());
        assertFalse(actual.getAuthorities().isEmpty());
        assertTrue(actual.getAuthorities().contains(new SimpleGrantedAuthority(Role.SUPERUSER.name())));
    }
}
