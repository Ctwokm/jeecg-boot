<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form :form="form" slot="detail">
        <a-row>
          <a-col :span="24">
            <a-form-item label="来煤批次号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['batchNo', validatorRules.batchNo]" placeholder="请输入来煤批次号"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="批次类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['batchNoType']" placeholder="请输入批次类型"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="trainNo" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['trainNo']" placeholder="请输入trainNo"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="供应商" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['venNo']" placeholder="请输入供应商"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="煤品种" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['coalNo']" placeholder="请输入煤品种"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="采样编码" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['sampleCode']" placeholder="请输入采样编码"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="制样编码" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['samplingCode']" placeholder="请输入制样编码"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="采样编码" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['laborCode']" placeholder="请输入采样编码"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="批次序号：当日生成批次序号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['dayBatchNum']" placeholder="请输入批次序号：当日生成批次序号" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="本批次包含车号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['carIds']" placeholder="请输入本批次包含车号"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="总车数" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['carNum']" placeholder="请输入总车数" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="本批次总净重、预计总重" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['allNetQty']" placeholder="请输入本批次总净重、预计总重" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="是否已生效" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['isValid']" placeholder="请输入是否已生效"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="建立时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date placeholder="请选择建立时间" v-decorator="['insertTime']" :trigger-change="true" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="建立用户" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['opCode']" placeholder="请输入建立用户"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="更新用户" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['updateCode']" placeholder="请输入更新用户"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="矿点" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['mineNo']" placeholder="请输入矿点"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="本批次总票重" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['allTickQty']" placeholder="请输入本批次总票重" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="运输单位编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['carrierNo']" placeholder="请输入运输单位编号"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="来煤计划ID" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['carPlanId']" placeholder="请输入来煤计划ID" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="人工采用编码" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['manSampleCode']" placeholder="请输入人工采用编码"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="批次关联ID，如底样面样和主批次关联" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['batchRelaId']" placeholder="请输入批次关联ID，如底样面样和主批次关联"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['remark']" placeholder="请输入备注"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="合同编码" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['contractNo']" placeholder="请输入合同编码"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="receiveTime" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date placeholder="请选择receiveTime" v-decorator="['receiveTime']" :trigger-change="true" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="receiveOp" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['receiveOp']" placeholder="请输入receiveOp"  ></a-input>
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
    name: 'BatchNoInfoForm',
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
        confirmLoading: false,
        validatorRules: {
          batchNo: {
            rules: [
              { required: true, message: '请输入来煤批次号!'},
            ]
          },
        },
        url: {
          add: "/common/batchNoInfo/add",
          edit: "/common/batchNoInfo/edit",
          queryById: "/common/batchNoInfo/queryById"
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
          this.form.setFieldsValue(pick(this.model,'batchNo','batchNoType','trainNo','venNo','coalNo','sampleCode','samplingCode','laborCode','dayBatchNum','carIds','carNum','allNetQty','isValid','insertTime','opCode','updateCode','mineNo','allTickQty','carrierNo','carPlanId','manSampleCode','batchRelaId','remark','contractNo','receiveTime','receiveOp'))
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
        this.form.setFieldsValue(pick(row,'batchNo','batchNoType','trainNo','venNo','coalNo','sampleCode','samplingCode','laborCode','dayBatchNum','carIds','carNum','allNetQty','isValid','insertTime','opCode','updateCode','mineNo','allTickQty','carrierNo','carPlanId','manSampleCode','batchRelaId','remark','contractNo','receiveTime','receiveOp'))
      },
    }
  }
</script>