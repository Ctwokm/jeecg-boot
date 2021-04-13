<template>

  <a-card>
    <!--查询区域-->
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="32">
          <a-col :md="4" :sm="10">
            <a-form-item label="化验编码">
              <!-- dict="batch_no_info,labor_code,labor_code,1=1 order by insert_time desc"-->
              <!--<a-input placeholder="请输入化验编码" v-model="queryParam.laborCode" ></a-input>-->
              <j-search-select-tag
                placeholder="请输入化验编码"
                v-model="queryParam.laborCode"
                dict="batch_no_info,labor_code,labor_code"

                :pageSize="6"
                :async="true">
              </j-search-select-tag>
            </a-form-item>
          </a-col>

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
            <a-form-item label="化验批次类型">
              <j-dict-select-tag  v-model="queryParam.batchType" placeholder="请选择" dictCode="	gk_hypclx"/>
            </a-form-item>
          </a-col>

          <a-col :md="4" :sm="6">
            <a-form-item label="确认状态">
              <j-dict-select-tag  v-model="queryParam.status" placeholder="请选择" dictCode="gk_qrzt"/>
            </a-form-item>
          </a-col>

          <a-col :md="6" :sm="10">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              &nbsp;
               <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
               <a-button type="primary" @click="handleEditDL" icon="danger">确认无效</a-button>
               <a-button type="primary" @click="handleAddDL" icon="primary">批量确认</a-button>

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
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        @change="handleTableChange"
        >

        <span slot="action" slot-scope="text, record">
          <a @click="printCode(record)">打印条码</a>
          <a-divider type="vertical" />
        </span>
      </a-table>
    </div>

    <template>
      <div>
        <!--<a-button type="primary" @click="showModal">
          Open Modal
        </a-button> @ok="handleOk"-->
        <a-modal v-model="visible" title="化验天平标准"  :footer="null" width="40%"  >
          <a-form>
            <a-row :gutter="12">
              <a-col :md="12" :sm="24">
                <a-form-item label="温度">
                  <a-input placeholder="请输入" id="temperature"  v-model="queryParam.temperature" ></a-input>
                </a-form-item>
              </a-col>

              <a-col :md="12" :sm="24">
                <a-form-item label="湿度">
                  <a-input placeholder="请输入" id="humidity" aria-readonly="true" v-model="queryParam.humidity" ></a-input>
                </a-form-item>
              </a-col>
            </a-row>

            <a-row :gutter="12">
              <a-col :md="12" :sm="24">
                <a-form-item label="执行标准">
                  <j-dict-select-tag  id="standard" v-model="queryParam.standard" placeholder="请选择" dictCode="gk_tpbz"/>
                </a-form-item>
              </a-col>

              <a-col :md="12" :sm="24">
                <a-form-item label="焦渣特性">
                  <a-input placeholder="请输入" id="lossStyle" v-model="queryParam.lossStyle" ></a-input>
                </a-form-item>
              </a-col>
            </a-row>

            <a-row :gutter="12">
              <a-col :md="12" :sm="24">
                <a-button type="primary" @click="handleOk1">
                  确定
                </a-button>
              </a-col>
            </a-row>


          </a-form>
        </a-modal>
      </div>
    </template>


    <!--<lab-report-modal ref="modalForm" @ok="modalFormOk"></lab-report-modal>-->
  </a-card>
</template>

<script>
import '@/assets/less/TableExpand.less'
import { mixinDevice } from '@/utils/mixin'
import moment from 'moment'
import { BusinessListMixin } from '@/mixins/BusinessListMixin'
import {JeecgListMixin} from '@/mixins/JeecgListMixin'
import { getAction } from '@api/manage'


