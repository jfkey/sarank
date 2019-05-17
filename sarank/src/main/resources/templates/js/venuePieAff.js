

// chart of search author weights .
var venue_pie_aff = echarts.init(document.getElementById('venuePieAff'));
				 
	venue_pie_aff.setOption({
	color:['#428BCA','#7D96BC','#FFAE8B','#67E0E3','#9BD4B9', '#EEDD78','#E7BCF3',  '#F49F42', '#759AA0' , '#96BFFF', '#837BEA'],
	 title : {
        text: 'Affiliation',
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
        show: false,
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
                {value:1.5, name:'ICDE'},
                {value:1.3, name:'WSDM'}, 
                {value:1.1, name:'CIKM'},
                {value:0.7, name:'ICDM'},
                {value:2, name:'SIGMOD'},
                {value:3, name:'VLDB'}
                
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

	
	$.get('/venue/pieaff').done(function(data) {
		venue_pie_aff.setOption({
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
	});
			