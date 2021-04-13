<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="24">
          <a-col :md="6" :sm="24">
            <a-form-item label="批次号">
              <a-input placeholder="请输入批次号" v-model="queryParam.batchNo"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="10" :sm="24">
            <a-form-item label="批次日期">
              <a-range-picker
                :ranges="{ 当天: [moment(), moment()], '三天内': [moment().subtract('days', 2), moment()], '一周内': [moment().subtract('days', 6), moment()], '本月': [moment().subtract('month',1), moment()] }"
                @change="onChange"
                format="YYYY/MM/DD"
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


  import {getAction,deleteAction} from '@/api/manage'
  import moment from 'moment'
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
          isorter:{
              column: 'insertTime',
              order: 'desc',
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
            title:'来煤批次号',
            align:"center",
            dataIndex: 'batchNo'
          },
          {
            title:'批次类型',
            align:"center",
            dataIndex: 'batchNoType'
          },
          {
            title:'车次号',
            align:"center",
            dataIndex: 'trainNo'
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
            title:'批次包含车号',
            align:"center",
            dataIndex: 'carIds'
          },
          {
            title:'总车数',
            align:"center",
            dataIndex: 'carNum'
          },
          {
            title:'本批次重量',
            align:"center",
            dataIndex: 'allNetQty'
          },

          {
            title:'批次总票重',
            align:"center",
            dataIndex: 'allTickQty'
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
          list: "/common/batchNoInfo/list",
          delete: "/common/batchNoInfo/delete",
          deleteBatch: "/common/batchNoInfo/deleteBatch",
          exportXlsUrl: "/common/batchNoInfo/exportXls",
          importExcelUrl: "common/batchNoInfo/importExcel",
          
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
        fieldList.push({type:'string',value:'batchNoType',text:'批次类型'})
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
        fieldList.push({type:'string',value:'isValid',text:'是否已生效'})
        fieldList.push({type:'string',value:'mineNo',text:'矿点'})
        fieldList.push({type:'number',value:'allTickQty',text:'本批次总票重'})
        fieldList.push({type:'string',value:'carrierNo',text:'运输单位编号'})
        this.superFieldList = fieldList
      },

      moment,
      //给时间查询条件赋值
      onChange(dates, dateStrings) {
          this.queryParam.beginTime = dateStrings[0];
          this.queryParam.endTime = dateStrings[1];
      },

      searchQuery: function(){
          //清空主表单选中内容
          this.selectedRowKeys = [];
          this.selectionRows = [];

          if (this.queryParam.beginTime == "" || this.queryParam.beginTime == null){
              this.queryParam.beginTime = moment().subtract('month',1).format('YYYY-MM-DD');
              this.queryParam.endTime = moment(new Date()).format('YYYY-MM-DD')
              console.log("组件内时间选择器为空");
          }
          console.log(this.queryParam);
          console.log("调用了组件内的查询function");
          //todo 目前没有被选中的话就不加载详细信息，如需加载改造一下就行

          // 加载主表数据
          this.loadData();
          this.queryParam.beginTime = null;
          this.queryParam.endTime = null;
      }

    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>