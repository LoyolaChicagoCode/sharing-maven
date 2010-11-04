package sharing;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
//import javax.servlet.SingleThreadModel;

public class Servlet extends HttpServlet /*implements SingleThreadModel*/ {

	private final static long serialVersionUID = 20060329; 

	private final static String SHARED = "Shared";  

	public void doGet(HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException {

	    System.out.println(Thread.currentThread() + " doGet starting");

	    int initial = -1;
		int result = -1;
		Object ref = null;

		HttpSession session = req.getSession();
		
//		synchronized (session) {
			Shared shared = (Shared) session.getAttribute(SHARED);
			if (shared == null) {
				shared = new Shared();
				session.setAttribute(SHARED, shared);			
			}
			
			initial = shared.get();
			shared.inc();
			result = shared.get();
			ref = shared;
//		}

		System.out.println(Thread.currentThread() + " doGet done processing");

		res.setContentType("text/html");
		PrintWriter out = res.getWriter();

		out.println("<html>");
		out.println("<head><title>Race Condition</title></head>");
		out.println("<body>");

		out.println("<h1>Race Condition</h1>");
		out.println("<p>Servlet identity: " + this + "</p>");
		out.println("<p>Session identity: " + session + "</p>");
		out.println("<p>Shared identity: " + ref + "</p>");
		out.println("<p>Initial value: " + initial + "</p>");
		out.println("<p>Final value: " + result + "</p>");
		
		out.println("</body>");
		out.println("</html>");
	}
}
