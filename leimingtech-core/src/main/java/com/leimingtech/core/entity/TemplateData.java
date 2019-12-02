package com.leimingtech.core.entity;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;

/**
 * @Author lanjun
 * @Date 2008-5-6
 * @Mail lanjun@zving.com
 */
public class TemplateData {
	protected String FirstFileName; // 第一页名称 如index.shtml

	protected String OtherFileName; // 其他页名称 如index_1.shtml

	protected int PageSize = 20;

	protected int PageIndex = 0;

	protected long Total;

	protected long PageCount;
	/**
	 * 动态分页
	 */
	protected String DynamicPreviousPage;   
	
	protected String DynamicNextPage;
	
	
	protected String DynamicLastPage;
	


	

	public String getDynamicPreviousPage() {
		return DynamicPreviousPage;
	}

	public void setDynamicPreviousPage(String dynamicPreviousPage) {
		DynamicPreviousPage = dynamicPreviousPage;
	}

	public String getDynamicNextPage() {
		return DynamicNextPage;
	}

	public void setDynamicNextPage(String dynamicNextPage) {
		DynamicNextPage = dynamicNextPage;
	}

	public String getDynamicLastPage() {
		return DynamicLastPage;
	}

	public void setDynamicLastPage(String dynamicLastPage) {
		DynamicLastPage = dynamicLastPage;
	}

	public String getPreviousPage() {
		if (PageIndex == 2) {
			return FirstFileName;
		} else if (PageIndex != 0) {
			return OtherFileName.replaceAll("@INDEX", String.valueOf(PageIndex-1));
		} else if (PageIndex == 0) {
			return "#";
		}

		return null;
	}

	public long getPageCount() {
		return PageCount;
	}

	public void setPageCount(long pageCount) {
		PageCount = pageCount;
	}

	public int getPageIndex() {
		return PageIndex;
	}

	public void setPageIndex(int pageIndex) {
		PageIndex = pageIndex;
	}

	public long getTotal() {
		return Total;
	}

	public void setTotal(long total) {
		Total = total;
	}

	public String getNextPage() {
		if (PageIndex != PageCount) {
			return OtherFileName.replaceAll("@INDEX", String.valueOf(PageIndex + 1));
		} else {
			return "#";
		}
	}

	public String getFirstPage() {
		return FirstFileName;
	}

	public String getLastPage() {
		if (PageCount == 1) {
			return FirstFileName;
		} else {
			return OtherFileName.replaceAll("@INDEX", String.valueOf(PageCount));
		}
	}

	public String getFirstFileName() {
		return FirstFileName;
	}

	public void setFirstFileName(String firstFileName) {
		FirstFileName = firstFileName;
	}

	public String getOtherFileName() {
		return OtherFileName;
	}

	public void setOtherFileName(String otherFileName) {
		OtherFileName = otherFileName;
	}

	public int getPageSize() {
		return PageSize;
	}

	public void setPageSize(int pageSize) {
		PageSize = pageSize;
	}

