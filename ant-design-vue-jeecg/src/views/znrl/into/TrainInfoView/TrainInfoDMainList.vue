<template>
  <a-card :bordered="false">

    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="24">
          <a-col :md="6" :sm="24">
            <a-form-item label="车次号">
              <a-input placeholder="请输入车次号" v-model="queryParam.trainNo"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="10" :sm="24">
            <a-form-item label="入厂日期">
              <a-range-picker
                :ranges="{ 当天: [moment(), moment()], '三天内': [moment().subtract('days', 2), moment()], '一周内': [moment().subtract('days', 6), moment()], '本月': [moment().subtract('month',1), moment()] }"
                @change="onChange"
                format="YYYY-MM-DD"
              />
            </a-form-item>
          </a-col>

          <a-col :md="6" :sm="24">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
            </span>
          </a-col>

        </a-row>
      </a-form>
    </div>


    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>

      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel">
            <a-icon type="delete"/>
            删除
          </a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px"> 批量操作
          <a-icon type="down"/>
        </a-button>
      </a-dropdown>
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
        rowKey="trainNo"
        filterMultiple="filterMultiple"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange,type:type}"
        @change="handleTableChange"
        :customRow="clickThenCheck"
      >

        <span slot="action" slot-scope="text, record">
<!--          <a @click="handleEdit(record)">编辑</a>-->
<!--          <a-divider type="vertical"/>-->
          <a-popconfirm title="确定删除车次信息吗?" @confirm="() => handleDeleteTrain(record.trainNo)">
            <a>删除车次</a>
          </a-popconfirm>
        </span>

      </a-table>
    </div>
    <!-- table区域-end -->


    <a-tabs defaultActiveKey="1">
      <a-tab-pane tab="车次详细信息" key="1">
        <train-info-detail-list ref="TrainInfoDetailList"></train-info-detail-list>
      </a-tab-pane>
    </a-tabs>

    <!-- 表单区域 -->
<!--    <trainOrderDMain-modal ref="modalForm" @ok="modalFormOk"></trainOrderDMain-modal>-->

  </a-card>
</template>

<script>
  import {getAction,deleteAction} from '@/api/manage'
  import {JeecgListMixin} from '@/mixins/JeecgListMixin'
  import moment from 'moment'
  import TrainInfoDetailList from './TrainInfoDetailList'
  import TrainInfoDetailModal from './form/TrainInfoDetailModal'

  export default {
    name: "TrainInfoDMainList",
    mixins: [JeecgListMixin],
    components: {
      TrainInfoDetailList,
      TrainInfoDetailModal,
    },
    data() {
      return {
        description: '火车入厂管理页面',
        /* 分页参数，既是前端的页面展示分页查询依据，也是后端查询依据，如果引入了混入js，这里覆盖会把混入js中的参数覆盖掉 */
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
          columns: [{
            title: '#',
            dataIndex: '',
          key: 'rowIndex',
          width: 60,
          align: "center",
          customRender: function (t, r, index) {
            return parseInt(index) + 1;
          }
        },
          {
            title: '车次',
            align: "center",
            dataIndex: 'trainNo'
          },
          {
            title: '入厂日期',
            align: "center",
            dataIndex: 'recordDtm',
          },
          {
            title: '车数',
            align: "center",
            dataIndex: 'carCnt',
            sorter: (a, b) => a.carCnt - b.carCnt
          },
          {
            title: '总毛重(t)',
            align: "center",
            dataIndex: 'totalMzQty'
          },
          {
            title: '总净重(t)',
            align: "center",
            dataIndex: 'totalNetQty'
          },
          {
            title: '备注',
            align: "center",
            dataIndex: 'remark'
          },
          {
            title: '操作',
            dataIndex: 'action',
            align: "center",
            scopedSlots: {customRender: 'action'},
          }],
        // 分页参数
        type: "radio",
        url: {
          // list: "/znrl/train/trainBasicListByPage",
          list: "/znrl/biztool/query",
          delete: "/znrl/train/deleteTrainInfo",
        }
      }
    },
    methods: {
      // 混入js中加载数据函数无法满足业务需求，所以这里重写一下，重写的function只会调用组件内的，不会调用混入js中的
      loadData(arg) {

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
        /*调用通用查询语句使用的参数，sql语句的id属性，赋值后可直接调用后台mapper.xml中的sql*/
        this.queryParam.sqlId = "qryTrainBasicListByPage";
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
      //需要在页面加载时调用的配置方法放在这里，目前默认实在页面渲染且调用数据加载function函数后调用本方法
      initDictConfig(){
        console.log("调用了组件中的配置");
        // this.queryParam.beginTime = moment().subtract('days', 6);
        // this.queryParam.endTime = moment();
        // this.disableMixinCreated = true;
      },
      //生成数组
      clickThenCheck(record) {
        return {
          on: {
            click: () => {
              this.onSelectChange(record.trainNo.split(","), [record]);
            }
          }
        };
      },
      moment,
      //给时间查询条件赋值
      onChange(dates, dateStrings) {
        this.queryParam.beginTime = dateStrings[0];
        this.queryParam.endTime = dateStrings[1];
      },
      // 查询，清除可能存在的选中内容，主表参数通过混入js获取的表单中的queryParam
      // 这个查询只需要对主表区域生效，详情页的内容只有当主表被选中时在渲染
      searchQuery: function(){
        //清空主表单选中内容
        this.selectedRowKeys = [];
        this.selectionRows = [];
        //清空关联详情页的主键
        this.$refs.TrainInfoDetailList.queryParam.trainNo = null;
        if (this.queryParam.beginTime == "" || this.queryParam.beginTime == null){
          this.queryParam.beginTime = moment().subtract('month',1).format('YYYY-MM-DD');
          this.queryParam.endTime = moment(new Date()).format('YYYY-MM-DD')
          console.log("组件内时间选择器为空");
        }
        console.log(this.queryParam);
        console.log("调用了组件内的查询function");
        //todo 目前没有被选中的话就不加载详细信息，如需加载改造一下就行
        // this.$refs.TrainInfoDetailList.loadData();
        //清空详情页选中内容
        this.$refs.TrainInfoDetailList.selectedRowKeys = [];
        this.$refs.TrainInfoDetailList.selectionRows = [];
        // 加载主表数据
        this.loadData();

      },
      //当主表单某条信息被选择时触发，打开对应子表单信息，并将trainNo作为子表的关联字段去查询相关子表信息
      onSelectChange(selectedRowKeys, selectionRows) {
        console.log('被选中的行: ', selectedRowKeys);
        this.selectedRowKeys = selectedRowKeys;
        this.selectionRows = selectionRows;
        //加载tab内容
        this.$refs.TrainInfoDetailList.getOrderMain(this.selectedRowKeys[0]);
      },
      //删除车次信息，调用的存储过程，会将该车次相关的批次信息，和车厢信息全都删掉，当然前提是该车次没有开始采样
      handleDeleteTrain: function (trainNo) {
        if(!this.url.delete){
          this.$message.error("请设置url.delete属性!")
          return
        }
        var that = this;
        deleteAction(that.url.delete, {trainNo: trainNo}).then((res) => {
          if (res.success) {
            that.$message.success(res.message);
            that.loadData();
          } else {
            that.$message.warning(res.message);
          }
        });
      },
      // 修改单个火车车厢信息
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