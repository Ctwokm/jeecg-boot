<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="32">

          <a-col :md="6" :sm="20">
            <a-form-item label="入厂日期">
              <a-range-picker
                :ranges="{ 当天: [moment(), moment()], '三天内': [moment().subtract('days', 2), moment()], '一周内': [moment().subtract('days', 6), moment()], '本月': [moment().subtract('month',1), moment()] }"
                @change="onChange"
                :default-value="getDateRange(queryParam.beginDate,queryParam.endDate)"
                format="YYYY-MM-DD"
              />
            </a-form-item>
          </a-col>

          <a-col :md="4" :sm="4">
            <a-form-item label="供煤单位">
              <j-dict-select-tag  v-model="queryParam.venName" placeholder="请选择" dictCode="vendor_info,ven_name ,ven_no "/>
            </a-form-item>
          </a-col>

          <a-col :md="3" :sm="4">
            <a-form-item label="煤种">
              <j-dict-select-tag  v-model="queryParam.coalName" placeholder="请选择" dictCode="coal_type,coal_name,coal_no"/>
            </a-form-item>
          </a-col>

          <a-col :md="3" :sm="4">
            <a-form-item label="发站">
              <j-dict-select-tag  v-model="queryParam.startPlace" placeholder="请选择" dictCode="dictionary_table,name,id,type ='START_RAIL_STATION'"/>
            </a-form-item>
          </a-col>




          <a-col :md="6" :sm="10">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">

              &nbsp;
               <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>

              <a-button type="primary" @click="submitAppr" icon="search">提交审批</a-button>

               <a-button type="primary" @click="previewRpt" icon="search">化验报告预览</a-button>

               <a-button type="primary" @click="previewOriginalReport" icon="search">原始报告预览</a-button>


              <!-- <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>-->
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->

    <!-- 操作按钮区域 -->
    <!--<div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('lab_report')">导出</a-button>
      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
        <a-button type="primary" icon="import">导入</a-button>
      </a-upload>
      &lt;!&ndash; 高级查询区域 &ndash;&gt;
      <j-super-query :fieldList="superFieldList" ref="superQueryModal" @handleSuperQuery="handleSuperQuery"></j-super-query>
      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel"><a-icon type="delete"/>删除</a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px"> 批量操作 <a-icon type="down" /></a-button>
      </a-dropdown>
    </div>-->

    <!-- table区域-begin -->
    <div>
      <!--<div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择 <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>项
        <a style="margin-left: 24px" @click="onClearSelected">清空</a>
      </div>-->

      <a-table
        ref="table"
        size="middle"
        :scroll="{x:true}"
        bordered
        rowKey="laborCode"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        class="j-table-force-nowrap"
        @change="handleTableChange">

        <!--<template slot="htmlSlot" slot-scope="text">
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
        </span>-->

      </a-table>
    </div>

   <!-- <lab-report-modal ref="modalForm" @ok="modalFormOk"></lab-report-modal>-->

    <a-modal
      v-model="visible"
      title="化验报告记录打印"
      :footer="null"
      width="75%"
    >
      <section ref="print" id="printContent" class="abcdefg">

        <iframe  :id="id" :src="this.url1" frameborder="0" width="100%" height="550px" scrolling="auto"></iframe>
      </section>
      <!--      <a-button  @click="close1"type="primary">打印</a-button>-->
      <a-button v-print="'#printContent'" ghost type="primary">打印</a-button>

    </a-modal>

    <a-modal
      v-model="visible1"
      title="化验原始记录打印"
      :footer="null"
      width="75%"
    >
      <section ref="print" id="printContent1" class="abcdefg">

        <iframe  :id="id" :src="this.url2" frameborder="0" width="100%" height="750px" scrolling="auto"></iframe>
      </section>
      <!--      <a-button  @click="close1"type="primary">打印</a-button>-->
      <a-button v-print="'#printContent'" ghost type="primary">打印</a-button>

    </a-modal>
  </a-card>
</template>

