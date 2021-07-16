package com.salgam.transaction.repository;

import java.sql.PreparedStatement;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.salgam.transaction.domain.Member;

@Repository
public class JdbcMemberTemplate {
	
	private final JdbcTemplate jdbcTemplate;
	
	public JdbcMemberTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public void save(Member member) {
		jdbcTemplate.update(
	      conn -> {
	        PreparedStatement ps = conn.prepareStatement(
	          "INSERT INTO member(member_id,name) "
	          + "VALUES (null,?)",
	          new String[]{"member_id"}
	        );
	        ps.setString(1, member.getName() );
	        return ps;
	      }
	    );
	}
}
