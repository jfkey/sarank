// fos change with year. 
var chart_fos_year = echarts.init(document.getElementById('chartFosYear'));
chart_fos_year.setOption({
//	tooltip: {
//		trigger: 'axis',
//		axisPointer: {
//			type: 'line',
//			lineStyle: {
//				color: 'rgba(0,0,0,0.2)',
//				width: 1,
//				type: 'solid'
//			}
//		}
//	},

	legend: {
		data: ["Machine learning", "Statistics", "Data mining", "Artificial intelligence", "Algorithm"]
	},
	 title: { 
					show:true,
					text: "Research Interest ",
					x:"center", 
					textStyle : {
						fontSize: 14,
					    fontWeight: '',
					    color: '#333'
					}
				},
	singleAxis: {
		top: 50,
		bottom: 50,
		axisTick: {},
		axisLabel: {},
		type: 'value',
		axisPointer: {
			animation: true,
			label: {
				show: false
			}
		},
		splitLine: {
			show: false,
			lineStyle: {
				type: 'dashed',
				opacity: 0.2
			}
		},
		min: function(value) {
			return value.min;
		},
		max: function(value) {
			return value.max;
		}
	},
	series: [{
		type: 'themeRiver',
		name: 'fos year',
		itemStyle: {
			emphasis: {
				shadowBlur: 20,
				shadowColor: 'rgba(0, 0, 0, 0.8)'
			}
		},
		data: [
		
		]
	}]
});
 

	$.get('/author/fosYear').done(function (data) {
			    chart_fos_year.setOption({
			    	legend: {
			            data: data.fos
			        },
			        series: [{
			            // 根据名字对应到相应的系列
			            data: data.fosYear
			        }
			        ]
			    });
			});
