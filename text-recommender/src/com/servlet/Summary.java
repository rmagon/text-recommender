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
import com.utilities.DatabaseHelper;
import com.utilities.Hotel;

/**
 * Servlet implementation class Summary
 */
@WebServlet("/Summary")
public class Summary extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Summary() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String hotels[] = request.getParameterValues("hotel");
		for(String s:hotels)
		response.getWriter().print(s);
		DatabaseHelper dbHotel = new DatabaseHelper();
		ArrayList<Hotel> hotelAllInfo = dbHotel.getHotelByCity(hotels);  
		request.setAttribute("hotels", hotelAllInfo);
		
		int index = 0,i=0;
		String cities[] = new String[hotelAllInfo.size()];
		HashMap<String,Integer> uniq_cities = new HashMap<String,Integer>();
		
		for(Hotel h:hotelAllInfo)
		{
			uniq_cities.put(h.getCity(),0);
	
		}
		
		Iterator it = uniq_cities.keySet().iterator();
		i=0;
		while(it.hasNext())
		{
			cities[i++]=(String)it.next();
		}
		
		HttpSession session=request.getSession();  
        session.setAttribute("hotels",cities);  
		request.getRequestDispatcher("summary.jsp").forward(request, response);
		
		
	}

}
