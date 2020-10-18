package com.bookshop01.admin.goods.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.bookshop01.goods.vo.GoodsVO;
import com.bookshop01.goods.vo.ImageFileVO;
import com.bookshop01.order.vo.OrderVO;

@Repository("adminGoodsDAO")
public class AdminGoodsDAOImpl  implements AdminGoodsDAO {
	@Autowired
	private SqlSession sqlSession; // XML 설정 파일에서 생성한 ID가 sqlSession인 빈을 자동 주입
	
	@Override
	public int insertNewGoods(Map newGoodsMap) throws DataAccessException {
		sqlSession.insert("mapper.admin.goods.insertNewGoods", newGoodsMap); // 매퍼 파일인 admin.goods.xml에서 지정한 id인 insertNewGoods인 SQL문을 요청
		return Integer.parseInt((String)newGoodsMap.get("goods_id"));
	}
	
	@Override
	public void insertGoodsImageFile(List fileList)  throws DataAccessException {
		for(int i = 0; i < fileList.size(); i++){
			ImageFileVO imageFileVO = (ImageFileVO)fileList.get(i);
			sqlSession.insert("mapper.admin.goods.insertGoodsImageFile", imageFileVO); // id가 insertGoodsImageFile인 SQL문을 요청
		}
	}
		
	@Override
	public List<GoodsVO>selectNewGoodsList(Map condMap) throws DataAccessException {
		ArrayList<GoodsVO> goodsList = (ArrayList)sqlSession.selectList("mapper.admin.goods.selectNewGoodsList", condMap); // id가 selectNewGoodsList인 SQL문을 요청
		return goodsList;
	}
	
	@Override
	public GoodsVO selectGoodsDetail(int goods_id) throws DataAccessException {
		GoodsVO goodsBean = new GoodsVO();
		goodsBean = (GoodsVO)sqlSession.selectOne("mapper.admin.goods.selectGoodsDetail", goods_id); // id가 selectGoodsDetail인 SQL문을 요청
		return goodsBean;
	}
	
	@Override
	public List selectGoodsImageFileList(int goods_id) throws DataAccessException {
		List imageList = new ArrayList();
		imageList = (List)sqlSession.selectList("mapper.admin.goods.selectGoodsImageFileList", goods_id); // id가 selectGoodsImageFileList인 SQL문을 요청
		return imageList;
	}
	
	@Override
	public void updateGoodsInfo(Map goodsMap) throws DataAccessException {
		sqlSession.update("mapper.admin.goods.updateGoodsInfo", goodsMap); // id가 updateGoodsInfo인 SQL문을 요청
	}
	
	@Override
	public void deleteGoodsImage(int image_id) throws DataAccessException {
		sqlSession.delete("mapper.admin.goods.deleteGoodsImage", image_id); // id가 deleteGoodsImage인 SQL문을 요청
	}
	
	@Override
	public void deleteGoodsImage(List fileList) throws DataAccessException {
		int image_id;
		
		for(int i = 0; i < fileList.size(); i++) {
			ImageFileVO bean = (ImageFileVO) fileList.get(i);
			image_id = bean.getImage_id();
			sqlSession.delete("mapper.admin.goods.deleteGoodsImage", image_id);	// id가 deleteGoodsImage인 SQL문을 요청
		}
	}

	@Override
	public List<OrderVO> selectOrderGoodsList(Map condMap) throws DataAccessException {
		List<OrderVO>  orderGoodsList = (ArrayList)sqlSession.selectList("mapper.admin.selectOrderGoodsList", condMap); // id가 selectOrderGoodsList인 SQL문을 요청
		return orderGoodsList;
	}	
	
	@Override
	public void updateOrderGoods(Map orderMap) throws DataAccessException {
		sqlSession.update("mapper.admin.goods.updateOrderGoods", orderMap); // id가 updateOrderGoods인 SQL문을 요청
		
	}

	@Override
	public void updateGoodsImage(List<ImageFileVO> imageFileList) throws DataAccessException {
		
		for(int i = 0; i < imageFileList.size(); i++) {
			ImageFileVO imageFileVO = imageFileList.get(i);
			sqlSession.update("mapper.admin.goods.updateGoodsImage", imageFileVO); // id가 updateGoodsImage인 SQL문을 요청
		}
		
	}





	

}
