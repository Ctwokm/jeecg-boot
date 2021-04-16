<template>
  <a-card :bordered="false">

    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">

          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="审批类型">
              <j-dict-select-tag v-model="queryParam.appr_name" placeholder="请选择审批类型"
                                 dictCode="act_re_procdef,name_,id_"/>
            </a-form-item>
          </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
            </a-col>
        </a-row>
      </a-form>
    </div>


    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择 <a style="font-weight: 600">{{
          selectedRowKeys.length }}</a>项
        <a style="margin-left: 24px" @click="onClearSelected">清空</a>
        <span style="float:right;">
          <a @click="loadData()"><a-icon type="sync" />刷新</a>
          <a-divider type="vertical" />
          <a-popover title="自定义列" trigger="click" placement="leftBottom">
            <template slot="content">
              <a-checkbox-group @change="onColSettingsChange" v-model="settingColumns" :defaultValue="settingColumns">
                <a-row style="width: 400px">
                  <template v-for="(item,index) in defColumns">
                    <template v-if="item.key!='rowIndex'&& item.dataIndex!='action'">
                        <a-col :span="12"><a-checkbox :value="item.dataIndex"><j-ellipsis :value="item.title" :length="10"></j-ellipsis></a-checkbox></a-col>
                    </template>
                  </template>
                </a-row>
              </a-checkbox-group>
            </template>
            <a><a-icon type="setting" />设置</a>
          </a-popover>
        </span>
      </div>

      <a-table
        ref="table"
        size="middle"
        bordered
        rowKey="processInstanceId"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        @change="handleTableChange">

        <span slot="action" slot-scope="text, record">
          <a @click="handleShowDetails(record)">查看已审批详情</a>
          <a-divider type="vertical"/>
          <a @click="handleShowFlowDetail(record)">查看流程状态</a>
        </span>

      </a-table>
    </div>

    <!-- table区域-end -->
    <a-modal
      v-model="visible"
      title="化验报告已审批内容"
      :footer="null"
      width="60%"
    >
      <section ref="print" id="printContent" class="ShowLabDetail">
        <iframe :src="this.url.newPreviewRptUrl" frameborder="0" width="100%" height="350px" scrolling="auto"></iframe>
      </section>
    </a-modal>
    <!-- 表单区域 -->
    <tasks-his-modal ref="modalForm" @ok="modalFormOk"></tasks-his-modal>


  </a-card>
</template>

<script>
  import TasksHisModal from './modules/TasksHisModal'
  import JInput from '@/components/jeecg/JInput.vue';
  import {initDictOptions, filterDictText,filterDictTextByCache} from '@/components/dict/JDictSelectUtil'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import Vue from 'vue'

  export default {
    name: "TasksHisList",
    mixins:[JeecgListMixin],
    components: {
      TasksHisModal,
      JInput
    },
    data() {
      return {
        description: '单表示例列表',
        visible:false, //控制弹窗
        visibleFlow:false, //控制弹窗
        //字典数组缓存
        sexDictOptions: [],
        //表头
        columns:[],
        //列设置
        settingColumns:[],
        //列定义
        defColumns: [
          {
            title: '#',
            dataIndex: '',
            key: 'rowIndex',
            width: 60,
            align: "center",
            customRender: function (t, r, index) {
              return parseInt(index) + 1;
            }
          },
          {
            title: 'ID',
            align: "center",
            dataIndex: 'processInstanceId'
          },
          {
            title: '审批内容',
            align: "center",
            dataIndex: 'exeComment'
          },
          {
            title: '申请人',
            align: "center",
            dataIndex: 'apprOpName'
          },
          {
            title: '任务名称',
            align: "center",
            dataIndex: 'taskName'
          },
          {
            title: '审批状态',
            align: "center",
            dataIndex: 'exeResult'
          },
          {
            title: '审批开始时间',
            align: "center",
            dataIndex: 'exeBeginTime'
          },
          {
            title: '审批结束时间',
            align: "center",
            dataIndex: 'exeEndTime'
          },
          {
            title: '审批节点详情',
            align: "center",
            dataIndex: '_operation'
          },
          {
            title: '操作',
            dataIndex: 'action',
            align: "center",
            scopedSlots: {
              filterDropdown: 'filterDropdown',
              filterIcon: 'filterIcon',
              customRender: 'action'},
          }
        ],
        url: {
          list: "/activiti/qryTasksHisList",
          previewRptUrl:Vue.prototype.API_BASE_URL+"/ureport/preview?_u=file:report_data.ureport.xml",
          newPreviewRptUrl: "",
        },
      }
    },
    methods: {
      searchQuery() {
        if (this.queryParam.appr_name != null && this.queryParam.appr_name != "") {
          var keys = this.queryParam.appr_name.split(':');
          this.queryParam.apprEventTypeCd = keys[0];//查询条件
        }
        this.loadData();
      },
      onSelectChange(selectedRowKeys, selectionRows) {
        this.selectedRowKeys = selectedRowKeys;
        this.selectionRows = selectionRows;
      },
      //查看审批流程的详情，包括审批流程图和当前操作人，历史操作人信息
      handleShowFlowDetail: function (record) {
        this.$refs.modalForm.handleShowFlowDetails(record);
        this.$refs.modalForm.title = "查看详情";
        this.$refs.modalForm.disableSubmit = false;
      },
      // 查看被审批的数据，一般数据是报表形式
      handleShowDetails: function (record) {
        if (record.exeComment !== null && record.exeComment !== undefined) {
          var keys = record.exeComment.split(':');
          var rptKey = keys[keys.length-1];//查询条件
          console.log(rptKey);
          this.visible=true;
          this.url.newPreviewRptUrl = this.url.previewRptUrl+"&labor_code="+ rptKey;
        }else {
          return ;
        }
      },
      initDictConfig() {
        console.log("--我才是真的方法!--")
        //初始化字典 - 性别
        initDictOptions('sex').then((res) => {
          if (res.success) {
            this.sexDictOptions = res.result;
          }
        });
      },
      //列设置更改事件
      onColSettingsChange (checkedValues) {
        var key = this.$route.name+":colsettings";
        Vue.ls.set(key, checkedValues, 7 * 24 * 60 * 60 * 1000)
        this.settingColumns = checkedValues;
        const cols = this.defColumns.filter(item => {
          if(item.key =='rowIndex'|| item.dataIndex=='action'){
            return true
          }
          if (this.settingColumns.includes(item.dataIndex)) {
            return true
          }
          return false
        })
        this.columns =  cols;
      },
      initColumns(){
        //权限过滤（列权限控制时打开，修改第二个参数为授权码前缀）
        var key = this.$route.name+":colsettings";
        let colSettings= Vue.ls.get(key);
        if(colSettings==null||colSettings==undefined){
          let allSettingColumns = [];
          this.defColumns.forEach(function (item,i,array ) {
            allSettingColumns.push(item.dataIndex);
          })
          this.settingColumns = allSettingColumns;
          this.columns = this.defColumns;
        }else{
          this.settingColumns = colSettings;
          const cols = this.defColumns.filter(item => {
            if(item.key =='rowIndex'|| item.dataIndex=='action'){
              return true;
            }
            if (colSettings.includes(item.dataIndex)) {
              return true;
            }
            return false;
          })
          this.columns =  cols;
        }
      }
    },
    created() {
      this.initColumns();
    },
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>