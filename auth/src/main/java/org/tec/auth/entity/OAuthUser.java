package org.tec.auth.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.tec.auth.repository.converter.PermissionConverter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Collection;

@Getter
@Setter
@ToString
@EqualsAndHashCode(of = {"username"})
@NoArgsConstructor
@Entity
@Table(name = "users")
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class OAuthUser implements UserDetails {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="id", updatable=false, nullable=false)
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    @Column(name="email", updatable=false, nullable=false)
    private String username;
    @Column(name="password", updatable=false, nullable=false)
    private String password;

    @Column(name="enhanced_pwd")
    private String enhancedPwd;

    @Column(name="is_active", insertable=false, updatable=false)
    private boolean accountNonExpired;
    @Column(name="is_active", insertable=false, updatable=false)
    private boolean accountNonLocked;
    @Column(name="is_active", insertable=false, updatable=false)
    private boolean credentialsNonExpired;
    @Column(name="is_active", insertable=false, updatable=false)
    private boolean enabled;

    @Column(name="permissions", insertable=false, updatable=false)
    private int permissionLevel;

    @Column(name="permissions", insertable=false, updatable=false)
    @Convert(converter = PermissionConverter.class)
    private Collection<GrantedAuthority> authorities;

    @SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
    public OAuthUser(final OAuthUser that) {
        try {
            BeanUtils.copyProperties(this, that);
            this.password = null;
            this.enhancedPwd = null;
            this.authorities = new ArrayList<>(that.authorities.size());

            for(final GrantedAuthority ga : that.authorities) {
                final SimpleGrantedAuthority orig = (SimpleGrantedAuthority) ga;
                this.authorities.add(new SimpleGrantedAuthority(orig.getAuthority()));
            }
        } catch (Exception e) {
            throw new RuntimeException("failed to copy bean " + that, e);
        }
    }
}
