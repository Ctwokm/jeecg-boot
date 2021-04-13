<template>
    <a-modal
      v-model="visibleFlow"
      title="当前流程状态"
      :footer="null"
      width="60%"
    >
      <section ref="print" id="printContentFlow" class="ShowFlowDetail">
        <iframe :src="this.url.verifyImgUrl" frameborder="0" width="100%" height="350px" scrolling="auto"></iframe>
      </section>
      <a-table
        :columns="columns"
        :pagination=false
        rowKey="taskName"
        :data-source="dataSource" size="small" />
    </a-modal>
</template>

<script>
  import { postAction } from '@/api/manage'
  import Vue from "vue";

  export default {
    name: "MyTodoModal",
    data () {
      return {
        title:"操作",
        visibleFlow:false,
        model: {},
        /* 数据源 */
        dataSource:[],
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },
        // 表头
        columns: [{
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
            title: '任务名称',
            align: "center",
            dataIndex: 'taskName'
          },
          {
            title: '执行人',
            align: "center",
            dataIndex: 'executor',
          },
          {
            title: '执行时间',
            align: "center",
            dataIndex: 'exeTime'
          },
          {
            title: '执行描述',
            align: "center",
            dataIndex: 'exeComment'
          }
        ],
        confirmLoading: false,
        form: this.$form.createForm(this),
        validatorRules:{
        },
        url: {
          verifyImgUrl: Vue.prototype.API_BASE_URL+"/activiti/traceImage/",
          qryHistoryProcessInstUrl:"/activiti/qryHistoryProcessInst"
        },
      }
    },
    created () {
    },
    methods: {
      handleShowFlowDetails: function (record) {
        this.visibleFlow = true;
        if (record.processInstanceId !== null && record.processInstanceId !== undefined) {
          this.url.verifyImgUrl = this.url.verifyImgUrl+record.processInstanceId+"?"+Math.random();
          var params = {};
          params.processInstanceId = record.processInstanceId;
          console.log(params);
          postAction(this.url.qryHistoryProcessInstUrl, params).then((res) => {
            if (res.success) {
              this.dataSource = res.result;
            }
            if(res.code===510){
              this.$message.warning(res.message)
            }
            this.loading = false;
          })
        }else {
          return ;
        }
      }
    }
  }
</script>

<style scoped>

</style>