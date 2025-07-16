package ar.edu.ps.tif.security.config.oauth;

import java.util.Map;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.core.GrantedAuthority;
import java.util.Collection;

public class CustomOAuth2User extends DefaultOAuth2User {

    // private final String jwtToken;

    // public CustomOAuth2User(Map<String, Object> attributes, String jwtToken) {
    //     super(null, attributes, "login");
    //     this.jwtToken = jwtToken;
    // }

    // public String getJwtToken() {
    //     return jwtToken;
    // }
    private final String jwtToken;

    public CustomOAuth2User(Collection<? extends GrantedAuthority> authorities, Map<String, Object> attributes, String nameAttributeKey, String jwtToken) {
        super(authorities, attributes, nameAttributeKey);
        this.jwtToken = jwtToken;
    }

    public String getJwtToken() {
        return jwtToken;
    }
}
