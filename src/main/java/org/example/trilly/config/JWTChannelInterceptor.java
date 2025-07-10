package org.example.trilly.config;

import org.example.trilly.CustomUserDetails;
import org.example.trilly.CustomUserDetailsService;
import org.example.trilly.jwt.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JWTChannelInterceptor implements ChannelInterceptor {

    @Autowired
    private JWTService jwtService;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if (accessor == null) return message;

        if (accessor.getCommand() != null && accessor.getCommand().toString().equals("CONNECT")) {
            String authHeader = accessor.getFirstNativeHeader("Authorization");
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                String username = jwtService.extractUsername(token);

                if (username != null) {
                    CustomUserDetails userDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(username);
                    if (jwtService.validateToken(token, userDetails)) {
                        Authentication auth = new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities()
                        );
                        accessor.setUser(auth);
                    }
                }
            }
        }

        return message;
    }
}
