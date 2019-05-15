var myChart = echarts.init(document.getElementById('chartAllPapers'));
				 
				
			// 显示标题，图例和空的坐标轴
				myChart.setOption({
			    tooltip: {
			    	trigger:'axis',
			    	axisPointer : {            
			            type : 'shadow'        
			        }
			    },
			    title: { 
					show:true,
					text: "Number of Papers ",
					x:"center", 
					textStyle : {
						fontSize: 14,
					    fontWeight: '',
					    color: '#333'
					}
				},
//			    legend: {
//			        data:['not-first author', 'first author']
//			    },
			    xAxis: {
			        type: 'category',
			        data: ['all','conference','journal', 'unknown']
			    },
			    yAxis: {
			    	type: 'value',
			    	show:true,
			    	name:'Counts' 
			    },
			    series: [ 
			    		{
			            name: 'not-first author',
			            type: 'bar',
			            color :['#516b91'],
			            barWidth:'40%',
			            stack: 'all papers',
			            label: {
			                normal: {
			                    position: 'insideRight'
			                }
			            },
			            data: [20,10,33,13]
			       },  {
			            name: 'first author',
			            type: 'bar', 
			            color :['#59c4e6'],
			            barWidth:'40%',
			            stack: 'all papers',
			            label: {
			                normal: {
			                    position: 'insideRight'
			                }
			            },
			            data: [20,30,3,10]
			        }
			       ]
			        
			});
			$.get('/author/firstAndNot').done(function (data) {
			    myChart.setOption({
       
			        series: [{
			            name: 'not-first author',
			            data: data.notFirst
			        },{
			        	name: 'first author', 
			        	data: data.first
			        }
			        ]
			    });
			});
		