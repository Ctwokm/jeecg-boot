<template>

  <div id="myChart" :style="{width: '1800px', height: '1400px'}"></div>
</template>

<script>
    let echarts = require('echarts');
    import { httpAction, getAction } from '@/api/manage'

    export default {
        name:'',
        data() {
            return {
                msg: 'point photo',
                point:[],
                da : {}
            }

        },
        components: {

        },
        mounted() {
            this.loadPointDate();

        },
        methods: {
            drawLine() {
                // 基于准备好的dom，初始化echarts实例 ,'dark'-- 加上dark 渲染的时候会呈现黑色效果；
                let myChart = echarts.init(document.getElementById('myChart'))
                // 绘制图表
                myChart.setOption({
                    title: {
                        text: '汽车采样点位分布图',
                        subtext: '',
                        left: 'center',
                        top: 0
                    },
                    grid: {
                        left: '3%',
                        right: '7%',
                        bottom: '7%',
                        containLabel: true
                    },
                    tooltip: {
                        // trigger: 'axis',
                        showDelay: 0,
                        formatter: function (params) {
                            if (params.value.length > 1) {
                                return params.seriesName + ' :<br/>'
                                    + params.value[0] + 'mm '
                                    + params.value[1] + 'mm ';
                            }
                            else {
                                return params.seriesName + ' :<br/>'
                                    + params.name + ' : '
                                    + params.value + 'mm ';
                            }
                        },
                        axisPointer: {
                            show: true,
                            type: 'cross',
                            lineStyle: {
                                type: 'dashed',
                                width: 1
                            }
                        }
                    },
                    toolbox: {
                        feature: {
                            dataZoom: {},
                            brush: {
                                type: ['rect', 'polygon', 'clear']
                            }
                        }
                    },
                    brush: {
                    },
                    legend: {
                        data: ['sampling Point'],
                        left: 'center',
                        bottom: 10
                    },
                    xAxis: [
                        {
                            type: 'value',
                            scale: true,
                            axisLabel: {
                                formatter: '{value} mm'
                            },
                            splitLine: {
                                show: false
                            },
                             min:200

                        }
                    ],
                    yAxis: [
                        {
                            type: 'value',
                            scale: true,
                            axisLabel: {
                                formatter: '{value} mm'
                            },
                            splitLine: {
                                show: false
                            },
                            min:    500
                        }
                    ],
                    series: [
                        {
                            name: 'sampling Point',
                            type: 'scatter',
                            emphasis: {
                                focus: 'series'
                            },
                            //设置数据source
                            data: this.point,
                            markArea: {
                                silent: true,
                                itemStyle: {
                                    color: 'transparent',
                                    borderWidth: 1,
                                    borderType: 'dashed'
                                },
                                data: [[{
                                    name: '汽车采样点位分布图',
                                    xAxis: 'min',
                                    yAxis: 'min'
                                }, {
                                    xAxis: 'max',
                                    yAxis: 'max'
                                }]]
                            },
                            markPoint: {
                                data: [
                                    {type: 'max', name: 'Max'},
                                    {type: 'min', name: 'Min'}
                                ]
                            },
                            markLine: {
                                lineStyle: {
                                    type: 'solid'
                                },
                                data: [
                                    {type: 'average', name: '平均值'},
                                    { xAxis: 160 }
                                ]
                            }
                        }
                    ]

                });

            },
            //定义获取点位的方法
            loadPointDate(){
                let url = "/znrl/carInfo/queryPointList";
                // 日期数据后面从页头取值；
                this.da = {
                    beginTime: '2019/06/23',
                    endTime: '2021/06/23'
                }
                this.da = JSON.stringify(this.da)

                httpAction(url,JSON.stringify(this.da),'post').then((res)=>{
                    if(res.success){
                        this.$message.success('加载成功！');
                        this.$emit('ok');
                        this.point = res.result;
                        //
                        this.drawLine();
                    }else{
                        this.$message.warning(res.message);
                    }
                });
            },

        }
    }

</script>

