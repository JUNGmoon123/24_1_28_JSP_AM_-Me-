package com.KoreaIT.java.jsp_AM_M;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/article/list")
public class ArticleListesvlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//한글로 나오게해줌
		response.setContentType("text/html;charset=UTF-8");

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("클래스가 없습니다.");
			e.printStackTrace();
		}

		//DB정보 가져오기, mysql의 테이블이름 써줘야함. (?앞쪽에)
		String url = "jdbc:mysql://127.0.0.1:3306/JSP_AM?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";		
		String user = "root";
		String password = "";
		
		Connection conn = null;

		try {
			conn = DriverManager.getConnection(url, user, password);
//			conn = DriverManager.getConnection(url, "root", "");
			response.getWriter().append("연결 성공!");
			//이부분은 그냥 그런갑다 함
			DBUtil dbUtil = new DBUtil(request, response);
			
			//dbutil쪽에서는 Secsql로 받는데 여기서는 string을 써줘서
			//어느쪽이든 선택해서 맞춰줘야함.
			SecSql sql = SecSql.from("SELECT * FROM article;");

			List<Map<String, Object>> articleRows = dbUtil.selectRows(conn, sql);
			
			//객체정보를 스트링으로 쭉 보여주겠다.
			response.getWriter().append(articleRows.toString());
			//jsp쪽으로 DB서버의 아티클 정보를 요청해서 넘겨줌
			//List/Map타입의 articleRows를 그리게 넘겨준다.
			//밑의 주소를 따라 넘겨준다.
			request.setAttribute("articleRows", articleRows);
			//getWriter로 바로 보여주는게 아니라 한번더 요청을한다.
			//이 요청이 화면에 그리게 해준다. 
			request.getRequestDispatcher("/jsp/article/list.jsp").forward(request, response);
		} catch (SQLException e) {
			System.out.println("에러 : " + e);
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	

}
