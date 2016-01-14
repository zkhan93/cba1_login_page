package io.github.zkhan93.contactpage.server;

import io.github.zkhan93.contactpage.server.service.ContactService;
import io.github.zkhan93.contactpage.server.service.LoginService;
import io.github.zkhan93.contactpage.server.util.Constants;
import io.github.zkhan93.contactpage.server.util.Util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Servlet implementation class Contact
 */
public class ContactController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ContactController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter out = new PrintWriter(response.getOutputStream());
		response.setContentType("application/json");
		JSONObject jResponse = new JSONObject();
		try {
			jResponse.put(Constants.JSON_KEYS.AUTHENTICATION, false);
			jResponse.put(Constants.JSON_KEYS.ERROR, "Invalid Request");
		} catch (JSONException ex) {
			ex.printStackTrace();
		}
		out.print(jResponse.toString());
		out.flush();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		JSONObject data = Util.getJSON(request.getReader());
		boolean authentication = false;
		try {
			if(data!=null)
				authentication = new LoginService().checkUser(
					data.getString(Constants.JSON_KEYS.PARAMS_KEYS.USERNAME), data.getString(Constants.JSON_KEYS.PARAMS_KEYS.PASSWORD));
		} catch (JSONException ex) {
			ex.printStackTrace();
		}
		JSONObject jResponse = new JSONObject();
		try {
			jResponse.put(Constants.JSON_KEYS.AUTHENTICATION, authentication);
			if (authentication) {
				jResponse.put(Constants.JSON_KEYS.CONTACTS, new ContactService().getContacts());
			} else {
				jResponse.put(Constants.JSON_KEYS.ERROR, "Invalid credentials");
			}
		} catch (JSONException ex) {
			ex.printStackTrace();
		}
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		out.print(jResponse.toString());
		out.flush();
	}
}