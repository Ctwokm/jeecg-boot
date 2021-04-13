<template>
  <a-card :bordered="false">

    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">

          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="审批类型">
              <j-dict-select-tag   v-model="queryParam.name" :triggerChange="true"
                                  dictCode="act_re_procdef,name_,id_"/>
            </a-form-item>
          </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="审批时间">
                <a-range-picker v-model="queryParam.birthdayRange"
                                format="YYYY-MM-DD"
                                :placeholder="['开始时间', '结束时间']"
                                @change="onBirthdayChange" />
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
            </a-col>
        </a-row>
      </a-form>
    </div>

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
      <a-button type="primary" icon="plus" @click="jump">创建单据</a-button>
      <a-button type="primary" icon="plus" @click="onetomany">一对多</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('单表示例')">导出</a-button>
      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
        <a-button type="primary" icon="import">导入</a-button>
      </a-upload>
      <!-- 高级查询区域 -->
      <j-super-query :fieldList="fieldList" ref="superQueryModal" @handleSuperQuery="handleSuperQuery"></j-super-query>
      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchApproval">
            <a-icon type="check"/>
            批量审批
          </a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px"> 批量操作
          <a-icon type="down"/>
        </a-button>
      </a-dropdown>
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

        <div slot="filterDropdown">
          <a-card>
            <a-checkbox-group @change="onColSettingsChange" v-model="settingColumns" :defaultValue="settingColumns">
              <a-row style="width: 400px">
                <template v-for="(item,index) in defColumns">
                  <template v-if="item.key!='rowIndex'&& item.dataIndex!='action'">
                    <a-col :span="12"><a-checkbox :value="item.dataIndex"><j-ellipsis :value="item.title" :length="10"></j-ellipsis></a-checkbox></a-col>
                  </template>
                </template>
              </a-row>
            </a-checkbox-group>
          </a-card>
        </div>
        <a-icon slot="filterIcon" type='setting' :style="{ fontSize:'16px',color:  '#108ee9' }" />

        <span slot="action" slot-scope="text, record">
          <a @click="handleShowDetails(record)">查看详情</a>
          <a-divider type="vertical"/>
          <a @click="handleShowFlowDetail(record)">查看流程状态</a>
          <a-divider type="vertical"/>
          <a @click="handleShowFlowApproval(record)">审批数据</a>
        </span>

      </a-table>
    </div>

    <a-modal
      v-model="visible"
      title="化验报告审批内容"
      :footer="null"
      width="60%"
    >
      <section ref="print" id="printContent" class="ShowLabDetail">
        <iframe :src="this.url.newPreviewRptUrl" frameborder="0" width="100%" height="350px" scrolling="auto"></iframe>
      </section>
      <!--      <a-button  @click="close1"type="primary">打印</a-button>-->
<!--      <a-button v-print="'#printContent'" ghost type="primary">打印</a-button>-->

    </a-modal>
    <a-modal
      v-model="visibleFlow"
      title="化验报告审批操作"
      :footer="null"
      width="60%"
    >
      <section ref="print" id="printContentFlow" class="ShowFlowDetail">
        <iframe :src="this.url.flowApproval" frameborder="0" width="100%" height="350px" scrolling="auto"></iframe>
      </section>
      <!--      <a-button  @click="close1"type="primary">打印</a-button>-->
      <!--      <a-button v-print="'#printContent'" ghost type="primary">打印</a-button>-->

    </a-modal>
    <!-- table区域-end -->

    <!-- 表单区域 -->
    <tasks-his-modal ref="modalForm" @ok="modalFormOk"></tasks-his-modal>


  </a-card>
</template>

