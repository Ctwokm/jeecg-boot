<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <!--          采用模糊查询-->

          <a-col :md="4" :sm="24">

            <a-form-item label="实时等级">
              <j-dict-select-tag v-model="queryParam.deviceBroadPri" placeholder="实时等级" dictCode="gk_device_broad_pri"/>

            </a-form-item>
          </a-col>
          <!--          采样类型字典-->
          <a-col :md="4" :sm="24">
            <a-form-item label="设备名称">
              <j-dict-select-tag v-model="queryParam.machineCode" placeholder="设备名称" dictCode="gk_device_type"/>
            </a-form-item>
          </a-col>
          <!--        日期的表单  -->
          <a-col :md="8" :sm="24">
            <a-form-item label="开始日期">
              <a-range-picker
                :ranges="{ 当天: [moment(), moment()], '三天内': [moment().subtract('days', 2), moment()], '一周内': [moment().subtract('days', 6), moment()], '本月': [moment().subtract('month',1), moment()] }"
                @change="onChange"
                :default-value="getDateRange(queryParam.beginTime,queryParam.endTime)"
                format="YYYY-MM-DD"
              />
            </a-form-item>
          </a-col>

          <a-col :md="8" :sm="24">
            <span style="float: right;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
              <!--              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>-->
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->

    <!-- 操作按钮区域 -->
<!--    <div class="table-operator">-->
<!--      <a-dropdown v-if="selectedRowKeys.length > 0">-->
<!--        <a-menu slot="overlay">-->
<!--          <a-menu-item key="1" @click="batchDel"><a-icon type="delete"/>处理</a-menu-item>-->
<!--        </a-menu>-->
<!--        <a-button style="margin-left: 8px"> 批量操作<a-icon type="down" /></a-button>-->
<!--      </a-dropdown>-->
<!--    </div>-->

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

<!--        <span slot="action" slot-scope="text, record">-->
<!--          <a @click="handleDeviceError(record)">处理</a>-->

<!--        </span>-->

      </a-table>
    </div>

<!--    加入refs的组件 modalForm modalFormOk 成功后重新加载-->
<!--    <device-broad-modal ref="modalForm" @ok="modalFormOk"></device-broad-modal>-->
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
  import DeviceBroadModal from './modules/DeviceBroadModal'

  import moment from 'moment'

  import { filterDictTextByCache } from '@/components/dict/JDictSelectUtil'

  export default {
    //名称
    name: 'DeviceBroadList',
    //混入js
    mixins:[JeecgListMixin, mixinDevice],

    //加载的时间的限制下

    data () {
      return {
        description: '设备实时信息',
        /* 排序参数 */
        isorter:{
          column: 'insertTime',
          order: 'desc',
        },
        //查询条件默认
        queryParam: {beginTime:moment().subtract('days',1).format('YYYY-MM-DD'),endTime:moment().format('YYYY-MM-DD')},
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
              title:'实时信息描述',
                align:"center",
              dataIndex: 'broadDec'
            },
            {
              title:'设备名称',
                align:"center",
              dataIndex: 'machineCode',
              customRender:function (text) {
              return filterDictTextByCache('gk_device_type',text)
             }
            },
            {
              title:'流程名称',
                align:"center",
              dataIndex: 'flowId',
              customRender:function (text) {
              return filterDictTextByCache('gk_flow_type',text)
              }
            },
            {
              title:'时间',
                align:"center",
              dataIndex: 'broadTime'
            }
        ],
        url: {
          list: "znrl/monitor/deviceBroad/list",

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
    methods: {
      initDictConfig(){
      },
      //给时间查询条件赋值
      onChange(dates, dateStrings) {
        this.queryParam.beginTime = dateStrings[0];
        this.queryParam.endTime = dateStrings[1];
      },
      //默认赋值
      getDateRange(startDate,endDate){
        return [
          startDate,
          endDate
        ];
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