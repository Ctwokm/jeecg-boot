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
      <a-button type="primary" icon="download" @click="handleExportXls('lab_report')">导出</a-button>
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

    <lab-report-modal ref="modalForm" @ok="modalFormOk"></lab-report-modal>
  </a-card>
</template>

<script>

  import '@/assets/less/TableExpand.less'
  import { mixinDevice } from '@/utils/mixin'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import LabReportModal from './modules/LabReportModal'

  export default {
    name: 'LabReportList',
    mixins:[JeecgListMixin, mixinDevice],
    components: {
      LabReportModal
    },
    data () {
      return {
        description: 'lab_report管理页面',
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
            title:'记录号',
            align:"center",
            dataIndex: 'labReportId'
          },
          {
            title:'制样编码',
            align:"center",
            dataIndex: 'laborCode'
          },
          {
            title:'1初次化验 2第2次 3第3次 4第4次',
            align:"center",
            dataIndex: 'batchType'
          },
          {
            title:'全水',
            align:"center",
            dataIndex: 'mt'
          },
          {
            title:'水分',
            align:"center",
            dataIndex: 'mad'
          },
          {
            title:'空干基灰分',
            align:"center",
            dataIndex: 'aad'
          },
          {
            title:'干燥基灰分',
            align:"center",
            dataIndex: 'ad'
          },
          {
            title:'收到基灰分',
            align:"center",
            dataIndex: 'aar'
          },
          {
            title:'空干基挥发分',
            align:"center",
            dataIndex: 'vad'
          },
          {
            title:'干燥基挥发分',
            align:"center",
            dataIndex: 'vd'
          },
          {
            title:'收到基挥发分',
            align:"center",
            dataIndex: 'var'
          },
          {
            title:'干燥无灰基挥发分',
            align:"center",
            dataIndex: 'vdaf'
          },
          {
            title:'空干基全硫',
            align:"center",
            dataIndex: 'stAd'
          },
          {
            title:'干燥基全硫',
            align:"center",
            dataIndex: 'stD'
          },
          {
            title:'收到基全硫',
            align:"center",
            dataIndex: 'stAr'
          },
          {
            title:'干燥基氢',
            align:"center",
            dataIndex: 'hd'
          },
          {
            title:'空气干燥基氢',
            align:"center",
            dataIndex: 'had'
          },
          {
            title:'收到基氢',
            align:"center",
            dataIndex: 'har'
          },
          {
            title:'干燥无灰基氢',
            align:"center",
            dataIndex: 'hdaf'
          },
          {
            title:'空气干燥基碳',
            align:"center",
            dataIndex: 'cad'
          },
          {
            title:'空气干燥基氮',
            align:"center",
            dataIndex: 'nad'
          },
          {
            title:'空干基弹筒发热量',
            align:"center",
            dataIndex: 'qbAd'
          },
          {
            title:'收到基弹筒发热量',
            align:"center",
            dataIndex: 'qbAr'
          },
          {
            title:'干燥基弹筒发热量',
            align:"center",
            dataIndex: 'qbD'
          },
          {
            title:'干燥无灰基弹筒发热量',
            align:"center",
            dataIndex: 'qbDaf'
          },
          {
            title:'空干基高位发热量',
            align:"center",
            dataIndex: 'qgrAd'
          },
          {
            title:'收到基高位发热量',
            align:"center",
            dataIndex: 'qgrAr'
          },
          {
            title:'干燥基高位发热量',
            align:"center",
            dataIndex: 'qgrD'
          },
          {
            title:'干燥无灰基高位发热量',
            align:"center",
            dataIndex: 'qgrDaf'
          },
          {
            title:'空干基低位发热量',
            align:"center",
            dataIndex: 'qnetAd'
          },
          {
            title:'收到基低位发热量J',
            align:"center",
            dataIndex: 'qnetArj'
          },
          {
            title:'收到基低位发热量M',
            align:"center",
            dataIndex: 'qnetArm'
          },
          {
            title:'干燥基低位发热量',
            align:"center",
            dataIndex: 'qnetD'
          },
          {
            title:'空干基固定碳',
            align:"center",
            dataIndex: 'fcad'
          },
          {
            title:'收到基固定碳',
            align:"center",
            dataIndex: 'fcar'
          },
          {
            title:'干燥基固定碳',
            align:"center",
            dataIndex: 'fcd'
          },
          {
            title:'干燥无灰基固定碳',
            align:"center",
            dataIndex: 'fcdaf'
          },
          {
            title:'焦渣特性',
            align:"center",
            dataIndex: 'crc'
          },
          {
            title:'变形温度',
            align:"center",
            dataIndex: 'dt'
          },
          {
            title:'软化温度',
            align:"center",
            dataIndex: 'st'
          },
          {
            title:'半球温度',
            align:"center",
            dataIndex: 'ht'
          },
          {
            title:'流动温度',
            align:"center",
            dataIndex: 'ft'
          },
          {
            title:'加权类型  1按比例加权  2按吨位加权',
            align:"center",
            dataIndex: 'proportionType'
          },
          {
            title:'加权值',
            align:"center",
            dataIndex: 'proportion'
          },
          {
            title:'加权标识 0未加权 1已加权 9加权后报告',
            align:"center",
            dataIndex: 'proportionTag'
          },
          {
            title:'0：未审核；1：审核中；2：审核通过；3：审核未通过',
            align:"center",
            dataIndex: 'apprStatus'
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
            title:'化验日期',
            align:"center",
            dataIndex: 'hyDate',
            customRender:function (text) {
              return !text?"":(text.length>10?text.substr(0,10):text)
            }
          },
          {
            title:'更新用户',
            align:"center",
            dataIndex: 'updateCode'
          },
          {
            title:'更新时间',
            align:"center",
            dataIndex: 'jhTime',
            customRender:function (text) {
              return !text?"":(text.length>10?text.substr(0,10):text)
            }
          },
          {
            title:'更新用户',
            align:"center",
            dataIndex: 'jhCode'
          },
          {
            title:'建立用户',
            align:"center",
            dataIndex: 'opCode'
          },
          {
            title:'opDesc',
            align:"center",
            dataIndex: 'opDesc'
          },
          {
            title:'备注',
            align:"center",
            dataIndex: 'remark'
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
          list: "/modules/labReport/list",
          delete: "/modules/labReport/delete",
          deleteBatch: "/modules/labReport/deleteBatch",
          exportXlsUrl: "/modules/labReport/exportXls",
          importExcelUrl: "modules/labReport/importExcel",
          
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
        fieldList.push({type:'int',value:'labReportId',text:'记录号'})
        fieldList.push({type:'string',value:'laborCode',text:'制样编码'})
        fieldList.push({type:'string',value:'batchType',text:'1初次化验 2第2次 3第3次 4第4次'})
        fieldList.push({type:'number',value:'mt',text:'全水'})
        fieldList.push({type:'number',value:'mad',text:'水分'})
        fieldList.push({type:'number',value:'aad',text:'空干基灰分'})
        fieldList.push({type:'number',value:'ad',text:'干燥基灰分'})
        fieldList.push({type:'number',value:'aar',text:'收到基灰分'})
        fieldList.push({type:'number',value:'vad',text:'空干基挥发分'})
        fieldList.push({type:'number',value:'vd',text:'干燥基挥发分'})
        fieldList.push({type:'number',value:'var',text:'收到基挥发分'})
        fieldList.push({type:'number',value:'vdaf',text:'干燥无灰基挥发分'})
        fieldList.push({type:'number',value:'stAd',text:'空干基全硫'})
        fieldList.push({type:'number',value:'stD',text:'干燥基全硫'})
        fieldList.push({type:'number',value:'stAr',text:'收到基全硫'})
        fieldList.push({type:'number',value:'hd',text:'干燥基氢'})
        fieldList.push({type:'number',value:'had',text:'空气干燥基氢'})
        fieldList.push({type:'number',value:'har',text:'收到基氢'})
        fieldList.push({type:'number',value:'hdaf',text:'干燥无灰基氢'})
        fieldList.push({type:'number',value:'cad',text:'空气干燥基碳'})
        fieldList.push({type:'number',value:'nad',text:'空气干燥基氮'})
        fieldList.push({type:'number',value:'qbAd',text:'空干基弹筒发热量'})
        fieldList.push({type:'number',value:'qbAr',text:'收到基弹筒发热量'})
        fieldList.push({type:'number',value:'qbD',text:'干燥基弹筒发热量'})
        fieldList.push({type:'number',value:'qbDaf',text:'干燥无灰基弹筒发热量'})
        fieldList.push({type:'number',value:'qgrAd',text:'空干基高位发热量'})
        fieldList.push({type:'number',value:'qgrAr',text:'收到基高位发热量'})
        fieldList.push({type:'number',value:'qgrD',text:'干燥基高位发热量'})
        fieldList.push({type:'number',value:'qgrDaf',text:'干燥无灰基高位发热量'})
        fieldList.push({type:'number',value:'qnetAd',text:'空干基低位发热量'})
        fieldList.push({type:'number',value:'qnetArj',text:'收到基低位发热量J'})
        fieldList.push({type:'number',value:'qnetArm',text:'收到基低位发热量M'})
        fieldList.push({type:'number',value:'qnetD',text:'干燥基低位发热量'})
        fieldList.push({type:'number',value:'fcad',text:'空干基固定碳'})
        fieldList.push({type:'number',value:'fcar',text:'收到基固定碳'})
        fieldList.push({type:'number',value:'fcd',text:'干燥基固定碳'})
        fieldList.push({type:'number',value:'fcdaf',text:'干燥无灰基固定碳'})
        fieldList.push({type:'int',value:'crc',text:'焦渣特性'})
        fieldList.push({type:'number',value:'dt',text:'变形温度'})
        fieldList.push({type:'number',value:'st',text:'软化温度'})
        fieldList.push({type:'number',value:'ht',text:'半球温度'})
        fieldList.push({type:'number',value:'ft',text:'流动温度'})
        fieldList.push({type:'string',value:'proportionType',text:'加权类型  1按比例加权  2按吨位加权'})
        fieldList.push({type:'number',value:'proportion',text:'加权值'})
        fieldList.push({type:'string',value:'proportionTag',text:'加权标识 0未加权 1已加权 9加权后报告'})
        fieldList.push({type:'string',value:'apprStatus',text:'0：未审核；1：审核中；2：审核通过；3：审核未通过'})
        fieldList.push({type:'date',value:'insertTime',text:'建立时间'})
        fieldList.push({type:'date',value:'hyDate',text:'化验日期'})
        fieldList.push({type:'string',value:'updateCode',text:'更新用户'})
        fieldList.push({type:'date',value:'jhTime',text:'更新时间'})
        fieldList.push({type:'string',value:'jhCode',text:'更新用户'})
        fieldList.push({type:'string',value:'opCode',text:'建立用户'})
        fieldList.push({type:'string',value:'opDesc',text:'opDesc'})
        fieldList.push({type:'string',value:'remark',text:'备注'})
        this.superFieldList = fieldList
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>