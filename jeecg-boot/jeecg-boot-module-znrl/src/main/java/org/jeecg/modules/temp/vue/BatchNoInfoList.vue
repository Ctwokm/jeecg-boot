<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('batch_no_info')">导出</a-button>
      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
        <a-button type="primary" icon="import">导入</a-button>
      </a-upload>
      <!-- 高级查询区域 -->
      <j-super-query :fieldList="superFieldList" ref="superQueryModal" @handleSuperQuery="handleSuperQuery"></j-super-query>
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
        :scroll="{x:true}"
        bordered
        rowKey="id"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        class="j-table-force-nowrap"
        @change="handleTableChange">

        <template slot="htmlSlot" slot-scope="text">
          <div v-html="text"></div>
        </template>
        <template slot="imgSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">无图片</span>
          <img v-else :src="getImgView(text)" height="25px" alt="" style="max-width:80px;font-size: 12px;font-style: italic;"/>
        </template>
        <template slot="fileSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">无文件</span>
          <a-button
            v-else
            :ghost="true"
            type="primary"
            icon="download"
            size="small"
            @click="downloadFile(text)">
            下载
          </a-button>
        </template>

        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)">编辑</a>

          <a-divider type="vertical" />
          <a-dropdown>
            <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a @click="handleDetail(record)">详情</a>
              </a-menu-item>
              <a-menu-item>
                <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
                  <a>删除</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>

      </a-table>
    </div>

    <batch-no-info-modal ref="modalForm" @ok="modalFormOk"></batch-no-info-modal>
  </a-card>
</template>

