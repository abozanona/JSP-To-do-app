package com.cotede.todolist;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public class TicketDBUtil {
	private DataSource mDataSource;
	public TicketDBUtil(DataSource dataSource) {
		this.mDataSource = dataSource;
	}
	
	public List<Ticket> getTickets()throws Exception {
		List<Ticket> allTickets = new ArrayList<Ticket>();
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myConn = mDataSource.getConnection();
			myStmt = myConn.createStatement();

			String sql = "SELECT * FROM TICKET ORDER BY id";
			myRs = myStmt.executeQuery(sql);
			
			while(myRs.next()) {
				int id = myRs.getInt("id");
				String value = myRs.getString("value");
				boolean isChecked = myRs.getBoolean("is_checked");
				Ticket ticket = new Ticket(id, value, isChecked);
				allTickets.add(ticket);
			}
			return allTickets;
		}
		finally {
			close(myConn, myStmt, myRs);
		}
	}

	public int addTicket(Ticket ticket)throws Exception {
		int lastInsertedId = 0;
		Connection myConn = null;
		Statement myStmt = null;
		PreparedStatement ps  = null;
		ResultSet myRs = null;
		try {
			myConn = mDataSource.getConnection();
			
			String sql = "INSERT INTO TICKET (value, is_checked) VALUES (?, ?)";
			ps = myConn.prepareStatement(sql);
			ps.setString(1, ticket.getValue());
			ps.setBoolean(2, ticket.isChecked());
			int res = ps.executeUpdate();
			
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery("select * from TICKET");
            if(myRs.last()){
            	lastInsertedId = myRs.getInt("id");
            }
		}
		finally {
			close(myConn, myStmt, myRs);
		}
		return lastInsertedId;
	}

	public void updateTicketChecked(int ticketId, boolean isChecked) throws Exception {
		Connection myConn = null;
		Statement myStmt = null;
		PreparedStatement ps  = null;
		ResultSet myRs = null;
		try {
			myConn = mDataSource.getConnection();
			
			String sql = "UPDATE TICKET set is_checked = ? WHERE id = ?";
			ps = myConn.prepareStatement(sql);
			ps.setBoolean(1, isChecked);
			ps.setInt(2, ticketId);
			int res = ps.executeUpdate();
		}
		finally {
			close(myConn, myStmt, myRs);
		}
	}

	public void deleteTicket(int ticketId)throws Exception {
		Connection myConn = null;
		Statement myStmt = null;
		PreparedStatement ps  = null;
		ResultSet myRs = null;
		try {
			myConn = mDataSource.getConnection();
			
			String sql = "DELETE FROM TICKET WHERE id=?";
			ps = myConn.prepareStatement(sql);
			ps.setInt(1, ticketId);
			int res = ps.executeUpdate();
		}
		finally {
			close(myConn, myStmt, myRs);
		}
	}

	private void close(Connection myConn, Statement myStmt, ResultSet myRs) {
		try {
			if(myRs != null) {
				myRs.close();
			}
			if(myStmt != null) {
				myStmt.close();
			}
			if(myConn != null) {
				myConn.close();
			}
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		
	}
}
