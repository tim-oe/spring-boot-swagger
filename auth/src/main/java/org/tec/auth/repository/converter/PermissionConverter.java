package org.tec.auth.repository.converter;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.tec.auth.enums.Role;

import javax.persistence.AttributeConverter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PermissionConverter implements AttributeConverter<Collection<? extends GrantedAuthority>, Integer> {
    @Override
    public Integer convertToDatabaseColumn(final Collection<? extends GrantedAuthority> attribute) {
        throw new UnsupportedOperationException("authorities is read only");
    }

    @Override
    @SuppressWarnings({"PMD.NPathComplexity"})
    public Collection<? extends GrantedAuthority> convertToEntityAttribute(final Integer dbData) {
        final List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();

        //Adding these roles by default for mobile. Any other roles can be granted access in child authentication classes
        //or move these grants into the mobile child auth class
        if (Role.isCustomer(dbData)) {
            grantedAuthorityList.add(new SimpleGrantedAuthority(Role.CUSTOMER.name()));
        }

        if (Role.isCustomerManager(dbData)) {
            grantedAuthorityList.add(new SimpleGrantedAuthority(Role.CUSTOMER_MANAGER.name()));
        }

        if (Role.isProductManager(dbData)) {
            grantedAuthorityList.add(new SimpleGrantedAuthority(Role.PRODUCT_MANAGER.name()));
        }

        if (Role.isAdmin(dbData)) {
            grantedAuthorityList.add(new SimpleGrantedAuthority(Role.ADMIN.name()));
        }

        if (Role.isGroupAdmin(dbData)) {
            grantedAuthorityList.add(new SimpleGrantedAuthority(Role.GROUP_ADMIN.name()));
        }

        if (Role.isManager(dbData)) {
            grantedAuthorityList.add(new SimpleGrantedAuthority(Role.MANAGER.name()));
        }

        if (Role.isSupport(dbData)) {
            grantedAuthorityList.add(new SimpleGrantedAuthority(Role.SUPPORT.name()));
        }

        if (Role.isSuperuser(dbData)) {
            grantedAuthorityList.add(new SimpleGrantedAuthority(Role.SUPERUSER.name()));
        }

        return grantedAuthorityList;
    }
}
