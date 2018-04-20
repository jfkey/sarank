$("#btn_advanced").click(function(e) {
	e.preventDefault();
	
		$("#input_multi").toggle();
		$("#input_single").toggle();
		
	if ($("#search_input").attr("name") == "k"){
		$("#search_input").attr("name", "");
		$("#title").attr("name", "k");
		$("#author").attr("name", "a");
		$("#year").attr("name", "y");
		return false;
		
	}else if ($("#search_input").attr("name") == ""){
		$("#search_input").attr("name", "k");
		$("#title").attr("name", "");
		$("#author").attr("name", "");
		$("#year").attr("name", "");
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