	/*public String getPageBar(int id) {
		StringBuffer sb = new StringBuffer();

		sb.append("<table width='100%' border='0' class='noBorder' align='center'><tr>");
		sb.append("<td height='18' align='center' valign='middle' style='border-width: 0px;color:#525252'>");
		sb.append("共" + Total + "条记录，每页" + PageSize + "条，当前第<span class='fc_ch1'>" + (PageIndex + 1) 
				+ "</span>/<span class='fc_ch1'>" + PageCount + "</span>页&nbsp;&nbsp;&nbsp;&nbsp;");
		if (PageIndex > 0) {
			sb.append("<a href='" + getFirstPage() + "'><span class='fc_ch1'>第一页</span></a>|");
			sb.append("<a href='" + getPreviousPage() + "'><span class='fc_ch1'>上一页</span></a>|");
		} else {
			sb.append("<span class='fc_hui2'>第一页</span>|");
			sb.append("<span class='fc_hui2'>上一页</span>|");
		}
		if (PageIndex + 1 != PageCount && PageCount>0) {
			sb.append("<a href='" + getNextPage() + "'><span class='fc_ch1'>下一页</span></a>|");
			sb.append("<a href='" + getLastPage() + "'><span class='fc_ch1'>最末页</span></a>");
		} else {
			sb.append("<span class='fc_hui2'>下一页</span>|");
			sb.append("<span class='fc_hui2'>最末页</span>");
		}

		sb.append("&nbsp;&nbsp;转到第<input id='_PageBar_Index_"+id+"' type='text' size='4' style='width:30px' ");
		sb.append("style='' onKeyUp=\"value=value.replace(/\\D/g,'')\">页");

		String listNameRule = getFirstFileName().substring(0, getFirstFileName().lastIndexOf("."));
		String ext = getUrlExtension(getFirstFileName());
		sb.append("&nbsp;&nbsp;<input type='button' onclick=\"if(/[^\\d]/.test(document.getElementById('_PageBar_Index_"
				+id+"').value)){alert('错误的页码');$('_PageBar_Index_"+id+"').focus();}else if(document.getElementById('_PageBar_Index_"
				+id+"').value>" + PageCount + "){alert('错误的页码');document.getElementById('_PageBar_Index_"
				+id+"').focus();}else{var PageIndex = (document.getElementById('_PageBar_Index_"
				+id+"').value)>0?document.getElementById('_PageBar_Index_"
				+id+"').value:1;if(PageIndex==1){window.location='"+getFirstFileName()+"'}else{window.location='"+listNameRule+"_'+PageIndex+'"+ext+"';}}\" style='' value='跳转'></td>");
		sb.append("</tr></table>");
		return sb.toString();
	}*/
	
	public String getPageBar(int id) {
		StringBuffer sb = new StringBuffer();

		sb.append("<table width='100%' border='0' class='noBorder' align='center'><tr>");
		
		sb.append("<td height='18' align='center' valign='middle' style='border-width: 0px;color:#525252'>");
		sb.append("<div class='epages' style='padding-left:-6px;'>");
		sb.append("<a title='Total record'>共" + Total + "条</a>&nbsp;&nbsp;");
		
		if (PageIndex > 1) {
			sb.append("<a href='" + getFirstPage() + "'><span class='fc_ch1'>首页</span></a>&nbsp;");
			sb.append("<a href='" + getPreviousPage() + "'><span class='fc_ch1'>上一页</span></a>&nbsp;");
		}
		
		if (PageIndex!= PageCount && PageCount>0) {
			sb.append("<a href='" + getNextPage() + "'><span class='fc_ch1'>下一页</span></a>&nbsp;");
			sb.append("<a href='" + getLastPage() + "'><span class='fc_ch1'>尾页</span></a>&nbsp;");
		} 
		sb.append("每页"+PageSize + "条，第<span class='fc_ch1'>" + (PageIndex) 
				+ "</span>/<span class='fc_ch1'>" + PageCount + "</span>页&nbsp;&nbsp;&nbsp;&nbsp;");
		sb.append("&nbsp;&nbsp;转到第<input id='_PageBar_Index_"+id+"' type='text' size='4' style='width:25px;height:15px;  margin-top: 2px;border:0;border-bottom:1px solid black;background:#fff;text-align: center;' ");
		sb.append("style='' onKeyUp=\"value=value.replace(/\\D/g,'')\">页");

		String listNameRule = getFirstFileName().substring(0, getFirstFileName().lastIndexOf("."));
		String ext = getUrlExtension(getFirstFileName());
		sb.append("&nbsp;&nbsp;<a id='nextPage' href='javascript:void(0);' onclick=\"if(/[^\\d]/.test(document.getElementById('_PageBar_Index_"
				+id+"').value)){alert('错误的页码');$('_PageBar_Index_"+id+"').focus();}else if(document.getElementById('_PageBar_Index_"
				+id+"').value>" + PageCount + "){alert('错误的页码');document.getElementById('_PageBar_Index_"
				+id+"').focus();}else{var PageIndex = (document.getElementById('_PageBar_Index_"
				+id+"').value)>0?document.getElementById('_PageBar_Index_"
				+id+"').value:1;if(PageIndex==1){window.location='"+getFirstFileName()+"'}else{window.location='"+listNameRule+"_'+PageIndex+'"+ext+"';}}\" style='margin-top:-1px;'>跳转</a></div></td>");
		sb.append("</tr></table>");
		return sb.toString();
	}

