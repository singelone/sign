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
public class PageSizeTag extends SimpleTagSupport {

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
		out.println("<label>页数");
		out.println("<select id=\"pageSelect\" class=\"form-control input-sm input-xsmall input-inline\"  onchange=\"pageChange(this.options[this.options.selectedIndex].value)\">");
		out.println("<option value=\"10\">10</option>");
		out.println("<option value=\"20\">20</option>");
		out.println("<option value=\"50\">50</option>");
		out.println("<option value=\"100\">100</option>");
		out.println("</select></label>");
		out.println("<script>$(\"#pageSelect option[value='"+page.getSize()+"']\").attr(\"selected\",\"selected\");");
		out.println("function pageChange(no) {window.location.href = \""+path+link+"&pageSize=\"+no}</script>");
				
	}
}
