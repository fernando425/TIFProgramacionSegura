package ar.edu.ps.tif.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ar.edu.ps.tif.dto.AuthLoginRequestDTO;
import ar.edu.ps.tif.dto.AuthResponseDTO;
import ar.edu.ps.tif.model.UserSec;
import ar.edu.ps.tif.repository.IUserRepository;
import ar.edu.ps.tif.utils.JwtUtils;

@Service
public class UserDetailsServiceImp implements UserDetailsService {

        @Autowired
        private IUserRepository userRepo;

        @Autowired
        private JwtUtils jwtUtils;

        @Autowired
        private PasswordEncoder passwordEncoder;

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                // Traer el usuario desde la base de datos
                UserSec userSec = userRepo.findUserEntityByUsername(username)
                                .orElseThrow(() -> new UsernameNotFoundException(
                                                "El usuario " + username + " no fue encontrado"));

                // Lista de autoridades (roles y permisos)
                List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

                // Cargar roles como autoridades
                userSec.getRolesList()
                                .forEach(role -> authorityList
                                                .add(new SimpleGrantedAuthority("ROLE_" + role.getRole())));

                // Cargar permisos asociados a los roles
                userSec.getRolesList().stream()
                                .flatMap(role -> role.getPermissionsList().stream())
                                .forEach(permission -> authorityList
                                                .add(new SimpleGrantedAuthority(permission.getPermissionName())));

                // Devolver objeto User de Spring Security
                return new User(
                                userSec.getUsername(),
                                userSec.getPassword(),
                                userSec.isEnabled(),
                                userSec.isAccountNotExpired(),
                                userSec.isCredentialNotExpired(),
                                userSec.isAccountNotLocked(),
                                authorityList);
        }

        public AuthResponseDTO loginUser(AuthLoginRequestDTO authLoginRequest) {

                // recuperamos nombre de usuario y contraseña
                String username = authLoginRequest.username();
                String password = authLoginRequest.password();

                Authentication authentication = this.authenticate(username, password);
                // si todo sale bien
                SecurityContextHolder.getContext().setAuthentication(authentication);
                String accessToken = jwtUtils.createToken(authentication);
                AuthResponseDTO authResponseDTO = new AuthResponseDTO(username, "login ok", accessToken, true);
                return authResponseDTO;

        }

        public Authentication authenticate(String username, String password) {
                // con esto debo buscar el usuario
                UserDetails userDetails = this.loadUserByUsername(username);

                if (userDetails == null) {
                        throw new BadCredentialsException("Ivalid username or password");
                }
                // si no es igual
                if (!passwordEncoder.matches(password, userDetails.getPassword())) {
                        throw new BadCredentialsException("Invalid password");
                }
                return new UsernamePasswordAuthenticationToken(username, userDetails.getPassword(),
                                userDetails.getAuthorities());
        }

}