package com.gil.couponsys02.models;

import com.gil.couponsys02.login.ClientType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {
	private String username;
	private String password;
	private ClientType clientType;
}
