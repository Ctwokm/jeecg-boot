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

          <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
              <a-dropdown v-if="selectedRowKeys.length > 0">
                <a-menu slot="overlay">
                  <a-menu-item key="1" @click="handleShowFlowApproval">
                    <a-icon type="check"/>
                    批量审批
                  </a-menu-item>
                </a-menu>
                <a-button style="margin-left: 8px"> 批量操作
                  <a-icon type="down"/>
                </a-button>
              </a-dropdown>
            </a-col>
          </span>
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
        rowKey="taskId"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        @change="handleTableChange">

        <span slot="action" slot-scope="text, record">
          <a @click="handleShowDetails(record)">查看详情</a>
          <a-divider type="vertical"/>
          <a @click="handleShowFlowDetail(record)">查看流程状态</a>
          <a-divider type="vertical"/>
          <a @click="handleShowFlowApproval">审批数据</a>
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
    </a-modal>

    <a-modal
      title="审批操作"
      :width="800"
      :visible="visibleFlow"
      @ok="batchApproval"
      @cancel="handleCancel"
      :confirmLoading="confirmLoading"
      cancelText="关闭">
      <a-spin :spinning="confirmLoading">
        <a-form-model ref="form"  :label-col="labelCol" :wrapper-col="wrapperCol"  :model="model">
          <a-form-model-item label="审批" required prop="res" hasFeedback>
            <a-select default-value="YES" style="width: 120px" v-model="model.res" >
              <a-select-option value="YES">
                同意
              </a-select-option>
              <a-select-option value="NO">
                驳回
              </a-select-option>
            </a-select>
          </a-form-model-item>
          <a-form-model-item label="建议"  prop="comment" hasFeedback>
            <a-textarea v-model="model.comment"  placeholder="请输入审批建议"/>
          </a-form-model-item>

        </a-form-model>
      </a-spin>
    </a-modal>

    <!-- table区域-end -->

    <!-- 查看流程详情表单区域 -->
    <my-todo-modal ref="modalForm" @ok="modalFormOk"></my-todo-modal>

  </a-card>
</template>

<script>
  import MyTodoModal from './modules/MyTodoModal'
  import JInput from '@/components/jeecg/JInput.vue';
  import Vue from 'vue'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import { postAction, getAction } from '@/api/manage'

  export default {
    name: "MyTodoList",
    mixins:[JeecgListMixin],
    components: {
      MyTodoModal,
      JDate,
      JInput
    },
    data() {
      return {
        description: '单表示例列表',
        visible:false, //控制弹窗
        visibleFlow:false, //控制弹窗
        model: {},
        //字典数组缓存
        sexDictOptions: [],
        //表头
        columns:[],
        //列设置
        settingColumns:[],
        //列定义
        defColumns:[
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
            dataIndex: 'taskId'
          },
          {
            title: '任务描述',
            align: "center",
            dataIndex: '_desc'
          },
          {
            title: '流程名称',
            align: "center",
            dataIndex: 'processName'
          },
          {
            title: '任务名称',
            align: "center",
            dataIndex: 'taskName'
          },
          {
            title: '申请人',
            align: "center",
            dataIndex: 'opName'
          },
          {
            title: '任务分配时间',
            align: "center",
            dataIndex: 'createTime'
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
        layout: {
          labelCol: { span: 3 },
          wrapperCol: { span: 14 },
        },
        labelCol: {
          xs: { span: 24 },
          sm: { span: 3 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },
        confirmLoading: false,
        url: {
          list: "/activiti/qryTodoTasks",
          previewRptUrl:Vue.prototype.API_BASE_URL+"/ureport/preview?_u=file:report_data.ureport.xml",
          newPreviewRptUrl: "",
          flowApproval: "/activiti/doTask"
        },
      }
    },
    methods: {
      searchQuery() {
          if (this.queryParam.appr_name != null && this.queryParam.appr_name != ""){
            var keys = this.queryParam.appr_name.split(':');
            this.queryParam.processKey = keys[0];//查询条件
          }
        this.loadData();
      },
      // loadData() {
      //   if(!this.url.list){
      //     this.$message.error("请设置url.list属性!")
      //     return
      //   }
      //   var params = {};
      //   debugger
      //   if (this.queryParam.appr_name != null && this.queryParam.appr_name != ""){
      //     var keys = this.queryParam.appr_name.split(':');
      //     params.processKey = keys[0];//查询条件
      //   }
      //   this.loading = true;
      //   getAction(this.url.list, params).then((res) => {
      //     if (res.success) {
      //       this.dataSource = res.result.records||res.result;
      //     }
      //     if(res.code===510){
      //       this.$message.warning(res.message)
      //     }
      //     this.loading = false;
      //   })
      // },
      handleCancel () {
        this.visibleFlow=false;
      },
      // 查看被审批的数据，一般数据是报表形式
      handleShowDetails: function (record) {
        if (record.paramJsonString !== null && record.paramJsonString !== undefined) {
          this.visible=true;
          this.url.newPreviewRptUrl = this.url.previewRptUrl+"&labor_code="+JSON.parse(record.paramJsonString).laborCode;
        }else {
          return ;
        }
      },
      //查看审批流程的详情，包括审批流程图和当前操作人，历史操作人信息
      handleShowFlowDetail: function (record) {
        this.$refs.modalForm.handleShowFlowDetails(record);
        this.$refs.modalForm.title = "查看详情";
      },
      //审批操作，单条或多条审批均是通过此视图进行
      handleShowFlowApproval: function () {
        this.visibleFlow=true;
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
          for (var a = 0; a < this.selectedRowKeys.length; a++) {
            var params = {};
            params.res = this.model.res;
            params.comment = this.model.comment;
            params.taskId = this.selectionRows[a].taskId;//
            params.taskTodoTag = this.selectionRows[a].taskTodoTag;
            params.assignee= this.selectionRows[a].assignee;
            postAction(this.url.flowApproval, params).then((res) => {
              if (res.success) {
                this.$message.success(res.message);
                this.loadData();
              } else {
                this.$message.warning(res.message);
              }
            });
          }
          this.onClearSelected();
          this.model = {};
          this.visibleFlow=false;
        }
      },
      initDictConfig() {
        console.log("--我才是真的方法!--")
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
      // this.loadData();
    },
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>