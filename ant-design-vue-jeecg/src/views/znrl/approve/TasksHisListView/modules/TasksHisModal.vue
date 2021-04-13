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
      <a-table :columns="columns" :data-source="dataSource" size="small" />

    </a-modal>
</template>

<script>
  import { httpAction, postAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import moment from "moment"

  export default {
    name: "TasksHisModal",
    data () {
      return {
        title:"操作",
        visibleFlow:false,
        visible: false,
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
          add: "/test/jeecgDemo/add",
          edit: "/test/jeecgDemo/edit",
          verifyImgUrl: "http://localhost:8088/jeecg-boot/activiti/traceImage/",
          qryHistoryProcessInstUrl:"/activiti/qryHistoryProcessInst"
        },
      }
    },
    created () {
    },
    methods: {
      handleShowFlowDetails: function (record) {
        // debugger
        // this.form.resetFields();
        // this.model = Object.assign({}, record);
        this.visibleFlow = true;
        if (record.processInstanceId !== null && record.processInstanceId !== undefined) {
          // JSON.stringify(object);
          // JSON.parse(record.paramJsonString);
          this.url.verifyImgUrl = this.url.verifyImgUrl+record.processInstanceId+"?"+Math.random();
          var params = {};
          params.processInstanceId = record.processInstanceId;
          console.log(params);
          debugger
          postAction(this.url.qryHistoryProcessInstUrl, params).then((res) => {
            if (res.success) {
              console.log(res);
              this.dataSource = res.result;
              if(res.result.total)
              {
                this.ipagination.total = res.result.total;
              }else{
                this.ipagination.total = 0;
              }
            }
            if(res.code===510){
              this.$message.warning(res.message)
            }
            this.loading = false;
          })
        }else {
          return ;
        }
      },
      close () {
        this.$emit('close');
        this.visible = false;
      },
      handleOk () {
        const that = this;
        // 触发表单验证
        this.form.validateFields((err, values) => {
          if (!err) {
            that.confirmLoading = true;
            let httpurl = '';
            let method = '';
            if(!this.model.id){
              httpurl+=this.url.add;
              method = 'post';
            }else{
              httpurl+=this.url.edit;
               method = 'put';
            }
            let formData = Object.assign(this.model, values);
            //时间格式化
            formData.punchTime = formData.punchTime?formData.punchTime.format('YYYY-MM-DD HH:mm:ss'):null;
            formData.birthday = formData.birthday?formData.birthday.format():null;

            console.log(formData)
            httpAction(httpurl,formData,method).then((res)=>{
              if(res.success){
                that.$message.success(res.message);
                that.$emit('ok');
              }else{
                that.$message.warning(res.message);
              }
            }).finally(() => {
              that.confirmLoading = false;
              that.close();
            })



          }
        })
      },
      handleCancel () {
        this.close()
      },


    }
  }
</script>

<style scoped>

</style>