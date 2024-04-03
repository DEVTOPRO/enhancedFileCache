package com.cps690.ehnacefilemethods.Dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
@Data
public class UserDto implements Serializable {
	  public String user_id;
	    public String email;
	    public String name;
	    public String given_name;
	    public String family_name;
	    public String nickname;
	    public String last_ip;
	    public int logins_count;
	    public Date created_at;
	    public UserDto() {
			super();
			// TODO Auto-generated constructor stub
		}
		public Date updated_at;
	    public Date last_login;
	    public boolean email_verified;
		public String getUser_id() {
			return user_id;
		}
		public void setUser_id(String user_id) {
			this.user_id = user_id;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getGiven_name() {
			return given_name;
		}
		public void setGiven_name(String given_name) {
			this.given_name = given_name;
		}
		public String getFamily_name() {
			return family_name;
		}
		public void setFamily_name(String family_name) {
			this.family_name = family_name;
		}
		public String getNickname() {
			return nickname;
		}
		public void setNickname(String nickname) {
			this.nickname = nickname;
		}
		public String getLast_ip() {
			return last_ip;
		}
		public void setLast_ip(String last_ip) {
			this.last_ip = last_ip;
		}
		public int getLogins_count() {
			return logins_count;
		}
		public void setLogins_count(int logins_count) {
			this.logins_count = logins_count;
		}
		public Date getCreated_at() {
			return created_at;
		}
		public void setCreated_at(Date created_at) {
			this.created_at = created_at;
		}
		public Date getUpdated_at() {
			return updated_at;
		}
		public void setUpdated_at(Date updated_at) {
			this.updated_at = updated_at;
		}
		public Date getLast_login() {
			return last_login;
		}
		public void setLast_login(Date last_login) {
			this.last_login = last_login;
		}
		public boolean isEmail_verified() {
			return email_verified;
		}
		public void setEmail_verified(boolean email_verified) {
			this.email_verified = email_verified;
		}
		
}
