package com.ly.a.web.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.springframework.data.domain.Page;

/**
 * desc：分页标签
 * ref ：
 * user：刘咏
 * date：2016/4/27
 * time：11:56
 */
public class PageTag extends SimpleTagSupport {

	private Page<?> page;
	private String link;

	public Page<?> getPage() {
		return page;
	}

	public void setPage(Page<?> page) {
		this.page = page;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	@Override
	public void doTag() throws JspException, IOException {

		String path = ((PageContext) getJspContext()).getServletContext()
				.getContextPath();

		JspWriter out = getJspContext().getOut();

		out.println("<ul class=\"pagination\" style=\"margin:4px\">");
		// 跳到首页
		if (page.isFirst()) {
			out.println("<li class=\"disabled\"><a href=\"#\"><i class=\"fa fa-step-backward\"></i></a></li>");
		} else {
			out.println("<li><a href=\"" + path + link + 0
					+ "\"><i class=\"fa fa-step-backward\"></i></a></li>");
		}

		// 上一页
		if (page.hasPrevious()) {
			out.println("<li><a href=\"" + path + link + (page.getNumber() - 1)
					+ "\"><i class=\"fa fa-backward\"></i></a></li>");
		} else {
			out.println("<li class=\"disabled\"><a href=\"#\"><i class=\"fa fa-backward\"></i></a></li>");
		}

		// 当前页，总页数，总条数
		out.println("<li class=\"disabled\"><a href=\"#\">"
				+ (page.getNumber() + 1) + "/" + page.getTotalPages() + "页("
				+ page.getTotalElements() + "条)</a></li>");

		// 下一页
		if (page.hasNext()) {
			out.println("<li><a href=\"" + path + link + (page.getNumber() + 1)
					+ "\"><i class=\"fa fa-forward\"></i></a></li>");
		} else {
			out.println("<li class=\"disabled\"><a href=\"#\"><i class=\"fa fa-forward\"></i></a></li>");
		}

		// 跳到末页
		if (page.isLast()) {
			out.println("<li class=\"disabled\"><a href=\"#\"><i class=\"fa fa-step-forward\"></i></a></li>");
		} else {
			out.println("<li><a href=\"" + path + link
					+ (page.getTotalPages() - 1)
					+ "\"><i class=\"fa fa-step-forward\"></i></a></li>");
		}

		out.println("</ul>");
	}
}
