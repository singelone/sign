package com.ly.a.web.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.springframework.data.domain.Page;

public class NewsPageTag extends SimpleTagSupport{

	private Page<?> page;
	private Integer maxIndexPages = 5;
	
	public Page<?> getPage() {
		return page;
	}

	public void setPage(Page<?> page) {
		this.page = page;
	}
	
	public void setMaxIndexPages(Integer maxIndexPages) {
		this.maxIndexPages = maxIndexPages;
	}
	
	@Override
	public void doTag() throws JspException, IOException {
		// TODO Auto-generated method stub
		
		JspWriter out = this.getJspContext().getOut(); 
		try {
			if(page!=null){
				out.write(build(page,maxIndexPages));
			}
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String build(Page<?> page, Integer maxIndexPages) {
		// TODO Auto-generated method stub
		StringBuffer sb=new StringBuffer();
		sb.append("<a href='"+(page.isFirst()?("javascript:void(0);"):("javascript:toXpage("+((page.getNumber()+1)-1)+")"))+"'><</a>");
		sb.append("<a href='"+((page.getNumber()+1)==1?("javascript:void(0)"):("javascript:toXpage(1)"))+"' class='"+((page.getNumber()+1)==1?"active":"")+"'>1</a>");
		int beginNum = (page.getNumber()+1)-1<=2?2:((page.getNumber()+1)==page.getTotalPages()?(page.getNumber()+1)-2:(page.getNumber()+1)-1);
		int endNum = beginNum ==2?(maxIndexPages>page.getTotalPages()-1?page.getTotalPages()-1:maxIndexPages):((page.getNumber()+1)+3>page.getTotalPages()-1?page.getTotalPages()-1:(page.getNumber()+1)+3);
		if(beginNum>2){
			sb.append("<a href='javascript:void(0);'>...</a>");
		}
		for(int i=beginNum;i<=endNum;i++){
			if(i==((page.getNumber()+1))){
				sb.append("<a href='javascript:void(0);' class='active'>"+i+"</a>");
			}else{
				sb.append("<a href='javascript:toXpage("+(i)+")'  class='' >"+i+"</a>");
			}
		}
        if(endNum<page.getTotalPages()-1){
			sb.append("<a href='javascript:void(0);'>...</a>");
		}
		if(page.getTotalPages()>1){
			sb.append("<a href='"+((page.getNumber()+1)==page.getTotalPages()?("javascript:void(0);"):("javascript:toXpage("+(page.getTotalPages())+")"))+"' class='"+((page.getNumber()+1)==page.getTotalPages()?"active":"")+"'>"+page.getTotalPages()+"</a>");
		}
		sb.append("<a href='"+(page.isLast()?("javascript:void(0)"):(page.getTotalPages()<=1?("javascript:void(0);"):("javascript:toXpage("+((page.getNumber()+1)+1)+")")))+"' >></a>");
		return sb.toString();
	}
	
}
