 var wc = echarts.init(document.getElementById('paperWordCloud'));
		
		
		$.ajax({
			url:"/paper/getwordcloud",
			success:function(keywords){
				showPaperFos(keywords);
		}});
		
		function showPaperFos(keywords) {
			
			var data = [];
            for (var name in keywords) {
                data.push({
                    name: name,
                    value: keywords[name]
                })
            }
			for (var name in keywords) {
                data.push({
                    name: name,
                    value: keywords[name]
                })
            }
			
            var maskImage = new Image();

            var option = {
            	 title: {
      				text: 'Fields of Study',
        			link: '#'
    			},
                series: [ {
                	 
                    type: 'wordCloud',
                    sizeRange: [14, 18],
                    rotationRange: [-90, 90],
                    rotationStep: 45,
                    gridSize: 0 ,
                    width:'80%',
                    height:'80%',
                    shape: 'circle',
//                  maskImage: maskImage,
                    drawOutOfBound: false,
                    textStyle: {
                        normal: {
                            color: function () {
                                return 'rgb(' + [
                                    Math.round(Math.random() * 160),
                                    Math.round(Math.random() * 160),
                                    Math.round(Math.random() * 160)
                                ].join(',') + ')';
                            }
                        },
                        emphasis: {
                            color: 'red'
                        }
                    },
                    data: data.sort(function (a, b) {
                        return b.value  - a.value;
                    })
                } ]
            };

                wc.setOption(option);


            window.onresize = function () {
                wc.resize();
            }
		}
		
		
