package org.tec.auth.svc.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.tec.auth.entity.OAuthUser;
import org.tec.auth.repository.OAuthUserRepository;
import org.tec.auth.svc.OEUserDetailsSvc;

import java.util.Optional;

@Service
public class OEUserDetailsSvcImpl  implements OEUserDetailsSvc {

    @Autowired
    private transient OAuthUserRepository oAuthUserRepository;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final Optional<OAuthUser> opt = oAuthUserRepository.findByUsername(username);
        if(!opt.isPresent() || !opt.get().isEnabled()){
            throw new UsernameNotFoundException("user not found " + username);
        }
        return opt.get();
    }
}
