package com.thefancygoodiebox;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Spring Security Konfiguration: Öffentliche REST-API (Development-Modus)
 * Definiert Security-Filter für alle /subscriptions Endpunkte
 */
@Configuration  // Spring Boot: Lade diese Config beim Startup
public class SecurityConfig {

  /**
   * Haupt-Security-Filter: CSRF + Authorization Rules
   * @Bean → Spring verwaltet als Singleton
   */
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    return http
            // 1. CSRF DEAKTIVIEREN (Standard für REST-APIs!)
            .csrf(csrf -> csrf.disable())  // ❌ Kein CSRF-Token nötig

            // 2. ALLE REQUESTS ERLAUBEN (Development!)
            .authorizeHttpRequests(auth -> auth
                    .anyRequest().permitAll()    // ✅ Jeder kann alles: GET/POST/DELETE
            )

            // 3. Filter-Chain bauen und zurückgeben
            .build();
  }
}

