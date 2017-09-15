package cn.itcast.lucene.search.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

/**
 * 全局异常处理器
 * <p>Title: GlobalExceptionResolver</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.cn</p> 
 * @version 1.0
 */
public class GlobalExceptionResolver implements HandlerExceptionResolver {

	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception exception) {
		//判断异常的种类
		String msg = null;
		if (exception instanceof MyException) {
			MyException custExp = (MyException) exception;
			msg = custExp.getExpMessage();
		} else {
			//如果时候自定义异常，取错误消息
			//如果时候运行时异常，取错误的堆栈。
			exception.printStackTrace();
			StringWriter s = new StringWriter();
			PrintWriter printWriter = new PrintWriter(s);
			exception.printStackTrace(printWriter);
			msg = s.toString();
		}
		//写日志、发短信、发邮件
		//...
		//返回一个错误页面，显示错误消息
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("msg", msg);
		modelAndView.setViewName("error");
		return modelAndView;
	}

}
