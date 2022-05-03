<%@ page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<!-- 引入echarts类库和Jquery类库 -->
<script type="text/javascript" src="js/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="js/echarts.js"></script>
</head>
<body>
	<div id="main" style="width:1000px;height:800px"></div>
	<script type="text/javascript">
	   var myChart = echarts.init(document.getElementById('main'));
	    var categories = [];
// 	    for (var i = 0; i < 2; i++) {
// 	        categories[i] = {
// 	            name: '类目' + i
// 	        };
// 	    }
	categories[0]={name:'机床'};
	categories[1]={name:'工序'};
	    option = {
	        // 图的标题
	        title: {
	            text: 'NC*3机床加工工序'
	        },
	        // 提示框的配置
	        tooltip: {
	            formatter: function (x) {
	                return x.data.des;
	            }
	        },
	        // 工具箱
	        toolbox: {
	            // 显示工具箱
	            show: true,
	            feature: {
	                mark: {
	                    show: true
	                },
	                // 还原
	                restore: {
	                    show: true
	                },
	                // 保存为图片
	                saveAsImage: {
	                    show: true
	                }
	            }
	        },
	        legend: [{
	            // selectedMode: 'single',
	            data: categories.map(function (a) {
	                return a.name;
	            })
	        }],
	        series: [{
	            type: 'graph', // 类型:关系图
	            layout: 'force', //图的布局，类型为力导图
	            symbolSize: 40, // 调整节点的大小
	            fontSize:5,
	            roam: true, // 是否开启鼠标缩放和平移漫游。默认不开启。如果只想要开启缩放或者平移,可以设置成 'scale' 或者 'move'。设置成 true 为都开启
	            edgeSymbol: ['circle', 'arrow'],
	            edgeSymbolSize: [2, 10],
	            edgeLabel: {
	                normal: {
	                    textStyle: {
	                        fontSize: 20
	                    }
	                }
	            },
	            force: {
	                repulsion: 2500,
	                edgeLength: [10, 50]
	            },
	            draggable: true,
	            lineStyle: {
	                normal: {
	                    width: 2,
	                    color: '#4b565b',
	                }
	            },
	            edgeLabel: {
	                normal: {
	                    show: true,
	                    formatter: function (x) {
	                        return x.data.name;
	                    }
	                }
	            },
	            label: {
	                normal: {
	                    show: true,
	                    textStyle: {}
	                }
	            },
	 
	            // 数据
	            data: [{
	                name: 'NC3*10',
	                des: '机床',
	                symbolSize: 70,
	                category: 0,
	            }, {
	                name: '小端顶面',
	                des: '工序1',
	                symbolSize: 50,
	                category: 1,
	            }, {
	                name: '铣连杆体油槽',
	                des: '工序2',
	                symbolSize: 50,
	                category: 1,
	            }, {
	                name: '加工大小端面和R50圆弧',
	                des: '工序3',
	                symbolSize: 50,
	                category: 1,
	            }, {
	                name: '铣大小端面扁式平面',
	                des: '工序4',
	                symbolSize: 50,
	                category: 1,
	            }],
	            links: [{
	                source: 'NC3*10',
	                target: '小端顶面',
	                name: '加工工序',
	                des: '要加工的工序1'
	            }, {
	                source: 'NC3*10',
	                target: '铣连杆体油槽',
	                name: '加工工序',
	                des: '要加工的工序2'
	            }, {
	                source: 'NC3*10',
	                target: '加工大小端面和R50圆弧',
	                name: '加工工序',
	                des: '要加工的工序3'
	            }, {
	                source: 'NC3*10',
	                target: '铣大小端面扁式平面',
	                name: '加工工序',
	                des: '要加工的工序4'
	            }],
	            categories: categories,
	        }]
	    };
	    myChart.setOption(option);
    </script>
	
</body>
</html>