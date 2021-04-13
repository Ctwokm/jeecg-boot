<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="24">

          <a-col :md="6" :sm="24">
            <a-form-item label="注册时间">
              <a-range-picker
                :ranges="{ 当天: [moment(), moment()], '三天内': [moment().subtract('days', 2), moment()], '一周内': [moment().subtract('days', 6), moment()], '本月': [moment().subtract('month',1), moment()] }"
                @change="onChange"
                format="YYYY-MM-DD"
              />
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="24">
            <a-form-item label="车牌号">
              <a-input placeholder="请输入车牌号" v-model="queryParam.carId"></a-input>
            </a-form-item>
          </a-col>

          <a-col :md="12" :sm="24">
            <span style="float: right;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>


    <!-- 操作按钮区域 -->
    <div class="table-operator">
    </div>

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
        rowKey="recId"
        filterMultiple="filterMultiple"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        @change="handleTableChange"
        :customRow="clickThenCheck"
      >

        <span slot="action" slot-scope="text, record">
          <a @click="unLockCar(record)">解锁</a>
          <a-divider type="vertical"/>
          <a-popconfirm title="确定锁车吗?" @confirm="() => lockCar(record)">
            <a>锁车</a>
          </a-popconfirm>
        </span>

      </a-table>
    </div>
    <!-- table区域-end -->

    <a-tabs defaultActiveKey="1">
      <a-tab-pane tab="锁车记录" key="1">
        <CarLockDetail ref="CarLockDetailList"></CarLockDetail>
      </a-tab-pane>
    </a-tabs>

    <!-- 表单区域 -->
    <CarLockModel ref="CarLockForm" @ok="modalFormOk"></CarLockModel>

  </a-card>
</template>

