<template>

  <a-card>
    <!--查询区域-->
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

          <a-col :md="4" :sm="6">
            <a-form-item label="运输方式">
              <j-dict-select-tag  v-model="queryParam.batchNoType" placeholder="请选择" dictCode="gk_ysfs"/>
            </a-form-item>
          </a-col>

          <a-col :md="4" :sm="6">
            <a-form-item label="批次号">
              <a-input placeholder="请输入批次号" v-model="queryParam.batchNo" ></a-input>
            </a-form-item>
          </a-col>


          <a-col :md="4" :sm="6">
            <a-form-item label="封装编码">
              <a-input placeholder="请输入封装编码" v-model="queryParam.packCode"></a-input>
            </a-form-item>
          </a-col>

          <a-col :md="6" :sm="10">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
               <a-button type="primary" @click="searchCard" icon="search">扫描卡</a-button>
              &nbsp;
               <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>


             <!-- <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>-->
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>


    <!-- table区域-begin -->
    <div>
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
        class="j-table-force-nowrap"
        @change="handleTableChange">
        <!-- :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"-->

        <span slot="action" slot-scope="text, record">
          <a @click="printCode(record)">打印条码</a>

          <a-divider type="vertical" />

        </span>

      </a-table>
    </div>

    <a-modal
      v-model="visible"
      title="化验条形码打印"
      :footer="null"

      width="35%"

    >
      <section ref="print" id="printContent" class="abcdefg">
        <barcode :value="barcodeNum" :option="barcode_option"></barcode>
      </section>
<!--      <a-button  @click="close1"type="primary">打印</a-button>-->
      <a-button v-print="'#printContent'" ghost type="primary">打印</a-button>

    </a-modal>


    <!--<lab-report-modal ref="modalForm" @ok="modalFormOk"></lab-report-modal>-->
  </a-card>
</template>

<script>
  import '@/assets/less/TableExpand.less'
  import { mixinDevice } from '@/utils/mixin'
  import moment from 'moment'
  import { BusinessListMixin } from '@/mixins/BusinessListMixin'
  import {JeecgListMixin} from '@/mixins/JeecgListMixin'
  //引用生成条形码插件
  import VueBarcode from '@xkeshi/vue-barcode'
  //引入化验条形码弹窗
  import LabCodeDialog from '@views/znrl/lab/labReportView/modules/LabCodeDialog'
  import PrintLabCode from '@views/znrl/lab/labReportView/modules/PrintLabCode'
  import { getAction } from '@api/manage'


  export default {
  name: 'LabReportManLaborList',
    mixins:[BusinessListMixin, mixinDevice,JeecgListMixin],

    //注册组件
    components: {
      'barcode': VueBarcode,
      LabCodeDialog,
      PrintLabCode,
    },

    data () {
      return {
        description: 'lab_report条码打印页面',

        queryParam:{beginDate: moment().subtract('days', 2).format('YYYY-MM-DD'),endDate:moment().format('YYYY-MM-DD')},
        isorter:{
          column: 'insertTime',
          order: 'desc',
        },
        visible: false,
        //barcodeNumShow:false,
        //条形码数据和样式
        barcodeNum: '',
        barcode_option:{
          displayValue:true,
          background:'#fff',
          width:1,

        },

        /*isMask:false,
        propsItem:{
          title:'我是dialog标题',
          contents:'我是dialog内容',
          ok:'我是确认按钮',
          cancel:'我是取消按钮',
          isMask:true,
          rgba:true
        },*/
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
            title:'制样编码',
            align:"center",
            dataIndex: 'samplingCode'
          },
          /*{
            title:'是否存在有限氢值',
            align:"center",
            dataIndex: 'latestOpTime',
            customRender:function (text) {
              return !text?"":(text.length>10?text.substr(0,10):text)
            }
          },*/
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
          list: "/znrl/labory/qryManInfo",
          delete: "/modules/labReport/delete",
          deleteBatch: "/modules/labReport/deleteBatch",
          exportXlsUrl: "/modules/labReport/exportXls",
          importExcelUrl: "modules/labReport/importExcel",
          getCardBySamplingUrl:"znrl/labory/getCardBySampling",

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
        dictOptions:{},
        superFieldList:[],
      }
    },
    methods: {

      showModal() {
        this.visible = true;

      },
      handleOktan(e) {
        console.log(e);
        this.visible = false;
      },
      handleCancel(e) {
        console.log(e);
        this.visible = false;
      },
      moment,
      getDateRange(beginDate,endDate){
        return[
          beginDate,
          endDate
        ];
      },

      //给时间查询条件赋值
      /*initDictConfig(){
        console.log("实现");
        this.queryParam.beginTime = moment().subtract('days', 1);
        this.queryParam.endTime = moment();
        this.disableMixinCreated = true;
        console.log(this.queryParam.endTime+"--"+this.queryParam.beginTime);
      },*/
      onChange(dates, dateStrings) {
        this.barcodeNumShow =true;
        this.queryParam.beginDate = dateStrings[0];
        this.queryParam.endDate = dateStrings[1];
      },
      searchCard(){
        getAction(this.url.getCardBySamplingUrl,).then((res) => {
          // console.log(res);
          if (res.code==0){
            this.$message.success('上传成功');
          }
          else {
            this.$message.error(res.message);
          }
        })
      },
      dialogVisible(){
        this.barcodeNumShow =false;
      },
      printCode(record){
        this.visible = true;
        console.log(record);
        this.barcodeNum = record.laborCode;
       // this.propsItem.isMask = this.isMask = true;
       //this.barcodeNumShow =true;
         /* this.$alert('打印条形码', {
            confirmButtonText: '打印',
            callback: action => {
              this.$message({
                type: 'info',
                message: `action: ${ action }`
              });
            }
          });*/
      }
    }
  }


</script>

<style scoped>
@import '~@assets/less/common.less';
</style>