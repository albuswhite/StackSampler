package com.example.bookbff.interceptor;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;
import java.util.Set;
@Slf4j
@Component
public class JwtInterceptor implements HandlerInterceptor {

    private final Set<String> allowedSubs = Set.of("starlord", "gamora", "drax", "rocket", "groot");

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        String jwtToken = request.getHeader("Authorization");
        String userAgent = request.getHeader("User-Agent");

        // Check if User-Agent header is present
        if (userAgent == null || userAgent.isEmpty()) {
            // Handle missing User-Agent header
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "User-Agent header is missing");
            return false;
        }

        if (jwtToken != null && jwtToken.startsWith("Bearer ")) {
            jwtToken = jwtToken.substring(7);

            try {
                String[] jwtParts = jwtToken.split("\\.");
                String payloadJson = new String(Base64.getUrlDecoder().decode(jwtParts[1]), StandardCharsets.UTF_8);
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode payload = objectMapper.readTree(payloadJson);

                // Check sub claim
                JsonNode subNode = payload.get("sub");
                if (subNode == null || !allowedSubs.contains(subNode.asText())) {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT token: Invalid sub claim");
                    return false;
                }

                // Check exp claim
                JsonNode expNode = payload.get("exp");
                if (expNode == null || expNode.asLong() <= Instant.now().getEpochSecond()) {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT token: Token expired");
                    return false;
                }

                // Check iss claim
                JsonNode issNode = payload.get("iss");
                if (issNode == null || !"cmu.edu".equals(issNode.asText())) {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT token: Invalid iss claim");
                    return false;
                }
            } catch (Exception e) {
                // Handle JWT token exceptions
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT token");
                return false;
            }
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "JWT token is missing");
            return false;
        }
        log.info("Jwt pass");
        return true;
    }
}
