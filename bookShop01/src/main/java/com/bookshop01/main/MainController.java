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

@Controller("mainController") // @Controller�� �̿��� MainController Ŭ������ ���� id�� mainController�� ���� �ڵ� ����
@EnableAspectJAutoProxy
public class MainController extends BaseController {
	@Autowired
	private GoodsService goodsService; // @Autowired�� �̿��� id�� goodsService�� ���� �ڵ� ����

	@RequestMapping(value = "/main/main.do", method = {RequestMethod.POST,RequestMethod.GET} )
	public ModelAndView main(HttpServletRequest request, HttpServletResponse response) throws Exception { // bookshop01/main/main.do�� ��û �� ���� �������� ���� ��
		HttpSession session;
		ModelAndView mav = new ModelAndView();
		String viewName = (String)request.getAttribute("viewName");
		mav.setViewName(viewName);
		
		session = request.getSession();
		session.setAttribute("side_menu", "user"); // �Ӽ�, �� side_menu�� ���� ���� ȭ�� ���ʿ� ǥ�õǴ� �޴� �׸��� �ٸ��� ��
		Map<String, List<GoodsVO> > goodsMap = goodsService.listGoods(); // ����Ʈ����, �Ű�, ���׵𼿷� ������ ��ȸ�� Map�� ����
		mav.addObject("goodsMap", goodsMap); // ���� �������� ��ǰ ������ ����
		return mav;
	}
}
