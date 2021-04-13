<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
<!--          采用模糊查询-->
<!--         模糊查询   首台模糊查询-->
<!--          下拉框字典-->
          <a-col :md="4" :sm="24">

            <a-form-item label="操作人员姓名">
              <j-dict-select-tag v-model="queryParam.logOpCode" placeholder="操作人员姓名" dictCode="sys_user,realname,id"/>
            </a-form-item>
          </a-col>
<!--          采样类型字典-->
          <a-col :md="4" :sm="24">
            <a-form-item label="操作类型">
              <j-dict-select-tag v-model="queryParam.logType" placeholder="操作类型" dictCode="gk_log_type"/>
            </a-form-item>
          </a-col>
<!--        日期的表单  -->
          <a-col :md="8" :sm="24">
            <a-form-item label="操作时间">
              <a-range-picker
                :ranges="{ 当天: [moment(), moment()], '三天内': [moment().subtract('days', 2), moment()], '一周内': [moment().subtract('days', 6), moment()], '本月': [moment().subtract('month',1), moment()] }"
                @change="onChange"
                :default-value="getDateRange(queryParam.opStartTime,queryParam.opEndTime)"
                format="YYYY-MM-DD"
              />
            </a-form-item>
          </a-col>

          <a-col :md="8" :sm="24">
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
<!--    <div class="table-operator">-->
<!--      <a-button @click="handleEditCY()" type="primary" icon="plus">编辑人工采样结果</a-button>-->
<!--      <a-button type="primary" icon="download" @click="handleExportXls('采样结果')">导出</a-button>-->
<!--      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">-->
<!--        <a-button type="primary" icon="import">导入</a-button>-->
<!--      </a-upload>-->
      <!-- 高级查询区域 -->
<!--      <j-super-query :fieldList="superFieldList" ref="superQueryModal" @handleSuperQuery="handleSuperQuery"></j-super-query>-->
<!--      <a-dropdown v-if="selectedRowKeys.length > 0">-->
<!--        <a-menu slot="overlay">-->
<!--          <a-menu-item key="1" @click="batchDel"><a-icon type="delete"/>删除</a-menu-item>-->
<!--        </a-menu>-->
<!--        <a-button style="margin-left: 8px"> 批量操作 <a-icon type="down" /></a-button>-->
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
<!--          <a @click="handleEdit(record)">编辑</a>-->

<!--&lt;!&ndash;          <a @click="handleDetail(record)">详情</a>&ndash;&gt;-->
<!--          <a-divider type="vertical" />-->
<!--          <a-dropdown>-->
<!--            <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>-->
<!--            <a-menu slot="overlay">-->
<!--              <a-menu-item>-->
<!--                <a @click="handleDetail(record)">详情</a>-->
<!--              </a-menu-item>-->
<!--&lt;!&ndash;              <a-menu-item>&ndash;&gt;-->
<!--&lt;!&ndash;                <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">&ndash;&gt;-->
<!--&lt;!&ndash;                  <a>删除</a>&ndash;&gt;-->
<!--&lt;!&ndash;                </a-popconfirm>&ndash;&gt;-->
<!--&lt;!&ndash;              </a-menu-item>&ndash;&gt;-->
<!--            </a-menu>-->
<!--          </a-dropdown>-->
<!--        </span>-->

      </a-table>
    </div>

<!--    加入refs的组件 modalForm modalFormOk 成功后重新加载-->
<!--    <log-mod-modal ref="modalForm" @ok="modalFormOk"></log-mod-modal>-->
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
  import LogModModal from './modules/LogModModal'

  import moment from 'moment'

  export default {
    //名称
    name: 'LogModList',
    //混入js
    mixins:[JeecgListMixin, mixinDevice],
    components: {
      LogModModal
    },
    //加载的时间的限制下

    data () {
      return {
        description: '用户操作日志页面',
        /* 排序参数 */
        isorter:{
          column: 'logDt',
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
            title:'工号',
            align:"center",
            dataIndex: 'logId'
          },
          {
            title:'姓名',
            align:"center",
            dataIndex: 'logOpName'
          },
          {
            title:'操作类型描述',
            align:"center",
            dataIndex: 'logTypeDec'
          },
          {
            title:'操作明细',
            align:"center",
            dataIndex: 'modDec'
          },
          {
            title:'操作时间',
            align:"center",
            dataIndex: 'logTime'
          }
        ],
        url: {
          list: "/znrl/monitor/logMod/list",
         // delete: "/common/takeSampleRec/delete",
         // deleteBatch: "/common/takeSampleRec/deleteBatch",
         // exportXlsUrl: "/znrl/takeSample/exportXls",
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
      // importExcelUrl: function(){
      //   return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      // },
    },
    methods: {
      initDictConfig(){
      },
      //给时间查询条件赋值
      onChange(dates, dateStrings) {
        this.queryParam.opStartTime = dateStrings[0];
        this.queryParam.opEndTime= dateStrings[1];
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