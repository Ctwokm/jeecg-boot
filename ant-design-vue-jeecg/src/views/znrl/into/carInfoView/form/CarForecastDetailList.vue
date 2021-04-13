<template>
  <a-card :bordered="false">
    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择 <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>项
        <a style="margin-left: 24px" @click="onClearSelected">清空</a>
      </div>

      <a-table
        ref="table"
        size="middle"
        bordered
        rowKey="recordId"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        @change="handleTableChange"
        :customRow="clickThenCheck">

        <span slot="action" slot-scope="text, record">
          <a @click="handleDelete(record)">删除</a>
        </span>
      </a-table>
    </div>
    <!-- table区域-end -->
    <!-- 表单区域 -->
  </a-card>
</template>

<script>
    import {JeecgListMixin} from '@/mixins/JeecgListMixin'
    import {getAction} from '@/api/manage'

    export default {
        name: "CarForecastDetailList",
        mixins: [JeecgListMixin],
        components: {

        },
        data() {
            return {
                forecastId: '',
                description: '汽车来煤预报明细',
                jsonString:{},
                isorter:{
                    column: 'insert_time',
                    order: 'desc',
                },
                // 表头
                columns: [
                    {
                        title: '流水号',
                        align: "center",
                        dataIndex: 'recordId',
                    },
                    {
                        title: '矿点',
                        align: "center",
                        dataIndex: 'mineNoName',
                    },
                    {
                        title: '卡号',
                        align: "center",
                        dataIndex: 'cardId',
                    },
                    {
                        title: '车牌号',
                        dataIndex: 'carId',
                        align: "center",
                    },
                    {
                        title: '预报人',
                        dataIndex: 'opCode',
                        align: "center",
                    },
                    {
                        title: '生成时间',
                        dataIndex: 'insertTime',
                        align: "center",
                    },
                    {
                        title: '操作',
                        dataIndex: 'action',
                        align:"center",
                        scopedSlots: { customRender: 'action' },
                    }
                ],
                url: {
                    //list: "/rapid/shipCargoRecord/list",
                    //delete: "/rapid/shipCargoRecord/delete",
                    list: "/znrl/biztool/query",
                    delete:"/znrl/biztool/updateById"

                },
                record_2:{}
            }
        },
        methods: {
            getLinkedData(record) {
                //将record 存起来，后面用于调用方法；
                this.record_2 = record;
                this.jsonString.forecastId = record[0].forecastId;
                this.jsonString.startDate = record.beginTime;
                this.jsonString.endDate = record.endTime;
                /*调用通用查询语句使用的参数，sql语句的id属性，赋值后可直接调用后台mapper.xml中的sql*/
                this.queryParam.sqlId = "qryCarForecastDetailList";
                /*调用通用查询语句使用的参数，分页属性参数，"0"为分页，其他为不分页*/
                this.queryParam.isPage = "Y";
                /*调用通用查询语句使用的业务参数*/
                this.queryParam.jsonString = this.jsonString;

                var params = this.getQueryParams();//查询条件
                this.loading = true;
                getAction(this.url.list,params).then((res) => {
                    if (res.success) {
                        this.dataSource = res.result.records;
                        this.ipagination.total = res.result.total;
                    } else {
                        this.dataSource = null;
                    }

                })
                this.loading = false;
            },
            //当鼠标点击行时触发
            clickThenCheck(record) {
                return {
                    on: {
                        click: () => {
                            this.onSelectChange([record.recordId], [record]);
                        }
                    }
                };
            },
            //将选中行的key和行内容赋值并打开子tab页面加载对应数据
            onSelectChange(selectedRowKeys, selectionRows) {
                this.selectedRowKeys = selectedRowKeys;
                this.selectionRows = selectionRows;
            },
            handleDelete(record){
                //debugger
                this.jsonString.recordId = record.recordId;
                /*调用通用查询语句使用的参数，sql语句的id属性，赋值后可直接调用后台mapper.xml中的sql*/
                this.queryParam.sqlId = "delCarForecastDetail";
                /*调用通用查询语句使用的业务参数*/
                this.queryParam.jsonString = this.jsonString;
                var params = this.getQueryParams();//查询条件
                this.loading = true;
                getAction(this.url.delete,params).then((res) => {
                    if (res.success) {
                        //alert(record);
                        //删除成功后再次调用
                        this.getLinkedData(this.record_2);
                    } else {
                        this.dataSource = null;
                    }
                })
            }

        }
    }
</script>
<style scoped>
  .ant-card {
    margin-left: -30px;
    margin-right: -30px;
  }
</style>