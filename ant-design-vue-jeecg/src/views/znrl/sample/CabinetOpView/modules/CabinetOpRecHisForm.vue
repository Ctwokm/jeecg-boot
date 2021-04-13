<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form :form="form" slot="detail">
        <a-row>
          <a-col :span="24">
            <a-form-item label="记录ID" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['recHisId', validatorRules.recHisId]" placeholder="请输入记录ID" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="煤样类型  61:6mm全水样 62:6mm存查样 31:3mm化验样 32:3mm存查样 21:0.2mm化验样 22:0.2mm存查样" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['sampleType']" placeholder="请输入煤样类型  61:6mm全水样 62:6mm存查样 31:3mm化验样 32:3mm存查样 21:0.2mm化验样 22:0.2mm存查样"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="样瓶编码:封装码（16字符）
1--8字符表示制样日期，例如20141225
9-14字符表示制样时间，例如213450
15-16字符表示煤样类型
06：6mm全水样
03：3mm存查样
21：0.2mm化验样
22：0.2mm存查样
" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['packCode']" placeholder="请输入样瓶编码:封装码（16字符）
1--8字符表示制样日期，例如20141225
9-14字符表示制样时间，例如213450
15-16字符表示煤样类型
06：6mm全水样
03：3mm存查样
21：0.2mm化验样
22：0.2mm存查样
"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="柜名;存查样柜1-10" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['ggName']" placeholder="请输入柜名;存查样柜1-10"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="层名 :A1到A14 ;B1到B14
" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['ccName']" placeholder="请输入层名 :A1到A14 ;B1到B14
"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="位名 1到30" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['wwName']" placeholder="请输入位名 1到30"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="1：自动存样（自动制样间或人工制样间->存查样柜）
2：自动取样（存查样柜->化验室）
3：自动弃样（存查样柜->人工制样间）
4：直传（自动制样间或人工制样间->存化验室）
5：人工存样（存查样间）
6：人工取样（存查样间）
" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['opType', validatorRules.opType]" placeholder="请输入1：自动存样（自动制样间或人工制样间->存查样柜）
2：自动取样（存查样柜->化验室）
3：自动弃样（存查样柜->人工制样间）
4：直传（自动制样间或人工制样间->存化验室）
5：人工存样（存查样间）
6：人工取样（存查样间）
"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="存取操作时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date placeholder="请选择存取操作时间" v-decorator="['opTime']" :trigger-change="true" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="操作人" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['operator']" placeholder="请输入操作人"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="存取煤样的来源设备名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['sourceName']" placeholder="请输入存取煤样的来源设备名称"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="存取煤样的目标设备名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['destName']" placeholder="请输入存取煤样的目标设备名称"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="制样时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date placeholder="请选择制样时间" v-decorator="['makeSampleTime']" :trigger-change="true" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="1：是 0：否" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['isSuccessed']" placeholder="请输入1：是 0：否"  ></a-input>
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
    name: 'CabinetOpRecHisForm',
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
          recHisId: {
            rules: [
              { required: true, message: '请输入记录ID!'},
            ]
          },
          opType: {
            rules: [
              { required: true, message: '存样类型'},
            ]
          },
        },
        url: {
          add: "/common/cabinetOpRecHis/add",
          edit: "/common/cabinetOpRecHis/edit",
          queryById: "/common/cabinetOpRecHis/queryById"
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
          this.form.setFieldsValue(pick(this.model,'recHisId','sampleType','packCode','ggName','ccName','wwName','opType','opTime','operator','sourceName','destName','makeSampleTime','isSuccessed','insertTime','opCode','updateCode'))
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
        this.form.setFieldsValue(pick(row,'recHisId','sampleType','packCode','ggName','ccName','wwName','opType','opTime','operator','sourceName','destName','makeSampleTime','isSuccessed','insertTime','opCode','updateCode'))
      },
    }
  }
</script>