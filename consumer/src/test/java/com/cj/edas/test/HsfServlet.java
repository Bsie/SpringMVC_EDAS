package com.cj.edas.test;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;


public class HsfServlet extends HttpServlet {
@Override
protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(req.getServletContext());
	SampleService sampleService = (SampleService) ctx.getBean("sampleService");
	resp.getWriter().println(Long.toString(System.currentTimeMillis()));
	}
}