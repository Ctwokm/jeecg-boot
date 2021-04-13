<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form :form="form" slot="detail">
        <a-row>
          <a-col :span="12">
            <a-form-item label="采样类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
<!--              <a-input-number  placeholder="请输入采样类型" style="width: 100%" />  v-decorator="['sampleTyp', validatorRules.sampleTyp]"-->
              <j-dict-select-tag  :triggerChange="true" v-decorator="['sampleTyp']" placeholder="采样类型" dictCode="gk_sample_type" style="width: 100%"/>
<!--              <j-dict-select-tag v-model="sampleTyp" placeholder="采样类型" dictCode="gk_sample_type" style="width: 100%"/>-->
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="采样人" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-dict-select-tag  :triggerChange="true" v-decorator="['userCode']" placeholder="请输入采样人" dictCode="sys_user,realname,id,username!='admin' order by create_time" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="采样重量(g)" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['sampleQty',validatorRules.sampleQty]" placeholder="请输入采样重量"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="采样点数" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['sampleCnt',validatorRules.sampleCnt]" placeholder="请输入采样点数"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="开始时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date v-decorator="['startTime']" placeholder="采样开始时间"  :showTime="true" dateFormat="YYYY-MM-DD HH:mm:ss"/>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="结束时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date v-decorator="['endTime']" placeholder="采样结束时间"  :showTime="true" dateFormat="YYYY-MM-DD HH:mm:ss"/>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="总吨数(t)" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['totalNetQty',validatorRules.totalNetQty]" placeholder="请输入采样总吨数"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="间隔时间(min)" :labelCol="labelColZ" :wrapperCol="wrapperColD">
              <a-input v-decorator="['sampleGape',validatorRules.sampleGape]" placeholder="采样间隔时间"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col v-if="showFlowSubmitButton" :span="24" style="text-align: center">
            <a-button @click="submitForm">提 交</a-button>
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

  export default {
    name: 'TakeSampleRecForm',
    components: {
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
        validatorRules: {
          sampleQty: {
            initialValue:0,
            rules: [
              { pattern:/^(\-|\+)?\d+(\.\d+)?$/,message:'请输入纯数字'},
            ]
          },
          sampleCnt: {
            initialValue:0,
            rules: [
              { pattern:/^(\-|\+)?\d+(\.\d+)?$/,message:'请输入纯数字'},
            ]
          },
          totalNetQty: {
            initialValue:0,
            rules: [
              { pattern:/^(\-|\+)?\d+(\.\d+)?$/,message:'请输入纯数字'},
            ]
          },
          sampleGape: {
            initialValue:0,
            rules: [
              { pattern:/^(\-|\+)?\d+(\.\d+)?$/,message:'请输入纯数字'},
            ]
          },
        },
        url: {
         // add: "/common/takeSampleRec/add",
          edit: "/znrl/takeSample/edit",
          queryById: "/znrl/takeSample/queryById"
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
      add () {
        this.edit({});
      },
      edit (record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          //表单赋值
          this.form.setFieldsValue(pick(this.model,'sampleTyp','userCode','sampleQty','sampleCnt','startTime','endTime','totalNetQty','sampleGape'))
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
            if(!this.model.sampleCode){//验证采样编码为唯一标识
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
      popupCallback(row){
        this.form.setFieldsValue(pick(row,'sampleTyp','userCode','sampleQty','sampleCnt','startTime','endTime','totalNetQty','sampleGap'))
      },
    }
  }
</script>