<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form :form="form" slot="detail">
        <a-row>
          <a-col :span="12">
            <a-form-item label="设备名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-dict-select-tag :disabled="true"  :triggerChange="true" v-decorator="['machineCode']" placeholder="设备名称" dictCode="gk_device_type" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="流程名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-dict-select-tag :disabled="true" :triggerChange="true" v-decorator="['flowId']" placeholder="流程名称" dictCode="gk_flow_type" style="width: 100%"/>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row>
          <a-col :span="12">
            <a-form-item label="故障时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input :disabled="true" v-decorator="['errorTime']" placeholder="故障时间" dateFormat="YYYY-MM-DD HH:mm:ss"/>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="故障优先级" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input :disabled="true" v-decorator="['errorPri']" placeholder="故障优先级"  ></a-input>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row>
          <a-col :span="24">
            <a-form-item label="故障描述" :labelCol="{span: 3}" :wrapperCol="{span: 19}" >
              <a-input :disabled="true" v-decorator="['errorDec']" placeholder="故障描述"  ></a-input>
            </a-form-item>
          </a-col>
        </a-row>

        <a-row>
          <a-col :span="24" >
<!--             <span style="float: left;">-->
               <a-form-item label="处理信息" :labelCol="{span: 3}" :wrapperCol="{span: 19}" >
                   <a-input v-decorator="['bak']" placeholder="请输入处理信息"  style="width: 100%"></a-input>
               </a-form-item>
<!--            </span>-->
          </a-col>
        </a-row>
        <a-row>
          <a-col  :span="24" style="text-align: center">
              <span  class="table-page-search-submitButtons">
                   <a-button @click="submitForm">处理确认</a-button>
              </span>
          </a-col>
        </a-row>
      </a-form>
    </j-form-container>
  </a-spin>
</template>

<script>

  import { httpAction, getAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import { validateDuplicateValue } from '@/utils/util'
  import ARow from 'ant-design-vue/es/grid/Row'

  export default {
    name: 'DeviceErrorForm',
    components: {
      ARow
    },
    props: {
      //流程表单data
      formData: {
        type: Object,
        default: ()=>{},
        required: false
      },
      //表单模式：true流程表单 false普通表单
      formBpm: {
        type: Boolean,
        default: false,
        required: false
      },
      //表单禁用
      disabled: {
        type: Boolean,
        default: false,
        required: false
      }
    },
    data () {
      return {
        form: this.$form.createForm(this),
        model: {},
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },
        labelColZ: {
          xs: { span: 24 },
          sm: { span: 6 },
        },
        wrapperColD: {
          xs: { span: 24 },
          sm: { span: 15 },
        },
        confirmLoading: false,
        url: {
         // add: "/common/takeSampleRec/add",
          edit: "znrl/monitor/deviceErr/edit",
          //queryById: "/znrl/takeSample/queryById"
        }
      }
    },
    computed: {
      formDisabled(){
        if(this.formBpm===true){
          if(this.formData.disabled===false){
            return false
          }
          return true
        }
        return this.disabled
      },
      showFlowSubmitButton(){
        if(this.formBpm===true){
          if(this.formData.disabled===false){
            return true
          }
        }
        return false
      }
    },
    created () {
      //如果是流程中表单，则需要加载流程表单data
      this.showFlowData();
    },
    methods: {
      edit (record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          //表单赋值
          this.form.setFieldsValue(pick(this.model,'errorDec','machineCode','flowId','errorConfirm','bak','confirmOp','errorTime','errorPri'))
        })
      },
      //渲染流程表单数据
      showFlowData(){
        if(this.formBpm === true){
          let params = {id:this.formData.dataId};
          getAction(this.url.queryById,params).then((res)=>{
            if(res.success){
              this.edit (res.result);
            }
          });
        }
      },
      submitForm () {

        const that = this;
        // 触发表单验证
        this.form.validateFields((err, values) => {
          debugger;
          if (!err) {
            that.confirmLoading = true;
            let httpurl = '';
            let method = '';
            if(!this.model.errorRecId){//验证采样编码为唯一标识
              httpurl+=this.url.add;
              method = 'post';
            }else{
              httpurl+=this.url.edit;
               method = 'put';
            }
            let formData = Object.assign(this.model, values);
            console.log("表单提交数据",formData)
            httpAction(httpurl,formData,method).then((res)=>{
              if(res.success){
                that.$message.success(res.message);
                that.$emit('ok');
              }else{
                that.$message.warning(res.message);
              }
            }).finally(() => {
              that.confirmLoading = false;
            })
          }

        })
      },
      // popupCallback(row){
      //   this.form.setFieldsValue(pick(row,'sampleTyp','userCode','sampleQty','sampleCnt','startTime','endTime','totalNetQty','sampleGap'))
      // },
    }
  }
</script>