<script>

  import '@/assets/less/TableExpand.less'
  import { mixinDevice } from '@/utils/mixin'
  import { BusinessListMixin } from '@/mixins/BusinessListMixin'
  import LabReportModal from './modules/LabReportModal'
  import moment from 'moment'
  import { getAction,postAction } from '@api/manage'
  import { ACCESS_TOKEN } from '@/store/mutation-types'

  export default {
    name: 'LabReportList',
    mixins:[BusinessListMixin, mixinDevice],
    components: {
      LabReportModal
    },
    data () {
      return {
        description: 'lab_report管理页面',
        visible:false, //控制弹窗
        visible1:false,
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
            title:'化验编码',
            align:"center",
            dataIndex: 'laborCode'
          },
          {
            title:'煤种',
            align:"center",
            dataIndex: 'coalName'
          },
          {
            title:'化验时间',
            align:"center",
            dataIndex: 'insertTime',
            customRender:function (text) {
              return !text?"":(text.length>10?text.substr(0,10):text)
            }
          },
          {
            title:'审批状态',
            align:"center",
            dataIndex: 'apprStatus',
            customRender:function (text) {
              return !text?"":(text.length>10?text.substr(0,10):text)
            }
          },
          {
            title:'全水(mt)',
            align:"center",
            dataIndex: 'mt'
          },
          {
            title:'水分(mad)',
            align:"center",
            dataIndex: 'mad'
          },
          {
            title:'空干基灰分(aad)',
            align:"center",
            dataIndex: 'aad'
          },
          {
            title:'干燥基灰分(ad)',
            align:"center",
            dataIndex: 'ad'
          },
          {
            title:'收到基灰分(aar)',
            align:"center",
            dataIndex: 'aar'
          },
          {
            title:'空干基挥发分(vad)',
            align:"center",
            dataIndex: 'vad'
          },
          {
            title:'干燥基挥发分(vd)',
            align:"center",
            dataIndex: 'vd'
          },
          /*{
            title:'收到基挥发分(var)',
            align:"center",
            dataIndex: 'var'
          },*/
          {
            title:'干燥无灰基挥发分(vdaf)',
            align:"center",
            dataIndex: 'vdaf'
          },
          {
            title:'空干基全硫(stAd)',
            align:"center",
            dataIndex: 'stad'
          },
         /* {
            title:'干燥基全硫',
            align:"center",
            dataIndex: 'stD'
          },*/
         /* {
            title:'收到基全硫',
            align:"center",
            dataIndex: 'stAr'
          },*/
          /*{
            title:'干燥基氢',
            align:"center",
            dataIndex: 'hd'
          },*/
          {
            title:'空气干燥基氢(had)',
            align:"center",
            dataIndex: 'had'
          },
          {
            title:'空干基固定碳(fcad)',
            align:"center",
            dataIndex: 'fcad'
          },
          /*{
            title:'收到基氢',
            align:"center",
            dataIndex: 'har'
          },*/
          /*{
            title:'干燥无灰基氢',
            align:"center",
            dataIndex: 'hdaf'
          },*/
         /* {
            title:'空气干燥基碳',
            align:"center",
            dataIndex: 'cad'
          },*/
          /*{
            title:'空气干燥基氮',
            align:"center",
            dataIndex: 'nad'
          },*/
          {
            title:'空干基弹筒发热量(qbAd)',
            align:"center",
            dataIndex: 'qbad'
          },
          {
            title:'干燥基高位发热量(qgrD)',
            align:"center",
            dataIndex: 'qgrd'
          },
          /*{
            title:'收到基弹筒发热量',
            align:"center",
            dataIndex: 'qbAr'
          },*/
          /*{
            title:'干燥基弹筒发热量',
            align:"center",
            dataIndex: 'qbD'
          },*/
         /* {
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
            title:'干燥无灰基高位发热量',
            align:"center",
            dataIndex: 'qgrDaf'
          },
          {
            title:'空干基低位发热量',
            align:"center",
            dataIndex: 'qnetAd'
          },*/
          {
            title:'收到基低位发热量(qnetArj)',
            align:"center",
            dataIndex: 'qnetarj'
          },
          /*{
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
          */
          /*{
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
          }*/
        ],
        url1:"",//化验报告报表 拼接后
        id:"",
        url2:"",//化验原始数据报表 拼接后

        url: {
          list: "/znrl/labory/qryReportData",
          delete: "/modules/labReport/delete",
          deleteBatch: "/modules/labReport/deleteBatch",
          exportXlsUrl: "/modules/labReport/exportXls",
          importExcelUrl: "modules/labReport/importExcel",
          submitApprUrl:"/znrl/labory/qryReportData",
          previewRptUrl:"http://localhost:8088/jeecg-boot/ureport/preview?_u=file:report_data.ureport.xml",
          previewOriginalReportUrl:"http://localhost:8088/jeecg-boot/ureport/preview?_u=file:report_org_data.ureport.xml",

          //审批相关
          startProcess: "/activiti/startProcess",
          doCall: "/znrl/biztool/doCall"
        },
        dictOptions:{},
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
        superFieldList:[],
      }
    },
    created() {

    },
    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      },
    },
    methods: {
      initDictConfig(){
        this.isorter.column = 'createDate'
        this.isorter.order = 'desc'
      },
      onChange(dates, dateStrings) {
        this.queryParam.beginDate = dateStrings[0];
        this.queryParam.endDate = dateStrings[1];
      },
      moment,
      getDateRange(beginDate,endDate) {
        return [
          beginDate,
          endDate
        ];
      },

      //当表单某条信息被选择时触发
      onSelectChange(selectedRowKeys, selectionRows) {
        console.log('被选中的行主键: ', selectedRowKeys);
        this.selectedRowKeys = selectedRowKeys;
        this.selectionRows = selectionRows;
      },

      //提交审批 by hujw
      submitAppr(){
          if (this.selectedRowKeys.length > 0) {
            console.log('被选中的行: ', this.selectionRows);
            if (this.selectionRows[0].apprStatus === '未审批' || this.selectionRows[0].apprStatus === '审批不通过') {
              //流程启动参数
              var params = {};
              //具体业务参数
              var bizParams = this.selectionRows[0];
              //把审批流程参数追加到业务参数中
              bizParams.apprEventTypeCd = 'UploadProcess_RC_CZH_2';//假如审批流程名是sampleStoreProcess （与bpmn流程文件的流程名一致）
              //组装启动流程参数
              params.processKey = "UploadProcess_RC_CZH_2";//假如审批流程名是sampleStoreProcess （与bpmn流程文件的流程名一致）
              params.jsonString = JSON.stringify(bizParams);

              //正式启动流程
              postAction(this.url.startProcess, params).then((res) => {
                if (res.success) {
                  // this.$message.success(res.message);
                  //调用回写业务操作（将流程标记写入业务数据中，方便查看）
                  var param = {};
                  debugger
                  bizParams.oprType = 'appr';
                  bizParams.processInstId = res.result;
                  param.pkgName = 'lab_brc_oper_laborReport';
                  param.jsonString = JSON.stringify(bizParams);
                  postAction(this.url.doCall, param).then((res) => {
                    if (res.success) {
                      this.$message.success(res.message);

                    } else {
                      this.$message.warning(res.message);
                    }
                  })
                } else {
                  this.$message.warning(res.message);
                }
              })
              this.loadData();
            }else {
              this.$message.warning(" 只有未审批或审批不通过状态的化验报告才能++提交审批。");
              return
            }
          }else {
            this.$message.warning(" 请选择一条或多条未审批数据！");
            return
          }
      },
      previewRpt(){
        this.visible=true;
        var selectedRow = this.selectionRows;
        var laborCode = selectedRow[0].laborCode;
        if (laborCode !== null && laborCode !== undefined) {
          this.url1 = this.url.previewRptUrl+"&labor_code="+laborCode;
        }else {
          return ;
        }
      },

      previewOriginalReport(){
        this.visible1=true;
        var selectedRow = this.selectionRows;
        var laborCode = selectedRow[0].laborCode;
        if (laborCode !== null && laborCode !== undefined) {
          this.url2 = this.url.previewOriginalReportUrl+"&labor_code="+"123";
          console.log(this.url2);
        }else {
          return ;
        }
      },

    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>