

// chart of search author weights .
var chart_search_author = echarts.init(document.getElementById('chartSearchConf'));
				 
	// 7D96BC 67E0E3
	chart_search_author.setOption({
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
        data:['Jiawei Han', 'Wenfei fan','Jerry Xu Yu','Xindong Wu',  'Shuai Ma', 'Xuelian lin']
  
    },
    series : [
        {
            name: 'influence',
            type: 'pie',
            radius : '70%',
            center: ['45%', '60%'],
            data:[
                {value:3.3, name:'Jiawei Han'},
                {value:2.5, name:'Wenfei Fan'},
                {value:2.0, name:'Jerry Xu Yu'},
                {value:1.3, name:'Xindong Wu'}, 
                {value:1.1, name:'Shuai Ma'},
                {value:0.4, name:'Xuelian lin'}
                
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
	chart_fos_year.setOption({
		legend: {
			data: data.confName
		},
		series: [{
			
			data: data.confWeight
		}]
	});
});