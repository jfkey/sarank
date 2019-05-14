

// chart of search author weights .
var chart_search_conf = echarts.init(document.getElementById('chartSearchConf'));
				 
	// 7D96BC 67E0E3
	chart_search_conf.setOption({
	color:['#428BCA','#7D96BC','#FFAE8B','#67E0E3','#9BD4B9', '#EEDD78'],
	 title : {
        text: 'Conference',
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
                {value:2, name:'SIGMOD'},
                {value:3, name:'VLDB'},
                {value:1.5, name:'ICDE'},
                {value:1.3, name:'WSDM'}, 
                {value:1.1, name:'CIKM'},
                {value:0.7, name:'ICDM'}
                
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
	chart_search_conf.setOption({
		legend: {
			data: data.confName
		},
		series: [{
			
			data: data.confWeight
		}]
	});
});