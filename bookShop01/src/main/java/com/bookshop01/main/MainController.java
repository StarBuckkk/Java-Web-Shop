package com.bookshop01.main;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.bookshop01.common.base.BaseController;
import com.bookshop01.goods.service.GoodsService;
import com.bookshop01.goods.vo.GoodsVO;

@Controller("mainController") // @Controller를 이용해 MainController 클래스에 대해 id가 mainController인 빈을 자동 생성
@EnableAspectJAutoProxy
public class MainController extends BaseController {
	@Autowired
	private GoodsService goodsService; // @Autowired를 이용해 id가 goodsService인 빈을 자동 주입

	@RequestMapping(value = "/main/main.do", method = {RequestMethod.POST,RequestMethod.GET} )
	public ModelAndView main(HttpServletRequest request, HttpServletResponse response) throws Exception { // bookshop01/main/main.do로 요청 시 메인 페이지를 보여 줌
		HttpSession session;
		ModelAndView mav = new ModelAndView();
		String viewName = (String)request.getAttribute("viewName");
		mav.setViewName(viewName);
		
		session = request.getSession();
		session.setAttribute("side_menu", "user"); // 속성, 즉 side_menu의 값에 따라 화면 왼쪽에 표시되는 메뉴 항목을 다르게 함
		Map<String, List<GoodsVO> > goodsMap = goodsService.listGoods(); // 베스트셀러, 신간, 스테디셀러 정보를 조회해 Map에 저장
		mav.addObject("goodsMap", goodsMap); // 메인 페이지로 상품 정보를 전달
		return mav;
	}
}
