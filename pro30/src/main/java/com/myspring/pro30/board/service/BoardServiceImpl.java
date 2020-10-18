package com.myspring.pro30.board.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.myspring.pro30.board.dao.BoardDAO;
import com.myspring.pro30.board.vo.ArticleVO;
import com.myspring.pro30.board.vo.ImageVO;


@Service("boardService")
@Transactional(propagation = Propagation.REQUIRED)
public class BoardServiceImpl  implements BoardService {
	@Autowired
	BoardDAO boardDAO;
	
	public List<ArticleVO> listArticles() throws Exception {
		List<ArticleVO> articlesList =  boardDAO.selectAllArticlesList(); // boardDAO의 selectArticlesList() 메서드를 호출
        return articlesList;
	}
	
	//단일 이미지 추가하기
//	@Override
//	public int addNewArticle(Map articleMap) throws Exception {
//		return boardDAO.insertNewArticle(articleMap); // 컨트롤러에서 전달된 articleMap을 다시 DAO의 insertNewArticle() 메서드 인자로 전달
//	}
	
	//단일 파일 보이기
//	@Override
//	public ArticleVO viewArticle(int articleNO) throws Exception {
//		ArticleVO articleVO = boardDAO.selectArticle(articleNO);
//		return articleVO;
//	}
	
	 //다중 이미지 추가하기
	@Override
	public int addNewArticle(Map articleMap) throws Exception {
		int articleNO = boardDAO.insertNewArticle(articleMap); // 글 정보를 저장한 후 글 번호를 가져옴
		articleMap.put("articleNO", articleNO);
		boardDAO.insertNewImage(articleMap); // 글 번호를 articleMap에 저장한 후 이미지 정보를 저장
		return articleNO;
	}
	
	//다중 파일 보이기
	@Override
	public Map viewArticle(int articleNO) throws Exception {
		Map articleMap = new HashMap();
		ArticleVO articleVO = boardDAO.selectArticle(articleNO); // 글 정보를 조회
		List<ImageVO> imageFileList = boardDAO.selectImageFileList(articleNO); // 이미지 파일 정보를 조회
		articleMap.put("article", articleVO);
		articleMap.put("imageFileList", imageFileList); // 글 정보와 이미지 파일 정보를 Map에 담음
		return articleMap;
	}
   
	@Override
	public void modArticle(Map articleMap) throws Exception {
		boardDAO.updateArticle(articleMap);
	}
	
	@Override
	public void removeArticle(int articleNO) throws Exception {
		boardDAO.deleteArticle(articleNO);
	}
	

	
}
