<template>
  <a-card :bordered="false">

    <!-- 操作按钮区域 -->
    <div class="table-operator" :md="24" :sm="24" style="margin: -25px 0px 10px 0px">
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
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
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        @change="handleTableChange">

        <span slot="batchInfo" slot-scope="text, record, index">
          <a-tooltip >
            <template slot="title">
              点击查看分批详情
            </template>
            <a @click="handleBatchInfo(record)">{{record.isBatch_dictText}}</a>
          </a-tooltip>
        </span>

        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)">编辑</a>
          <a-divider type="vertical"/>
          <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.recordNo)">
            <a>删除</a>
          </a-popconfirm>
        </span>
      </a-table>
    </div>
    <!-- table区域-end -->
    <!-- 表单区域 -->
    <ship-cargo-modal ref="modalForm"></ship-cargo-modal>

    <ship-batch-modal ref="batchInfo"></ship-batch-modal>
  </a-card>
</template>

<script>
  import {JeecgListMixin} from '@/mixins/JeecgListMixin'
  import {getAction} from '@/api/manage'
  import {deleteAction} from '@/api/manage'
  import ShipCargoModal from './modules/ShipCargoModal'
  import ShipBatchModal from './modules/ShipBatchModal'

  export default {
    name: "ShipCargoList",
    mixins: [JeecgListMixin],
    components: {
      ShipCargoModal,
      ShipBatchModal,
    },
    data() {
      return {
        shipRecId: '',
        description: '船运煤信息',
        // 表头
        columns: [
          {
            title: '矿点',
            align: "center",
            dataIndex: 'mineName',
          },
          {
            title: '煤种',
            align: "center",
            dataIndex: 'coalName',
          },
          {
            title: '舱位号',
            align: "center",
            dataIndex: 'cabinNos',
          },
          {
            title: '运单吨位',
            dataIndex: 'tickQty',
            align: "center",
          },
          {
            title: '装港水尺吨位',
            dataIndex: 'departQty',
            align: "center",
          },
          {
            title: '到港水尺吨位',
            dataIndex: 'arriveQty',
            align: "center",
          },
          {
            title: '到厂皮带秤吨位',
            dataIndex: 'receiveQty',
            align: "center",
          },
          {
            title: '扣损类型',
            dataIndex: 'lossType_dictText',
            align: "center",
          },
          {
            title: '扣吨',
            dataIndex: 'kdQty',
            align: "center",
          },
          {
            title: '采样方式',
            dataIndex: 'sampleType_dictText',
            align: "center",
          },
          {
            title: '分批状态',
            dataIndex: '',
            align: "center",
            scopedSlots: {customRender: 'batchInfo'},
          },
          {
            title: '操作',
            dataIndex: '',
            align: 'center',
            width: 130,
            key: 'operation',
            scopedSlots: {customRender: 'action'},
          },
        ],
        url: {
          //list: "/rapid/shipCargoRecord/list",
          //delete: "/rapid/shipCargoRecord/delete",
          list: "business/ship/cargoRecordByPage",
          delete: "business/ship/deleteCargoRecord",
        }
      }
    },
    methods: {
      loadData(arg) {
        if (arg === 1) {
          this.ipagination.current = 1;
        }
        getAction(this.url.list, {shipRecId: this.shipRecId, pageNo : this.ipagination.current,
          pageSize :this.ipagination.pageSize}).then((res) => {
          if (res.success) {
            this.dataSource = res.result.records;
            this.ipagination.total = res.result.total;
          } else {
            this.dataSource = null;
          }
        })
      },
      getLinkedData(shipRecId) {
        this.shipRecId = shipRecId;
        this.loadData(1);
      },
      handleAdd: function () {
        this.$refs.modalForm.add(this.shipRecId);
        this.$refs.modalForm.title = "添加船运煤信息";
      },
      //根据recordNo删除选中的信息
      handleDelete: function (recordNo) {
        var that = this;
        deleteAction(that.url.delete, {shipRecId:this.shipRecId, recordNo: recordNo}).then((res) => {
          if (res.success) {
            that.$message.success(res.message);
            that.loadData();
          } else {
            that.$message.warning(res.message);
          }
        });
      },
      handleBatchInfo: function (record) {
        this.$refs.batchInfo.detail(record);
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