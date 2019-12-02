var _pageNum = 10;
/**
 *@param(_nPageCount):总页数
 *@param(_nCurrIndex):当前页,0是第一页
 *@param(_sPageName):页面文件名前缀
 *@param(_sPageExt):页面文件名后缀
 *@param(_pageNum):显示的最大页数
 */
//pageControl(11, 0, "t2916570", "shtml", __pageNum);
function pageControl(_nPageCount, _nCurrIndex, _sPageName,_sPageExt,_pageNum){
	//alert(111);
	//alert(_nPageCount);
	//alert(_nCurrIndex);
	//alert(_sPageName);
	//alert(_sPageExt);
	//alert(_pageNum);
	if(_nPageCount == null || _nPageCount<=1){
        return;
    }
	if(_pageNum>_nPageCount){
		_pageNum=_nPageCount;
	}
	if(_pageNum<1){
		_pageNum=1;
	}
	var midIndex=Math.ceil(_pageNum/2.0);
	var nCurrIndex = _nCurrIndex || 0;
	var startPageIndex=(_nPageCount - (_nCurrIndex-1-midIndex) >_pageNum)? ((_nCurrIndex-midIndex+1<0)? 0 :((_nCurrIndex-midIndex+1>=_nPageCount-_pageNum)?_nPageCount-_pageNum : _nCurrIndex-midIndex+1)) :  (_nPageCount - _pageNum);
	var endPageIndex=startPageIndex + _pageNum;

    if(nCurrIndex==0){
        //document.write("<li><a class='pageUP' href=\"javascript:void(0);\" target=\"_self\" ></a></li>");
        document.write("<li><a href=\"javascript:void(0);\">上一页</a></li>");
    }else if(nCurrIndex==1){
    	document.write("<li><a href=\""+_sPageName+"\" target=\"_self\">上一页</a></li>");
       // document.write("<li><a class='pageUP' href=\""+_sPageName+"."+_sPageExt+"\" target=\"_self\" ></a></li>");
    }else{
    	document.write("<li><a href=\""+_sPageName+(nCurrIndex-1)+"\" target=\"_self\" >上一页</a></li>");
        //document.write("<li><a class='pageUP' href=\""+_sPageName+"_"+(nCurrIndex-1)+"."+_sPageExt+"\" target=\"_self\" ></a></li>");
    }
	
	for(var i=startPageIndex;i<endPageIndex;i++){
		if(nCurrIndex == i){
			if(_pageNum > 1)
			//document.write("<li><font>"+formatNUM(i+1) + "</font></li>");
			document.write("<li><a href=\"javascript:void(0)\" class=\"now\" target=\"_self\" >"+formatNUM(i+1)+"</a></li>");
		}else{
			if(i==0){
				document.write("<li><a href=\""+_sPageName+"\" target=\"_self\" >"+formatNUM(i+1)+"</a></li>");
			}else{
				document.write("<li><a href=\""+_sPageName+ i +"\" target=\"_self\" >"+formatNUM(i+1)+"</a></li>");
			}
			
		}
	}

	if(nCurrIndex < _nPageCount-1){
		//document.write("<li><a class='pageDOWN' href=\""+_sPageName+"_"+(nCurrIndex+1)+"."+_sPageExt+"\" target=\"_self\" ></a></li>");
		document.write("<li><a href=\""+_sPageName+(nCurrIndex+1)+ "\" target=\"_self\">下一页</a></li>"); 
    }else{
        //document.write("<li><a class='pageDOWN' href=\"javascript:void(0);\" target=\"_self\" ></a></li>");
        document.write("<li><a href=\"javascript:void(0)\" >下一页</a></li>");
    }
     function formatNUM(_v){
        if(_v<10){
            return "0"+_v;
        }else{
            return _v;
        }
     }
}

//use
//pageControl(${PAGE_COUNT}, ${PAGE_INDEX}, "${PAGE_NAME}","${PAGE_EXT}",10);

//<li><a href="javascript:void(0)">上一页</a></li>
//<li><a class="now" href="javascript:void(0)">1</a></li>
//<li><a href="javascript:void(0)">2</a></li>
//<li><a href="javascript:void(0)">3</a></li>
//<li><a href="javascript:void(0)">4</a></li>
//<li><a href="javascript:void(0)">5</a></li>
//<li><a href="javascript:void(0)">6</a></li>
//<li><a href="javascript:void(0)">...38</a></li>
//<li><a href="javascript:void(0)">下一页</a></li> 