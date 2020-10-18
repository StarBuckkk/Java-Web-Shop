package com.bookshop01.goods.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.bookshop01.goods.vo.GoodsVO;
import com.bookshop01.goods.vo.ImageFileVO;

@Repository("goodsDAO")
public class GoodsDAOImpl  implements GoodsDAO{
	@Autowired
	private SqlSession sqlSession; // XML 설정 파일에서 생성한 ID가 sqlSession인 빈을 자동 주입

	@Override
	public List<GoodsVO> selectGoodsList(String goodsStatus ) throws DataAccessException {
		List<GoodsVO> goodsList = (ArrayList)sqlSession.selectList("mapper.goods.selectGoodsList", goodsStatus); // 매퍼 파일인 goods.xml에서 지정한 id인 selectGoodsList인 SQL문을 요청
	   return goodsList;	
     
	}
	@Override
	public List<String> selectKeywordSearch(String keyword) throws DataAccessException {
	   List<String> list = (ArrayList)sqlSession.selectList("mapper.goods.selectKeywordSearch", keyword); // id가 selectKeywordSearch인 SQL문을 요청
	   return list;
	}
	
	@Override
	public ArrayList selectGoodsBySearchWord(String searchWord) throws DataAccessException {
		ArrayList list = (ArrayList)sqlSession.selectList("mapper.goods.selectGoodsBySearchWord", searchWord); // id가 selectGoodsBySearchWord인 SQL문을 요청
		 return list;
	}
	
	@Override
	public GoodsVO selectGoodsDetail(String goods_id) throws DataAccessException {
		GoodsVO goodsVO = (GoodsVO)sqlSession.selectOne("mapper.goods.selectGoodsDetail", goods_id); // id가 selectGoodsDetail인 SQL문을 요청
		return goodsVO;
	}
	
	@Override
	public List<ImageFileVO> selectGoodsDetailImage(String goods_id) throws DataAccessException {
		List<ImageFileVO> imageList = (ArrayList)sqlSession.selectList("mapper.goods.selectGoodsDetailImage", goods_id); // id가 selectGoodsDetailImage인 SQL문을 요청
		return imageList;
	}
	
}
