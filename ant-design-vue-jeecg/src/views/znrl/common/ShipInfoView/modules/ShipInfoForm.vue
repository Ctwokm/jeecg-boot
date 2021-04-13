<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form :form="form" slot="detail">
        <a-row>
          <a-col :span="24">
            <a-form-item label="船名" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['shipName', {rules:[{required:true}]}]" placeholder="请输入船名"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="船英文名" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['shipEngName']" placeholder="请输入船英文名"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="船号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['shipCode', {rules:[{required:true}]}]" placeholder="请输入船号"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="班次" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['fixShipNo']" placeholder="请输入班次"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="舱位数" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['cabinCnt', {rules:[{required:true}],initialValue:'5'}]" placeholder="请输入舱位数" :min="1" :max="7" :step="1" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="状态" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-dict-select-tag type="radio" v-decorator="['status', {rules:[{required:true}],initialValue:'1'}]" :trigger-change="true" dictCode="gk_ship_info_status"/>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="额定载重" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['loadTun']" placeholder="请输入额定载重" :min="0" :max="1000000" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="总排水量" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['totalTun']" placeholder="请输入总排水量" :min="0" :max="1000000" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="船宽(m)" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['width']" placeholder="请输入船宽(m)" :min="0" :max="200" :step="0.01" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="船长(m)" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['length']" placeholder="请输入船长(m)" :min="0" :max="1000" :step="0.01" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="国籍" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['nationCd']" placeholder="请输入国籍"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="运输公司" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-popup v-decorator="['carrierName']"
                       :trigger-change="true"
                       code="CARRIER_INFO"
                       org-fields="carrier_no,carrier_name"
                       dest-fields="companyNo,carrierName"
                       @callback="popupCallback" />
              <a-input v-decorator="['companyNo']" disabled></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-textarea v-decorator="['remark']" placeholder="请输入备注" :rows="3" />
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
    name: 'ShipInfoForm',
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
        },
        url: {
          add: "/common/shipInfo/add",
          edit: "/common/shipInfo/edit",
          /*queryById: "/common/shipnfo/queryById"*/
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
      //加载表单data
      this.showData();
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
          this.form.setFieldsValue(pick(this.model, 'shipName', 'shipEngName', 'shipCode', 'fixShipNo', 'cabinCnt', 'status', 'loadTun', 'totalTun', 'width', 'length', 'nationCd', 'carrierName', 'companyNo', 'remark'))
        })
      },
      //加载表单data
      showData(){
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
            if(!this.model.shipId){
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
        this.form.setFieldsValue(row)
      }
    }
  }
</script>

<style scoped>
  .ant-input-number{
    width: 100%;
  }
</style>
