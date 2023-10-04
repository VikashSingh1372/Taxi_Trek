package com.cab.response;

import com.cab.entity.UserRole;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class JwtResponse {
private String jwt;
private String message;
private boolean isAutenticated;
private boolean isError;
private String errorDetail;
private UserRole role;

}
