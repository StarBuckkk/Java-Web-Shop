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
	private SqlSession sqlSession; // XML ���� ���Ͽ��� ������ ID�� sqlSession�� ���� �ڵ� ����
	
	public List<CartVO> selectCartList(CartVO cartVO) throws DataAccessException { 
		List<CartVO> cartList = (List)sqlSession.selectList("mapper.cart.selectCartList", cartVO); // ���� ������ cart.xml���� ������ id�� selectCartList�� SQL���� ��û
		return cartList;
	}

	public List<GoodsVO> selectGoodsList(List<CartVO> cartList) throws DataAccessException { 
		
		List<GoodsVO> myGoodsList;
		myGoodsList = sqlSession.selectList("mapper.cart.selectGoodsList", cartList); // ���� ������ cart.xml���� ������ id�� selectGoodsList�� SQL���� ��û
		return myGoodsList;
	}
	public boolean selectCountInCart(CartVO cartVO) throws DataAccessException {
		String  result = sqlSession.selectOne("mapper.cart.selectCountInCart", cartVO); // id�� selectCountInCart�� SQL���� ��û
		return Boolean.parseBoolean(result); // �̹� ��ٱ��Ͽ� �߰��� ��ǰ���� ��ȸ
	}

	public void insertGoodsInCart(CartVO cartVO) throws DataAccessException {
		int cart_id = selectMaxCartId();
		cartVO.setCart_id(cart_id); // ��ٱ��Ͽ� �߰�
		sqlSession.insert("mapper.cart.insertGoodsInCart", cartVO); // id�� insertGoodsInCart�� SQL���� ��û
	}
	
	public void updateCartGoodsQty(CartVO cartVO) throws DataAccessException {
		sqlSession.insert("mapper.cart.updateCartGoodsQty", cartVO); // id�� updateCartGoodsQty�� SQL���� ��û
	}
	
	public void deleteCartGoods(int cart_id) throws DataAccessException {
		sqlSession.delete("mapper.cart.deleteCartGoods", cart_id); // id�� deleteCartGoods�� SQL���� ��û
	}

	private int selectMaxCartId() throws DataAccessException {
		int cart_id = sqlSession.selectOne("mapper.cart.selectMaxCartId"); // id�� selectMaxCartId�� SQL���� ��û
		return cart_id;
	}

}
