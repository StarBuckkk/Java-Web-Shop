package sec03_Board.brd02;

import java.util.List;

public class BoardService {
	BoardDAO boardDAO;
	public BoardService() {
		boardDAO = new BoardDAO(); // ������ ȣ�� �� BoardDAO ��ü ����
	}

	public List<ArticleVO> listArticles() {
		List<ArticleVO> articlesList = boardDAO.selectAllArticles();
		return articlesList;
	}
	
	public void addArticle(ArticleVO article){ // �� �� ��ȣ�� ��Ʈ�ѷ��� ��ȯ
		boardDAO.insertNewArticle(article);		
	}

}