export default {
  name: 'sResultOrg',
  mixins:[BusinessListMixin, mixinDevice,JeecgListMixin],

  //注册组件
  components: {


  },

  data () {
    return {
      description: '化验原始数据之定硫仪页面',
      visible: false,
      queryParam:{beginDate: moment().subtract('days', 2).format('YYYY-MM-DD'),endDate:moment().format('YYYY-MM-DD')},
      isorter:{
        column: 'opTime',
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
          title:'化验编码',
          align:"center",
          dataIndex: 'laborCode'
        },
        {
          title:'确认状态',
          align:"center",
          dataIndex: 'statusName'
        },
        {
          title:'化验批次类型',
          align:"center",
          dataIndex: 'batchType'
        },
        {
          title:'化验时间',
          align:"center",
          dataIndex: 'opTime'
        },
        {
          title:'化验人',
          align:"center",
          dataIndex: 'opName'
        },
        {
          title:'试样重量',
          align:"center",
          dataIndex: 'samplingWeight'
        },
        {
          title:'空干基全硫',
          align:"center",
          dataIndex: 'stAd'
        },
        {
          title:'总时间',
          align:"center",
          dataIndex: 'totalTime'
        },
        {
          title:'化验设备',
          align:"center",
          dataIndex: 'deviceName'
        },
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
        list: "/znrl/labory/qrySResOrgData",
        delete: "/modules/labReport/delete",
        deleteBatch: "/modules/labReport/deleteBatch",
        exportXlsUrl: "/modules/labReport/exportXls",
        importExcelUrl: "modules/labReport/importExcel",
        //getCardBySamplingUrl:"business/monitor/getCardBySampling",

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
    moment,
    getDateRange(beginDate,endDate){
      return[
        beginDate,
        endDate
      ];
    },
    handleEditDL(){

      if(this.selectedRowKeys.length < 1){
        this.$message.warning('请先选中化验记录！');
        return;
      }
      /*if(this.selectedRowKeys.length > 1){
        this.$message.warning('请只选择一条记录！');
        return;
      }*/
      //selectionRows Table选中的记录
      //console.log(this.selectionRows);
      //取值
      var effectRow = new Object();
      effectRow["updated"] = JSON.stringify(this.selectionRows);
      //console.log(effectRow["updated"]);
      getAction(this.url.deleteScalesUrl,effectRow).then((res) => {
        // console.log(res);
        if (res.code==0){
          this.$message.success(res.message);
        }
        else {
          this.$message.error(res.message);
        }
      })
      //this.$refs.modalForm.edit(this.selectionRows[0]);
      //  this.$refs.modalForm.title = "编辑";
      // this.$refs.modalForm.disableSubmit = false;
    },


    onChange(dates, dateStrings) {
      this.queryParam.beginDate = dateStrings[0];
      this.queryParam.endDate = dateStrings[1];
    },

    //批量确认弹窗
    handleAddDL(){
      this.visible = true;

    },
    //弹窗关闭
    showModal() {
      console.log(e);
      this.visible = false;
    },
    //批量确认发起请求
    handleOk1() {
      if(this.selectedRowKeys.length < 1){
        this.$message.warning('请先选中化验记录！');
        return;
      }
      var commonObj = new Object();
      var paramObj = new Object();
      commonObj.temperature = this.queryParam.temperature;
      commonObj.humidity = this.queryParam.humidity;
      commonObj.lossStyle = this.queryParam.lossStyle;
      commonObj.standard = this.queryParam.standard;

      if (commonObj.temperature == null || commonObj.temperature == ""){
        this.$message("提示", "请录入温度值");
        return false;
      }

      if (commonObj.humidity == null || commonObj.humidity == ""){
        this.$message("提示", "请录入湿度值");
        return false;
      }
      /*if(dataType == 'Scales'|| dataType == 'I'){
        if (commonObj.lossStyle == null || commonObj.lossStyle == ""){
          this.$message("提示", "请选择焦渣特性信息");
          return false;
        }
      }*/

      if (commonObj.standard == null || commonObj.standard == ""){
        this.$message("提示", "请选择化验标准信息");
        return false;
      }
      paramObj["publicInfo"] = JSON.stringify(commonObj);
      console.log(paramObj["publicInfo"]);
      var arrayTickQtys = [];
      for ( var i = 0; i<this.selectionRows.length; i++) {
        var o = {};
        o.id = this.selectionRows[i].id;
        arrayTickQtys[i] = o;
      }
      paramObj["updateInfo"] = JSON.stringify(arrayTickQtys);
      console.log(paramObj["------------"]);
      console.log(paramObj["updateInfo"]);
      getAction(this.url.addScalesUrl,paramObj).then((res) => {
        console.log(res);
        if (res.code==0){
          this.$message.success(res.message);
          this.visible = false;
        }
        else {
          this.$message.error(res.message);
        }
      })
    },


  }
}


</script>

<style scoped>
@import '~@assets/less/common.less';
</style>