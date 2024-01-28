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

		System.out.println("연결됨?4");
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("클래스가 없습니다.");
			e.printStackTrace();
		}
		System.out.println("연결됨?3");
		//DB정보 가져오기, mysql의 테이블이름 써줘야함. (?앞쪽에)
		String url = "jdbc:mysql://127.0.0.1:3306/JSP_AM?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";		
		String user = "root";
		String password = "";
		
		Connection conn = null;
		System.out.println("연결됨?2");
		try {
			conn = DriverManager.getConnection(url, user, password);
//			conn = DriverManager.getConnection(url, "root", "");
			response.getWriter().append("연결 성공!");
			//이부분은 그냥 그런갑다 함
			DBUtil dbUtil = new DBUtil(request, response);
			
			//dbutil쪽에서는 Secsql로 받는데 여기서는 string을 써줘서
			//어느쪽이든 선택해서 맞춰줘야함.
			SecSql sql = SecSql.from("SELECT * FROM article;");
			System.out.println("연결됨?1");
			List<Map<String, Object>> articleRows = dbUtil.selectRows(conn, sql);
			
			//객체정보를 스트링으로 쭉 보여주겠다.
			response.getWriter().append(articleRows.toString());
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
