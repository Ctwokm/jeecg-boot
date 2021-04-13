<template>
  <a-card :bordered="false">

    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="24">

          <a-col :md="6" :sm="24">
            <a-form-item label="入厂日期(弃用)">
              <a-range-picker
                :ranges="{ 当天: [moment(), moment()], '三天内': [moment().subtract('days', 2), moment()], '一周内': [moment().subtract('days', 6), moment()], '本月': [moment().subtract('month',1), moment()] }"
                @change="onChange"
                format="YYYY/MM/DD"
              />
            </a-form-item>
          </a-col>

          <a-col :md="5" :sm="24">
              <a-form-item label="卡号">
                <a-input placeholder="请输入卡号" v-model="queryParam.cardId"></a-input>
              </a-form-item>
          </a-col>
          <a-col :md="4" :sm="24">
            <a-form-item label="车牌号">
              <a-input placeholder="请输入车牌号" v-model="queryParam.carId"></a-input>
            </a-form-item>
          </a-col>

          <span style="float: right;overflow: hidden;" class="table-page-search-submitButtons">
            <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
            <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
          </span>

        </a-row>
      </a-form>
    </div>

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('一对多示例')">导出</a-button>
      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel"><a-icon type="delete"/>删除</a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px"> 批量操作 <a-icon type="down" /></a-button>
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
        rowKey="id"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        @change="handleTableChange">

        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)">编辑</a>

          <a-divider type="vertical" />
          <a-dropdown>
            <a class="ant-dropdown-link">更多<a-icon type="down" /></a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.recordNo)">
                  <a>删除</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>

      </a-table>
    </div>
    <!-- table区域-end -->

    <!-- 表单区域 -->
    <CarTransRecodeModule ref="modalForm" @ok="modalFormOk"></CarTransRecodeModule>
  </a-card>
</template>

<script>
  import CarTransRecodeModule from './form/CarTransRecodeModule'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import moment from 'moment'

  export default {
    /*name: "JeecgOrderMainList",*/
    mixins: [JeecgListMixin],
    components: {
        CarTransRecodeModule,
    },
    data () {
      return {
        description: '汽车来煤管理',
        dateFormat: 'YYYY/MM/DD',
        /* 排序参数 */
        // 表头
        columns: [
          {
            title: '序号',
            dataIndex: '',
            key:'rowIndex',
            width:40,
            align:"center",
            customRender:function (t,r,index) {
              return parseInt(index)+1;
            }
          },
          {
            title: '卡号',
            align:"center",
            dataIndex: 'cardId'
          },
          {
            title: '批次号',
            align:"center",
            dataIndex: 'batchNo'
          },
          {
            title: '车牌号',
            align:"center",
            dataIndex: 'carId'
          },
          {
            title: '供应商',
            align:"center",
            dataIndex: 'venName'
          },
          {
            title: '煤种',
            align:"center",
            dataIndex: 'coalName'
          },
          {
            title: '毛重',
            align:"center",
            dataIndex: 'mzQty'
          },
          {
            title: '皮重',
            align:"center",
            dataIndex: 'pzQty'
          },
          {
            title: '净重',
            align:"center",
            dataIndex: 'netQty'
          },
          {
            title: '扣吨',
            align:"center",
            dataIndex: 'kdQty'
          },
          {
              title: '入厂时间',
              align:"center",
              dataIndex: 'recordDtm'
          },
          {
            title: '称重时间',
            align:"center",
            dataIndex: 'czDtm'
          },
          {
            title: '称轻时间',
            align:"center",
            dataIndex: 'jqDtm'
          },
          {
            title:'车辆状态',
            align:'center',
            dataIndex:'carStatus'
          },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            scopedSlots: { customRender: 'action' },
          }

        ],
		    url: {
          list: "/znrl/carInfo/carInfoList",
          delete: "/znrl/carInfo/deleteByCarInfo",
          exportXlsUrl: "/test/jeecgOrderMain/exportXls",
        },
        /*本页面自定义排序方法*/
        isorter:{
            sort: 'recordDtm',
            order: 'desc',
        },

        pickerOptions: {
            shortcuts: [{
                text: '最近一天',
                onClick(picker) {
                    const end = new Date();
                    const start = new Date();
                    start.setTime(start.getTime() - 3600 * 1000 * 24 * 1);
                    picker.$emit('pick', [start, end]);
                }
            }, {
                text: '最近三天',
                onClick(picker) {
                    const end = new Date();
                    const start = new Date();
                    start.setTime(start.getTime() - 3600 * 1000 * 24 * 3);
                    picker.$emit('pick', [start, end]);
                }
            }, {
                text: '最近一周',
                onClick(picker) {
                    const end = new Date();
                    const start = new Date();
                    start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
                    picker.$emit('pick', [start, end]);
                }
            }]
        },
      }
    },
    methods: {
        moment,
        onChange(dates, dateStrings) {
            this.queryParam.beginTime = dateStrings[0];
            this.queryParam.endTime = dateStrings[1];
        },
        handleAdd: function () {
            this.$refs.modalForm.add();
            this.$refs.modalForm.doActionTag="ADD";
            this.$refs.modalForm.title = "来煤信息录入";
            this.$refs.modalForm.disableSubmit = false;
        },
        handleEdit: function (record) {
            this.$refs.modalForm.edit(record);
            this.$refs.modalForm.doActionTag="MOD";
            this.$refs.modalForm.title = "来煤信息编辑";
            this.$refs.modalForm.disableSubmit = false;
        }
    }
  }
</script>
<style scoped>
  /** Button按钮间距 */
  .ant-btn {
    margin-left: 3px
  }
  .ant-card-body .table-operator{
    margin-bottom: 18px;
  }
  .ant-table-tbody .ant-table-row td{
    padding-top:15px;
    padding-bottom:15px;
  }
  .anty-row-operator button{margin: 0 5px}
  .ant-btn-danger{background-color: #ffffff}

  .ant-modal-cust-warp{height: 100%}
  .ant-modal-cust-warp .ant-modal-body{height:calc(100% - 110px) !important;overflow-y: auto}
  .ant-modal-cust-warp .ant-modal-content{height:90% !important;overflow-y: hidden}
</style>