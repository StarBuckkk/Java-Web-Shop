package sec03_Board.brd08;

import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;


public class BoardDAO {
	private DataSource dataFactory;
	Connection conn;
	PreparedStatement pstmt;

	public BoardDAO() {
		try {
			Context ctx = new InitialContext();
			Context envContext = (Context) ctx.lookup("java:/comp/env"); // JNDI에 접근하기 위해 기본 경로를 지정
			dataFactory = (DataSource) envContext.lookup("jdbc/oracle"); // 톰캣 context.xml에 설정한 name값인 jdbc/oracle을 이용해 톰캣이 미리 연결한 DataSource를 받아온다.
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List selectAllArticles(Map pagingMap){
		List articlesList = new ArrayList();
		int section = (Integer)pagingMap.get("section"); // 전송된 section과 pageNum 값을 가져옴
		int pageNum=(Integer)pagingMap.get("pageNum");
		try{
		   conn = dataFactory.getConnection(); // DataSource를 이용해 데이터베이스에 연결
		   String query ="SELECT * FROM ( " 
						+ "select ROWNUM  as recNum,"+"LVL," // 계층형으로 조회된 레코드의 ROWNUM(recNum)이 표시되도록 조회
							+"articleNO,"
							+"parentNO,"
							+"title,"
							+"id,"
							+"writeDate"
				                  +" from (select LEVEL as LVL, " // 계층형 SQL문으로 글을 계층별로 조회
								+"articleNO,"
								+"parentNO,"
								+"title,"
								+"id,"
								 +"writeDate"
							   +" from t_board" 
							   +" START WITH  parentNO=0"
							   +" CONNECT BY PRIOR articleNO = parentNO"
							  +"  ORDER SIBLINGS BY articleNO DESC)" // 오라클의 계층형 SQL문을 실행
					+") "                        
					+" where recNum between(?-1)*100+(?-1)*10+1 and (?-1)*100+?*10"; // section과 pageNum 값으로 레코드 번호의 범위를 조건으로 정함
		   				// (이들 값이 각각 1로 전송되었으면 between 1 and 10이 된다.
		   
		   System.out.println(query);
		   pstmt= conn.prepareStatement(query);
		   pstmt.setInt(1, section); // select 문의 각 '?'에 순서대로 회원 정보를 조회 / ?은 1부터 시작
		   pstmt.setInt(2, pageNum);
		   pstmt.setInt(3, section);
		   pstmt.setInt(4, pageNum);
		   ResultSet rs =pstmt.executeQuery();
		   
		   while(rs.next()){ // 각 글의 깊이(계층)을 level 속성에 저장
		      int level = rs.getInt("lvl");
		      int articleNO = rs.getInt("articleNO");
		      int parentNO = rs.getInt("parentNO");
		      String title = rs.getString("title");
		      String id = rs.getString("id");
		      Date writeDate= rs.getDate("writeDate");
		      
		      ArticleVO article = new ArticleVO();
		      
		      article.setLevel(level); // 글 정보를 ArticleVO 객체의 속성에 설정
		      article.setArticleNO(articleNO);
		      article.setParentNO(parentNO);
		      article.setTitle(title);
		      article.setId(id);
		      article.setWriteDate(writeDate);
		      articlesList.add(article);	
		   } //end while
		   
		   rs.close();
		   pstmt.close();
		   conn.close();
	  }catch(Exception e){
	     e.printStackTrace();	
	  }
	  return articlesList;
    } 
	
	public List selectAllArticles() {
		List articlesList = new ArrayList();
		try {
			conn = dataFactory.getConnection();
			
			String query = "SELECT LEVEL,articleNO,parentNO,title,content,id,writeDate" + " from t_board"
					+ " START WITH  parentNO=0" + " CONNECT BY PRIOR articleNO=parentNO"
					+ " ORDER SIBLINGS BY articleNO DESC"; // 오라클의 계층형 SQL문을 실행
			
			System.out.println(query);
			pstmt = conn.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			 
			while (rs.next()) { // 각 글의 깊이(계층)을 level 속성에 저장
				int level = rs.getInt("level");
				int articleNO = rs.getInt("articleNO");
				int parentNO = rs.getInt("parentNO");
				String title = rs.getString("title");
				String content = rs.getString("content");
				String id = rs.getString("id");
				Date writeDate = rs.getDate("writeDate");
				
				ArticleVO article = new ArticleVO();
				
				article.setLevel(level); // 글 정보를 ArticleVO 객체의 속성에 설정
				article.setArticleNO(articleNO);
				article.setParentNO(parentNO);
				article.setTitle(title);
				article.setContent(content);
				article.setId(id);
				article.setWriteDate(writeDate);
				articlesList.add(article);
			}
			
			rs.close();
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return articlesList;
	}


	private int getNewArticleNO() {
		try {
			conn = dataFactory.getConnection();
			
			String query = "SELECT  max(articleNO) from t_board "; // 기본 글 번호 중 가장 큰 번호를 조회
			
			System.out.println(query);
			pstmt = conn.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery(query);
			
			if (rs.next()) {
				return (rs.getInt(1) + 1); // 가장 큰 번호에 1을 더한 번호를 반환
			}
			
			rs.close();
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int insertNewArticle(ArticleVO article) { // PreparedStatement의 insert문은 게시물정보를 저장하기 위해 ?를 사용
		int articleNO = getNewArticleNO();
		try {
			conn = dataFactory.getConnection();
			
			int parentNO = article.getParentNO(); 
			String title = article.getTitle();
			String content = article.getContent();
			String id = article.getId();
			String imageFileName = article.getImageFileName();
			
			String query = "INSERT INTO t_board (articleNO, parentNO, title, content, imageFileName, id)"
					+ " VALUES (?, ? ,?, ?, ?, ?)"; // insert문을 이용해 글 정보를 추가
			
			System.out.println(query);
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, articleNO); // insert문의 각 '?'에 순서대로 회원 정보를 세팅 / ?은 1부터 시작
			pstmt.setInt(2, parentNO);
			pstmt.setString(3, title);
			pstmt.setString(4, content);
			pstmt.setString(5, imageFileName);
			pstmt.setString(6, id);
			pstmt.executeUpdate();
			
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return articleNO; // SQL문으로 새 글을 추가하고 새 글 번호를 반환
	}

	public ArticleVO selectArticle(int articleNO) {
		ArticleVO article = new ArticleVO();
		try {
			conn = dataFactory.getConnection();
			
			String query = "select articleNO,parentNO,title,content, imageFileName,id,writeDate" + " from t_board"
					+ " where articleNO=?"; // 전달받은 글 번호를 이용해 글 정보를 조회
			
			System.out.println(query);
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, articleNO); // 첫번째 '?'에 전달된 articleNO를 인자로 넣음
			ResultSet rs = pstmt.executeQuery();
			
			rs.next();
			
			int _articleNO = rs.getInt("articleNO");
			int parentNO = rs.getInt("parentNO");
			String title = rs.getString("title");
			String content = rs.getString("content");
			String imageFileName = rs.getString("imageFileName"); //파일이름에 특수문자가 있을 경우 인코딩합니다.
			String id = rs.getString("id");
			Date writeDate = rs.getDate("writeDate");

			article.setArticleNO(_articleNO);
			article.setParentNO(parentNO);
			article.setTitle(title);
			article.setContent(content);
			article.setImageFileName(imageFileName);
			article.setId(id);
			article.setWriteDate(writeDate);
			
			rs.close();
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return article;
	}

	public void updateArticle(ArticleVO article) { // update
		int articleNO = article.getArticleNO();
		String title = article.getTitle();
		String content = article.getContent();
		String imageFileName = article.getImageFileName();
		try {
			conn = dataFactory.getConnection();
			
			String query = "update t_board  set title=?,content=?";
			
			if (imageFileName != null && imageFileName.length() != 0) { // 수정된 이미지 파일이 있을 때만 imageFileName을 SQL문에 추가
				query += ",imageFileName=?"; 
			}
			
			query += " where articleNO=?";

			System.out.println(query);
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, title);
			pstmt.setString(2, content);
			
			if (imageFileName != null && imageFileName.length() != 0) { // 이미지 파일을 수정하는 경우와 그렇지 않은 경우를 구분해서 설정
				pstmt.setString(3, imageFileName);
				pstmt.setInt(4, articleNO);
				
			} else {
				pstmt.setInt(3, articleNO);
			}
			
			pstmt.executeUpdate(); // 연 순서대로 자원반환
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deleteArticle(int articleNO) {
		try {
			conn = dataFactory.getConnection();
			
			String query = "DELETE FROM t_board "; // 오라클의 계층형 SQL문을 이용해 삭제 글과 관련된 자식 글까지 모두 삭제
			query += " WHERE articleNO in (";
			query += "  SELECT articleNO FROM  t_board ";
			query += " START WITH articleNO = ?";
			query += " CONNECT BY PRIOR  articleNO = parentNO )";
			
			System.out.println(query);
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, articleNO);
			pstmt.executeUpdate();
			
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<Integer> selectRemovedArticles(int articleNO) {
		List<Integer> articleNOList = new ArrayList<Integer>();
		try {
			conn = dataFactory.getConnection();
			
			String query = "SELECT articleNO FROM  t_board  "; // 삭제된 글들의 articleNO를 조회
			query += " START WITH articleNO = ?";
			query += " CONNECT BY PRIOR  articleNO = parentNO";
			
			System.out.println(query);
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, articleNO);
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				articleNO = rs.getInt("articleNO");
				articleNOList.add(articleNO);
			}
			
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return articleNOList;
	}

	public int selectTotArticles() {
		try {
			conn = dataFactory.getConnection();
			
			String query = "select count(articleNO) from t_board "; // 전체 글 수를 조회
			System.out.println(query);
			pstmt = conn.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			
			if (rs.next()) {
				return (rs.getInt(1));
			}
			
			rs.close();
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

}