	@Test
	public void test() {
		TemplateData t=new TemplateData();
		t.setFirstFileName("list.shtml");
		t.setPageSize(4);
		t.setPageCount(4);
		t.setTotal(15);
		t.setPageIndex(1);
		t.setOtherFileName("list_@INDEX.shtml");
		String pagebar=t.getPageBar(0);
		System.out.println(pagebar);
	}
	
	//繁体版分页条
	public String getPageBarBig5(int id) {
		StringBuffer sb = new StringBuffer();

		sb.append("<table width='100%' border='0' class='noBorder' align='center'><tr>");
		sb.append("<td height='18' align='center' valign='middle' style='border-width: 0px;color:#525252'>");
		sb.append("共" + Total + "條記錄，每頁" + PageSize + "條，當前第<span class='fc_ch1'>" + (PageIndex + 1) 
				+ "</span>/<span class='fc_ch1'>" + PageCount + "</span>页&nbsp;&nbsp;&nbsp;&nbsp;");
		if (PageIndex > 0) {
			sb.append("<a href='" + getFirstPage() + "'><span class='fc_ch1'>第一頁</span></a>|");
			sb.append("<a href='" + getPreviousPage() + "'><span class='fc_ch1'>上一頁</span></a>|");
		} else {
			sb.append("<span class='fc_hui2'>第一頁</span>|");
			sb.append("<span class='fc_hui2'>上一頁</span>|");
		}
		if (PageIndex + 1 != PageCount && PageCount>0) {
			sb.append("<a href='" + getNextPage() + "'><span class='fc_ch1'>下一頁</span></a>|");
			sb.append("<a href='" + getLastPage() + "'><span class='fc_ch1'>最末頁</span></a>");
		} else {
			sb.append("<span class='fc_hui2'>下一頁</span>|");
			sb.append("<span class='fc_hui2'>最末頁</span>");
		}

		sb.append("&nbsp;&nbsp;轉到第<input id='_PageBar_Index_"+id+"' type='text' size='4' style='width:30px' ");
		sb.append("style='' onKeyUp=\"value=value.replace(/\\D/g,'')\">頁");

		String listNameRule = getFirstFileName().substring(0, getFirstFileName().lastIndexOf("."));
		String ext = getUrlExtension(getFirstFileName());
		sb.append("&nbsp;&nbsp;<input type='button' onclick=\"if(/[^\\d]/.test(document.getElementById('_PageBar_Index_"
				+id+"').value)){alert('錯誤的頁碼');$('_PageBar_Index_"+id+"').focus();}else if(document.getElementById('_PageBar_Index_"
				+id+"').value>" + PageCount + "){alert('錯誤的頁碼');document.getElementById('_PageBar_Index_"
				+id+"').focus();}else{var PageIndex = (document.getElementById('_PageBar_Index_"
				+id+"').value)>0?document.getElementById('_PageBar_Index_"
				+id+"').value:1;if(PageIndex==1){window.location='"+getFirstFileName()+"'}else{window.location='"+listNameRule+"_'+PageIndex+'"+ext+"';}}\" style='' value='跳轉'></td>");
		sb.append("</tr></table>");
		return sb.toString();
	}
	
