package com.advocacia.estacio.infra.security;

import org.springframework.stereotype.Component;

@Component("authz")
public class AuthorizationUtils {

    private final SecurityUtils securityUtils;

    public AuthorizationUtils(SecurityUtils securityUtils) {
        this.securityUtils = securityUtils;
    }

    public boolean isResourceOwner(Long idDono) {
        return securityUtils.getIdUsuarioLogado().equals(idDono);
    }
}