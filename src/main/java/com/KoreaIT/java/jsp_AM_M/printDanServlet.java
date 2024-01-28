package com.KoreaIT.java.jsp_AM_M;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/printDan")
public class printDanServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		
		//사람이 주소에 직접입력
		//Parameter가져온건 다 String이다.
		String inputedDan = request.getParameter("dan");
		String inputedLimit = request.getParameter("limit");
		
		//if문이 없으면 밑에 int에 값이 null이라서 웹에서 실행조차 안됨.
		if(inputedDan == null) {
			inputedDan = "1";
		}
		if(inputedLimit == null) {
			inputedLimit = "1";
		}
		
		int dan = Integer.parseInt(inputedDan);
		int limit = Integer.parseInt(inputedLimit);
		
		response.getWriter().append(String.format("==%d단==<br>", dan));


		for (int i = 1; i <= 9; i++) {
			response.getWriter().append(String.format("%d * %d = %d<br>", dan, i, dan * i));
		}
	}
}