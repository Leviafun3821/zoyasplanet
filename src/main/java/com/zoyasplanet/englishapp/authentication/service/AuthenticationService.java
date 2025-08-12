package com.zoyasplanet.englishapp.authentication.service;

public interface AuthenticationService {
    String authenticateAndGenerateToken(String username, String password);
}
