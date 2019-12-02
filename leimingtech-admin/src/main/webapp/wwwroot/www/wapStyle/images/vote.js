function clickInput(id){
	document.getElementById(id+'_Button').checked = true;
}

function checkVote(id){
	var f = document.getElementById('voteForm_'+id);
	var dts = f.getElementsByTagName('dt');
	var arrs = [];
	for(var i=0;i<dts.length;i++){
		var subjectID = dts[i].getAttribute("id");
		var maxitems = dts[i].getAttribute("maxitems");
		var minitems = dts[i].getAttribute("minitems");
		arrs.push(subjectID + "|" + maxitems + "|" + minitems);
	}
	var str='';
	var position = '';
	var err = false;
	for(var i=0;i<arrs.length;i++){
		var substr = arrs[i];
		var array = substr.split("|"); 
		var sid = array[0];
		var maxitems = array[1];
		var minitems = array[2];
		var eles = document.getElementsByName('Subject_'+sid);
		var flag = false;
		if (eles!=null && eles.length==1) {
			if (eles[0].getAttribute("type")=="radio" || eles[0].getAttribute("type")=="checkbox") {
				if (eles[0].checked) {
					flag = true;
				}
			} else if (eles[0].value && eles[0].value.trim()) {
				continue;
			}
		} else {
			var selectnum = 0;
			for(var j=0;j<eles.length;j++){
				if(eles[j].checked){
					selectnum = selectnum + 1;
					flag = true;
					break;
				}
			}
			if(maxitems >= selectnum && selectnum >= minitems){
				flag = true;
			}
		}
		if(!flag){
			err = true;
			if(document.getElementById(sid)){
				if(document.getElementById(sid).innerText){
					str+='\n'+document.getElementById(sid).innerText;
				}else{
					str+='\n'+document.getElementById(sid).textContent;
				}
				if(!position){
					position = sid;
				}
			}
		}
	}
	if(err){
		var url = window.location+'';
		alert('您还有以下调查没有填写：'+str);
		window.location = url.substring(0,url.lastIndexOf('#'))+'#'+position;
		return false;
	}
	return true;
}