<script>
    //引入JS方法
    import {getAction} from '@/api/manage'
    import {JeecgListMixin} from '@/mixins/JeecgListMixin'
    import moment from 'moment'
    import CarLockModel from './form/CarLockForm'
    import CarLockDetail from './form/CarLockDetailList'
    /*import ShipCargoList from './ShipCargoList'
    import ShipRecordModal from './modules/ShipRecordModal'
    import ShipCargoModal from './modules/ShipCargoModal'*/

    export default {
        name: "CarLockRecordList",
        mixins: [JeecgListMixin],
        components: {
            CarLockModel,
            CarLockDetail,
            // ShipRecordModal,
            // ShipCargoModal,
        },
        data() {
            return {
                description: '汽车锁车管理',
                /* 这里可以自定义参数或者重写覆盖js里面的参数 */
                isorter:{
                    column: 'reg_dtm',
                    order: 'desc',
                },
                /* 分页参数 */
                ipagination:{
                    current: 1,
                    pageSize: 5,
                    pageSizeOptions: ['5', '10', '20'],
                    showTotal: (total, range) => {
                        return range[0] + "-" + range[1] + " 共" + total + "条"
                    },
                    showQuickJumper: true,
                    showSizeChanger: true,
                    total: 0
                },
                jsonString:{},
                // 表头
                columns: [
                    {
                        title: '序号',
                        dataIndex: '',
                        key:'rowIndex',
                        width:60,
                        align:"center",
                        customRender:function (t,r,index) {
                            return parseInt(index)+1;
                        }
                    },
                    {
                        title: '卡类型',
                        align:"center",
                        dataIndex: 'cardTypeName'
                    },
                    {
                        title: '车牌号',
                        align:"center",
                        dataIndex: 'carId'
                    },
                    {
                        title: '车卡状态',
                        align:"center",
                        dataIndex: 'statusName',
                    },
                    {
                        title: '车类型',
                        align:"center",
                        dataIndex: 'carTypeName',
                    },
                    {
                        title: '注册时间',
                        align:"center",
                        dataIndex: 'regDtm'
                    },
                    {
                        title: '失效时间',
                        align:"center",
                        dataIndex: 'endDtm'
                    },
                    {
                        title: '操作',
                        dataIndex: 'action',
                        align:"center",
                        scopedSlots: { customRender: 'action' },
                    }
                ],
                url: {
                    //list: "/rapid/shipRecord/list",//部分字段未查全，可以使用自定义sql集成分页page和查询构造器queryWarpper来实现
                    //delete: "/rapid/shipRecord/delete",
                    list: "/znrl/biztool/query",

                },
            }
        },
        methods: {
            moment,
            loadData(arg){
                console.log("调用了组件中的加载数据function");
                if(!this.url.list){
                    this.$message.error("请设置url.list属性!")
                    return
                }
                //加载数据 若传入参数1则加载第一页的内容
                if (arg === 1) {
                    this.ipagination.current = 1;
                }
                // debugger
                if (this.queryParam.beginTime === "" || this.queryParam.beginTime === null){
                    this.queryParam.beginTime = moment().subtract('month',1).format('YYYY-MM-DD');
                    this.queryParam.endTime = moment(new Date()).format('YYYY-MM-DD')
                    console.log("刷新页面加载数据，组件内时间选择器为空");
                }
                // if (this.queryParam.trainNo != "" || this.queryParam.trainNo != null){
                //   this.queryParam.beginTime = null;
                //   this.queryParam.endTime = null;
                //   console.log(this.queryParam.trainNo);
                // }

                //------------直接调用查询语句操作参数：sqlId，isPage,jsonString------------
                //------------直接调用查询语句操作参数：手动向下箭头------------
                //------------直接调用查询语句操作参数：手动向下箭头------------

                this.jsonString.beginTime = this.queryParam.beginTime;
                this.jsonString.endTime = this.queryParam.endTime;
                this.jsonString.carId = this.queryParam.carId;
                /*调用通用查询语句使用的参数，sql语句的id属性，赋值后可直接调用后台mapper.xml中的sql*/
                this.queryParam.sqlId = "qryRegisteredCarListByPage";
                /*调用通用查询语句使用的参数，分页属性参数，"0"为分页，其他为不分页*/
                this.queryParam.isPage = "Y";
                /*调用通用查询语句使用的业务参数*/
                this.queryParam.jsonString = this.jsonString;

                //------------直接调用查询语句操作参数：手动向上箭头------------
                //------------直接调用查询语句操作参数：手动向上箭头------------
                //------------直接调用查询语句操作参数：sqlId，isPage,jsonString------------

                var params = this.getQueryParams();//查询条件
                this.loading = true;
                // debugger
                getAction(this.url.list, params).then((res) => {
                    if (res.success) {
                        //update-begin---author:zhangyafei    Date:20201118  for：适配不分页的数据列表------------
                        this.dataSource = res.result.records||res.result;
                        if(res.result.total)
                        {
                            this.ipagination.total = res.result.total;
                        }else{
                            this.ipagination.total = 0;
                        }
                        //update-end---author:zhangyafei    Date:20201118  for：适配不分页的数据列表------------
                    }
                    if(res.code===510){
                        this.$message.warning(res.message)
                    }
                    this.loading = false;
                })
            },
            //给时间查询条件赋值
            onChange(dates, dateStrings) {
                this.queryParam.beginTime = dateStrings[0];
                this.queryParam.endTime = dateStrings[1];
            },
            //当鼠标点击行时触发
            clickThenCheck(record) {
                return {
                    on: {
                        click: () => {
                            this.onSelectChange([record.recId], [record]);
                        }
                    }
                };
            },
            //将选中行的key和行内容赋值并打开子tab页面加载对应数据
            onSelectChange(selectedRowKeys, selectionRows) {
                this.selectedRowKeys = selectedRowKeys;
                this.selectionRows = selectionRows;
                this.$refs.CarLockDetailList.getLinkedData(selectionRows[0].carId);
            },
            // 清理掉选中的参数
            onClearSelected() { //debugger
                this.selectedRowKeys = [];
                this.selectionRows = [];
            },

            // 查询，清除可能存在的选中内容，主表参数通过混入js获取的表单中的queryParam
            searchQuery: function(){
                this.loadData();
                this.onClearSelected();
            },
            lockCar: function (record) {
                if (this.selectedRowKeys.length <= 0 || this.selectedRowKeys.length > 1) {
                    alert('请选择单行车辆！')
                    return
                }
                //console.log(this.selectionRows[0].recordNo);
                this.selectionRows[0].action = 'LOCK';

                this.$refs.CarLockForm.add(this.selectionRows[0])
                this.$refs.CarLockForm.title = '锁车操作'
                this.$refs.CarLockForm.disableSubmit = false
            },
            unLockCar: function (id) {
                alert(id);
            },

        }
    }
</script>
<style scoped>
  .ant-card-body .table-operator {
    margin-bottom: 18px;
  }

  .ant-table-tbody .ant-table-row td {
    padding-top: 15px;
    padding-bottom: 15px;
  }

  .anty-row-operator button {
    margin: 0 5px
  }

  .ant-btn-danger {
    background-color: #ffffff
  }

  .ant-modal-cust-warp {
    height: 100%
  }

  .ant-modal-cust-warp .ant-modal-body {
    height: calc(100% - 110px) !important;
    overflow-y: auto
  }

  .ant-modal-cust-warp .ant-modal-content {
    height: 90% !important;
    overflow-y: hidden
  }
</style>