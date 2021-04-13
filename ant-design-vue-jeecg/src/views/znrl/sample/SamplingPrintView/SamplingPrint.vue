<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <!--          采用模糊查询-->
          <a-col :md="4" :sm="24">
            <a-form-item label="采样编码">
              <j-search-select-tag
                placeholder="请输入采样编码"
                v-model="queryParam.sampleCode"
                dict="batch_no_info,sample_code,sample_code"
                :pageSize="6"
                :async="true">
              </j-search-select-tag>
            </a-form-item>
          </a-col>
          <!--          采用模糊查询-->
          <a-col :md="4" :sm="24">
            <a-form-item label="制样编码">
              <j-search-select-tag
                placeholder="请输入制样编码"
                v-model="queryParam.samplingCode"
                dict="batch_no_info,sampling_code,sampling_code"
                :pageSize="6"
                :async="true">
              </j-search-select-tag>
            </a-form-item>
          </a-col>
          <!--         模糊查询   首台模糊查询-->
          <!--          下拉框字典-->
          <a-col :md="4" :sm="24">

            <a-form-item label="批次类型">
              <j-dict-select-tag v-model="queryParam.batchNoType" placeholder="批次类型" dictCode="gk_batch_type"/>

            </a-form-item>
          </a-col>
          <!--          采样类型字典-->

          <!--        日期的表单  -->
          <a-col :md="8" :sm="24">
            <a-form-item label="批次时间">
              <a-range-picker
                :ranges="{ 当天: [moment(), moment()], '三天内': [moment().subtract('days', 2), moment()], '一周内': [moment().subtract('days', 6), moment()], '本月': [moment().subtract('month',1), moment()] }"
                @change="onChange"
                :default-value="getDateRange(queryParam.startTime,queryParam.endTime)"
                format="YYYY/MM/DD"
              />
            </a-form-item>
          </a-col>

          <a-col :md="6" :sm="24">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
              <!--              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>-->
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->

    <!-- 操作按钮区域 -->

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
          <a @click="printRecord(record)">打印</a>
        </span>

      </a-table>
    </div>

    <!--    加入refs的组件 modalForm modalFormOk 成功后重新加载-->
    <barcode-modal ref="modalForm" ></barcode-modal>
  </a-card>
</template>

<script>

  import '@/assets/less/TableExpand.less'
  import { mixinDevice } from '@/utils/mixin'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import moment from 'moment'
  //导入组件
  import BarcodeModal from '../BarcodeModules/BarcodeModal'

  export default {
    name: 'SamplingPrint',
    components: { BarcodeModal },
    mixins:[JeecgListMixin, mixinDevice],
    data () {
      return {
        description: '制样码打印页面',
        isorter:{
          column: 'insertTime',
          order: 'desc',
        },
        //查询条件默认
        queryParam: {startTime:moment().subtract('month',1).format('YYYY-MM-DD'),endTime:moment().format('YYYY-MM-DD')},

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
            title:'批次号',
            align:"center",
            dataIndex: 'batchNo'
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
      printRecord(record){
        this.$refs.modalForm.print(record,'sampling');
        this.$refs.modalForm.title = "打印";
        //this.$refs.modalForm.disableSubmit = false;
      },
      moment,
      //给时间查询条件赋值
      onChange(dates, dateStrings) {
        this.queryParam.startTime = dateStrings[0];
        this.queryParam.endTime = dateStrings[1];
      },
      //默认赋值
      getDateRange(startDate,endDate){
        return [
          startDate,
          endDate
        ];
      },
      getSuperFieldList(){
        let fieldList=[];
        this.superFieldList = fieldList
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>