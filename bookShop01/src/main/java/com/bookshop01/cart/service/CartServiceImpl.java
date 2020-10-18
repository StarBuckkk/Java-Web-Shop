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

@Service("cartService") // CartServiceImpl 클래스를 이용해 id가 cartService인 빈을 자동 생성
@Transactional(propagation = Propagation.REQUIRED)
public class CartServiceImpl  implements CartService{
	@Autowired
	private CartDAO cartDAO; // @Autowired를 이용해 id가 cartDAO인 빈을 자동 주입
	
	public Map<String ,List> myCartList(CartVO cartVO) throws Exception {
		Map<String, List> cartMap = new HashMap<String, List>();
		List<CartVO> myCartList = cartDAO.selectCartList(cartVO); // 장바구니 페이지에 표시할 장바구니 정보를 조회
		
		if(myCartList.size() ==0 ) { // 카트에 저장된 상품이없는 경우
			return null;
		}
		
		List<GoodsVO> myGoodsList = cartDAO.selectGoodsList(myCartList); // 장바구니 페이지에 표시할 상품 정보를 조회
		cartMap.put("myCartList", myCartList);
		cartMap.put("myGoodsList", myGoodsList); 
		
		return cartMap; // 장바구니 정보와 상품 정보를 cartMap에 저장하여 반환
	}
	
	public boolean findCartGoods(CartVO cartVO) throws Exception {
		 return cartDAO.selectCountInCart(cartVO); // 테이블에 추가하기 전에 동일한 상품 번호의 개수를 조회
		
	}	
	public void addGoodsInCart(CartVO cartVO) throws Exception {
		cartDAO.insertGoodsInCart(cartVO); // 장바구니에 추가
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
