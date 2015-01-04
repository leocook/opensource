package cn.wulinweb.hust.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.wulinweb.hust.bean.LUrlCondition;
import cn.wulinweb.hust.service.impl.LongUrlService;
import cn.wulinweb.hust.util.ControllerUtil;

public class LongUrlController extends HttpServlet {
	private static LongUrlService service = new LongUrlService();
	private static final Logger LOGGER = LoggerFactory.getLogger(LongUrlController.class);

	//localhost:8080/HushDemo/LongUrlController?param={"type":0,"url":"http://weibo.com","date":"2014/12/30 14:10:32","token":"adasdasdasdasdad","ip":"156.25.11.223"}
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String param = request.getParameter("param");
		LUrlCondition condition = null;
		String sUrl = null;
		int code = 0;
				
		try {
			condition = ControllerUtil.getCondition(param);
					
			sUrl = service.getShortUrl(condition);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			code = 1;
		}
		
		String json = ControllerUtil.getJson(code,sUrl);
		
		response.getWriter().write(json);
	}

	

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