	//英文版分页条
	public String getPageBarEN(int id) {
		StringBuffer sb = new StringBuffer();

		sb.append("<table width='100%' border='0' class='noBorder' align='center'><tr>");
		sb.append("<td height='18' align='center' valign='middle' style='border-width: 0px;color:#525252'>");
		sb.append("Total " + Total + " items , " + PageSize + " per page , current:<span class='fc_ch1'>" + (PageIndex + 1) 
				+ "</span>/<span class='fc_ch1'>" + PageCount + "</span>Pages&nbsp;&nbsp;&nbsp;&nbsp;");
		if (PageIndex > 0) {
			sb.append("<a href='" + getFirstPage() + "'><span class='fc_ch1'>Home</span></a>|");
			sb.append("<a href='" + getPreviousPage() + "'><span class='fc_ch1'>Previous</span></a>|");
		} else {
			sb.append("<span class='fc_hui2'>Home</span>|");
			sb.append("<span class='fc_hui2'>Previous</span>|");
		}
		if (PageIndex + 1 != PageCount && PageCount>0) {
			sb.append("<a href='" + getNextPage() + "'><span class='fc_ch1'>Next</span></a>|");
			sb.append("<a href='" + getLastPage() + "'><span class='fc_ch1'>End</span></a>");
		} else {
			sb.append("<span class='fc_hui2'>Next</span>|");
			sb.append("<span class='fc_hui2'>End</span>");
		}

		sb.append("&nbsp;&nbsp;Jump to <input id='_PageBar_Index_"+id+"' type='text' size='4' style='width:30px' ");
		sb.append("style='' onKeyUp=\"value=value.replace(/\\D/g,'')\"> page");

		String listNameRule = getFirstFileName().substring(0, getFirstFileName().lastIndexOf("."));
		String ext = getUrlExtension(getFirstFileName());
		sb.append("&nbsp;&nbsp;<input type='button' onclick=\"if(/[^\\d]/.test(document.getElementById('_PageBar_Index_"
				+id+"').value)){alert('Error Page');$('_PageBar_Index_"+id+"').focus();}else if(document.getElementById('_PageBar_Index_"
				+id+"').value>" + PageCount + "){alert('Error Page');document.getElementById('_PageBar_Index_"
				+id+"').focus();}else{var PageIndex = (document.getElementById('_PageBar_Index_"
				+id+"').value)>0?document.getElementById('_PageBar_Index_"
				+id+"').value:1;if(PageIndex==1){window.location='"+getFirstFileName()+"'}else{window.location='"+listNameRule+"_'+PageIndex+'"+ext+"';}}\" style='' value='jump'></td>");
		sb.append("</tr></table>");
		return sb.toString();
	}
	/**
	 * 动态分页
	 * @param id
	 * @return
	 */
	public String getDynamicPageBar(String catId) {
		StringBuffer sb = new StringBuffer();

		sb.append("<table width='100%' border='0' class='noBorder' align='center'><tr>");
		
		sb.append("<td height='18' align='center' valign='middle' style='border-width: 0px;color:#525252'>");
		sb.append("<div class='epages' style='padding-left:-6px;'>");
		sb.append("<a title='Total record'>共" + Total + "条</a>&nbsp;&nbsp;");
		
		if (PageIndex > 1) {
			sb.append("<a href='" + getFirstPage() + "'><span class='fc_ch1'>首页</span></a>&nbsp;");
			sb.append("<a href='" + DynamicPreviousPage + "'><span class='fc_ch1'>上一页</span></a>&nbsp;");
		}
		
		if (PageIndex!= PageCount && PageCount>0) {
			sb.append("<a href='" + DynamicNextPage + "'><span class='fc_ch1'>下一页</span></a>&nbsp;");
			sb.append("<a href='" + DynamicLastPage + "'><span class='fc_ch1'>尾页</span></a>&nbsp;");
		} 
		sb.append("每页"+PageSize + "条，第<span class='fc_ch1'>" + (PageIndex) 
				+ "</span>/<span class='fc_ch1'>" + PageCount + "</span>页&nbsp;&nbsp;&nbsp;&nbsp;");
		sb.append("&nbsp;&nbsp;转到第<input id='pageBar'  name='pageBar' type='text' size='4' style='width:25px;height:15px;  margin-top: 2px;border:0;border-bottom:1px solid black;background:#fff;text-align: center;' ");
		sb.append("style='' onKeyUp=\"value=value.replace(/\\D/g,'')\">页");

		
		sb.append("&nbsp;&nbsp;<a id='nextPage' href='javascript:void(0);' onclick=\"if(/[^\\d]/.test(document.getElementById('pageBar"
				+"').value)){alert('错误的页码');$('pageBar').focus();}else if(document.getElementById('pageBar"
				+"').value>" + PageCount + "){alert('错误的页码');document.getElementById('pageBar"
				+"').focus();}else{var PageIndex = (document.getElementById('pageBar"
				+"').value)>0?document.getElementById('pageBar"
				+"').value:1;if(PageIndex==1){window.location='"+getFirstFileName()+"'}else{window.location='newsListController.do?newsList&catId="+catId+"&PageIndex='+PageIndex+'';}}\" style='margin-top:-1px;'>跳转</a></div></td>");
		sb.append("</tr></table>");

		return sb.toString();
	}
	
	
	
