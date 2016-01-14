package io.github.zkhan93.contactpage.server;

import io.github.zkhan93.contactpage.server.service.LoginService;
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
 * Servlet implementation class Home
 */
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * if the request is a GET request send invalid response
	 * 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = new PrintWriter(response.getOutputStream());
		response.setContentType("application/json");
		JSONObject jResponse = new JSONObject();
		try {
			jResponse.put("Authentication", false);
			jResponse.put("error", "Invalid Request");
		} catch (JSONException ex) {
			ex.printStackTrace();
		}
		out.print(jResponse.toString());
		out.flush();
	}

	/**
	 * if the request is POST request the check for post data convert it into
	 * JSONObject, check username and password and response accordingly
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// reading data from request stream and converting into jsonObject
		JSONObject jsonData = Util.getJSON(request.getReader());

		JSONObject jResponse = new JSONObject();
		try {
			if (jsonData != null) {
				// reading username and password from JSONObject created earlier
				String username, password;
				username = jsonData.getString("username");
				password = jsonData.getString("password");
				// checking for correctness and response accordingly
				boolean authenticated = new LoginService().checkUser(username,
						password);
				jResponse.put("Authentication", authenticated);
				if (authenticated) {
					jResponse.put("msg", "Welcome");
				} else {
					jResponse.put("error", "Invalid credentials");
				}
			} else {
				jResponse.put("Authentication", false);
				jResponse.put("error", "No data received by server");
			}
		} catch (JSONException ex) {
			ex.printStackTrace();
		}
		// writing data to response
		PrintWriter out = new PrintWriter(response.getOutputStream());
		response.setContentType("application/json");
		out.print(jResponse.toString());
		out.flush();
	}

}
