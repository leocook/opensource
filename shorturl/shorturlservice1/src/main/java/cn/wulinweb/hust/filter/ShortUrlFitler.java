package cn.wulinweb.hust.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.wulinweb.hust.service.ShortUrlService;
import cn.wulinweb.hust.service.impl.ShortUrlServiceImpl;

public class ShortUrlFitler implements Filter {
	private static final Logger LOGGER = LoggerFactory.getLogger(ShortUrlFitler.class);
	
	private String longUrlController;
	private static ShortUrlService service = new ShortUrlServiceImpl();

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		
		String path = request.getRequestURL().toString();
		if(path.endsWith(longUrlController)){
			LOGGER.debug("接收了一次长链接转短链接的请求");
			chain.doFilter(request, response);
			return;
		}
		LOGGER.debug("接收了一次用短链接跳转到长链接的请求");
		path = path.substring((path.lastIndexOf("/")+1));
		
		path = service.getLongUrlByShort(path);
		
		response.sendRedirect(path);
		return;
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		longUrlController = arg0.getInitParameter("longUrlController");
	}
	
}
