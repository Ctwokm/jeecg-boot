<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->

    <!-- 操作按钮区域 -->
    <div class="table-operator">
<!--布局有点bug，暂且不开放文件上传-->
<!--      <j-upload v-model="fileList"  text="上传BPMN文件"></j-upload>-->
      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDeploy"><a-icon type="check"/>批量发布</a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px"> 批量操作 <a-icon type="down" /></a-button>
      </a-dropdown>

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
        rowKey="processKey"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        class="j-table-force-nowrap"
        @change="handleTableChange">

        <span slot="action" slot-scope="text, record">
          <a @click="deploy(record)">发布</a>
        </span>

      </a-table>
    </div>

    <coal-type-modal ref="modalForm" @ok="modalFormOk"></coal-type-modal>
  </a-card>
</template>

<script>

  import '@/assets/less/TableExpand.less'
  import { mixinDevice } from '@/utils/mixin'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import { postAction } from '@/api/manage'
  import JUpload from '@/components/jeecg/JUpload'
  export default {
    mixins:[JeecgListMixin, mixinDevice],
    components: {
      JUpload
    },
    data () {
      return {
        description: '发布工作流管理页面',
        isorter:{
          column: 'processKey',
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
            title:'可发布流程',
            align:"center",
            dataIndex: 'processKey'
          },
          {
            title:'发布流程名称',
            align:"center",
            dataIndex: 'processName'
          },
          {
            title:'最近发布时间',
            align:"center",
            dataIndex: 'deployTime'
          },
          {
            title:'最近发布ID',
            align:"center",
            dataIndex: 'id'
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
          list: "/activiti/listToDeployBpmn",
          deploy: "/activiti/deployBpmn",
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
      getSuperFieldList(){
        let fieldList=[];
        fieldList.push({type:'string',value:'processKey',text:'可发布流程'})
        fieldList.push({type:'string',value:'processName',text:'发布流程名称'})
        fieldList.push({type:'string',value:'deployTime',text:'最近发布时间'})
        fieldList.push({type:'string',value:'id',text:'最近发布ID'})
        this.superFieldList = fieldList
      },
      //发布流程
      deploy: function (record){
        //通过插槽获取选定行的信息
        console.log(record.processKey);
        let params = {}
        params.processKey = record.processKey;
        console.log(params)
        postAction(this.url.deploy, params).then((res) => {
          if (res.success) {
            this.loadData()
            this.$message.success(res.message)
          } else {
            this.$message.warning(res.message)
          }
        })
      },
      // 批量发布流程
      batchDeploy: function () {
        if(!this.url.deploy){
          this.$message.error("请设置url.deploy属性!");
          return;
        }
        if (this.selectedRowKeys.length <= 0) {
          this.$message.warning('请选择一条记录！');
          return;
        } else {
          var that = this;
          this.$confirm({
            title: "确认批量发布流程",
            content: "是否确认批量发布选中的流程?",
            onOk: function () {
              for (var a = 0; a < that.selectedRowKeys.length; a++) {
                that.loading = true;
                postAction(that.url.deploy, {processKey: that.selectedRowKeys[a]}).then((res) => {
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
            }
          });
        }
      },
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>