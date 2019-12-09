package com.cisneiros.prova;

import java.util.Date;

import javax.persistence.Entity;

@Entity
public class User {

	private long id;
	private String username;
	private String nome;
	private String password;
	private Date createdAt;
	private Date updatedAt;
	
	public User(String username, String password, String nome) {
		this.username = username;
		this.password = password;
		this.nome = nome;
		createdAt = new Date();
		updatedAt = new Date();
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
}
