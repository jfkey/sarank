// citations change with year. 
var chart_cite_year = echarts.init(document.getElementById('chartCiteYear'));
				 
				
			// 显示标题，图例和空的坐标轴
				
				chart_cite_year.setOption({
			    tooltip: {
			    	trigger:'axis',
			    	axisPointer : {            
			            type : 'shadow'        
			        }
			    },
			   title: { 
					show:true,
					text: "Citations Change Over Time ",
					x:"center", 
					textStyle : {
						fontSize: 14,
					    fontWeight: '',
					    color: '#333'
					}
				},
			    xAxis: {
			        type: 'category',
			        data: []
			    },
			    yAxis: {
			    	type: 'value',
			    	show:true,
			    	name:'citations' 
			    },
			    series: [ 
			    		{
			            name: 'citations',
			            type: 'bar',
			            color :['#3685BC'],
			            barWidth:'70%',
			            stack: 'all papers',
			            label: {
			                normal: {
			                  	show:true,
			                    position: 'insideRight'
			                }
			            },
			            data: [20,12,33,22]
			       }
			       ]
 
			});
			
			// myChart.showLoading();
			$.get('/author/citePerYear').done(function (data) {
			    chart_cite_year.hideLoading();
			    chart_cite_year.setOption({
			    	xAxis: {
			            data: data.years
			        },
			        series: [{
			            // 根据名字对应到相应的系列
			            name: 'citations', 
			            data: data.citations
			        }
			        ]
			    });
			});