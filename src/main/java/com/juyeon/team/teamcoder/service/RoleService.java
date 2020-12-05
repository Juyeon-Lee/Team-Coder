package com.juyeon.team.teamcoder.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Service
public class RoleService {
    public void reloadRolesForAuthenticatedUser(String newRole) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        //System.out.println("<<<<<<Before>>>>>>>" +Arrays.toString(auth.getAuthorities().toArray()));
        List<GrantedAuthority> updatedAuthorities = new ArrayList<>(auth.getAuthorities());
        updatedAuthorities.add(new SimpleGrantedAuthority(newRole));

        Authentication newAuth = new UsernamePasswordAuthenticationToken(
                auth.getPrincipal(),auth.getCredentials(),updatedAuthorities
        );
        SecurityContextHolder.getContext().setAuthentication(newAuth);
        //System.out.println("<<<<<<After>>>>>>>" +Arrays.toString(SecurityContextHolder.getContext().getAuthentication().getAuthorities().toArray()));
    }
}
