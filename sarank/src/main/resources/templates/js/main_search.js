
				var input_map = new Map(); 
				var isSingle = true;
				var search_param ="";
				$("#btn_advanced").click(function(e) {
					e.preventDefault();
					$("#input_multi").toggle(); 
					$("#input_single").toggle();
					isSingle = !isSingle;
					return false;
				});
				
				
				function searchPapers(input_map){
					// 判断是否初始化
					if ($("#page").pagination()) {
						$("#page").pagination('remote');
					} else {
						$("#page").pagination({
							pageSize: 10,
							showJump: true, 
							showInfo: true,
							prevBtnText: '&laquo;',
							nextBtnText: '&raquo;',
							remote: {
								url: '/search',	
								pageParams:function() {
									return {
										k:input_map.get("k"), n: input_map.get("n"), y: input_map.get("y"), i:input_map.get("i")
									};
								} ,
								success: function(data) {
									showItems(data);
								},
								totalName: 'total'
							}
						});
					}
				}
				
				function showItems(data) {
					var t = $("#paper_entity").empty();
					t.css("height", "auto");
					if(!data) return;
					var keywords = data.keywords;
					data = data.papers;
					data.forEach(function(singlePaper) {
						var paperStr = "<div class='paper-item'>";
						var tmp, title, i;
						title = singlePaper.Title;
						var regEx ; 
						for (i = 0; i < keywords.length; i ++) {
							tmp = keywords[i];
							regEx = new RegExp(tmp, "i");
							title = title.replace(regEx, "<font color='red'>"+tmp+"</font>");
						}
						
						paperStr += "<div ><a style='font-size:x-large;' href='/paper?paid=" + singlePaper.PaperID + "'>" +
								title + "</a>" +
							"<div  class='paper-author'>";
						var authorIDs = singlePaper.AuthorID;
						var authorNames = singlePaper.AuthorName;
						var infosContent = "";
						for(var i = 0; i < authorIDs.length; i ++) {
							// infosContent += '<li>' + infosArr[i] + ':' + infosArr[i+1] +  '</li>'
							infosContent += "<a href='/paper/paid='" + authorIDs[i] + ">" + authorNames[i] + "</a>"
							infosContent += ",&nbsp;&nbsp;";
						}
						infosContent += "<br />";
						paperStr += infosContent;
						paperStr += "<a href='/venue/venid='" + singlePaper.VenueID + ">" + (singlePaper.VenueName == null ? "&nbsp;" : singlePaper.VenueName ) + "(" + singlePaper.PublishYear + ")</a><p></p> </div></div>";
						$(paperStr).appendTo(t);
					});
					$('html, body').animate({scrollTop: 0},500);
					
					
				}
				
				
				
				$("#btn_search").click(function (e) {
//					e.preventDefault();  
					
					search_param="";
					if (isSingle){
						input_map.set("k",$("#search_input").val() );
						search_param = "k=" + encodeURIComponent($("#search_input").val());
					} 
					if (!isSingle){
						input_map.set("k",$("#title").val() );
						input_map.set("n",$("#name").val() );
						input_map.set("y",$("#year").val() );
						search_param = "k=" + encodeURIComponent($("#title").val()) +"&n=" + encodeURIComponent($("#name").val()) +"&y=" + encodeURIComponent($("#year").val());
					}
					
					// searchPapers("/search?"+search_param);
					searchPapers(input_map);
					return false;
				});
				
				
				$("#page").on("pageClicked", function (event, data) {
					// ajax 分页查询
    				var tmp = search_param + "&i=" + $("#page").pagination('getPageIndex');
    				// alert("page index : " + search_param);
					// $.get("/search?"+search_param + "&i=" + $("#page").pagination('getPageIndex'), function (data) { showItems(data)}, "json");
					input_map.set("i", $("#page").pagination('getPageIndex'));
					// searchPapers("/search?"+search_param + "&i=" + $("#page").pagination('getPageIndex'));
					searchPapers(input_map);
					
				})	
				
				$("#page").on("jumpClicked", function (event, data) {
					//跳转按钮点击事件
				})
	

