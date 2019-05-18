

// chart of search author weights .
var chart_search_author = echarts.init(document.getElementById('chartSearchAuthor'));
				 
	
	chart_search_author.setOption({
	color:['#FFAE8B','#428BCA','#67E0E3','#EEDD78', '#7D96BC', '#9BD4B9', '#E7BCF3', '#96BFFF', '#837BEA', '#F49F42', '#759AA0'],
	 title : {
        
        x:'left',
        textStyle : {
						fontSize: 16,
					    fontWeight: '', 
					    color: '#000000'
					}
    },
    tooltip : {
        trigger: 'item',
        formatter: "{a} <br/>{b} : {c} ({d}%)"
    },
    legend: {
        type: 'scroll',
        orient: 'vertical',
        right: 10,
        top: 20,
        bottom: 20,
        show: false, 
        data:['Jiawei Han', 'Wenfei fan','Jerry Xu Yu','Xindong Wu',  'Shuai Ma', 'Xuelian lin']
  
    },
    series : [
        {
            name: 'influence',
            type: 'pie',
            radius : '70%',
            center: ['45%', '60%'],
            data:[
               
                
            ],
            itemStyle: {
                emphasis: {
                    shadowBlur: 0,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
            }
        }
    ]
})
	
	$.get('/search/chartauthor').done(function(data) {
		if (! (!data && typeof(data)!="undefined" && data!=0)){
			
	chart_search_author.setOption({
			
			title : {
        	text: 'Author',
		 	},
			legend: {
				data: data.authorName
			},
			series: [{
				data:(function(){
				var res = [];
				var len =  data.authorWeight.length; 
				for (var i = 0, size = len;i<size; i++ ) {
					res.push({
	                	name: data.authorName[i],
	                	value: data.authorWeight[i]
	                });	
				}
                return res; 
				
				})() 
				
			}]
		
		});

		}
		
	});
			