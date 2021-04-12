<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form :form="form" slot="detail">
        <a-row>
          <a-col :span="24">
            <a-form-item label="记录ID" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['sampleIntfRecId', validatorRules.sampleIntfRecId]" placeholder="请输入记录ID" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="入厂流水（车厢采样需要填）" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['recordNo']" placeholder="请输入入厂流水（车厢采样需要填）" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="车牌号/火车车号（车厢采样需要填）" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['carId']" placeholder="请输入车牌号/火车车号（车厢采样需要填）"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="卡号：汽车填RFID扫描编码" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['cardId']" placeholder="请输入卡号：汽车填RFID扫描编码"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="采样编码" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['sampleCode']" placeholder="请输入采样编码"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="汽车来煤批次号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['carBatchNo']" placeholder="请输入汽车来煤批次号"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="火车来煤批次号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['trainBatchNo']" placeholder="请输入火车来煤批次号"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="车辆数：本批次车辆/车厢节数" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['carNum']" placeholder="请输入车辆数：本批次车辆/车厢节数" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="本批次总净重、预计总重" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['allNetQty']" placeholder="请输入本批次总净重、预计总重" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="煤种" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['coalType']" placeholder="请输入煤种"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="水分" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['coalWater']" placeholder="请输入水分" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="采样方式 0：皮带采样  1：皮带大水分旁路 2：人工采样 3：车厢采样 4：车厢大水分旁路'" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['sampleTyp']" placeholder="请输入采样方式 0：皮带采样  1：皮带大水分旁路 2：人工采样 3：车厢采样 4：车厢大水分旁路'"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="采样点数（车厢采样）" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['sampleCnt']" placeholder="请输入采样点数（车厢采样）" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="采样片区 （车厢采样）：采样填值 格式：“1；2；5” " :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['sampleAreas']" placeholder="请输入采样片区 （车厢采样）：采样填值 格式：“1；2；5” "  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="采样坐标（车厢采样）" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['sampleCordna']" placeholder="请输入采样坐标（车厢采样）"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="数据状态 0: 未读取 1: 已读取" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['dataStatus']" placeholder="请输入数据状态 0: 未读取 1: 已读取"  ></a-input>
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
            <a-form-item label="本批次总票重" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['allTickQty']" placeholder="请输入本批次总票重" style="width: 100%" />
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
    name: 'TakeSampleIntfForm',
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
          sampleIntfRecId: {
            rules: [
              { required: true, message: '请输入记录ID!'},
            ]
          },
        },
        url: {
          add: "/modules/takeSampleIntf/add",
          edit: "/modules/takeSampleIntf/edit",
          queryById: "/modules/takeSampleIntf/queryById"
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
          this.form.setFieldsValue(pick(this.model,'sampleIntfRecId','recordNo','carId','cardId','sampleCode','carBatchNo','trainBatchNo','carNum','allNetQty','coalType','coalWater','sampleTyp','sampleCnt','sampleAreas','sampleCordna','dataStatus','insertTime','opCode','updateCode','allTickQty'))
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
        this.form.setFieldsValue(pick(row,'sampleIntfRecId','recordNo','carId','cardId','sampleCode','carBatchNo','trainBatchNo','carNum','allNetQty','coalType','coalWater','sampleTyp','sampleCnt','sampleAreas','sampleCordna','dataStatus','insertTime','opCode','updateCode','allTickQty'))
      },
    }
  }
</script>