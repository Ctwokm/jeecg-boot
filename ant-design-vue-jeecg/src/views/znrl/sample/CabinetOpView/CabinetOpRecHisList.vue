<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :md="6" :sm="24">
            <a-form-item label="操作日期">
              <a-range-picker
                :ranges="{ 当天: [moment(), moment()], '三天内': [moment().subtract('days', 2), moment()], '一周内': [moment().subtract('days', 6), moment()], '本月': [moment().subtract('month',1), moment()] }"
                @change="onChange"
                format="YYYY-MM-DD"
              />
            </a-form-item>
          </a-col>
          <a-col :md="5" :sm="24">
            <a-form-item label="煤样类型">
              <a-select style="width: 100%" v-model="queryParam.sampleType" >
                <a-select-option value="">
                  全部
                </a-select-option>
                <a-select-option value="61">
                  6mm全水样
                </a-select-option>
                <a-select-option value="62">
                  6mm存查样
                </a-select-option>
                <a-select-option value="31">
                  3mm化验样
                </a-select-option>
                <a-select-option value="32">
                  3mm存查样
                </a-select-option>
                <a-select-option value="21">
                  0.2mm化验样
                </a-select-option>
                <a-select-option value="22">
                  0.2mm存查样
                </a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <span style="float: right;overflow: hidden;" class="table-page-search-submitButtons">
            <a-button type="primary" style="margin-left: 15px" @click="searchQuery" icon="search">查询</a-button>
          </span>
        </a-row>

      </a-form>
    </div>
    <!-- 查询区域-END -->

    <!-- 操作按钮区域 -->
    <div class="table-operator">
<!--      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>-->
<!--      <a-button type="primary" icon="download" @click="handleExportXls('cabinet_op_rec_his')">导出</a-button>-->
<!--      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">-->
<!--        <a-button type="primary" icon="import">导入</a-button>-->
<!--      </a-upload>-->
<!--      &lt;!&ndash; 高级查询区域 &ndash;&gt;-->
<!--      <j-super-query :fieldList="superFieldList" ref="superQueryModal" @handleSuperQuery="handleSuperQuery"></j-super-query>-->
<!--      <a-dropdown v-if="selectedRowKeys.length > 0">-->
<!--        <a-menu slot="overlay">-->
<!--          <a-menu-item key="1" @click="batchDel"><a-icon type="delete"/>删除</a-menu-item>-->
<!--        </a-menu>-->
<!--        <a-button style="margin-left: 8px"> 批量操作 <a-icon type="down" /></a-button>-->
<!--      </a-dropdown>-->
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

    <cabinet-op-rec-his-modal ref="modalForm" @ok="modalFormOk"></cabinet-op-rec-his-modal>
  </a-card>
</template>

