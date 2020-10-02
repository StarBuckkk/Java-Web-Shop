package sec03.FilterAPI;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

/**
 * Servlet Filter implementation class EncoderFilter
 */
//@WebFilter("/*")
public class EncoderFilter implements Filter { // 필터 구현
	ServletContext context;

	public void init(FilterConfig fConfig) throws ServletException {
		System.out.println("utf-8 인코딩............");
		context = fConfig.getServletContext();

	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) // doFilter() 안에서 실제 필터 기능을 구현
			throws ServletException, IOException {
		System.out.println("doFilter 호출");
		request.setCharacterEncoding("utf-8"); // 여기서 인코딩 설정작업
		String context = ((HttpServletRequest) request).getContextPath(); // 웹 어플리케이션의 컨텍스트 이름을 가져옴
		String pathinfo = ((HttpServletRequest) request).getRequestURI(); // 웹 브라우저에서 요청한 요청 URI를 가져옴
		String realPath = request.getRealPath(pathinfo); // 요청 URI의 실제 경로를 가져옴
		String mesg = " Context  정보:" + context + "\n URI 정보 : " + pathinfo + "\n 물리적 경로:  " + realPath;
		System.out.println(mesg);

		long begin = System.currentTimeMillis();
		chain.doFilter(request, response); // 응답필터 / 다음 필터로 넘기는 작업 수행
		long end = System.currentTimeMillis();
		System.out.println("작업 시간:" + (end - begin) + "ms");

	}

	/**
	 * 
	 * 
	 * /**
	 * 
	 * @see Filter#destroy()
	 */
	public void destroy() {
		System.out.println("destroy 호출");
	}

}
