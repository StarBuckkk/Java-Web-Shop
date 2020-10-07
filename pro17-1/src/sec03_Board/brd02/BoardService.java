package sec03_Board.brd02;

import java.util.List;

public class BoardService {
	BoardDAO boardDAO;
	public BoardService() {
		boardDAO = new BoardDAO(); // 생성자 호출 시 BoardDAO 객체 생성
	}

	public List<ArticleVO> listArticles() {
		List<ArticleVO> articlesList = boardDAO.selectAllArticles();
		return articlesList;
	}
	
	public void addArticle(ArticleVO article){ // 새 글 번호를 컨트롤러로 반환
		boardDAO.insertNewArticle(article);		
	}

}
