<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form :form="form" slot="detail">
        <a-row>
          <a-col :span="24">
            <a-form-item label="船名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['shipName', validatorRules.shipId]" placeholder="请输入船名称"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="船英文名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['shipEngName']" placeholder="请输入船英文名称"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="船号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['shipCode', validatorRules.shipId]" placeholder="请输入船号"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="如果无航次则为空" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['fixShipNo']" placeholder="请输入如果无航次则为空"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="舱位数量" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['cabinCnt']" placeholder="请输入舱位数量" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="额定载重" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['loadTun']" placeholder="请输入额定载重" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="总排水量" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['totalTun']" placeholder="请输入总排水量" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="舷宽" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['width']" placeholder="请输入舷宽" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="船长度" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['length']" placeholder="请输入船长度" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="国籍" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['nationCd']" placeholder="请输入国籍"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="经营公司" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['companyNo']" placeholder="请输入经营公司"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['remark']" placeholder="请输入备注"  ></a-input>
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
          shipId: {
            rules: [
              { required: true, message: '请输入船ID!'},
            ]
          },
        },
        url: {
          add: "/ShipInfo/shipInfo/add",
          edit: "/ShipInfo/shipInfo/edit",
          queryById: "/ShipInfo/shipInfo/queryById"
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
          this.form.setFieldsValue(pick(this.model,'shipId','shipName','shipEngName','shipCode','fixShipNo','cabinCnt','loadTun','totalTun','width','length','nationCd','companyNo','remark','status'))
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
            if(this.model.shipId == null){
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
        this.form.setFieldsValue(pick(row,'shipId','shipName','shipEngName','shipCode','fixShipNo','cabinCnt','loadTun','totalTun','width','length','nationCd','companyNo','remark','status'))
      },
    }
  }
</script>