package com.tecsup.lab08.crud.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.tecsup.lab08.crud.bean.Employee;
import com.tecsup.lab08.crud.db.ConnectionDB;

public class EmployeeDAOImpl implements EmployeeDAO {

	/* (non-Javadoc)
	 * @see com.tecsup.lab08.crud.dao.EmployeeDAO2#getEmployees()
	 */
	@Override
	public ArrayList<Employee> findAll() {

		System.out.println("getEmployees()");

		ResultSet rs = null;
		PreparedStatement pst = null;
		Connection con = ConnectionDB.getConnection();
		String stm = "Select employee_id, first_name, last_name, salary from employees_2 order by employee_id";
		ArrayList<Employee> records = new ArrayList<Employee>();
		
		try {
			pst = con.prepareStatement(stm);
			pst.execute();
			rs = pst.getResultSet();
			while (rs.next()) {
				Employee emp = new Employee();
				emp.setId(rs.getInt(1));
				emp.setFirstname(rs.getString(2));
				emp.setLastname(rs.getString(3));
				emp.setSalary(rs.getDouble(4));
				records.add(emp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pst.close();
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return records;
	}


	/**
	 * 
	 * @param id
	 */
	public Employee findById(int id) {

		System.out.println("getEmployee()");

		ResultSet rs = null;
		PreparedStatement pst = null;
		Connection con = ConnectionDB.getConnection();
		String stm = "Select employee_id, first_name, last_name, salary from employees_2 where employee_id = ?";
		Employee emp = new Employee();
		try {
			pst = con.prepareStatement(stm);
			pst.setInt(1, id);
			pst.execute();
			rs = pst.getResultSet();
			if (rs.next()) {
				emp.setId(rs.getInt(1));
				emp.setFirstname(rs.getString(2));
				emp.setLastname(rs.getString(3));
				emp.setSalary(rs.getDouble(4));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pst.close();
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return emp;
	}
	
	/* (non-Javadoc)
	 * @see com.tecsup.lab08.crud.dao.EmployeeDAO2#createEmployee(java.lang.String, java.lang.String, java.lang.Double)
	 */
	@Override
	public int  create(String pFirstname, String pLastname, Double pSalary) {

		ResultSet rs = null;
		PreparedStatement pst = null;
		Connection con = ConnectionDB.getConnection();
		int idGenerator = -1;
		
		String stm = "SELECT EMPLOYEES_SEQ.NEXTVAL FROM DUAL";
		
		try {
			pst = con.prepareStatement(stm);
			pst.execute();
			rs = pst.getResultSet();
			if (rs.next())  idGenerator = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pst.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		String stm2 = "INSERT INTO employees_2 (EMPLOYEE_ID, FIRST_NAME, LAST_NAME, SALARY)  VALUES (?,?,?,?)";
		try {
			pst = con.prepareStatement(stm2);
			pst.setInt(1, idGenerator);
			pst.setString(2, pFirstname);
			pst.setString(3, pLastname);
			pst.setDouble(4, pSalary);
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pst.close();
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return idGenerator;
	}

	/* (non-Javadoc)
	 * @see com.tecsup.lab08.crud.dao.EmployeeDAO2#deleteEmployee(int)
	 */
	@Override
	public void delete(int id) {

		PreparedStatement pst = null;
		Connection con = ConnectionDB.getConnection();
		String stm = "DELETE FROM employees_2 WHERE EMPLOYEE_ID =? ";
		try {
			pst = con.prepareStatement(stm);
			pst.setInt(1, id);
			pst.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pst.close();
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	/* (non-Javadoc)
	 * @see com.tecsup.lab08.crud.dao.EmployeeDAO2#updateEmployee(int, java.lang.String, java.lang.String, java.lang.Double)
	 */
	@Override
	public void update(int id, String pFirstname, String pLastname, Double pSalary) {

		PreparedStatement pst = null;
		Connection con = ConnectionDB.getConnection();
		String stm = "UPDATE EMPLOYEES_2 SET FIRST_NAME=?, LAST_NAME = ?, SALARY = ? WHERE EMPLOYEE_ID = ?";
		try {
			pst = con.prepareStatement(stm);
			pst.setString(1, pFirstname);
			pst.setString(2, pLastname);
			pst.setDouble(3, pSalary);
			pst.setInt(4, id);
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pst.close();
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
