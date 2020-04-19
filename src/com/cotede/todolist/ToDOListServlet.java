package com.cotede.todolist;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.*;

@WebServlet("/main")
public class ToDOListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private TicketDBUtil mTicketDBUtil;
	
	@Resource(name="jdbc/to_do_list_database")
	private DataSource mDataSource;
	
	@Override
	public void init() throws ServletException{
		super.init();
		
		try {
			mTicketDBUtil = new TicketDBUtil(mDataSource);
		}
		catch (Exception ex) {
			throw new ServletException(ex);
		}
	}
	
    public ToDOListServlet() {
        super();
    }

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Ticket> allTickets;
		try {
			allTickets = mTicketDBUtil.getTickets();
		} catch (Exception e) {
			allTickets = new ArrayList<Ticket>();
			e.printStackTrace();
		}
		request.setAttribute("ALL_TICKETS", allTickets);

		RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
		dispatcher.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		StringBuffer jb = new StringBuffer();
		String line = null;
		try {
			BufferedReader reader = request.getReader();
		while ((line = reader.readLine()) != null)
	    	jb.append(line);
		} catch (Exception e) {
			throw new IOException("Error reading request content");
		}
	
		JSONObject input;
		JSONObject output;
		String value;
		boolean isChecked;
		try {
			input = new JSONObject(jb.toString());
			value = input.getString("value");
			isChecked = input.getBoolean("isChecked");
		} catch (JSONException e) {
			throw new IOException("Error parsing JSON request string");
		}
		Ticket ticket = new Ticket(0, value, isChecked);
		try {
			int lastInsertedId = mTicketDBUtil.addTicket(ticket);
			output = new JSONObject();
			output.put("success", true);
			output.put("id", lastInsertedId);
		}
		catch(Exception ex) {
			ex.printStackTrace();
			output = new JSONObject();
			try {
				output.put("success", false);
			} catch (JSONException e) {
				
			}
		}
		PrintWriter out = response.getWriter();
		out.print(output.toString());
	}

	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		StringBuffer jb = new StringBuffer();
		String line = null;
		try {
			BufferedReader reader = request.getReader();
		while ((line = reader.readLine()) != null)
	    	jb.append(line);
		} catch (Exception e) {
			throw new IOException("Error reading request content");
		}
	
		JSONObject input;
		JSONObject output;
		int ticketId;
		boolean isChecked;
		try {
			input = new JSONObject(jb.toString());
			ticketId = input.getInt("ticketId");
			isChecked = input.getBoolean("isChecked");
		} catch (JSONException e) {
			throw new IOException("Error parsing JSON request string");
		}
		Ticket ticket = new Ticket(ticketId, "", isChecked);
		try {
			mTicketDBUtil.updateTicketChecked(ticketId, isChecked);
			output = new JSONObject();
			output.put("success", true);
		}
		catch(Exception ex) {
			ex.printStackTrace();
			output = new JSONObject();
			try {
				output.put("success", false);
			} catch (JSONException e) {
				
			}
		}
		PrintWriter out = response.getWriter();
		out.print(output.toString());
	}

	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String idString = request.getParameter("ticketId");
		int id;
		try {
			id = Integer.parseInt(idString);
		}
		catch(Exception ex) {
			throw new IOException("Ticket ID is missing");
		}
		
		JSONObject output;
		try {
			mTicketDBUtil.deleteTicket(id);
			output = new JSONObject();
			output.put("success", true);
		}
		catch(Exception ex) {
			ex.printStackTrace();
			output = new JSONObject();
			try {
				output.put("success", false);
			} catch (JSONException e) {
				
			}
		}
		PrintWriter out = response.getWriter();
		out.print(output.toString());
	}
}
