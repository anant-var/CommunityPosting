package com.example.feedservice.service;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;

/**
 * For dev: extract user from X-User-* headers.
 * Replace this with real token-based user fetch in production.
 */
@Service
public class UserAdapterService {

    public static class UserInfo {
        public final String id;
        public final String name;
        public final String role;
        public UserInfo(String id, String name, String role){
            this.id = id; this.name = name; this.role = role;
        }
    }

    public UserInfo getUserFromRequest(HttpServletRequest req){
        String id = req.getHeader("X-User-Id");
        String name = req.getHeader("X-User-Name");
        String role = req.getHeader("X-User-Role");
        if(id == null || name == null){
            // fallback to anonymous dev user
            return new UserInfo("anonymous","Anonymous","PATIENT");
        }
        return new UserInfo(id, name, role != null ? role : "PATIENT");
    }
}
