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
            <a-form-item label="采样日期">
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
    <div class="table-operator">
      <a-button @click="handleEditCY()" type="primary" icon="plus">编辑人工制样结果</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('制样结果')">导出</a-button>
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

<!--          <a @click="handleDetail(record)">详情</a>-->
          <a-divider type="vertical" />
          <a-dropdown>
            <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a @click="handleDetail(record)">详情</a>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>

      </a-table>
    </div>

<!--    加入refs的组件 modalForm modalFormOk 成功后重新加载-->
    <prepar-sampling-modal ref="modalForm" @ok="modalFormOk"></prepar-sampling-modal>
  </a-card>
</template>

<script>
  //自适应 css
  import '@/assets/less/TableExpand.less'
  //混入js 判断phone还是PC
  import { mixinDevice } from '@/utils/mixin'
  //混入js 查询相关
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  //导入组件
  import PreparSamplingModal from './modules/PreparSamplingModal'

  import moment from 'moment'

  export default {
    //名称
    name: 'PreparSamplingList',
    //混入js
    mixins:[JeecgListMixin, mixinDevice],
    components: {
      PreparSamplingModal
    },
    //加载的时间的限制下

    data () {
      return {
        description: '制样管理页面',
        /* 排序参数 */
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
            title:'制样编号',
            align:"center",
            dataIndex: 'sampleCode'
          },
          {
            title:'批次类型',
            align:"center",
            dataIndex: 'batchTypeName'
          },
          {
            title:'制样方式',
            align:"center",
            dataIndex: 'zyTypeName'
          },
          {
            title:'来样重量',
            align:"center",
            dataIndex: 'zyWeight'
          },
          {
            title:'6mm全水样封装码',
            align:"center",
            dataIndex: 'packCode6'
          },
          {
            title:'3mm存查样封装码',
            align:"center",
            dataIndex: 'packCode3'
          },
          {
            title:'0.2mm化验样封装码',
            align:"center",
            dataIndex: 'packCode21'
          },
          {
            title:'0.2mm存查样封装码',
            align:"center",
            dataIndex: 'packCode22'
          },

          {
            title:'制样时间',
            align:"center",
            dataIndex: 'startTime'
          },
          {
            title:'制样人',
            align:"center",
            dataIndex: 'userName'
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
          list: "/znrl/sampling/list",
         // delete: "/common/PreparSampling/delete",
         // deleteBatch: "/common/takeSampleRec/deleteBatch",
          exportXlsUrl: "/znrl/sampling/exportXls",
          // 导出
          //importExcelUrl: "/znrl/takeSample/exportXls",
          
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
      handleEditCY(){

        if(this.selectedRowKeys.length < 1){
          this.$message.warning('请先选择录入人工制样结果的批次记录！');
          return;
        }
        if(this.selectedRowKeys.length > 1){
          this.$message.warning('请只选择一条记录！');
          return;
        }
        //selectionRows Table选中的记录
        console.log(this.selectionRows);
        this.$refs.modalForm.edit(this.selectionRows[0]);
        this.$refs.modalForm.title = "编辑";
        this.$refs.modalForm.disableSubmit = false;
      },
      moment,
      //高级查询用的
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