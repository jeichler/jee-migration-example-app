package com.acme.anvil;

import java.io.IOException;
import java.io.PrintWriter;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.acme.anvil.service.ItemLookupBean;

public class AnvilWebServlet extends HttpServlet {

	private static final Logger LOG = Logger.getLogger(AnvilWebServlet.class);
	
	/** this should trigger the hard coded IP address rule **/
	private static final String IP_ADDRESS = "192.168.0.1";
	
	@Inject
	private ItemLookupBean lh;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String itemId = req.getParameter("id");
		if(StringUtils.isNotBlank(itemId)) {
			Long id = Long.parseLong(itemId);
			lh.lookupItem(id);
			
			/** should trigger cloud-readiness rule for session **/
			HttpSession httpSession = req.getSession();
			httpSession.setAttribute("itemId", itemId);
			
			PrintWriter out = resp.getWriter();
			out.println("<html>");
			out.println("<body>");
			out.println("<h1>ID: "+id+"</h1>");
			out.println("<h1>IP: "+IP_ADDRESS+"</h1>");
			out.println("</body>");
			out.println("</html>");	
		}
		else {
			PrintWriter out = resp.getWriter();
			out.println("<html>");
			out.println("<body>");
			out.println("<h1>No ID Provided.</h1>");
			out.println("</body>");
			out.println("</html>");
		}
	}
}
