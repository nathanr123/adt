package com.cti.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.stereotype.Service;

/**
 * Reference org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl
 * 
 * @author mkyong
 * 
 */
@Service("userLoginAttemptAndAuthenticationService")
public class UserLoginAttemptAndAuthenticationService extends JdbcDaoImpl {

	@Autowired
	private DataSource dataSource;

	@PostConstruct
	private void initialize() {
		setDataSource(dataSource);
	}

	@Override
	@Value("select * from user where username = ?")
	public void setUsersByUsernameQuery(String usersByUsernameQueryString) {
		super.setUsersByUsernameQuery(usersByUsernameQueryString);
	}

	@Override
	@Value("select username, userrole from user where username =?")
	public void setAuthoritiesByUsernameQuery(String queryString) {
		super.setAuthoritiesByUsernameQuery(queryString);
	}

	// override to get accountNonLocked
	@Override
	public List<UserDetails> loadUsersByUsername(String username) {
		return getJdbcTemplate().query(super.getUsersByUsernameQuery(),
				new String[] { username }, new RowMapper<UserDetails>() {
					public UserDetails mapRow(ResultSet rs, int rowNum)
							throws SQLException {

						String username = rs.getString("username");

						String password = rs.getString("password");

						boolean enabled = rs.getBoolean("enabled");

						boolean accountNonExpired = rs
								.getBoolean("accountNonExpired");
						boolean credentialsNonExpired = rs
								.getBoolean("credentialsNonExpired");

						boolean accountNonLocked = rs
								.getBoolean("accountNonLocked");

						String role = rs.getString("userrole");

						return new User(username, password, enabled,
								accountNonExpired, credentialsNonExpired,
								accountNonLocked, getAuthorities(role));
					}

				});
	}

	public Collection<? extends GrantedAuthority> getAuthorities(String roles) {
		List<GrantedAuthority> authList = getGrantedAuthorities(getRoles(roles));
		return authList;
	}

	public List<String> getRoles(String role) {

		List<String> roles = new ArrayList<String>();

		roles.add(role);

		return roles;
	}

	public static List<GrantedAuthority> getGrantedAuthorities(
			List<String> roles) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

		for (String role : roles) {
			authorities.add(new SimpleGrantedAuthority(role));
		}
		return authorities;
	}

	// override to pass accountNonLocked
	@Override
	public UserDetails createUserDetails(String username,
			UserDetails userFromUserQuery,
			List<GrantedAuthority> combinedAuthorities) {
		String returnUsername = userFromUserQuery.getUsername();

		if (super.isUsernameBasedPrimaryKey()) {
			returnUsername = username;
		}

		return new User(returnUsername, userFromUserQuery.getPassword(),
				userFromUserQuery.isEnabled(),
				userFromUserQuery.isAccountNonExpired(),
				userFromUserQuery.isCredentialsNonExpired(),
				userFromUserQuery.isAccountNonLocked(), combinedAuthorities);
	}

}