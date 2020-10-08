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
			Context envContext = (Context) ctx.lookup("java:/comp/env"); // JNDI�� �����ϱ� ���� �⺻ ��θ� ����
			dataFactory = (DataSource) envContext.lookup("jdbc/oracle"); // ��Ĺ context.xml�� ������ name���� jdbc/oracle�� �̿��� ��Ĺ�� �̸� ������ DataSource�� �޾ƿ´�.
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List selectAllArticles(Map pagingMap){
		List articlesList = new ArrayList();
		int section = (Integer)pagingMap.get("section"); // ���۵� section�� pageNum ���� ������
		int pageNum=(Integer)pagingMap.get("pageNum");
		try{
		   conn = dataFactory.getConnection(); // DataSource�� �̿��� �����ͺ��̽��� ����
		   String query ="SELECT * FROM ( " 
						+ "select ROWNUM  as recNum,"+"LVL," // ���������� ��ȸ�� ���ڵ��� ROWNUM(recNum)�� ǥ�õǵ��� ��ȸ
							+"articleNO,"
							+"parentNO,"
							+"title,"
							+"id,"
							+"writeDate"
				                  +" from (select LEVEL as LVL, " // ������ SQL������ ���� �������� ��ȸ
								+"articleNO,"
								+"parentNO,"
								+"title,"
								+"id,"
								 +"writeDate"
							   +" from t_board" 
							   +" START WITH  parentNO=0"
							   +" CONNECT BY PRIOR articleNO = parentNO"
							  +"  ORDER SIBLINGS BY articleNO DESC)" // ����Ŭ�� ������ SQL���� ����
					+") "                        
					+" where recNum between(?-1)*100+(?-1)*10+1 and (?-1)*100+?*10"; // section�� pageNum ������ ���ڵ� ��ȣ�� ������ �������� ����
		   				// (�̵� ���� ���� 1�� ���۵Ǿ����� between 1 and 10�� �ȴ�.
		   
		   System.out.println(query);
		   pstmt= conn.prepareStatement(query);
		   pstmt.setInt(1, section); // select ���� �� '?'�� ������� ȸ�� ������ ��ȸ / ?�� 1���� ����
		   pstmt.setInt(2, pageNum);
		   pstmt.setInt(3, section);
		   pstmt.setInt(4, pageNum);
		   ResultSet rs =pstmt.executeQuery();
		   
		   while(rs.next()){ // �� ���� ����(����)�� level �Ӽ��� ����
		      int level = rs.getInt("lvl");
		      int articleNO = rs.getInt("articleNO");
		      int parentNO = rs.getInt("parentNO");
		      String title = rs.getString("title");
		      String id = rs.getString("id");
		      Date writeDate= rs.getDate("writeDate");
		      
		      ArticleVO article = new ArticleVO();
		      
		      article.setLevel(level); // �� ������ ArticleVO ��ü�� �Ӽ��� ����
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
					+ " ORDER SIBLINGS BY articleNO DESC"; // ����Ŭ�� ������ SQL���� ����
			
			System.out.println(query);
			pstmt = conn.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			 
			while (rs.next()) { // �� ���� ����(����)�� level �Ӽ��� ����
				int level = rs.getInt("level");
				int articleNO = rs.getInt("articleNO");
				int parentNO = rs.getInt("parentNO");
				String title = rs.getString("title");
				String content = rs.getString("content");
				String id = rs.getString("id");
				Date writeDate = rs.getDate("writeDate");
				
				ArticleVO article = new ArticleVO();
				
				article.setLevel(level); // �� ������ ArticleVO ��ü�� �Ӽ��� ����
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
			
			String query = "SELECT  max(articleNO) from t_board "; // �⺻ �� ��ȣ �� ���� ū ��ȣ�� ��ȸ
			
			System.out.println(query);
			pstmt = conn.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery(query);
			
			if (rs.next()) {
				return (rs.getInt(1) + 1); // ���� ū ��ȣ�� 1�� ���� ��ȣ�� ��ȯ
			}
			
			rs.close();
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int insertNewArticle(ArticleVO article) { // PreparedStatement�� insert���� �Խù������� �����ϱ� ���� ?�� ���
		int articleNO = getNewArticleNO();
		try {
			conn = dataFactory.getConnection();
			
			int parentNO = article.getParentNO(); 
			String title = article.getTitle();
			String content = article.getContent();
			String id = article.getId();
			String imageFileName = article.getImageFileName();
			
			String query = "INSERT INTO t_board (articleNO, parentNO, title, content, imageFileName, id)"
					+ " VALUES (?, ? ,?, ?, ?, ?)"; // insert���� �̿��� �� ������ �߰�
			
			System.out.println(query);
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, articleNO); // insert���� �� '?'�� ������� ȸ�� ������ ���� / ?�� 1���� ����
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

		return articleNO; // SQL������ �� ���� �߰��ϰ� �� �� ��ȣ�� ��ȯ
	}

	public ArticleVO selectArticle(int articleNO) {
		ArticleVO article = new ArticleVO();
		try {
			conn = dataFactory.getConnection();
			
			String query = "select articleNO,parentNO,title,content, imageFileName,id,writeDate" + " from t_board"
					+ " where articleNO=?"; // ���޹��� �� ��ȣ�� �̿��� �� ������ ��ȸ
			
			System.out.println(query);
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, articleNO); // ù��° '?'�� ���޵� articleNO�� ���ڷ� ����
			ResultSet rs = pstmt.executeQuery();
			
			rs.next();
			
			int _articleNO = rs.getInt("articleNO");
			int parentNO = rs.getInt("parentNO");
			String title = rs.getString("title");
			String content = rs.getString("content");
			String imageFileName = rs.getString("imageFileName"); //�����̸��� Ư�����ڰ� ���� ��� ���ڵ��մϴ�.
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
			
			if (imageFileName != null && imageFileName.length() != 0) { // ������ �̹��� ������ ���� ���� imageFileName�� SQL���� �߰�
				query += ",imageFileName=?"; 
			}
			
			query += " where articleNO=?";

			System.out.println(query);
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, title);
			pstmt.setString(2, content);
			
			if (imageFileName != null && imageFileName.length() != 0) { // �̹��� ������ �����ϴ� ���� �׷��� ���� ��츦 �����ؼ� ����
				pstmt.setString(3, imageFileName);
				pstmt.setInt(4, articleNO);
				
			} else {
				pstmt.setInt(3, articleNO);
			}
			
			pstmt.executeUpdate(); // �� ������� �ڿ���ȯ
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deleteArticle(int articleNO) {
		try {
			conn = dataFactory.getConnection();
			
			String query = "DELETE FROM t_board "; // ����Ŭ�� ������ SQL���� �̿��� ���� �۰� ���õ� �ڽ� �۱��� ��� ����
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
			
			String query = "SELECT articleNO FROM  t_board  "; // ������ �۵��� articleNO�� ��ȸ
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
			
			String query = "select count(articleNO) from t_board "; // ��ü �� ���� ��ȸ
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
