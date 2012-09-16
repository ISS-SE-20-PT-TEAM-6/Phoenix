package sg.edu.nus.iss.phoenix.radioprogram.controller;

import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sg.edu.nus.iss.phoenix.core.controller.FCUtilities;
import sg.edu.nus.iss.phoenix.radioprogram.delegate.RPDelegate;
import sg.edu.nus.iss.phoenix.radioprogram.entity.RPSearchObject;
import sg.edu.nus.iss.phoenix.radioprogram.entity.RadioProgram;

/**
 * Servlet implementation class ProcessController
 */
@WebServlet("/ProcessController/*")
public class ProcessController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ProcessController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(request, response);
	}

	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String selection = FCUtilities.stripPath(request.getPathInfo())
				.toLowerCase();
		switch (selection) {
		case "process":
			RPDelegate rpdel = new RPDelegate();
			RadioProgram rp = new RadioProgram();
			rp.setName(request.getParameter("name"));
			rp.setDescription(request.getParameter("description"));
			String dur = request.getParameter("typicalDuration");
			System.out.println(rp.toString());
			Time t = Time.valueOf(dur);
			rp.setTypicalDuration(t);
			String ins = (String) request.getParameter("ins");
			Logger.getLogger(getClass().getName()).log(Level.INFO,
					"Insert Flag: " + ins);
			if (ins.equalsIgnoreCase("true")) {
				rpdel.insertRP(rp);
			} else {
				rpdel.updateRP(rp);
			}
			ArrayList<RadioProgram> data = rpdel.findAllRP();
			request.setAttribute("rps", data);
			RequestDispatcher rd = request
					.getRequestDispatcher("/pages/crudrp.jsp");
			rd.forward(request, response);
			break;
		case "delete":
			RPDelegate rpdel1 = new RPDelegate();
			String name = request.getParameter("name");
			RadioProgram rp1 = new RadioProgram(name);
			rpdel1.deleteRP(rp1);
			ArrayList<RadioProgram> data1 = rpdel1.findAllRP();
			request.setAttribute("rps", data1);
			RequestDispatcher rd1 = request
					.getRequestDispatcher("/pages/crudrp.jsp");
			rd1.forward(request, response);
			break;
		case "load":
			RPDelegate rpdel2 = new RPDelegate();
			ArrayList<RadioProgram> data2 = rpdel2.findAllRP();
			request.setAttribute("rps", data2);
			RequestDispatcher rd2 = request
					.getRequestDispatcher("/pages/crudrp.jsp");
			rd2.forward(request, response);
			break;
		case "search":
			RPDelegate rdel3 = new RPDelegate();
			RPSearchObject search3 = new RPSearchObject();
			ArrayList<RadioProgram> list = new ArrayList<RadioProgram>();
			if (request.getParameter("name") != null
					|| request.getParameter("description") != null) {
				search3.setName(request.getParameter("name"));
				search3.setDescription(request.getParameter("description"));
				list = rdel3.searchPrograms(search3);
			} else {
				list = rdel3.findAllRP();
			}
			request.getSession().setAttribute("searchrplist", list);
			RequestDispatcher rd3 = getServletContext().getRequestDispatcher(
					"/pages/searchrp.jsp");
			;
			rd3.forward(request, response);
			break;
		default:
			RPDelegate rpdel4 = new RPDelegate();
			ArrayList<RadioProgram> data4 = rpdel4.findAllRP();
			request.setAttribute("rps", data4);
			RequestDispatcher rd4 = request
					.getRequestDispatcher("/pages/crudrp.jsp");
			rd4.forward(request, response);
			break;
		}
	}

}
