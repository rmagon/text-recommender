package com.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.tfidf.TfidfHelper;
import com.utilities.Hotel;

/**
 * Servlet implementation class Recommender
 */
@WebServlet("/Recommender")
public class Recommender extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Recommender() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String selectedHotel = request.getParameter("hotel");
		System.out.println("req:"+selectedHotel);
		HttpSession session=request.getSession(true);  
		String cities[] = (String[])session.getAttribute("hotels");
		
		TfidfHelper reco = new TfidfHelper();
		ArrayList<Hotel> recoList = reco.run(cities,selectedHotel);
		request.setAttribute("hotels", recoList);
		request.getRequestDispatcher("summary.jsp").forward(request, response);
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
	}

}