<script>

  import '@/assets/less/TableExpand.less'
  import { mixinDevice } from '@/utils/mixin'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import BatchNoInfoModal from './modules/BatchNoInfoModal'

  export default {
    name: 'BatchNoInfoList',
    mixins:[JeecgListMixin, mixinDevice],
    components: {
      BatchNoInfoModal
    },
    data () {
      return {
        description: 'batch_no_info管理页面',
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
            title:'来煤批次号',
            align:"center",
            dataIndex: 'batchNo'
          },
          {
            title:'0:汽车来煤批次 1:火车来煤批次 2:船运煤批次 6:船煤抽检批次 7:火车煤抽检批次 8:汽车煤抽检批次 9:入炉煤采样批次 3:人工采样批次',
            align:"center",
            dataIndex: 'batchNoType'
          },
          {
            title:'trainNo',
            align:"center",
            dataIndex: 'trainNo'
          },
          {
            title:'供应商',
            align:"center",
            dataIndex: 'venNo'
          },
          {
            title:'煤品种',
            align:"center",
            dataIndex: 'coalNo'
          },
          {
            title:'采样编码',
            align:"center",
            dataIndex: 'sampleCode'
          },
          {
            title:'制样编码',
            align:"center",
            dataIndex: 'samplingCode'
          },
          {
            title:'采样编码',
            align:"center",
            dataIndex: 'laborCode'
          },
          {
            title:'批次序号：当日生成批次序号',
            align:"center",
            dataIndex: 'dayBatchNum'
          },
          {
            title:'本批次包含车号',
            align:"center",
            dataIndex: 'carIds'
          },
          {
            title:'总车数',
            align:"center",
            dataIndex: 'carNum'
          },
          {
            title:'本批次总净重、预计总重',
            align:"center",
            dataIndex: 'allNetQty'
          },
          {
            title:'是否已生效，是否形成正式数据： 0 ：未完成 1：已完成  2：完成处理中',
            align:"center",
            dataIndex: 'isValid'
          },
          {
            title:'建立时间',
            align:"center",
            dataIndex: 'insertTime',
            customRender:function (text) {
              return !text?"":(text.length>10?text.substr(0,10):text)
            }
          },
          {
            title:'建立用户',
            align:"center",
            dataIndex: 'opCode'
          },
          {
            title:'更新用户',
            align:"center",
            dataIndex: 'updateCode'
          },
          {
            title:'矿点',
            align:"center",
            dataIndex: 'mineNo'
          },
          {
            title:'本批次总票重',
            align:"center",
            dataIndex: 'allTickQty'
          },
          {
            title:'运输单位编号',
            align:"center",
            dataIndex: 'carrierNo'
          },
          {
            title:'来煤计划ID',
            align:"center",
            dataIndex: 'carPlanId'
          },
          {
            title:'人工采用编码',
            align:"center",
            dataIndex: 'manSampleCode'
          },
          {
            title:'批次关联ID，如底样面样和主批次关联',
            align:"center",
            dataIndex: 'batchRelaId'
          },
          {
            title:'备注',
            align:"center",
            dataIndex: 'remark'
          },
          {
            title:'合同编码',
            align:"center",
            dataIndex: 'contractNo'
          },
          {
            title:'receiveTime',
            align:"center",
            dataIndex: 'receiveTime',
            customRender:function (text) {
              return !text?"":(text.length>10?text.substr(0,10):text)
            }
          },
          {
            title:'receiveOp',
            align:"center",
            dataIndex: 'receiveOp'
          },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            fixed:"right",
            width:147,
            scopedSlots: { customRender: 'action' }
          }
        ],
        url: {
          list: "/modules/batchNoInfo/list",
          delete: "/modules/batchNoInfo/delete",
          deleteBatch: "/modules/batchNoInfo/deleteBatch",
          exportXlsUrl: "/modules/batchNoInfo/exportXls",
          importExcelUrl: "modules/batchNoInfo/importExcel",
          
        },
        dictOptions:{},
        superFieldList:[],
      }
    },
    created() {
    this.getSuperFieldList();
    },
    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      },
    },
    methods: {
      initDictConfig(){
      },
      getSuperFieldList(){
        let fieldList=[];
        fieldList.push({type:'string',value:'batchNo',text:'来煤批次号'})
        fieldList.push({type:'string',value:'batchNoType',text:'0:汽车来煤批次 1:火车来煤批次 2:船运煤批次 6:船煤抽检批次 7:火车煤抽检批次 8:汽车煤抽检批次 9:入炉煤采样批次 3:人工采样批次'})
        fieldList.push({type:'string',value:'trainNo',text:'trainNo'})
        fieldList.push({type:'string',value:'venNo',text:'供应商'})
        fieldList.push({type:'string',value:'coalNo',text:'煤品种'})
        fieldList.push({type:'string',value:'sampleCode',text:'采样编码'})
        fieldList.push({type:'string',value:'samplingCode',text:'制样编码'})
        fieldList.push({type:'string',value:'laborCode',text:'采样编码'})
        fieldList.push({type:'int',value:'dayBatchNum',text:'批次序号：当日生成批次序号'})
        fieldList.push({type:'string',value:'carIds',text:'本批次包含车号'})
        fieldList.push({type:'int',value:'carNum',text:'总车数'})
        fieldList.push({type:'number',value:'allNetQty',text:'本批次总净重、预计总重'})
        fieldList.push({type:'string',value:'isValid',text:'是否已生效，是否形成正式数据： 0 ：未完成 1：已完成  2：完成处理中'})
        fieldList.push({type:'date',value:'insertTime',text:'建立时间'})
        fieldList.push({type:'string',value:'opCode',text:'建立用户'})
        fieldList.push({type:'string',value:'updateCode',text:'更新用户'})
        fieldList.push({type:'string',value:'mineNo',text:'矿点'})
        fieldList.push({type:'number',value:'allTickQty',text:'本批次总票重'})
        fieldList.push({type:'string',value:'carrierNo',text:'运输单位编号'})
        fieldList.push({type:'int',value:'carPlanId',text:'来煤计划ID'})
        fieldList.push({type:'string',value:'manSampleCode',text:'人工采用编码'})
        fieldList.push({type:'string',value:'batchRelaId',text:'批次关联ID，如底样面样和主批次关联'})
        fieldList.push({type:'string',value:'remark',text:'备注'})
        fieldList.push({type:'string',value:'contractNo',text:'合同编码'})
        fieldList.push({type:'date',value:'receiveTime',text:'receiveTime'})
        fieldList.push({type:'string',value:'receiveOp',text:'receiveOp'})
        this.superFieldList = fieldList
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>