package com.bookshop01.cart.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bookshop01.cart.dao.CartDAO;
import com.bookshop01.cart.vo.CartVO;
import com.bookshop01.goods.vo.GoodsVO;

@Service("cartService") // CartServiceImpl Ŭ������ �̿��� id�� cartService�� ���� �ڵ� ����
@Transactional(propagation = Propagation.REQUIRED)
public class CartServiceImpl  implements CartService{
	@Autowired
	private CartDAO cartDAO; // @Autowired�� �̿��� id�� cartDAO�� ���� �ڵ� ����
	
	public Map<String ,List> myCartList(CartVO cartVO) throws Exception {
		Map<String, List> cartMap = new HashMap<String, List>();
		List<CartVO> myCartList = cartDAO.selectCartList(cartVO); // ��ٱ��� �������� ǥ���� ��ٱ��� ������ ��ȸ
		
		if(myCartList.size() ==0 ) { // īƮ�� ����� ��ǰ�̾��� ���
			return null;
		}
		
		List<GoodsVO> myGoodsList = cartDAO.selectGoodsList(myCartList); // ��ٱ��� �������� ǥ���� ��ǰ ������ ��ȸ
		cartMap.put("myCartList", myCartList);
		cartMap.put("myGoodsList", myGoodsList); 
		
		return cartMap; // ��ٱ��� ������ ��ǰ ������ cartMap�� �����Ͽ� ��ȯ
	}
	
	public boolean findCartGoods(CartVO cartVO) throws Exception {
		 return cartDAO.selectCountInCart(cartVO); // ���̺� �߰��ϱ� ���� ������ ��ǰ ��ȣ�� ������ ��ȸ
		
	}	
	public void addGoodsInCart(CartVO cartVO) throws Exception {
		cartDAO.insertGoodsInCart(cartVO); // ��ٱ��Ͽ� �߰�
	}
	
	public boolean modifyCartQty(CartVO cartVO) throws Exception {
		boolean result = true;
		cartDAO.updateCartGoodsQty(cartVO);
		return result;
	}
	public void removeCartGoods(int cart_id) throws Exception {
		cartDAO.deleteCartGoods(cart_id);
	}
	
}
