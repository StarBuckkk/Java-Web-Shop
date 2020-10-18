package com.myspring.pro30.board.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.myspring.pro30.board.vo.ArticleVO;
import com.myspring.pro30.board.vo.ImageVO;


@Repository("boardDAO")
public class BoardDAOImpl implements BoardDAO {
	@Autowired
	private SqlSession sqlSession; // XML 설정 파일에서 생성한 ID가 sqlSession인 빈을 자동 주입

	@Override
	public List selectAllArticlesList() throws DataAccessException {
		List<ArticleVO> articlesList = sqlSession.selectList("mapper.board.selectAllArticlesList"); // 매퍼 파일인 board.xml에서 지정한 id인 selectAllArticlesList인 SQL문을 요청
		return articlesList;
	}

	
	@Override
	public int insertNewArticle(Map articleMap) throws DataAccessException {
		int articleNO = selectNewArticleNO(); // 새 글에 대한 글 번호를 가져옴
		articleMap.put("articleNO", articleNO); // 글 번호를 articleNO에 저장
		sqlSession.insert("mapper.board.insertNewArticle", articleMap); // 글 정보를 게시판 테이블에 추가한 후 글 번호를 반환
		
		return articleNO;
	}
    
	//다중 파일 업로드
	
	@Override
	public void insertNewImage(Map articleMap) throws DataAccessException {
		List<ImageVO> imageFileList = (ArrayList)articleMap.get("imageFileList");
		int articleNO = (Integer)articleMap.get("articleNO"); // articleNO이 글 번호를 가져옴
		int imageFileNO = selectNewImageFileNO(); // 이미지 번호를 가져옴
		
		for(ImageVO imageVO : imageFileList) { // 향상된 for문
			imageVO.setImageFileNO(++imageFileNO);
			imageVO.setArticleNO(articleNO);
		} // imageVO 객체를 차례대로 가져와 이미지 번호와 글 번호 속성을 설정
		
		sqlSession.insert("mapper.board.insertNewImage", imageFileList);
	}
	
	@Override
	public ArticleVO selectArticle(int articleNO) throws DataAccessException {
		return sqlSession.selectOne("mapper.board.selectArticle", articleNO);
	}

	@Override
	public void updateArticle(Map articleMap) throws DataAccessException {
		sqlSession.update("mapper.board.updateArticle", articleMap);
	}

	@Override
	public void deleteArticle(int articleNO) throws DataAccessException {
		sqlSession.delete("mapper.board.deleteArticle", articleNO);
		
	}
	
	@Override
	public List selectImageFileList(int articleNO) throws DataAccessException {
		List<ImageVO> imageFileList = null;
		imageFileList = sqlSession.selectList("mapper.board.selectImageFileList", articleNO);
		return imageFileList;
	}
	
	private int selectNewArticleNO() throws DataAccessException {
		return sqlSession.selectOne("mapper.board.selectNewArticleNO"); // 새 글 번호를 가져옴
	}
	
	private int selectNewImageFileNO() throws DataAccessException {
		return sqlSession.selectOne("mapper.board.selectNewImageFileNO");
	}

}
