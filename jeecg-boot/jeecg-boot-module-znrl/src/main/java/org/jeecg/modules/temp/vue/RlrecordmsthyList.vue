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
      <a-button type="primary" icon="download" @click="handleExportXls('rlrecordmsthy')">导出</a-button>
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

    <rlrecordmsthy-modal ref="modalForm" @ok="modalFormOk"></rlrecordmsthy-modal>
  </a-card>
</template>

<script>

  import '@/assets/less/TableExpand.less'
  import { mixinDevice } from '@/utils/mixin'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import RlrecordmsthyModal from './modules/RlrecordmsthyModal'

  export default {
    name: 'RlrecordmsthyList',
    mixins:[JeecgListMixin, mixinDevice],
    components: {
      RlrecordmsthyModal
    },
    data () {
      return {
        description: 'rlrecordmsthy管理页面',
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
            title:'供应商',
            align:"center",
            dataIndex: 'venNo'
          },
          {
            title:'承运商',
            align:"center",
            dataIndex: 'carrierNo'
          },
          {
            title:'煤品种',
            align:"center",
            dataIndex: 'coalNo'
          },
          {
            title:'煤矿',
            align:"center",
            dataIndex: 'colryNo'
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
            title:'票重',
            align:"center",
            dataIndex: 'tickQty'
          },
          {
            title:'毛重',
            align:"center",
            dataIndex: 'mzQty'
          },
          {
            title:'皮重',
            align:"center",
            dataIndex: 'pzQty'
          },
          {
            title:'净重',
            align:"center",
            dataIndex: 'netQty'
          },
          {
            title:'运损',
            align:"center",
            dataIndex: 'lossQty'
          },
          {
            title:'扣吨',
            align:"center",
            dataIndex: 'kdQty'
          },
          {
            title:'亏吨',
            align:"center",
            dataIndex: 'kudQty'
          },
          {
            title:'盈吨',
            align:"center",
            dataIndex: 'ydQty'
          },
          {
            title:'称重时间',
            align:"center",
            dataIndex: 'czDtm',
            customRender:function (text) {
              return !text?"":(text.length>10?text.substr(0,10):text)
            }
          },
          {
            title:'检轻时间',
            align:"center",
            dataIndex: 'jqDtm',
            customRender:function (text) {
              return !text?"":(text.length>10?text.substr(0,10):text)
            }
          },
          {
            title:'卸煤时间',
            align:"center",
            dataIndex: 'xmDtm',
            customRender:function (text) {
              return !text?"":(text.length>10?text.substr(0,10):text)
            }
          },
          {
            title:'速度',
            align:"center",
            dataIndex: 'carSpd'
          },
          {
            title:'火车来煤批次号',
            align:"center",
            dataIndex: 'trainBatchNo'
          },
          {
            title:'采样编码',
            align:"center",
            dataIndex: 'sampleCode'
          },
          {
            title:'采样方式 ：0：皮带采样  1：皮带大水分旁路 2：人工采样 3：车厢采样 4：车厢大水分旁路',
            align:"center",
            dataIndex: 'sampleTyp'
          },
          {
            title:'是否空车，1：为非空车，0或者null为空车',
            align:"center",
            dataIndex: 'emptyFlg'
          },
          {
            title:'已出厂(0:否、1:是)',
            align:"center",
            dataIndex: 'leaveFlg'
          },
          {
            title:'入厂时间',
            align:"center",
            dataIndex: 'recordDtm',
            customRender:function (text) {
              return !text?"":(text.length>10?text.substr(0,10):text)
            }
          },
          {
            title:'出厂时间',
            align:"center",
            dataIndex: 'leaveDtm',
            customRender:function (text) {
              return !text?"":(text.length>10?text.substr(0,10):text)
            }
          },
          {
            title:'翻车机编号',
            align:"center",
            dataIndex: 'fcjCode'
          },
          {
            title:'上车单是否确认 0 ：未确认 1：已确认',
            align:"center",
            dataIndex: 'cfmFlg'
          },
          {
            title:'发站',
            align:"center",
            dataIndex: 'startPlace'
          },
          {
            title:'到站',
            align:"center",
            dataIndex: 'finalPlace'
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
            title:'备注',
            align:"center",
            dataIndex: 'remark'
          },
          {
            title:'入厂轨道号  1号轨道 1#；2号轨道 2#；3号轨道 3# ...',
            align:"center",
            dataIndex: 'trackNo'
          },
          {
            title:'过衡号，1号衡：1;   2号衡：2;',
            align:"center",
            dataIndex: 'balanceNo'
          },
          {
            title:'对位时间',
            align:"center",
            dataIndex: 'aimTime',
            customRender:function (text) {
              return !text?"":(text.length>10?text.substr(0,10):text)
            }
          },
          {
            title:'附加批次号，如底样面样的',
            align:"center",
            dataIndex: 'attachBatchNo'
          },
          {
            title:'收货人（配置ID）',
            align:"center",
            dataIndex: 'goodsReceiver'
          },
          {
            title:'发站时间',
            align:"center",
            dataIndex: 'deliverTime',
            customRender:function (text) {
              return !text?"":(text.length>10?text.substr(0,10):text)
            }
          },
          {
            title:'合同编码',
            align:"center",
            dataIndex: 'contractNo'
          },
          {
            title:'入厂流水',
            align:"center",
            dataIndex: 'recordNo'
          },
          {
            title:'大票编号',
            align:"center",
            dataIndex: 'tickNo'
          },
          {
            title:'车次',
            align:"center",
            dataIndex: 'trainNo'
          },
          {
            title:'卡号',
            align:"center",
            dataIndex: 'cardId'
          },
          {
            title:'车牌号',
            align:"center",
            dataIndex: 'carId'
          },
          {
            title:'火车车型',
            align:"center",
            dataIndex: 'carTyp'
          },
          {
            title:'车厢序号',
            align:"center",
            dataIndex: 'xhNum'
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
          list: "/modules/rlrecordmsthy/list",
          delete: "/modules/rlrecordmsthy/delete",
          deleteBatch: "/modules/rlrecordmsthy/deleteBatch",
          exportXlsUrl: "/modules/rlrecordmsthy/exportXls",
          importExcelUrl: "modules/rlrecordmsthy/importExcel",
          
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
        fieldList.push({type:'string',value:'venNo',text:'供应商'})
        fieldList.push({type:'string',value:'carrierNo',text:'承运商'})
        fieldList.push({type:'string',value:'coalNo',text:'煤品种'})
        fieldList.push({type:'string',value:'colryNo',text:'煤矿'})
        fieldList.push({type:'string',value:'coalSize',text:'粒度 1：13mm≤粒度＜50mm 2：50mm≤粒度＜100mm'})
        fieldList.push({type:'string',value:'coalWater',text:'水分 1：湿煤2：一般湿煤3：干煤4：一般干煤'})
        fieldList.push({type:'number',value:'tickQty',text:'票重'})
        fieldList.push({type:'number',value:'mzQty',text:'毛重'})
        fieldList.push({type:'number',value:'pzQty',text:'皮重'})
        fieldList.push({type:'number',value:'netQty',text:'净重'})
        fieldList.push({type:'number',value:'lossQty',text:'运损'})
        fieldList.push({type:'number',value:'kdQty',text:'扣吨'})
        fieldList.push({type:'number',value:'kudQty',text:'亏吨'})
        fieldList.push({type:'number',value:'ydQty',text:'盈吨'})
        fieldList.push({type:'date',value:'czDtm',text:'称重时间'})
        fieldList.push({type:'date',value:'jqDtm',text:'检轻时间'})
        fieldList.push({type:'date',value:'xmDtm',text:'卸煤时间'})
        fieldList.push({type:'number',value:'carSpd',text:'速度'})
        fieldList.push({type:'string',value:'trainBatchNo',text:'火车来煤批次号'})
        fieldList.push({type:'string',value:'sampleCode',text:'采样编码'})
        fieldList.push({type:'string',value:'sampleTyp',text:'采样方式 ：0：皮带采样  1：皮带大水分旁路 2：人工采样 3：车厢采样 4：车厢大水分旁路'})
        fieldList.push({type:'string',value:'emptyFlg',text:'是否空车，1：为非空车，0或者null为空车'})
        fieldList.push({type:'string',value:'leaveFlg',text:'已出厂(0:否、1:是)'})
        fieldList.push({type:'date',value:'recordDtm',text:'入厂时间'})
        fieldList.push({type:'date',value:'leaveDtm',text:'出厂时间'})
        fieldList.push({type:'string',value:'fcjCode',text:'翻车机编号'})
        fieldList.push({type:'string',value:'cfmFlg',text:'上车单是否确认 0 ：未确认 1：已确认'})
        fieldList.push({type:'string',value:'startPlace',text:'发站'})
        fieldList.push({type:'string',value:'finalPlace',text:'到站'})
        fieldList.push({type:'date',value:'insertTime',text:'建立时间'})
        fieldList.push({type:'string',value:'opCode',text:'建立用户'})
        fieldList.push({type:'string',value:'updateCode',text:'更新用户'})
        fieldList.push({type:'string',value:'remark',text:'备注'})
        fieldList.push({type:'string',value:'trackNo',text:'入厂轨道号  1号轨道 1#；2号轨道 2#；3号轨道 3# ...'})
        fieldList.push({type:'string',value:'balanceNo',text:'过衡号，1号衡：1;   2号衡：2;'})
        fieldList.push({type:'date',value:'aimTime',text:'对位时间'})
        fieldList.push({type:'string',value:'attachBatchNo',text:'附加批次号，如底样面样的'})
        fieldList.push({type:'string',value:'goodsReceiver',text:'收货人（配置ID）'})
        fieldList.push({type:'date',value:'deliverTime',text:'发站时间'})
        fieldList.push({type:'string',value:'contractNo',text:'合同编码'})
        fieldList.push({type:'int',value:'recordNo',text:'入厂流水'})
        fieldList.push({type:'string',value:'tickNo',text:'大票编号'})
        fieldList.push({type:'string',value:'trainNo',text:'车次'})
        fieldList.push({type:'string',value:'cardId',text:'卡号'})
        fieldList.push({type:'string',value:'carId',text:'车牌号'})
        fieldList.push({type:'string',value:'carTyp',text:'火车车型'})
        fieldList.push({type:'number',value:'xhNum',text:'车厢序号'})
        this.superFieldList = fieldList
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>