package com.guavapay.ms.card.security;

import lombok.Data;

@Data
public class CustomUserPrincipal {

    private Long id;
    private String username;
    private String fullName;
    private String email;
    private String mobile;
}
