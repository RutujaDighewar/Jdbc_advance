/**
 * 
 */
package com.app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


/**
 * @author Rutuja
 *
 */
public class JdbcCRUD {

	private static Connection con = null;
	private static String url = "jdbc:mysql://localhost:3306/clc";
	private static String username = "root";
	private static String password = "root";
	private boolean flag = Boolean.FALSE;
	Scanner sc = new Scanner(System.in);

	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url, username, password);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	public void insertStudent() throws SQLException {
		Statement smt = con.createStatement();
		System.out.println("How many student want to add");
		int noOfStudent = sc.nextInt();
		for (int i = 0; i < noOfStudent; i++) {
			Student s = new Student();
			System.out.println("Enter Name");
			s.setName(sc.next());
			System.out.println("Enter Address");
			s.setAddress(sc.next());
			smt.executeUpdate("insert into student(name, address)values('" + s.getName() + "','" + s.getAddress() + "')");
			flag = Boolean.TRUE;
		}
		if (flag == Boolean.TRUE)
			System.out.println("Student successfully inserted...!");
		else
			System.out.println("Student not saved...!");
	}

	public void deleteStudent() {
     System.out.println();
		try (Statement smt = con.createStatement();) {
			System.out.println("Which row want to delete");
			int id = sc.nextInt();
			int result = smt.executeUpdate("delete from student where id=" + id + "");
			if (result > 0) {
				System.out.println("Successfully deleted...");
			} else {
				System.out.println("Delete failed...try again...!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateStudent() {
		System.out.println();
		try (Statement smt = con.createStatement();) {
			System.out.println("Which row want to update");
			int id = sc.nextInt();
			System.out.println("Enter Name");
			String name = sc.next();

			int result = smt.executeUpdate("update student set name='" + name + "' where id=" + id + "");
			if (result > 0) {
				System.out.println("Successfully updated...");
			} else {
				System.out.println("Update failed...try again...!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void selectStudent() {
		System.out.println();
		List<Student> students = new ArrayList<Student>();
		try (Statement smt = con.createStatement();) {
			ResultSet rs = smt.executeQuery("select * from student");
			while (rs.next()) {
				Student st = new Student();
				st.setId(rs.getInt(1));
				st.setName(rs.getString(2));
				st.setAddress(rs.getString(3));
				students.add(st);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("student id    student name    student address");
		for (Student st : students) {
			System.out.println(st.getId() + "\t\t" + st.getName() + "\t\t" + st.getAddress());
		}
	}

	public static void main(String[] args) throws SQLException {
		JdbcCRUD crud = new JdbcCRUD();
		crud.insertStudent();
		crud.selectStudent();
		crud.updateStudent();
		crud.selectStudent();
		crud.deleteStudent();
		crud.selectStudent();

	}

}
