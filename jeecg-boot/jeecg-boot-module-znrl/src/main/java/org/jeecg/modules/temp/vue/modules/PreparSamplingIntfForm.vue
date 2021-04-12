<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form :form="form" slot="detail">
        <a-row>
          <a-col :span="24">
            <a-form-item label="信息记录ID" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['samplingIntfRecId', validatorRules.samplingIntfRecId]" placeholder="请输入信息记录ID" style="width: 100%" />
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
            <a-form-item label="煤种" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['coalType']" placeholder="请输入煤种"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="粒度 1：13mm≤粒度＜50mm 2：50mm≤粒度＜100mm" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['coalSize']" placeholder="请输入粒度 1：13mm≤粒度＜50mm 2：50mm≤粒度＜100mm"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="水分 1：湿煤2：一般湿煤3：干煤4：一般干煤" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['coalWater']" placeholder="请输入水分 1：湿煤2：一般湿煤3：干煤4：一般干煤"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="样品重量" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['coalQty']" placeholder="请输入样品重量" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="样品包数" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['packCnt']" placeholder="请输入样品包数" style="width: 100%" />
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
    name: 'PreparSamplingIntfForm',
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
          samplingIntfRecId: {
            rules: [
              { required: true, message: '请输入信息记录ID!'},
            ]
          },
        },
        url: {
          add: "/modules/preparSamplingIntf/add",
          edit: "/modules/preparSamplingIntf/edit",
          queryById: "/modules/preparSamplingIntf/queryById"
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
          this.form.setFieldsValue(pick(this.model,'samplingIntfRecId','sampleCode','samplingCode','coalType','coalSize','coalWater','coalQty','packCnt','dataStatus','insertTime','opCode','updateCode'))
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
        this.form.setFieldsValue(pick(row,'samplingIntfRecId','sampleCode','samplingCode','coalType','coalSize','coalWater','coalQty','packCnt','dataStatus','insertTime','opCode','updateCode'))
      },
    }
  }
</script>