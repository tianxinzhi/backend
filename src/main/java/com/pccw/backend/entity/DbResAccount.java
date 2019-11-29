package com.pccw.backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


/**
 *  one area may have many reposity/shop/store
 */

@Data
@Entity
@Table(name = "res_account")
@SequenceGenerator(name="id_account",sequenceName = "account_seq",allocationSize = 1)
public class DbResAccount extends Base {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_account")
	private Long id;

	@Column(name = "account_name",length = 255)
	private String accountName;
	
	@Column(name = "account_password",length = 512)
	private String accountPassword;

	@JsonBackReference
	@JoinColumn(name = "account_id")
	@OneToMany(cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.EAGER)
	private List<DbResAccountRole> accountRoles;

	public List<SimpleGrantedAuthority> getRoles() {
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		accountRoles.stream().forEach(role ->{
			authorities.add(new SimpleGrantedAuthority("" + role.getRoleId()));
		});
		return authorities;
	}
}
