package vn.tonnguyen.porsche_store_v1.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;

@Component
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        for (GrantedAuthority auth : authorities) {
            String role = auth.getAuthority();

            if (role.equals("ROLE_ADMIN")) {
                response.sendRedirect("/admin/dashboard");
                return;
            } else if (role.equals("ROLE_SHIPPER")) {
                response.sendRedirect("/shipper/dashboard");
                return;
            } else if (role.equals("ROLE_WAREHOUSE")) {
                response.sendRedirect("/warehouse/dashboard");
                return;
            } else if (role.equals("ROLE_USER")) {
                response.sendRedirect("/");
                return;
            }
        }
        response.sendRedirect("/access-denied");
    }
}