	/**
	 * 文章分页
	 * @param id
	 * @return
	 */
	public String getPageBreakBar(int id) {
		StringBuffer sb = new StringBuffer();
		if(this.getTotal()>1){
			sb.append("<table width='100%' border='0' class='noBorder' align='center'><tr>");
			sb.append("<td height='18' align='center' valign='middle' style='border-width: 0px;color:#525252'>");
			//sb.append("共 " + Total + " 页 ");
			if (PageIndex > 1) {
				sb.append("<a href='" + getPreviousPage() + "'><span class='fc_ch1'>上一页</span></a>&nbsp;&nbsp;");
			} else {
				sb.append("<span class='fc_hui2'>上一页</span>&nbsp;&nbsp;");
			}
			
			StringBuffer pageList = new StringBuffer();
			for (int i = 0; i < PageCount; i++) {
				String href = null;
				if(i==0){
					href = this.FirstFileName;
				}else{
					href = OtherFileName.replaceAll("@INDEX", String.valueOf(i + 1));
				}
				
				if(i==this.PageIndex-1){
					pageList.append(" "+(i + 1));
				}else{
					pageList.append("  <a href=");
					pageList.append(href);
					pageList.append(">" + (i + 1) + "</a>");
				}
			}
			
		    sb.append(pageList);

			if (PageIndex!= PageCount && PageCount>0) {
				sb.append("&nbsp;&nbsp;<a href='" + getNextPage() + "'><span class='fc_ch1'>下一页</span></a>&nbsp;");
			} else {
				sb.append("&nbsp;&nbsp;<span class='fc_hui2'>下一页</span>&nbsp;");
			}

			sb.append("&nbsp;&nbsp;</td>");
			sb.append("</tr></table>");
		}
		return sb.toString();
	}
	
	
	/**
	 * 文章动态分页
	 * @param id
	 * @return
	 */
	public String getDynamicPageBreakBar(int id) {
		StringBuffer sb = new StringBuffer();
		if(this.getTotal()>1){
			sb.append("<table width='100%' border='0' class='noBorder' align='center'><tr>");
			sb.append("<td height='18' align='center' valign='middle' style='border-width: 0px;color:#525252'>");
			//sb.append("共 " + Total + " 页 ");
			if (PageIndex > 1) {
				sb.append("<a href='" + getDynamicPreviousPage() + "'><span class='fc_ch1'>上一页</span></a>&nbsp;&nbsp;");
			} else {
				sb.append("<span class='fc_hui2'>上一页</span>&nbsp;&nbsp;");
			}
			
			StringBuffer pageList = new StringBuffer();
			for (int i = 0; i < PageCount; i++) {
				String href = null;
				if(i==0){
					href = this.FirstFileName;
				}else{
					href = OtherFileName+(i+1);
				}
				
				if(i==this.PageIndex-1){
					pageList.append(" "+(i + 1));
				}else{
					pageList.append("  <a href=");
					pageList.append(href);
					pageList.append(">" + (i + 1) + "</a>");
				}
			}
			
		    sb.append(pageList);

			if (PageIndex!= PageCount && PageCount>0) {
				sb.append("&nbsp;&nbsp;<a href='" + getDynamicNextPage() + "'><span class='fc_ch1'>下一页</span></a>&nbsp;");
			} else {
				sb.append("&nbsp;&nbsp;<span class='fc_hui2'>下一页</span>&nbsp;");
			}

			sb.append("&nbsp;&nbsp;</td>");
			sb.append("</tr></table>");
		}
		return sb.toString();
	}
	
	public String getUrlExtension(String url) {
		if (StringUtils.isEmpty(url)) {
			return "";
		}
		int index = url.indexOf('?');
		if (index > 0) {
			url = url.substring(0, index);
		}
		int i1 = url.lastIndexOf('/');
		int i2 = url.lastIndexOf('.');
		if (i1 >= i2) {
			return "";
		} else {
			return url.substring(i2).toLowerCase();
		}
	}
}
