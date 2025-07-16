package ar.edu.ps.tif.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import ar.edu.ps.tif.model.UserSec;
import ar.edu.ps.tif.repository.IUserRepository;
import ar.edu.ps.tif.security.config.oauth.CustomOAuth2User;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    //  private final IUserRepository userRepo;

    // public CustomOAuth2UserService(IUserRepository userRepo) {
    //     this.userRepo = userRepo;
    // }

    // @Override
    // public OAuth2User loadUser(OAuth2UserRequest userRequest) {
    //     // Cargar los datos del usuario desde el proveedor OAuth (GitHub)
    //     OAuth2User oAuth2User = super.loadUser(userRequest);

    //     // Obtener el nombre de usuario de GitHub
    //     String username = oAuth2User.getAttribute("login");

    //     if (username == null || username.isEmpty()) {
    //         throw new RuntimeException("No se pudo obtener el nombre de usuario desde GitHub.");
    //     }

    //     // Validar que el usuario exista en la base de datos
    //     UserSec userSec = userRepo.findUserEntityByUsername(username)
    //             .orElseThrow(() -> new RuntimeException("Usuario no encontrado en la base de datos: " + username));

    //     // No se hace más nada acá: el token y redirección lo hace el SuccessHandler
    //     return oAuth2User;
    // }
      private final IUserRepository userRepo;

    public CustomOAuth2UserService(IUserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String username = oAuth2User.getAttribute("login");
        if (username == null || username.isEmpty()) {
            throw new RuntimeException("No se pudo obtener el nombre de usuario desde GitHub.");
        }

        UserSec userSec = userRepo.findUserEntityByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado en la base de datos: " + username));

        // Convertir roles y permisos a GrantedAuthority
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        userSec.getRolesList().forEach(role -> 
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRole()))
        );

        userSec.getRolesList().stream()
            .flatMap(role -> role.getPermissionsList().stream())
            .forEach(permission -> 
                authorities.add(new SimpleGrantedAuthority(permission.getPermissionName()))
            );

        // No creamos token acá, se genera en SuccessHandler o controller
        return new CustomOAuth2User(authorities, oAuth2User.getAttributes(), "login", null);
    }
}
