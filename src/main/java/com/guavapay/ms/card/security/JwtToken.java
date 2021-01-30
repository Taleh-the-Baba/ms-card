package com.guavapay.ms.card.security;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class JwtToken implements Serializable {

    private String accessToken;
}
