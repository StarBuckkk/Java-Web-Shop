package com.bookshop01.cart.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bookshop01.cart.service.CartService;
import com.bookshop01.cart.vo.CartVO;
import com.bookshop01.common.base.BaseController;
import com.bookshop01.goods.vo.GoodsVO;
import com.bookshop01.member.vo.MemberVO;

/*
 * 상품을 장바구니에 추가하려면 우선 브라우저에서 전송된 상품 번호를 이용해 그 상품이 
 * 장바구니 테이블에 이미 추가된 상품인지 확인해야 함
 * 장바구니에 없으면 상품 번호를 장바구니 테이블에 추가
 * */

@Controller("cartController")
@RequestMapping(value = "/cart")
public class CartControllerImpl extends BaseController implements CartController { // 상속받고 구현
	@Autowired 
	private CartService cartService; // @Autowired를 이용해 id가 cartService인 빈을 자동 주입
	@Autowired
	private CartVO cartVO;
	@Autowired
	private MemberVO memberVO; // @Autowired를 이용해 id가 memberVO인 빈을 자동 주입
	
	/*
	 * 조회한 장바구니 목록과 상품 정보 목록을 맵에 저장,
	 * 장바구니 목록을 표시하는 페이지에서 상품을 주문할 경우에 대비해
	 * 상품 정보를 미리 세션에 바인딩
	 * */
	
	@RequestMapping(value = "/myCartList.do", method = RequestMethod.GET)
	public ModelAndView myCartMain(HttpServletRequest request, HttpServletResponse response)  throws Exception {
		String viewName = (String)request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView(viewName);
		HttpSession session = request.getSession();
		MemberVO memberVO = (MemberVO)session.getAttribute("memberInfo");
		String member_id = memberVO.getMember_id();
		cartVO.setMember_id(member_id);
		Map<String ,List> cartMap = cartService.myCartList(cartVO); // 장바구니 페이지에 표시할 상품 정보를 조회
		session.setAttribute("cartMap", cartMap); // 장바구니 목록 화면에서 상품 주문 시 사용하기 위해서 장바구니 목록을 세션에 저장한다.
		//mav.addObject("cartMap", cartMap);
		
		return mav;
	}
	@RequestMapping(value = "/addGoodsInCart.do", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	public  @ResponseBody String addGoodsInCart(@RequestParam("goods_id") int goods_id, // 전송된 상품 번호를 받음
			                    HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		memberVO = (MemberVO)session.getAttribute("memberInfo");
		String member_id = memberVO.getMember_id();
		
		cartVO.setMember_id(member_id);
		//카트 등록전에 이미 등록된 제품인지 판별한다.
		cartVO.setGoods_id(goods_id);
		cartVO.setMember_id(member_id);
		boolean isAreadyExisted = cartService.findCartGoods(cartVO); // 상품 번호가 장바구니 테이블에 있는지 조회
		System.out.println("isAreadyExisted:" + isAreadyExisted);
		
		if(isAreadyExisted == true) { // 상품 번호가 이미 장바구니 테이블에 있으면
			return "already_existed"; // 이미 추가되었다는 메세지를 부라우저에 전송하고
		}else { // 없으면 장바구니 테이블에 추가
			cartService.addGoodsInCart(cartVO);
			return "add_success";
		}
	}
	
	@RequestMapping(value = "/modifyCartQty.do", method = RequestMethod.POST)
	public @ResponseBody String  modifyCartQty(@RequestParam("goods_id") int goods_id,
			                                   @RequestParam("cart_goods_qty") int cart_goods_qty,
			                                    HttpServletRequest request, HttpServletResponse response) throws Exception{
		HttpSession session = request.getSession();
		memberVO = (MemberVO)session.getAttribute("memberInfo");
		String member_id = memberVO.getMember_id();
		cartVO.setGoods_id(goods_id);
		cartVO.setMember_id(member_id);
		cartVO.setCart_goods_qty(cart_goods_qty);
		boolean result = cartService.modifyCartQty(cartVO);
		
		if(result == true) {
			return "modify_success";
		   
		}else{
			return "modify_failed";	
		}
		
	}
	
	@RequestMapping(value = "/removeCartGoods.do", method = RequestMethod.POST)
	public ModelAndView removeCartGoods(@RequestParam("cart_id") int cart_id,
			                          HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		cartService.removeCartGoods(cart_id);
		mav.setViewName("redirect:/cart/myCartList.do");
		
		return mav;
	}
}
