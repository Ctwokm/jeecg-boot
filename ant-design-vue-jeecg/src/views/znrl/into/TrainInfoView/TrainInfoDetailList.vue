<template>
  <a-card :bordered="false">

    <!-- 操作按钮区域 -->
    <div class="table-operator" :md="24" :sm="24" style="margin: -25px 0px 10px 0px">
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
        rowKey="recordNo"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :scroll="{ x: 2350 }"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        @change="handleTableChange">

        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)">编辑</a>
          <a-divider type="vertical"/>
          <a-dropdown>
            <a class="ant-dropdown-link">
              更多 <a-icon type="down"/>
            </a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a href="javascript:;" @click="handleDetail(record)">详情</a>
              </a-menu-item>
              <a-menu-item>
                <a-popconfirm title="确定删除车节信息吗?" @confirm="() => handleDeleteOneTrain(record.recordNo)">
                  <a>删除车节</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>
      </a-table>
    </div>
    <!-- table区域-end -->
    <!-- 表单区域 -->
    <train-info-detail-modal ref="modalForm" @ok="modalFormOk"></train-info-detail-modal>
  </a-card>
</template>

<script>
  import TrainInfoDetailModal from './form/TrainInfoDetailModal'
  import {JeecgListMixin} from '@/mixins/JeecgListMixin'
  import {getAction} from '@/api/manage'
  import {deleteAction} from '@/api/manage'

  export default {
    name: "TrainInfoDetailList",
    mixins: [JeecgListMixin],
    components: {
      TrainInfoDetailModal
    },
    data() {
      return {
        description: '订单客户信息',
        // 表头
        columns: [
          {dataIndex: 'recordNo', title: '流水号',align:'center', fixed: 'left', width:80},
          {dataIndex: 'xhNum', title: '车厢序号',align:'center', fixed: 'left',sorter: (a, b) => a.xhNum - b.xhNum, width:100},
          {dataIndex: 'carId', title: '车号',align:'center', fixed: 'left',width:80 },
          {dataIndex: 'trackNo', title: '轨道号',align:'center' , width:80},
          {dataIndex: 'batchNo', title: '批次号',align:'center', width:180},
          {dataIndex: 'sampleCode', title: '采样编码',align:'center', width:120},
          // {dataIndex: 'attachBatchNo', title: '抽检批次号',align:'center', width:180},
          {dataIndex: 'mineName', title: '矿点',align:'center'},
          {dataIndex: 'coalName', title: '煤种',align:'center', width:80},
          //{dataIndex: 'venName', title: '供应商',align:'center', width:80},
          {dataIndex: 'sampleTypName', title: '采样方式',align:'center', width:100},
          {dataIndex: 'emptyFlgName', title: '是否空车',align:'center', width:100},
          {dataIndex: 'tickQty', title: '票重(t)',align:'center',width:80},
          {dataIndex: 'mzQty', title: '毛重(t)',align:'center',width:80},
          {dataIndex: 'pzQty', title: '皮重(t)',align:'center',width:80},
          {dataIndex: 'netQty', title: '净重(t)',align:'center',width:80},
          {dataIndex: 'speed', title: '过衡速度',align:'center',width:80},

          {dataIndex: 'startPlaceName', title: '发站',align:'center', width:80},
          {dataIndex: 'finalPlaceName', title: '到站',align:'center', width:80},
          {dataIndex: 'aimTime', title: '对位时间',align:'center', width:180},

          // {dataIndex: 'ydQty', title: '盈亏(t)',align:'center', width:80},
          // {dataIndex: 'lossQty', title: '运损(t)',align:'center', width:80},
          {dataIndex: 'carTyp', title: '车型',align:'center', width:80},
          {dataIndex: 'fcTime', title: '翻车时间',align:'center', width:180},
          {
            title: '操作',
            key: 'operation',
            align: 'center',
            width: 130,
            scopedSlots: {customRender: 'action'},
            fixed: 'right',
          },
        ],
        url: {
          list: "/znrl/train/trainDetailListByPage",
          delete: "/znrl/train/deleteOneTrain",
          deleteBatch: "/test/order/deleteBatchCustomer",
        }
      }
    },
    methods: {
      loadData(arg) {
        if (arg === 1) {
          this.ipagination.current = 1;
        }
        //update-begin--Author:kangxiaolin  Date:20190905 for：[442]主子表分开维护，生成的代码子表的分页改为真实的分页--------------------
        var params = this.getQueryParams();
        console.log(params);
        if (params.trainNo == null || params.trainNo == ""){
          this.dataSource = null;
        }else {
          getAction(this.url.list, {trainNo: params.trainNo, pageNo : this.ipagination.current,
            pageSize :this.ipagination.pageSize}).then((res) => {
            if (res.success) {
              this.dataSource = res.result.records||res.result;
              this.ipagination.total = res.result.total;
            } else {
              this.dataSource = null;
            }
          })
        }
        //update-end--Author:kangxiaolin  Date:20190905 for：[442]主子表分开维护，生成的代码子表的分页改为真实的分页--------------------
      },
      //根据主表trainNo加载子表信息
      getOrderMain(trainNo) {
        this.queryParam.trainNo = trainNo;
        this.loadData(1);
      },
      //根据车厢流水号删除单个车厢，并进行重新排序
      handleDeleteOneTrain: function (recordNo) {
        if(!this.url.delete){
          this.$message.error("请设置url.delete属性!")
          return
        }
        var that = this;
        deleteAction(that.url.delete, {recordNo: recordNo}).then((res) => {
          if (res.success) {
            that.$message.success(res.message);
            that.loadData();
          } else {
            that.$message.warning(res.message);
          }
        });
      },
      handleAdd: function () {
        this.$refs.modalForm.add(this.queryParam.mainId);
        this.$refs.modalForm.title = "添加客户信息";
      },
    }
  }
</script>
<style scoped>
  .ant-card {
    margin-left: -30px;
    margin-right: -30px;
  }
</style>