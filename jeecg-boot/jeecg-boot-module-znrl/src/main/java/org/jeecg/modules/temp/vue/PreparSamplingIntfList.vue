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
      <a-button type="primary" icon="download" @click="handleExportXls('prepar_sampling_intf')">导出</a-button>
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

    <prepar-sampling-intf-modal ref="modalForm" @ok="modalFormOk"></prepar-sampling-intf-modal>
  </a-card>
</template>

<script>

  import '@/assets/less/TableExpand.less'
  import { mixinDevice } from '@/utils/mixin'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import PreparSamplingIntfModal from './modules/PreparSamplingIntfModal'

  export default {
    name: 'PreparSamplingIntfList',
    mixins:[JeecgListMixin, mixinDevice],
    components: {
      PreparSamplingIntfModal
    },
    data () {
      return {
        description: 'prepar_sampling_intf管理页面',
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
            title:'信息记录ID',
            align:"center",
            dataIndex: 'samplingIntfRecId'
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
            title:'煤种',
            align:"center",
            dataIndex: 'coalType'
          },
          {
            title:'粒度 1：13mm≤粒度＜50mm 2：50mm≤粒度＜100mm',
            align:"center",
            dataIndex: 'coalSize'
          },
          {
            title:'水分 1：湿煤2：一般湿煤3：干煤4：一般干煤',
            align:"center",
            dataIndex: 'coalWater'
          },
          {
            title:'样品重量',
            align:"center",
            dataIndex: 'coalQty'
          },
          {
            title:'样品包数',
            align:"center",
            dataIndex: 'packCnt'
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
            title: '操作',
            dataIndex: 'action',
            align:"center",
            fixed:"right",
            width:147,
            scopedSlots: { customRender: 'action' }
          }
        ],
        url: {
          list: "/modules/preparSamplingIntf/list",
          delete: "/modules/preparSamplingIntf/delete",
          deleteBatch: "/modules/preparSamplingIntf/deleteBatch",
          exportXlsUrl: "/modules/preparSamplingIntf/exportXls",
          importExcelUrl: "modules/preparSamplingIntf/importExcel",
          
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
        fieldList.push({type:'int',value:'samplingIntfRecId',text:'信息记录ID'})
        fieldList.push({type:'string',value:'sampleCode',text:'采样编码'})
        fieldList.push({type:'string',value:'samplingCode',text:'制样编码'})
        fieldList.push({type:'string',value:'coalType',text:'煤种'})
        fieldList.push({type:'string',value:'coalSize',text:'粒度 1：13mm≤粒度＜50mm 2：50mm≤粒度＜100mm'})
        fieldList.push({type:'string',value:'coalWater',text:'水分 1：湿煤2：一般湿煤3：干煤4：一般干煤'})
        fieldList.push({type:'number',value:'coalQty',text:'样品重量'})
        fieldList.push({type:'int',value:'packCnt',text:'样品包数'})
        fieldList.push({type:'string',value:'dataStatus',text:'数据状态 0: 未读取 1: 已读取'})
        fieldList.push({type:'date',value:'insertTime',text:'建立时间'})
        fieldList.push({type:'string',value:'opCode',text:'建立用户'})
        fieldList.push({type:'string',value:'updateCode',text:'更新用户'})
        this.superFieldList = fieldList
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>