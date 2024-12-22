package com.javaworld.instagram.commonlib.messaging;

import static java.time.ZonedDateTime.now;

import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.ZonedDateTimeSerializer;
import java.time.ZonedDateTime;

public class Event<K, T> implements Serializable {
	
    private static final long serialVersionUID = 1L;

	public enum Type {
		DELETE, CREATE, GENERATE_USER_ACCOUNT_INFORMATION_REPORT
	}

	private final Type eventType;
	private final K key;
	private final T data;
	private final ZonedDateTime eventCreatedAt;

	public Event() {
		this.eventType = null;
		this.key = null;
		this.data = null;
		this.eventCreatedAt = null;
	}

	public Event(Type eventType, K key, T data) {
		this.eventType = eventType;
		this.key = key;
		this.data = data;
		this.eventCreatedAt = now();
	}

	public Type getEventType() {
		return eventType;
	}

	public K getKey() {
		return key;
	}

	public T getData() {
		return data;
	}

	@JsonSerialize(using = ZonedDateTimeSerializer.class)
	public ZonedDateTime getEventCreatedAt() {
		return eventCreatedAt;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Event [eventType=");
		builder.append(eventType);
		builder.append(", key=");
		builder.append(key);
		builder.append(", data=");
		builder.append(data);
		builder.append(", eventCreatedAt=");
		builder.append(eventCreatedAt);
		builder.append("]");
		return builder.toString();
	}

}