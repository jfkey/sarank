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
});