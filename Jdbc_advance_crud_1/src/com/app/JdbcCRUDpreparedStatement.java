/**
 * 
 */
package com.app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author Rutuja
 *
 */
public class JdbcCRUDpreparedStatement {
	private static Connection con = null;
	private static String URL = "jdbc:mysql://localhost:3306/clc";
	private static String USERNAME = "root";
	private static String PASSWORD = "root";
	Scanner sc = new Scanner(System.in);
	private boolean flag = Boolean.FALSE;

	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	public void insertStudent() {
		try (PreparedStatement ps = con.prepareStatement("insert into student(name , address)values(?,?)");) {
			System.out.println("How many student want to add");
			int noOfStudent = sc.nextInt();
			for (int i = 0; i < noOfStudent; i++) {
				Student s = new Student();
				System.out.println("Enter Name");
				s.setName(sc.next());
				System.out.println("Enter Address");
				s.setAddress(sc.next());
				ps.setString(1, s.getName());
				ps.setString(2, s.getAddress());
				ps.executeUpdate();
				flag = Boolean.TRUE;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (flag == Boolean.TRUE)
			System.out.println("Student successfully inserted...");
		else
			System.out.println("Student not saved");
	}

	public void updateStudent() {
		System.out.println();
		try (PreparedStatement ps = con.prepareStatement("update student set name = ? where id = ?");) {
			System.out.println("which row want to update");
			int id = sc.nextInt();
			System.out.println("Enter name");
			String name = sc.next();
			ps.setString(1, name);
			ps.setInt(2, id);
			
			int result = ps.executeUpdate();
			if (result > 0) {
				System.out.println("Update successfully...");
			} else {
				System.out.println("Update failed...Try again...!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deleteStudent() {
		System.out.println();
		try(PreparedStatement ps= con.prepareStatement("delete from student where id= ?");){
			System.out.println("Which row want to delete ?");
			int id=sc.nextInt();
			ps.setInt(1, id);
			int result = ps.executeUpdate();
			if(result >0) {
				System.out.println("Delete successfully...");
			}else {
				System.out.println("Delete failed...Try again...!");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		

	}

	public void selectStudent() {
		System.out.println();
		List<Student> students = new ArrayList<Student>();
		try(PreparedStatement ps= con.prepareStatement("Select * from student");){
			ResultSet rs= ps.executeQuery();
			while(rs.next()) {
				Student s= new Student();
				s.setId(rs.getInt(1));
				s.setName(rs.getString(2));
				s.setAddress(rs.getString(3));
				students.add(s);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		System.out.println("student id    student name    student address");
		for(Student st : students) {
			System.out.println(st.getId()+"\t\t"+st.getName()+"\t\t"+st.getAddress());
		}

	}

	public static void main(String[] args) {
		JdbcCRUDpreparedStatement crud=new JdbcCRUDpreparedStatement();
		crud.insertStudent();
		crud.selectStudent();
		crud.updateStudent();
		crud.selectStudent();
		crud.deleteStudent();
		crud.selectStudent();

	}

}
