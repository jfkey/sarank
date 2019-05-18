

// chart of search author weights .
var chart_search_conf = echarts.init(document.getElementById('chartSearchConf'));
				 
	// 7D96BC 67E0E3
	chart_search_conf.setOption({
	color:['#428BCA','#7D96BC','#FFAE8B','#67E0E3','#9BD4B9', '#EEDD78','#E7BCF3', '#F49F42', '#759AA0', '#96BFFF', '#837BEA'],
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
        data:['VLDB', 'SIGMOD','ICDE','WSDM',  'CIKM', 'ICDM']
  
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
                    shadowColor: 'rgba(0, 0, 0, 0.1)'
                }
            }
        }
    ]
})
	
$.get('/search/chartconf').done(function(data) {
if (! (!data && typeof(data)!="undefined" && data!=0)){
		chart_search_conf.setOption({
		 title : {
        	text: 'Conference',
		 },
		legend: {
			data: data.confName
		},
		series: [{
			data:(function(){
				var res = [];
				var len =  data.confWeight.length; 
				for (var i = 0, size = len;i<size; i++ ) {
					res.push({
	                	name: data.confName[i],
	                	value: data.confWeight[i]
	                });	
				}
                return res; 
				
			})() 
		}]
	});

	}
	
});

 