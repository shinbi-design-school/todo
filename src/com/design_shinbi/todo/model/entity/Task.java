package com.design_shinbi.todo.model.entity;

import java.sql.Date;
import java.sql.Timestamp;

public class Task {
	private int id;
	private String content;
	private Date deadline;
	private Timestamp createdAt;
	private Timestamp updatedAt;
	
	public Task() {		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public Timestamp getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	public String toString() {
		String line = String.format(
			"%d: %s %s",
			this.id,
			this.content,
			this.deadline == null ? "" : ("締切:" + this.deadline.toString())
		);
		
		return line;
	}
}
