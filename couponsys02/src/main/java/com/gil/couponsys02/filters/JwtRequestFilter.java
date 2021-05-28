package com.gil.couponsys02.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.gil.couponsys02.exceptions.ErrorDetails;
import com.gil.couponsys02.exceptions.UnauthorizedAccessException;
import com.gil.couponsys02.login.ClientType;
import com.gil.couponsys02.login.LoginCacheManager;
import com.gil.couponsys02.login.LoginManager;
import com.gil.couponsys02.services.AdminService;
import com.gil.couponsys02.services.ClientService;
import com.gil.couponsys02.services.CompanyService;
import com.gil.couponsys02.services.CustomerService;
import com.gil.couponsys02.services.UserDetailsServiceImpl;
import com.gil.couponsys02.utils.JsonUtils;
import com.gil.couponsys02.utils.JwtUtil;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	@Autowired
	private LoginCacheManager loginCacheManager;

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	@Autowired
	private JwtUtil jwtUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		try {
			String authHeader = request.getHeader("Authorization");
			String usernameToken = null;
			String jwtToken = null;
			ClientType clientTypeRequest = getClientTypeFromRequest(request.getRequestURI());

			if (authHeader != null && authHeader.startsWith("Bearer ")) {
				jwtToken = authHeader.substring(7);
				 usernameToken = jwtUtil.extractUsername(jwtToken);
			}

			if (usernameToken != null && clientTypeRequest != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				UserDetails userDetails = this.userDetailsService.loadUserByUsername(usernameToken);
				if (jwtUtil.validateToken(jwtToken, userDetails, clientTypeRequest)) {
					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
							userDetails, null, userDetails.getAuthorities());

					usernamePasswordAuthenticationToken
							.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

					SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

					setClientServiceAsAttribute(request, clientTypeRequest, loginCacheManager
							.getService(clientTypeRequest.name() + jwtUtil.extractClientServiceId(jwtToken)));
				}
			}

			filterChain.doFilter(request, response);
		} catch (Exception e) {
			ErrorDetails errorDetails = new ErrorDetails(e.getMessage(), HttpStatus.FORBIDDEN);
			response.setStatus(errorDetails.getStatus());
			response.getWriter().write(JsonUtils.convertObjectToJson(errorDetails));
			response.setContentType("text/json");
		}

	}

	private void setClientServiceAsAttribute(HttpServletRequest request, ClientType clientTypeRequest,
			ClientService service) {
		switch (clientTypeRequest) {
		case ADMINISTRATOR:
			request.setAttribute("service", (AdminService) service);
			break;
		case COMPANY:
			request.setAttribute("service", (CompanyService) service);
			break;
		case CUSTOMER:
			request.setAttribute("service", (CustomerService) service);
			break;
		default:
			break;

		}

	}

	private ClientType getClientTypeFromRequest(String requestURI) {

		ClientType clientType = null;
		String path = requestURI.split("/")[1];
		switch (path) {
		case "admin":
			clientType = ClientType.ADMINISTRATOR;
			break;
		case "company":
			clientType = ClientType.COMPANY;
			break;
		case "customer":
			clientType = ClientType.CUSTOMER;
			break;
		default:
			// TODO
		}
		return clientType;

	}

}
