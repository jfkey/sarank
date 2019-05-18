

// chart of search author weights .
var ven_pie_author = echarts.init(document.getElementById('venuePieAuthor'));
				 
	
	ven_pie_author.setOption({
	color:['#FFAE8B','#428BCA','#67E0E3','#EEDD78', '#7D96BC', '#9BD4B9', '#E7BCF3', '#96BFFF', '#837BEA', '#F49F42', '#759AA0'],
	 title : {
        text: 'Author',
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
        show: false,
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
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
            }
        }
    ]
})
	
	$.get('/venue/pieauthor').done(function(data) {
		ven_pie_author.setOption({
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
			