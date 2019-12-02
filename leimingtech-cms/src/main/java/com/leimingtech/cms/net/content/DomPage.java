/*
 * Copyright (C) 2014 hu
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */

package com.leimingtech.cms.net.content;

import org.jsoup.nodes.Document;

/**
 * 网页简易html模型
 * 
 * @company 雷铭智信
 * @author 谢进伟
 * @DateTime 2015-7-30 上午1:30:32
 */
public class DomPage {
	
	public DomPage (Document doc ){
		this.doc = doc;
	}
	
	private Document doc = null;
	
	public String getURL() {
		return doc.baseUri();
	}
	
	public Document getDoc() {
		return doc;
	}
	
	public void setDoc(Document doc) {
		this.doc = doc;
	}
}
