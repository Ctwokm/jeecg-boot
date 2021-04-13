<template>
  <a-card :bordered="false">

    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="24">

          <a-col :md="6" :sm="24" v-if="toggleSearchStatus">
            <a-form-item label="来煤日期">
              <a-range-picker
                v-model="queryParam.searchDate"
                :ranges="{ 当天: [moment(), moment()], '三天内': [moment().subtract('days', 2), moment()], '一周内': [moment().subtract('days', 6), moment()], '本月': [moment().subtract('month',1), moment()] }"
                @change="onChange"
                format="YYYY-MM-DD"
              />
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="24" v-if="toggleSearchStatus">
            <a-form-item label="船名(船号)">
              <j-search-select-tag
                placeholder="请输入采样编码"
                v-model="queryParam.shipId"
                dict="ship_info,ship_name,ship_id"
                :pageSize="6"
                :async="true">
              </j-search-select-tag>
            </a-form-item>
          </a-col>

          <a-col :md="6" :sm="24">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
              <a @click="handleToggleSearch" style="margin-left: 8px">
                {{ toggleSearchStatus ? '收起' : '展开' }}
                <a-icon :type="toggleSearchStatus ? 'up' : 'down'"/>
              </a>
            </span>
          </a-col>

        </a-row>
      </a-form>
    </div>


    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
      <a-button @click="processBatch" type="primary" icon="apartment">分批</a-button>
      <a-button @click="setCurrentShip" type="primary" icon="setting">设定卸煤船</a-button>
      <a-button @click="finishUnload" type="primary" icon="check">卸煤完成</a-button>
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
        rowKey="shipRecId"
        filterMultiple="filterMultiple"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange, type: selectType}"
        @change="handleTableChange"
        :customRow="clickThenCheck"
      >

        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)">编辑</a>
          <a-divider type="vertical"/>
          <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.shipRecId)">
            <a>删除</a>
          </a-popconfirm>
        </span>

      </a-table>
    </div>
    <!-- table区域-end -->

    <a-tabs defaultActiveKey="1">
      <a-tab-pane tab="船运煤信息" key="1">
        <ship-cargo-list ref="shipCargoList"></ship-cargo-list>
      </a-tab-pane>
    </a-tabs>

    <!-- 表单区域 -->
    <ship-record-modal ref="modalForm" @ok="modalOk"></ship-record-modal>
    <!--设置当前卸煤船-->
    <current-ship-modal ref="currentShip" @ok="modalOk"></current-ship-modal>
    <!--卸煤完成-->
    <finish-unload-modal ref="finishUnload" @ok="modalOk"></finish-unload-modal>
  </a-card>
</template>

