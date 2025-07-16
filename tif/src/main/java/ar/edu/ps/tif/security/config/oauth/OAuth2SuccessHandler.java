package ar.edu.ps.tif.security.config.oauth;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    // private final JwtUtils jwtUtils;

    // public OAuth2SuccessHandler(JwtUtils jwtUtils) {
    //     this.jwtUtils = jwtUtils;
    // }

    // @Override
    // public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
    //                                     Authentication authentication) throws IOException, ServletException {

    //     if (authentication.getPrincipal() instanceof OAuth2User oAuth2User) {
    //         String username = oAuth2User.getAttribute("login");
    //         String token = jwtUtils.createToken(authentication);

    //         String redirectUrl = "/html/oauth2-success.html?token=" + token + "&username=" + username;
    //         response.sendRedirect(redirectUrl);
    //     } else {
    //         response.sendRedirect("/html/login.html?error=oauth");
    //     }
    // }
   @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        if (authentication.getPrincipal() instanceof CustomOAuth2User customOAuth2User) {
            String username = customOAuth2User.getAttribute("login");
            String token = customOAuth2User.getJwtToken();

            // Redirigimos al HTML con el token y username
            String redirectUrl = "http://localhost:8080/html/home.html?token=" + token + "&username=" + username;
            response.sendRedirect(redirectUrl);
        } else {
            response.sendRedirect("/html/login.html?error=oauth");
        }
    }
}