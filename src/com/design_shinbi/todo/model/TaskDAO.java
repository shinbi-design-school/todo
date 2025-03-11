package com.design_shinbi.todo.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.design_shinbi.todo.model.entity.Task;

public class TaskDAO {
	private Connection connection;
	
	public TaskDAO(Connection connection) {
		this.connection = connection;		
	}
	
	public List<Task> findAll() throws SQLException {
		List<Task> tasks = new ArrayList<Task>();
		
		String sql = "SELECT * FROM tasks";
		Statement statement = this.connection.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);
		
		while(resultSet.next()) {
			int id = resultSet.getInt("id");
			String content = resultSet.getString("content");
			Date deadline = resultSet.getDate("deadline");
			Timestamp createdAt = resultSet.getTimestamp("created_at");
			Timestamp updatedAt = resultSet.getTimestamp("updated_at");
			
			Task task = new Task();
			task.setId(id);
			task.setContent(content);
			task.setDeadline(deadline);
			task.setCreatedAt(createdAt);
			task.setUpdatedAt(updatedAt);
			
			tasks.add(task);
		}
		
		
		resultSet.close();
		statement.close();

		return tasks;
	}
	
	
	public void add(String content, Date deadline) throws SQLException {
		String sql = "INSERT INTO tasks(content, deadline, created_at, updated_at) VALUES(?, ?, ?, ?)";
		
		Timestamp now = new Timestamp(System.currentTimeMillis());
		
		PreparedStatement statement = this.connection.prepareStatement(sql);
		statement.setString(1, content);
		if(deadline == null) {
			statement.setNull(2, Types.DATE);
		}
		else {
			statement.setDate(2, deadline);
		}
		statement.setTimestamp(3, now);
		statement.setTimestamp(4, now);
		
		statement.executeUpdate();
		
		statement.close();
	}
	
	
	public void update(int id, String content, Date deadline) throws SQLException {
		String sql = "UPDATE tasks SET content=?, deadline=?, updated_at=? WHERE id=?";
		
		Timestamp now = new Timestamp(System.currentTimeMillis());
		
		PreparedStatement statement = this.connection.prepareStatement(sql);
		statement.setString(1, content);
		if(deadline == null) {
			statement.setNull(2, Types.DATE);
		}
		else {
			statement.setDate(2, deadline);
		}
		statement.setTimestamp(3, now);
		statement.setInt(4, id);
		
		statement.executeUpdate();
		
		statement.close();
	}
	
	
	public void delete(int id) throws SQLException {
		String sql = "DELETE FROM tasks WHERE id = ?";
		
		PreparedStatement statement = this.connection.prepareStatement(sql);
		statement.setInt(1, id);
		
		statement.executeUpdate();
		
		statement.close();
	}
}