<script>

  import '@/assets/less/TableExpand.less'
  import { mixinDevice } from '@/utils/mixin'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import CabinetOpRecHisModal from './modules/CabinetOpRecHisModal'
  import moment from 'moment'
  import { httpAction, getAction } from '@/api/manage'

  export default {
    name: 'CabinetOpRecHisList',
    mixins:[JeecgListMixin, mixinDevice],
    components: {
      CabinetOpRecHisModal
    },
    data () {
      return {
        description: 'cabinet_op_rec_his管理页面',
        /* 排序参数 */
        isorter:{
          column: 'insert_time',
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
            title:'记录ID',
            align:"center",
            dataIndex: 'recHisId'
          },
          {
            title:'样品批次号',
            align:"center",
            dataIndex: 'batchNo'
          },
          {
            title:'制样编码',
            align:"center",
            dataIndex: 'samplingCode'
          },
          {
            title:'操作时间',
            align:"center",
            dataIndex: 'opTime',
            customRender:function (text) {
              return !text?"":(text.length>10?text.substr(0,10):text)
            }
          },
          {
            title:'煤样类型',
            align:"center",
            dataIndex: 'sampleType',
            customRender:function (text) {
              let st_type = '';
              switch (text){
                case '61':
                  st_type = '6mm全水样';
                  break;
                case '62':
                  st_type = '6mm存查样';
                  break;
                case '31':
                  st_type = '3mm化验样';
                  break;
                case '32':
                  st_type = '3mm存查样';
                  break;
                case '21':
                  st_type = '0.2mm化验样';
                  break;
                case '22':
                  st_type = '0.2mm存查样';
                  break;
              }
              return st_type;

            }
          },
          {
            title:'样瓶编码',
            align:"center",
            dataIndex: 'packCode'
          },
          // {
          //   title:'柜名;存查样柜1-10',
          //   align:"center",
          //   dataIndex: 'ggName'
          // },
          // {
          //   title:'层名 ',
          //   align:"center",
          //   dataIndex: 'ccName'
          // },
          // {
          //   title:'位名 1到30',
          //   align:"center",
          //   dataIndex: 'wwName'
          // },
          {
            title:'操作类型',
            align:"center",
            dataIndex: 'opType',
            customRender:function (text) {
              let op_type = '';
              switch (text){
                case '1':
                  op_type = '自动存样';
                  break;
                case '2':
                  op_type = '自动取样';
                  break;
                case '3':
                  op_type = '自动弃样';
                  break;
                case '4':
                  op_type = '直传';
                  break;
                case '5':
                  op_type = '人工存样';
                  break;
                case '6':
                  op_type = '人工取样';
                  break;
                case '7':
                  op_type = '人工弃样';
                  break;
              }
              return op_type;

            }
          },
          {
            title:'目的站',
            align:"center",
            dataIndex: 'opDest'
          },
          {
            title:'操作状态',
            align:"center",
            dataIndex: 'opResult',
            customRender:function (text) {
              return !text?"":(text=="1"?"完成":"未完成")
            }
          },
          {
            title:'操作人',
            align:"center",
            dataIndex: 'opName'
          }
          /*{
            title:'存取煤样的来源设备名称',
            align:"center",
            dataIndex: 'sourceName'
          },

          {
            title:'制样时间',
            align:"center",
            dataIndex: 'makeSampleTime',
            customRender:function (text) {
              return !text?"":(text.length>10?text.substr(0,10):text)
            }
          },*/

          // {
          //   title:'建立时间',
          //   align:"center",
          //   dataIndex: 'insertTime',
          //   customRender:function (text) {
          //     return !text?"":(text.length>10?text.substr(0,10):text)
          //   }
          // },
          // {
          //   title:'建立用户',
          //   align:"center",
          //   dataIndex: 'opCode'
          // },
          // {
          //   title:'更新用户',
          //   align:"center",
          //   dataIndex: 'updateCode'
          // },
          /*{
            title: '操作',
            dataIndex: 'action',
            align:"center",
            fixed:"right",
            width:147,
            scopedSlots: { customRender: 'action' }
          }*/
        ],
        url: {
          // list: "/common/cabinetOpRecHis/list",
          list: "/znrl/cabinet/qryCabinetOpRec",
          delete: "/common/cabinetOpRecHis/delete",
          deleteBatch: "/common/cabinetOpRecHis/deleteBatch",
          exportXlsUrl: "/common/cabinetOpRecHis/exportXls",
          importExcelUrl: "common/cabinetOpRecHis/importExcel",

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
      moment,
      onChange(dates, dateStrings) {
        this.queryParam.depositStartTime = dateStrings[0]
        this.queryParam.depositEndTime = dateStrings[1]
      },
      getSuperFieldList(){
        let fieldList=[];
        fieldList.push({type:'int',value:'recHisId',text:'记录ID'})
        fieldList.push({type:'string',value:'sampleType',text:'煤样类型  61:6mm全水样 62:6mm存查样 31:3mm化验样 32:3mm存查样 21:0.2mm化验样 22:0.2mm存查样'})
        fieldList.push({type:'string',value:'packCode',text:'样瓶编码'})
        fieldList.push({type:'string',value:'ggName',text:'柜名;存查样柜1-10'})
        fieldList.push({type:'string',value:'ccName',text:'层名'})
        fieldList.push({type:'string',value:'wwName',text:'位名 1到30'})
        fieldList.push({type:'string',value:'opType',text:'样品类型'})
        fieldList.push({type:'date',value:'opTime',text:'存取操作时间'})
        fieldList.push({type:'string',value:'operator',text:'操作人'})
        fieldList.push({type:'string',value:'sourceName',text:'存取煤样的来源设备名称'})
        fieldList.push({type:'string',value:'destName',text:'存取煤样的目标设备名称'})
        fieldList.push({type:'date',value:'makeSampleTime',text:'制样时间'})
        fieldList.push({type:'string',value:'isSuccessed',text:'1：是 0：否'})
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