<script>
  //引入JS方法
  import {deleteAction} from '@/api/manage'
  import {putAction} from '@/api/manage'
  import {JeecgListMixin} from '@/mixins/JeecgListMixin'
  import moment from 'moment'

  import ShipCargoList from './ShipCargoList'
  import ShipRecordModal from './modules/ShipRecordModal'
  import ShipCargoModal from './modules/ShipCargoModal'
  import CurrentShipModal from './modules/CurrentShipModal'
  import FinishUnloadModal from './modules/FinishUnloadModal'

  export default {
    name: "ShipRecordList",
    mixins: [JeecgListMixin],
    components: {
      ShipCargoList,
      ShipRecordModal,
      ShipCargoModal,
      CurrentShipModal,
      FinishUnloadModal,
    },
    data() {
      return {
        description: '轮船来煤管理',
        /* 这里可以自定义参数或者重写覆盖js里面的参数 */
        isorter:{
          column: 'shipRecId',
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
        // 表头
        columns: [
          {
            title: '#',
            dataIndex: '',
            key:'rowIndex',
            width:60,
            align:"center",
            customRender:function (t,r,index) {
              return parseInt(index)+1;
            }
          },
          {
            title: '船运编号',
            align:"center",
            dataIndex: 'shipTransNo'
          },
          {
            title: '船名',
            align:"center",
            dataIndex: 'shipName'
          },
          {
            title: '水尺吨位',
            align:"center",
            dataIndex: 'waterTun',
          },
          {
            title: '船舶状态',
            align:"center",
            dataIndex: 'status_dictText',
          },
          /*{
            title: '装港用时',
            align:"center",
            dataIndex: 'loadHours'
          },
          {
            title: '卸港用时',
            align:"center",
            dataIndex: 'unloadHours'
          },*/
          {
            title: '始发港口',
            align:"center",
            dataIndex: 'startPortName'
          },
          {
            title: '到达港口',
            align:"center",
            dataIndex: 'finalPortName'
          },
          /*{
            title: '预计到港时间',
            align:"center",
            dataIndex: 'estimateTime'
          },*/
          {
            title: '实际到港时间',
            align:"center",
            dataIndex: 'factTime'
          },
          /*{
            title: '接卸时间',
            align:"center",
            dataIndex: 'arrangeTime'
          },*/
          {
            title: '卸港完成时间',
            align:"center",
            dataIndex: 'unloadTime'
          },
          {
            title: '离港时间',
            align:"center",
            dataIndex: 'departTime'
          },
          /*{
            title: '泊位号',
            align:"center",
            dataIndex: 'berthNo'
          },*/
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            scopedSlots: { customRender: 'action' },
          }
        ],
        selectType: "radio",//设置table选择框类型
        url: {
          //list: "/rapid/shipRecord/list",//部分字段未查全，可以使用自定义sql集成分页page和查询构造器queryWarpper来实现
          //delete: "/rapid/shipRecord/delete",
          list: "business/ship/recordByPage",
          delete: "business/ship/deleteRecord",
          batch: "business/ship/processBatch",
        },
      }
    },
    methods: {
      moment,
      //给时间查询条件赋值
      onChange(dates, dateStrings) {
        this.queryParam.beginDate = dateStrings[0];
        this.queryParam.endDate = dateStrings[1];
      },
      //当鼠标点击行时触发
      clickThenCheck(record) {
        return {
          on: {
            click: () => {
              this.onSelectChange([record.shipRecId], [record]);
            }
          }
        };
      },
      //将选中行的key和行内容赋值并打开子tab页面加载对应数据
      onSelectChange(selectedRowKeys, selectionRows) {
        this.selectedRowKeys = selectedRowKeys;
        this.selectionRows = selectionRows;
        this.$refs.shipCargoList.getLinkedData(this.selectedRowKeys[0]);
      },
      // 清理掉选中的参数
      onClearSelected() {
        this.selectedRowKeys = [];
        this.selectionRows = [];
        this.$refs.shipCargoList.shipRecId = "";
        this.$refs.shipCargoList.loadData();
        this.$refs.shipCargoList.selectedRowKeys = [];
        this.$refs.shipCargoList.selectionRows = [];
      },

      //根据shipRecId删除选中的信息
      handleDelete: function (shipRecId) {
        var that = this;
        deleteAction(that.url.delete, {shipRecId: shipRecId}).then((res) => {
          if (res.success) {
            that.$message.success(res.message);
            that.loadData();
            that.$refs.shipCargoList.loadData();
          } else {
            that.$message.warning(res.message);
          }
        });
      },
      processBatch: function (){
        if(this.selectionRows.length != 1){
          this.$message.warning("请选择一个船运信息进行分批");
          return;
        }
        let that = this;
        this.$confirm({
          title: '确认',
          content: '确认要分批吗 ?',
          onOk() {
            var shipRecId = that.selectedRowKeys[0];
            putAction(that.url.batch, {shipRecId: shipRecId}).then(res=>{
              if(res.success){
                that.$message.success(res.message)
                that.loadData();
                that.$refs.shipCargoList.loadData();
              }else{
                that.$message.error(res.message)
              }
            })
          },
          onCancel() {
          },
        });
      },
      setCurrentShip: function(){
        this.$refs.currentShip.prepare();
      },
      finishUnload: function(){
        if(this.selectionRows.length != 1){
          this.$message.warning("请选择一个船运信息");
          return;
        }
        var shipRecId = this.selectedRowKeys[0];
        this.$refs.finishUnload.prepare(shipRecId);
      },
      // 查询，清除可能存在的选中内容，主表参数通过混入js获取的表单中的queryParam
      searchQuery: function(){
        this.loadData();
        this.onClearSelected();
      },
      modalOk: function(){
        this.loadData();
        this.onClearSelected();
      }
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