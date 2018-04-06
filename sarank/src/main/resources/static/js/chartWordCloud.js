 var wc = echarts.init(document.getElementById('chartWordCloud'));
		
		$.ajax({
			url:"/author/getAvatar",
			success:function(keywords){
				showAvatar(keywords);
		}});
		
		function showAvatar(keywords) {
//			  keywords = {
//              "minOpacity": 50,
//              "maxOpacity": 54,
//              "prevIcon": 12,
//              "children": 21,
//              "shape": 98,
//              "nextIcon": 12,
//              "showNextBtn": 17,
//              "stopIcon": 21,
//              "visibleMin": 83,
//              "visual": 97,
//              "colorSaturation": 56,
//              "colorAlpha": 66,
//              "emptyItem": 10,
//              "inactiveO": 14,
//              
//                        "she": 98,
//              "nIcon": 12,
//              "howNn": 17,
//              "stIon": 21,
//              "vsMin": 83,
//              "vual": 97,
//              "colation": 56,
//              "coorApha": 66,
//              "emptem": 10,
//              "incveO": 14,
//           	
//           	"Nn": 17,
//              "son": 21,
//              "vsin": 83,
//              "al": 97,
//              "colaion": 56, 
//              "coApha": 66,
//              "eptem": 10,
//              "icveO": 14,
//           	
//            };
			var data = [];
            for (var name in keywords) {
                data.push({
                    name: name,
                    value: keywords[name]
                })
            }

            var maskImage = new Image();

            var option = {
                series: [ {
                    type: 'wordCloud',
                    sizeRange: [12, 16],
                    rotationRange: [-90, 90],
                    rotationStep: 45,
                    gridSize: 0 ,
                    width:'80%',
                    height:'80%',
//                  shape: 'circle',
                    maskImage: maskImage,
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

            maskImage.onload = function () {
                option.series[0].maskImage
                wc.setOption(option);
            }

               maskImage.src = 'img/logo-avatar.png';

            window.onresize = function () {
                wc.resize();
            }
		}
		
//          var keywords = {
//          
//            "minOpacity": 50,
//            "maxOpacity": 54,
//            "prevIcon": 12,
//            "children": 21,
//            "shape": 98,
//            "nextIcon": 12,
//            "showNextBtn": 17,
//            "stopIcon": 21,
//            "visibleMin": 83,
//            "visual": 97,
//            "colorSaturation": 56,
//            "colorAlpha": 66,
//            "emptyItem": 10,
//            "inactiveO": 14,
//         
//          };

          