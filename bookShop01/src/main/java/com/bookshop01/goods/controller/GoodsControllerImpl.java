package com.bookshop01.goods.controller;

import java.util.ArrayList;
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

import com.bookshop01.common.base.BaseController;
import com.bookshop01.goods.service.GoodsService;
import com.bookshop01.goods.vo.GoodsVO;

import net.sf.json.JSONObject;

@Controller("goodsController")
@RequestMapping(value = "/goods")
public class GoodsControllerImpl extends BaseController implements GoodsController { // BaseController�� ��ӹް� GoodsController�� ����
	@Autowired
	private GoodsService goodsService;
	
	/*
	 * 1. ���ǿ� ����� �ֱ� �� ��ǰ ����� ������
	 * 2. ��ǰ ��Ͽ� ����� ��ǰ ������ �� �� �̸��̰� ��� �� ��ǰ�� ��ǰ ��Ͽ� �ִ��� üũ
	 * 3. ������ ��ǰ ��Ͽ� �߰�
	 * 4. �ٽ� ��ǰ ����� ���ǿ� ����
	 * 5. ȭ�鿡 ��ǰ�� ǥ���ϴ� quickMenu.jsp
	 * */
	
	@RequestMapping(value = "/goodsDetail.do", method = RequestMethod.GET)
	public ModelAndView goodsDetail(@RequestParam("goods_id") String goods_id, // ��ȸ�� ��ǰ ��ȣ�� ���޹���
			                       HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = (String)request.getAttribute("viewName");
		HttpSession session = request.getSession();
		Map goodsMap = goodsService.goodsDetail(goods_id); // ��ǰ ������ ��ȸ�� �� Map���� ��ȯ
		ModelAndView mav = new ModelAndView(viewName);
		mav.addObject("goodsMap", goodsMap);
		GoodsVO goodsVO = (GoodsVO)goodsMap.get("goodsVO"); // ��ȸ�� ��ǰ ������ ���� �޴��� ǥ���ϱ� ���� ����
		addGoodsInQuick(goods_id, goodsVO,session);
		
		return mav;
	}
	
	@RequestMapping(value = "/keywordSearch.do", method = RequestMethod.GET, produces = "application/text; charset=utf8") // �������� �����ϴ� JSON �������� �ѱ� ���ڵ��� ����
	public @ResponseBody String  keywordSearch(@RequestParam("keyword") String keyword, // JSON �����͸� �������� ���, �˻��� Ű���带 ������
			                                  HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		//System.out.println(keyword);
		
		if(keyword == null || keyword.equals("") )
			return null ;
	
		keyword = keyword.toUpperCase();
	    List<String> keywordList = goodsService.keywordSearch(keyword); // ������ Ű���尡 ���Ե� ��ǰ ������ ��ȸ
	    
	 // ���� �ϼ��� JSONObject ����(��ü)
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("keyword", keywordList); // ��ȸ�� �����͸� JSON�� ����
		 		
	    String jsonInfo = jsonObject.toString(); // JSON�� ���ڿ��� ��ȯ�� �� �������� ���
	   // System.out.println(jsonInfo);
	    
	    return jsonInfo ;
	}
	
	@RequestMapping(value = "/searchGoods.do", method = RequestMethod.GET)
	public ModelAndView searchGoods(@RequestParam("searchWord") String searchWord,
			                       HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = (String)request.getAttribute("viewName");
		List<GoodsVO> goodsList = goodsService.searchGoods(searchWord); // �˻�â���� ������ �ܾ ���Ե� ��ǰ ������ ��ȸ
		ModelAndView mav = new ModelAndView(viewName);
		mav.addObject("goodsList", goodsList);
		
		return mav;
		
	}
	
	private void addGoodsInQuick(String goods_id,GoodsVO goodsVO,HttpSession session) {
		boolean already_existed = false;
		List<GoodsVO> quickGoodsList; //�ֱ� �� ��ǰ ���� ArrayList
		quickGoodsList = (ArrayList<GoodsVO> )session.getAttribute("quickGoodsList"); // ���ǿ� ����� �ֱ� �� ��ǰ ����� ������
		
		if(quickGoodsList != null) { // �ֱ� �� ��ǰ�� �ִ� ���
			if(quickGoodsList.size() < 4) { // �ֱ� �� ��ǰ ����Ʈ�� ��ǰ������ ���� ������ ���
				
				for(int i = 0; i < quickGoodsList.size(); i++) {
					GoodsVO _goodsBean =(GoodsVO)quickGoodsList.get(i);
					
					if(goods_id.equals(_goodsBean.getGoods_id() ) ) { // // ��ǰ ����� ������ �̹� �����ϴ� ��ǰ���� ��
						already_existed = true; // �̹� ������ ��� already_existed�� true
						break;
					} 
				}
				if(already_existed == false) { // already_existed�� false�̸� ��ǰ ������ ��Ͽ� ����
					quickGoodsList.add(goodsVO);
				}
			}
			
		}else {
			quickGoodsList = new ArrayList<GoodsVO>(); // �ֱ� �� ��ǰ ����� ���ǿ� ����
			quickGoodsList.add(goodsVO); // �ֱ� �� ��ǰ ��Ͽ� ����� ��ǰ ������ ���ǿ� ����
			
		}
		session.setAttribute("quickGoodsList", quickGoodsList);
		session.setAttribute("quickGoodsListNum", quickGoodsList.size() );
	}
}
