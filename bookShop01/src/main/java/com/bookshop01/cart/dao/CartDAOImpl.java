package com.bookshop01.cart.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.bookshop01.cart.vo.CartVO;
import com.bookshop01.goods.vo.GoodsVO;

@Repository("cartDAO")
public class CartDAOImpl  implements  CartDAO{
	@Autowired
	private SqlSession sqlSession; // XML 설정 파일에서 생성한 ID가 sqlSession인 빈을 자동 주입
	
	public List<CartVO> selectCartList(CartVO cartVO) throws DataAccessException { 
		List<CartVO> cartList = (List)sqlSession.selectList("mapper.cart.selectCartList", cartVO); // 매퍼 파일인 cart.xml에서 지정한 id인 selectCartList인 SQL문을 요청
		return cartList;
	}

	public List<GoodsVO> selectGoodsList(List<CartVO> cartList) throws DataAccessException { 
		
		List<GoodsVO> myGoodsList;
		myGoodsList = sqlSession.selectList("mapper.cart.selectGoodsList", cartList); // 매퍼 파일인 cart.xml에서 지정한 id인 selectGoodsList인 SQL문을 요청
		return myGoodsList;
	}
	public boolean selectCountInCart(CartVO cartVO) throws DataAccessException {
		String  result = sqlSession.selectOne("mapper.cart.selectCountInCart", cartVO); // id가 selectCountInCart인 SQL문을 요청
		return Boolean.parseBoolean(result); // 이미 장바구니에 추가된 상품인지 조회
	}

	public void insertGoodsInCart(CartVO cartVO) throws DataAccessException {
		int cart_id = selectMaxCartId();
		cartVO.setCart_id(cart_id); // 장바구니에 추가
		sqlSession.insert("mapper.cart.insertGoodsInCart", cartVO); // id가 insertGoodsInCart인 SQL문을 요청
	}
	
	public void updateCartGoodsQty(CartVO cartVO) throws DataAccessException {
		sqlSession.insert("mapper.cart.updateCartGoodsQty", cartVO); // id가 updateCartGoodsQty인 SQL문을 요청
	}
	
	public void deleteCartGoods(int cart_id) throws DataAccessException {
		sqlSession.delete("mapper.cart.deleteCartGoods", cart_id); // id가 deleteCartGoods인 SQL문을 요청
	}

	private int selectMaxCartId() throws DataAccessException {
		int cart_id = sqlSession.selectOne("mapper.cart.selectMaxCartId"); // id가 selectMaxCartId인 SQL문을 요청
		return cart_id;
	}

}
