<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form :form="form" slot="detail">
        <a-row>
          <a-col :span="24">
            <a-form-item label="mineNo" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['mineNo']" placeholder="请输入mineNo"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="矿名" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['mineName']" placeholder="请输入矿名"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="area" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['area']" placeholder="请输入area"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="transDist" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['transDist']" placeholder="请输入transDist" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="transTime" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['transTime']" placeholder="请输入transTime" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="yearOutp" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['yearOutp']" placeholder="请输入yearOutp" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="coalNo" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['coalNo']" placeholder="请输入coalNo"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="lealPerson" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['lealPerson']" placeholder="请输入lealPerson"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="transMode" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['transMode']" placeholder="请输入transMode"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="mineProp" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['mineProp']" placeholder="请输入mineProp"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['remark']" placeholder="请输入备注"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="简称" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['forShort']" placeholder="请输入简称"  ></a-input>
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
    name: 'CoalMineForm',
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
          add: "/common/coalMine/add",
          edit: "/common/coalMine/edit",
          queryById: "/common/coalMine/queryById"
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
          this.form.setFieldsValue(pick(this.model,'mineNo','mineName','area','transDist','transTime','yearOutp','coalNo','lealPerson','transMode','mineProp','remark','forShort'))
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
        this.form.setFieldsValue(pick(row,'mineNo','mineName','area','transDist','transTime','yearOutp','coalNo','lealPerson','transMode','mineProp','remark','forShort'))
      },
    }
  }
</script>