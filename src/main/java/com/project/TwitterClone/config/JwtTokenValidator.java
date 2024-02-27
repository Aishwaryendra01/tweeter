package com.project.TwitterClone.config;

import java.io.IOException;
import java.util.List;

import javax.crypto.SecretKey;

import org.apache.catalina.authenticator.SpnegoAuthenticator.AuthenticateAction;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtTokenValidator extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String jwt =request.getHeader(JwtConstant.JWT_HEADER);
	
		//jwt is returned with bearer keyword and space ie.... Bearer jwt
		//here we are checking that our jwt token is being filtering or not ....agar filter ho rhi ho tabhi pass hoga
		if(jwt!=null) {
			 jwt=jwt.substring(7);//why 7 in index
			 //because we have to take the jwt after bearer keyword and space
			 
			 try {
				 SecretKey key=Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());
				 Claims claims=Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
				 
				 //claim se email aur authorities access krne k liye
				 String email=String.valueOf(claims.get("email"));
				 String authorities=String.valueOf(claims.get("authorities"));
				 
				 //for converying the authorities in to granted authority 
				 List<GrantedAuthority> auths= AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);
				 Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, auths);
				 
				 SecurityContextHolder.getContext().setAuthentication(authentication);
				 
				 
			 }catch(Exception e){
				 throw new BadCredentialsException("inavlid token"); 
			 }
			 // after validating the token uske bad aage pass krne k liye
			 filterChain.doFilter(request, response); 
			 
			 
		}
	}

}
