

// citations change with year.
var ct = echarts.init(document.getElementById('paperCitationTrend'));
				 
				
			// 显示标题，图例和空的坐标轴
				
ct.setOption({
	color:['#428BCA','#7D96BC','#FFAE8B','#67E0E3','#9BD4B9', '#EEDD78','#E7BCF3', '#96BFFF', '#837BEA'], 
	title: { 
					show:true,
					text: "Citation Trend",
					x:"left", 
					textStyle : {
						fontSize: 14,
					    fontWeight: '',
					    color: '#333'
					}
				},
	 xAxis: {
        type: 'category',
        boundaryGap: false,
        data: ['2000', '2001', '2002', '2003', '2005', '2006', '2007']
    },
    yAxis: {
        type: 'value'
    },
    series: [{
        data: [8, 9, 9, 4, 12, 10, 5],
        type: 'line',
        smooth: true,
        areaStyle: {}
    }]  
	
			  
});
			
			// myChart.showLoading();
			$.get('/paper/ct').done(function (data) {
			    ct.hideLoading();
			    ct.setOption({
			    	xAxis: {
			            data: data.years
			        },
			        series: [{
			           data: data.citations
			        }
			        ]
			    });
			});