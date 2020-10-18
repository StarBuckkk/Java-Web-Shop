package com.bookshop01.goods.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bookshop01.goods.dao.GoodsDAO;
import com.bookshop01.goods.vo.GoodsVO;
import com.bookshop01.goods.vo.ImageFileVO;

@Service("goodsService")
@Transactional(propagation = Propagation.REQUIRED) // GoodsServiceImpl 클래스를 이용해 id가 goodsService인 빈을 자동 생성
public class GoodsServiceImpl implements GoodsService {
	@Autowired
	private GoodsDAO goodsDAO; // OrderServiceImpl 클래스를 이용해 id가 goodsDAO인 빈을 자동 생성
	
	public Map<String, List<GoodsVO> > listGoods() throws Exception {
		Map<String, List<GoodsVO> > goodsMap = new HashMap<String, List<GoodsVO> >();
		List<GoodsVO> goodsList = goodsDAO.selectGoodsList("bestseller"); // GoodsService의 selectGoodsList() 메서드를 호출
		goodsMap.put("bestseller", goodsList);
		goodsList = goodsDAO.selectGoodsList("newbook");
		goodsMap.put("newbook", goodsList);
		
		goodsList = goodsDAO.selectGoodsList("steadyseller");
		goodsMap.put("steadyseller", goodsList);
		return goodsMap; // newbook, bestseller, steadyseller를 조건으로 각각 도서 정보를 조회해서 Map에 저장한 후 반환
	}
	
	public Map goodsDetail(String _goods_id) throws Exception {
		Map goodsMap = new HashMap();
		GoodsVO goodsVO = goodsDAO.selectGoodsDetail(_goods_id);
		goodsMap.put("goodsVO", goodsVO);
		List<ImageFileVO> imageList = goodsDAO.selectGoodsDetailImage(_goods_id);
		goodsMap.put("imageList", imageList); // 상품 정보와 이미지 정보를 조회한 후 HashMap에 저장
		return goodsMap;
	}
	
	public List<String> keywordSearch(String keyword) throws Exception {
		List<String> list = goodsDAO.selectKeywordSearch(keyword);
		return list;
	}
	
	public List<GoodsVO> searchGoods(String searchWord) throws Exception {
		List goodsList = goodsDAO.selectGoodsBySearchWord(searchWord);
		return goodsList;
	}
	
	
}
