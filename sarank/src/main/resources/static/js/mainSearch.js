$("#btn_advanced").click(function(e) {
	e.preventDefault();
	
		$("#input_multi").toggle();
		$("#input_single").toggle();
		
	if ($("#search_input").attr("name") == "keywords"){
		$("#search_input").attr("name", "");
		$("#title").attr("name", "keywords");
	
		return false;
		
	}else if ($("#search_input").attr("name") == ""){
		$("#search_input").attr("name", "keywords");
		$("#title").attr("name", "");
	
		return false;
	}
	
});

$(document).ready(function() {
	$('#select_start option:first').attr('selected',true);
	$('#select_end option:last').attr('selected',true);
	num = 4;
	
	i = $("#ul_ath").children().length; 
	if (  i > num ){
		for (j=num; j<i; j++) {
			$("#ul_ath").children().eq(j).css("display", "none");
		} 
	}
	
	i = $("#ul_aff").children().length; 
	if (  i > num ){
		for (j=num; j<i; j++) {
			$("#ul_aff").children().eq(j).css("display", "none");
		} 
	}
	i = $("#ul_jou").children().length; 
	if (  i > num ){
		for (j=num; j<i; j++) {
			$("#ul_jou").children().eq(j).css("display", "none");
		} 
	}
	i = $("#ul_con").children().length; 
	if (  i > num ){
		for (j= num; j<i; j++) {
			$("#ul_con").children().eq(j).css("display", "none");
		} 
	}
	
// 
          
//    	var content1 = $("#search_input").text().trim();
//    	var content2 = $("#title").text().trim();
//	    if(content1 != "") {
//	    	for(i = 0; i < $(".div-paper-item-title").length; i++) {
//	    		title = $(".div-paper-item-title").eq(i).text();
//	    		var keywords = content1.split(" ");
//	    		for(j = 0; j < keywords.length; j++) {
//	    			title = title.replace(new RegExp(keywords[j], "i"), "<font color='red'>" + keywords[j] + "</font>");
//	    		}
//	    		$(".div-paper-item-title").eq(i).html(title);
//	    	}
//	    }
//	    
//	    if(content2 != "") {
//	    	for(i = 0; i < $(".div-paper-item-title").length; i++) {
//	    		title = $(".div-paper-item-title").eq(i).text();
//	    		var keywords = content2.split(" ");
//	    		for(j = 0; j < keywords.length; j++) {
//	    			title = title.replace(new RegExp(keywords[j], "i"), "<font color='red'>" + keywords[j] + "</font>");
//	    		}
//	    		$(".div-paper-item-title").eq(i).html(title);
//	    	}
//	    }
	    
});
 
function showMore(type){
	num = 4;
	i = $("#ul_"+type ).children().length; 
	if (  i > num ){
		for (j=num; j<i; j++) {
			$("#ul_"+type).children().eq(j).css("display", "");
		} 
	}
	
	return false;
}

function changeRT(type) {
	i = $("#ul_rank_type").children().length;
	for (j = 0; j < i; j ++ ){
		// $("#ul_rank_type").css("background-color", "red"); 
		var li = $("#ul_rank_type").children().eq(j).children().eq(0).css("color",  "#222222");

//		$("#ul_rank_type").children().eq(j).css();
			
	}
	$("#ul_rank_type").children().eq(type-1).children().eq(0).css("color", "#337AB7")
	
	return false;
}
