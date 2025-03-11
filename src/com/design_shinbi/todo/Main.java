package com.design_shinbi.todo;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.design_shinbi.todo.model.TaskDAO;
import com.design_shinbi.todo.model.entity.Task;

public class Main {
	private static final int ACTION_LIST   = 1;
	private static final int ACTION_ADD    = 2;
	private static final int ACTION_UPDATE = 3;
	private static final int ACTION_DELETE  = 4;
	private static final int ACTION_EXIT     = 0;
	
	
	private static Connection connectDb() throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		
		String dbUrl = "jdbc:mysql://localhost/todo";
		String userName = "root";
		String password = "";
		
		Connection connection = DriverManager.getConnection(
			dbUrl, userName, password
		);
		
		return connection;
	}
	
	
	private static int selectAction(Scanner scanner) {
		int action = -1;
		
		while(
				action != ACTION_LIST
				&& action != ACTION_ADD
				&& action != ACTION_UPDATE
				&& action != ACTION_DELETE
				&& action != ACTION_EXIT
		) {
			System.out.println(
				String.format(
					"[%d] 一覧, [%d] 追加, [%d] 更新, [%d] 削除, [%d] 終了",
					ACTION_LIST,
					ACTION_ADD,
					ACTION_UPDATE,
					ACTION_DELETE,
					ACTION_EXIT
				)
			);
			
			String line = scanner.nextLine();
			try {
				action = Integer.parseInt(line);
			}
			catch(Exception e) {				
			}
		}
		
		return action;
	}
	
	
	private static void showList(TaskDAO dao) throws SQLException {
		List<Task> tasks = dao.findAll();
		for(Task task : tasks) {
			System.out.println(task);
		}		
	}
	
	
	private static void addTask(TaskDAO dao, Scanner scanner) throws SQLException {
		String content = "";
		while(content.isEmpty()) {
			System.out.println("内容を入力してください。[必須]");
			content = scanner.nextLine().trim();
		}
		
		Date deadline = null;
		System.out.println("締切を入力してください。[任意] (例: 2025-03-12)");
		String line = scanner.nextLine().trim();
		if(!line.isEmpty()) {
			try {
				deadline = Date.valueOf(line);
			}
			catch(Exception e) {				
			}
		}
		
		dao.add(content, deadline);
	}
	
	
	private static void updateTask(TaskDAO dao, Scanner scanner) throws SQLException {
		int id = 0;
		while(id <= 0) {
			System.out.println("ID を入力してください。[必須]");
			String idString = scanner.nextLine();
			id = Integer.parseInt(idString);
		}
		
		String content = "";
		while(content.isEmpty()) {
			System.out.println("内容を入力してください。[必須]");
			content = scanner.nextLine().trim();
		}
		
		Date deadline = null;
		System.out.println("締切を入力してください。[任意] (例: 2025-03-12)");
		String line = scanner.nextLine().trim();
		if(!line.isEmpty()) {
			try {
				deadline = Date.valueOf(line);
			}
			catch(Exception e) {				
			}
		}
		
		dao.update(id, content, deadline);
	}

	
	private static void deleteTask(TaskDAO dao, Scanner scanner) throws SQLException {
		int id = 0;
		while(id <= 0) {
			System.out.println("ID を入力してください。[必須]");
			String idString = scanner.nextLine();
			id = Integer.parseInt(idString);
		}

		dao.delete(id);
	}	
	
	
	public static void main(String[] args) throws Exception {
		Scanner scanner = new Scanner(System.in);
		
		Connection connection = connectDb();
		TaskDAO dao = new TaskDAO(connection);
		
		boolean loop = true;
		while(loop) {
			int action = selectAction(scanner);
			
			if(action == ACTION_LIST) {
				showList(dao);
			}
			else if(action == ACTION_ADD) {
				addTask(dao, scanner);
			}
			else if(action == ACTION_UPDATE) {
				updateTask(dao, scanner);
			}
			else if(action == ACTION_DELETE) {
				deleteTask(dao, scanner);
			}
			else if(action == ACTION_EXIT) {
				loop = false;
			}
		}
	}
}
