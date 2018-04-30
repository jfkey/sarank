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
		 data:[ 'Machine learning', 'World Wide Web', 'Machine', 'learning', 'asdf'
          ]
	},
	 title: { 
					show:true,
					text: "",
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
			
			
			 ["2009", "2", "Machine learning"],
			 ["2010", "0", "Machine learning"]
			,["2011", "8", "Machine learning"]
			,["2012", "3", "Machine learning"]
			,["2013", "0", "Machine learning"]
			,["2014", "2", "Machine learning"]
			
			,["2011", "5", "World Wide Web"]
			,["2012", "1", "World Wide Web"]
			,["2013", "2", "World Wide Web"]
			,["2014", "5", "World Wide Web"]
			
			,["2009", "2", "Machine"]
			,["2010", "0", "Machine"]
			,["2011", "8", "Machine"]
			,["2012", "3", "Machine"]
			,["2013", "0", "Machine"]
			,["2014", "2", "Machine"]
			
			 ,["2009", "2", "learning"]
			 ,["2010", "0", "learning"]
			,["2011", "8", "learning"]
			,["2012", "3", "learning"]
			,["2013", "0", "learning"]
			,["2014", "2", "learning"]
			
			 ,["2009", "2", "asdf"]
			 ,["2010", "0", "asdf"]
			,["2011", "8", "asdf"]
			,["2012", "3", "asdf"]
			,["2013", "0", "asdf"]
			,["2014", "2", "asdf"]
			
		], 
		color:['#d87c7c', '#919e8b', '#d7ab82', '#6e7074', '#61a0a8', ]
  	  
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
