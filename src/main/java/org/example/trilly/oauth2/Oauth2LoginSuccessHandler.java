package org.example.trilly.oauth2;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.example.trilly.jwt.JWTService;
import org.example.trilly.models.User;
import org.example.trilly.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
@AllArgsConstructor
public class Oauth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JWTService jwtService;
    private final UserRepository userRepository;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String username = oAuth2User.getAttribute("name");

        User user = userRepository.findByUsername(username);
        user = user == null ? User.builder()
                                    .username(username).role("ROLE_USER").password("").createdAt(LocalDateTime.now())
                                    .build() : user;
        userRepository.save(user);

        String token = jwtService.generateToken(user);

        String redirectUrl = "http://localhost:3000/oauth2/success?token=" + token + "&username=" + user.getUsername();
        getRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }
}
