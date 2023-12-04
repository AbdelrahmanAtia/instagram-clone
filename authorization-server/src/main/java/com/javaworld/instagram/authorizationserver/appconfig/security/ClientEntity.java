package com.javaworld.instagram.authorizationserver.appconfig.security;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name = "user")
public class ClientEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Version
	private int version;

	@Column(unique = true, name = "username")
	private String clientId;

	@Column(name = "password")
	private String clientSecret;

	@Column(name = "user_uuid")
	private UUID clientUuid;

	// TODO: Include other fields for client details (e.g., clientSecret,
	// grantTypes,
	// redirectUris, scopes, etc.)

	public ClientEntity() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	public UUID getClientUuid() {
		return clientUuid;
	}

	public void setClientUuid(UUID clientUuid) {
		this.clientUuid = clientUuid;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ClientEntity [id=");
		builder.append(id);
		builder.append(", version=");
		builder.append(version);
		builder.append(", clientId=");
		builder.append(clientId);
		builder.append(", clientSecret=");
		builder.append(clientSecret);
		builder.append(", clientUuid=");
		builder.append(clientUuid);
		builder.append("]");
		return builder.toString();
	}

}