<script>
  import TasksHisModal from './modules/TasksHisModal'
  import JSuperQuery from '@/components/jeecg/JSuperQuery.vue';
  import JInput from '@/components/jeecg/JInput.vue';
  import {initDictOptions, filterDictText,filterDictTextByCache} from '@/components/dict/JDictSelectUtil'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import Vue from 'vue'
  import { filterObj } from '@/utils/util';
  import { httpAction, postAction } from '@/api/manage'

  //高级查询modal需要参数
  const superQueryFieldList=[
    {
      type: "string",
      value: "name",
      text: "用户名"
    }, {
      type: "int",
      value: "age",
      text: "年龄"
    }, {
      type: "date",
      value: "birthday",
      text: "生日"
    }
  ]
  export default {
    name: "TasksHisList",
    mixins:[JeecgListMixin],
    components: {
      TasksHisModal,
      JSuperQuery,
      JInput
    },
    data() {
      return {
        description: '单表示例列表',
        visible:false, //控制弹窗
        visibleFlow:false, //控制弹窗
        //字典数组缓存
        sexDictOptions: [],
        importExcelUrl:`${window._CONFIG['domianURL']}/test/jeecgDemo/importExcel`,
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
          delete: "/test/jeecgDemo/delete",
          deleteBatch: "/test/jeecgDemo/deleteBatch",
          exportXlsUrl: "/test/jeecgDemo/exportXls",
          previewRptUrl:"http://localhost:8088/jeecg-boot/ureport/preview?_u=file:report_data.ureport.xml",
          newPreviewRptUrl: "",
          flowApproval: "/activiti/doTask"
        },
        fieldList:superQueryFieldList
      }
    },
    methods: {
      // 查看被审批的数据，一般数据是报表形式
      handleShowDetails: function (record) {
        // debugger
        if (record.paramJsonString !== null && record.paramJsonString !== undefined) {
          this.visible=true;
          // JSON.stringify(object);
          // JSON.parse(record.paramJsonString);
          this.url.newPreviewRptUrl = this.url.previewRptUrl+"&labor_code="+JSON.parse(record.paramJsonString).laborCode;

        }else {
          return ;
        }
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
      //审批操作，单条或多条审批均是通过此视图进行
      handleShowFlowApproval: function (record) {
        this.$refs.modalForm.handleShowFlowDetails(record);
        this.$refs.modalForm.title = "查看详情";
        this.$refs.modalForm.disableSubmit = false;
      },
      //批量审批
      batchApproval: function () {
        if(!this.url.flowApproval){
          this.$message.error("请设置url.deleteBatch属性!")
          return
        }
        if (this.selectedRowKeys.length <= 0) {
          this.$message.warning('请选择至少一条记录！');
          return;
        } else {
          var ids = "";
          for (var a = 0; a < this.selectedRowKeys.length; a++) {
            ids += this.selectedRowKeys[a] + ",";
          }
          var that = this;
          this.$confirm({
            title: "注意",
            content: "批量审批选中数据只可全过，或者全不过，是否确认继续?",
            onOk: function () {
              // that.visibleFlow=true;
              var params = {};
              var yesOrNo = 'YES';
              var comment = '同意操作';
              params.res = yesOrNo;
              params.comment = comment;
              params.taskId = that.selectionRows[0].taskId;//
              params.taskTodoTag = that.selectionRows[0].taskTodoTag;
              params.assignee= that.selectionRows[0].assignee;
              that.loading = true;
              debugger
              postAction(that.url.flowApproval, params).then((res) => {
                if (res.success) {
                  that.$message.success(res.message);
                  that.loadData();
                  that.onClearSelected();
                } else {
                  that.$message.warning(res.message);
                }
              }).finally(() => {
                that.loading = false;
              });
            }
          });
        }
      },
      getQueryParams(){
        //高级查询器
        let sqp = {}
        if(this.superQueryParams){
          sqp['superQueryParams']=encodeURI(this.superQueryParams)
          sqp['superQueryMatchType'] = this.superQueryMatchType
        }
        var param = Object.assign(sqp, this.queryParam, this.isorter ,this.filters);

        param.field = this.getQueryField();
        param.pageNo = this.ipagination.current;
        param.pageSize = this.ipagination.pageSize;
        delete param.birthdayRange; //范围参数不传递后台
        return filterObj(param);
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
      onetomany: function () {
        this.$refs.jeecgDemoTabsModal.add();
        this.$refs.jeecgDemoTabsModal.title = "编辑";
      },
      //跳转单据页面
      jump() {
        this.$router.push({path: '/jeecg/helloworld'})
      },
      onBirthdayChange: function (value, dateString) {
        console.log(dateString[0],dateString[1]);
        this.queryParam.birthday_begin=dateString[0];
        this.queryParam.birthday_end=dateString[1];
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
        //this.defColumns = colAuthFilter(this.defColumns,'testdemo:');

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