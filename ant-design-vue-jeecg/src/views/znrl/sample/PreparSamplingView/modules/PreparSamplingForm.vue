<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form :form="form" slot="detail" >
        <a-row :gutter = "24">
          <a-col :span="12">
            <a-form-item label="制样方式" :label-col="{ span: 8 }" :wrapper-col="{ span: 16 }">

              <j-dict-select-tag  :triggerChange="true" v-decorator="['zyType']" placeholder="制样方式" dictCode="gk_sampling_type" />
<!--              <j-dict-select-tag v-model="sampleTyp" placeholder="采样类型" dictCode="gk_sample_type" style="width: 100%"/>-->
            </a-form-item>
          </a-col>
          <a-col :span="1">

          </a-col>
          <a-col :span="11">
            <a-form-item label="来样重量(g)" :label-col="{ span: 8 }" :wrapper-col="{ span: 8 }">
              <a-input v-decorator="['zyWeight',validatorRules.zyWeight]" placeholder="来样重量"  ></a-input>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter = "24">
          <a-col :span="12">
            <a-form-item label="6mm全水样封装码" :label-col="{ span: 8 }" :wrapper-col="{ span: 16 }">
              <a-input v-decorator="['packCode6']" placeholder="请输入6mm全水样封装码" ></a-input>
            </a-form-item>

          </a-col>
          <a-col :span="1">
            <a-button type="primary" @click="createPackCode(61)" icon="undo" />
          </a-col>

          <a-col :span="11">
            <a-form-item label="重量(g)" :label-col="{ span: 8 }" :wrapper-col="{ span: 8 }">
              <a-input v-decorator="['sampleWeight61',validatorRules.sampleWeight61]" placeholder="请输入重量"  ></a-input>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter = "24">
          <a-col :span="12">
            <a-form-item label="3mm存查样封装码" :label-col="{ span: 8 }" :wrapper-col="{ span: 16 }">
              <a-input v-decorator="['packCode3']" placeholder="请输入3mm存查样封装码"  ></a-input>
            </a-form-item>
          </a-col>

          <a-col :span="1">
            <a-button type="primary" @click="createPackCode(32)" icon="undo" />
          </a-col>

          <a-col :span="11">
            <a-form-item label="重量(g)" :label-col="{ span: 8 }" :wrapper-col="{ span: 8 }">
              <a-input v-decorator="['sampleWeight32',validatorRules.sampleWeight32]" placeholder="请输入重量"  ></a-input>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter = "24">
          <a-col :span="12">
            <a-form-item label="0.2mm化验样封装码" :label-col="{ span: 8 }" :wrapper-col="{ span: 16 }">
              <a-input v-decorator="['packCode21']" placeholder="请输入0.2mm化验样封装码"  ></a-input>
            </a-form-item>
          </a-col>

          <a-col :span="1">
            <a-button type="primary" @click="createPackCode(21)" icon="undo" />
          </a-col>

          <a-col :span="11">
            <a-form-item label="重量(g)" :label-col="{ span: 8 }" :wrapper-col="{ span: 8 }">
              <a-input v-decorator="['sampleWeight21',validatorRules.sampleWeight21]" placeholder="请输入重量"  ></a-input>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter = "24">
          <a-col :span="12">
            <a-form-item label="0.2mm存查样封装码" :label-col="{ span: 8 }" :wrapper-col="{ span: 16 }">
              <a-input v-decorator="['packCode22']" placeholder="请输入0.2mm存查样封装码"  ></a-input>
            </a-form-item>
          </a-col>

          <a-col :span="1">
            <a-button type="primary" @click="createPackCode(22)" icon="undo" />
          </a-col>

          <a-col :span="11">
            <a-form-item label="重量(g)":label-col="{ span: 8 }" :wrapper-col="{ span: 8 }">
              <a-input v-decorator="['sampleWeight22',validatorRules.sampleWeight22]" placeholder="重量"  ></a-input>
            </a-form-item>
          </a-col>

        </a-row>
        <a-row :gutter = "24">
          <a-col :span="12">
            <a-form-item label="制样开始时间" :label-col="{ span: 8 }" :wrapper-col="{ span: 16 }">
              <j-date v-decorator="['startTime']" placeholder="制样开始时间"  :showTime="true" dateFormat="YYYY-MM-DD HH:mm:ss"/>
            </a-form-item>
          </a-col>
          <a-col :span="1">

          </a-col>
          <a-col :span="11">
            <a-form-item label="制样结束时间" :label-col="{ span: 8 }" :wrapper-col="{ span: 16 }">
              <j-date v-decorator="['endTime']" placeholder="制样结束时间"  :showTime="true" dateFormat="YYYY-MM-DD HH:mm:ss"/>
              </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter = "24">
          <a-col :span="12">
            <a-form-item label="制样人" :label-col="{ span: 8 }" :wrapper-col="{ span: 16 }">
              <j-dict-select-tag  :triggerChange="true" v-decorator="['userCode']" placeholder="请输入制样人" dictCode="sys_user,realname,id,username!='admin' order by create_time" style="width: 100%"/>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row>
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
    name: 'PreparSamplingForm',
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
        entity:{
          jsonString:{},
          pkgName:""
        },
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
          zyWeight: {
            initialValue:0,
            rules: [
              { pattern:/^(\-|\+)?\d+(\.\d+)?$/,message:'请输入纯数字'},
            ]
          },
          sampleWeight61: {
            initialValue:0,
            rules: [
              { pattern:/^(\-|\+)?\d+(\.\d+)?$/,message:'请输入纯数字'},
            ]
          },
          sampleWeight32: {
            initialValue:0,
            rules: [
              { pattern:/^(\-|\+)?\d+(\.\d+)?$/,message:'请输入纯数字'},
            ]
          },
          sampleWeight22: {
            initialValue:0,
            rules: [
              { pattern:/^(\-|\+)?\d+(\.\d+)?$/,message:'请输入纯数字'},
            ]
          },
          sampleWeight21: {
            initialValue:0,
            rules: [
              { pattern:/^(\-|\+)?\d+(\.\d+)?$/,message:'请输入纯数字'},
            ]
          },
        },
        url: {
         // add: "/common/takeSampleRec/add",
          edit: "/znrl/biztool/doCall",
          queryById: "/znrl/sampling/queryById"
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
          this.form.setFieldsValue(pick(this.model,'zyType','zyWeight','packCode6','sampleWeight61','packCode3','sampleWeight32','packCode21','sampleWeight21','packCode22','sampleWeight22','startTime','endTime','userCode'))
        })
      },
      //自动生成封装编码
      createPackCode(type){
        var samplingCode = this.model.samplingCode
        var supplementZero = "";
        for (var i=0;i<16-(samplingCode.length+type.length);i++) {
          supplementZero+="0";
        }
        if(type == 61){
          this.form.setFieldsValue({packCode6 : samplingCode+supplementZero+type});
        }else if(type == 32){
          this.form.setFieldsValue({packCode3 : samplingCode+supplementZero+type})
        }else if(type == 21){
          this.form.setFieldsValue({packCode21 : samplingCode+supplementZero+type})
        }else if(type == 22){
          this.form.setFieldsValue({packCode22 : samplingCode+supplementZero+type})
        }
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
            httpurl+=this.url.edit;
            method = 'post';
            let formData = Object.assign(this.model, values);
            console.log("表单提交数据",formData);

            this.entity.pkgName = "sampling_result_record";
            let jsonString = JSON.stringify(formData);
            console.log(jsonString);
            this.entity.jsonString  = jsonString;
            debugger;
            httpAction(httpurl,this.entity,method).then((res)=>{
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
        this.form.setFieldsValue(pick(row,'zyType','zyWeight','packCode6','sampleWeight61','packCode3','sampleWeight32','packCode21','sampleWeight21','packCode22','sampleWeight22','startTime','endTime','userCode'))
      },
    }
  }
</script>