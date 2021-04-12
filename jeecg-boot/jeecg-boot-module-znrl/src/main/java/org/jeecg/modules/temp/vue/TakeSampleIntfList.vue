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
      <a-button type="primary" icon="download" @click="handleExportXls('take_sample_intf')">导出</a-button>
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

    <take-sample-intf-modal ref="modalForm" @ok="modalFormOk"></take-sample-intf-modal>
  </a-card>
</template>

<script>

  import '@/assets/less/TableExpand.less'
  import { mixinDevice } from '@/utils/mixin'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import TakeSampleIntfModal from './modules/TakeSampleIntfModal'

  export default {
    name: 'TakeSampleIntfList',
    mixins:[JeecgListMixin, mixinDevice],
    components: {
      TakeSampleIntfModal
    },
    data () {
      return {
        description: 'take_sample_intf管理页面',
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
            title:'记录ID',
            align:"center",
            dataIndex: 'sampleIntfRecId'
          },
          {
            title:'入厂流水（车厢采样需要填）',
            align:"center",
            dataIndex: 'recordNo'
          },
          {
            title:'车牌号/火车车号（车厢采样需要填）',
            align:"center",
            dataIndex: 'carId'
          },
          {
            title:'卡号：汽车填RFID扫描编码',
            align:"center",
            dataIndex: 'cardId'
          },
          {
            title:'采样编码',
            align:"center",
            dataIndex: 'sampleCode'
          },
          {
            title:'汽车来煤批次号',
            align:"center",
            dataIndex: 'carBatchNo'
          },
          {
            title:'火车来煤批次号',
            align:"center",
            dataIndex: 'trainBatchNo'
          },
          {
            title:'车辆数：本批次车辆/车厢节数',
            align:"center",
            dataIndex: 'carNum'
          },
          {
            title:'本批次总净重、预计总重',
            align:"center",
            dataIndex: 'allNetQty'
          },
          {
            title:'煤种',
            align:"center",
            dataIndex: 'coalType'
          },
          {
            title:'水分',
            align:"center",
            dataIndex: 'coalWater'
          },
          {
            title:'采样方式 0：皮带采样  1：皮带大水分旁路 2：人工采样 3：车厢采样 4：车厢大水分旁路'',
            align:"center",
            dataIndex: 'sampleTyp'
          },
          {
            title:'采样点数（车厢采样）',
            align:"center",
            dataIndex: 'sampleCnt'
          },
          {
            title:'采样片区 （车厢采样）：采样填值 格式：“1；2；5” ',
            align:"center",
            dataIndex: 'sampleAreas'
          },
          {
            title:'采样坐标（车厢采样）',
            align:"center",
            dataIndex: 'sampleCordna'
          },
          {
            title:'数据状态 0: 未读取 1: 已读取',
            align:"center",
            dataIndex: 'dataStatus'
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
            title:'本批次总票重',
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
          list: "/modules/takeSampleIntf/list",
          delete: "/modules/takeSampleIntf/delete",
          deleteBatch: "/modules/takeSampleIntf/deleteBatch",
          exportXlsUrl: "/modules/takeSampleIntf/exportXls",
          importExcelUrl: "modules/takeSampleIntf/importExcel",
          
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
        fieldList.push({type:'int',value:'sampleIntfRecId',text:'记录ID'})
        fieldList.push({type:'int',value:'recordNo',text:'入厂流水（车厢采样需要填）'})
        fieldList.push({type:'string',value:'carId',text:'车牌号/火车车号（车厢采样需要填）'})
        fieldList.push({type:'string',value:'cardId',text:'卡号：汽车填RFID扫描编码'})
        fieldList.push({type:'string',value:'sampleCode',text:'采样编码'})
        fieldList.push({type:'string',value:'carBatchNo',text:'汽车来煤批次号'})
        fieldList.push({type:'string',value:'trainBatchNo',text:'火车来煤批次号'})
        fieldList.push({type:'int',value:'carNum',text:'车辆数：本批次车辆/车厢节数'})
        fieldList.push({type:'number',value:'allNetQty',text:'本批次总净重、预计总重'})
        fieldList.push({type:'string',value:'coalType',text:'煤种'})
        fieldList.push({type:'number',value:'coalWater',text:'水分'})
        fieldList.push({type:'string',value:'sampleTyp',text:'采样方式 0：皮带采样  1：皮带大水分旁路 2：人工采样 3：车厢采样 4：车厢大水分旁路''})
        fieldList.push({type:'int',value:'sampleCnt',text:'采样点数（车厢采样）'})
        fieldList.push({type:'string',value:'sampleAreas',text:'采样片区 （车厢采样）：采样填值 格式：“1；2；5” '})
        fieldList.push({type:'string',value:'sampleCordna',text:'采样坐标（车厢采样）'})
        fieldList.push({type:'string',value:'dataStatus',text:'数据状态 0: 未读取 1: 已读取'})
        fieldList.push({type:'date',value:'insertTime',text:'建立时间'})
        fieldList.push({type:'string',value:'opCode',text:'建立用户'})
        fieldList.push({type:'string',value:'updateCode',text:'更新用户'})
        fieldList.push({type:'number',value:'allTickQty',text:'本批次总票重'})
        this.superFieldList = fieldList
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>