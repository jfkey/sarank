 var wc2 = echarts.init(document.getElementById('chartWordCloud'));

            var keywords = {
              "visualMap": 22199,
              "continuous": 10288,
              "contoller": 620,
              "series": 274470,
       
              "borderColorSaturation": 10,
              "handleIcon": 2,
              "handleStyle": 6,
              "borderType": 1,
              "constantSpeed": 1,
              "polyline": 2,
              "blendMode": 1,
              "dataBackground": 1,
              "textAlign": 1,
              "textBaseline": 1,
              "brush": 3
            };

            var data = [];
            for (var name in keywords) {
                data.push({
                    name: name,
                    value: Math.sqrt(keywords[name])
                })
            }

         

            var option = {
                series: [ {
                    type: 'wordCloud',
                    sizeRange: [10, 100],
                    rotationRange: [-90, 90],
                    rotationStep: 45,
                    gridSize: 2,
                    shape: 'pentagon',
                    
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

            
            wc2.setOption(option);
            
            window.onresize = function () {
                wc2.resize();
            }