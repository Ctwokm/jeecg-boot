<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form :form="form" slot="detail">
        <a-row>
          <a-col :span="24">
            <a-form-item label="控制信息记录ID" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['cmdRecId', validatorRules.cmdRecId]" placeholder="请输入控制信息记录ID" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="machinCode" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['machinCode']" placeholder="请输入machinCode"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="设备类型 1:	汽车车厢采样机 2	:	制样机 3	:	翻车机 4:		存查样柜 5:		调度转发用 6	:	火车车厢采样机 7:		入厂皮带采样机 8	:	入炉皮带采样机 " :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['machinType']" placeholder="请输入设备类型 1:	汽车车厢采样机 2	:	制样机 3	:	翻车机 4:		存查样柜 5:		调度转发用 6	:	火车车厢采样机 7:		入厂皮带采样机 8	:	入炉皮带采样机 "  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="命令代码 1：启动 2：停止 3：继续工作（采样、制样）4.卸样 5.打包 6.大水分旁路" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['commandCode', validatorRules.commandCode]" placeholder="请输入命令代码 1：启动 2：停止 3：继续工作（采样、制样）4.卸样 5.打包 6.大水分旁路"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="采样编码(采样机命令)；制样编码（制样机命令）" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['sampleCode']" placeholder="请输入采样编码(采样机命令)；制样编码（制样机命令）"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="命令发送时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date placeholder="请选择命令发送时间" v-decorator="['sendCmdTime']" :trigger-change="true" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="数据状态 0: 未读取 1: 已读取" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['dataStatus']" placeholder="请输入数据状态 0: 未读取 1: 已读取"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="入厂流水" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['recordNo']" placeholder="请输入入厂流水" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="车号：汽车采样时需要填此字段给采样机" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['carId']" placeholder="请输入车号：汽车采样时需要填此字段给采样机"  ></a-input>
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
    name: 'MachinCmdInfoForm',
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
          cmdRecId: {
            rules: [
              { required: true, message: '请输入控制信息记录ID!'},
            ]
          },
          commandCode: {
            rules: [
              { required: true, message: '请输入命令代码 1：启动 2：停止 3：继续工作（采样、制样）4.卸样 5.打包 6.大水分旁路!'},
            ]
          },
        },
        url: {
          add: "/modules/machinCmdInfo/add",
          edit: "/modules/machinCmdInfo/edit",
          queryById: "/modules/machinCmdInfo/queryById"
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
          this.form.setFieldsValue(pick(this.model,'cmdRecId','machinCode','machinType','commandCode','sampleCode','sendCmdTime','dataStatus','recordNo','carId','insertTime','opCode','updateCode'))
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
        this.form.setFieldsValue(pick(row,'cmdRecId','machinCode','machinType','commandCode','sampleCode','sendCmdTime','dataStatus','recordNo','carId','insertTime','opCode','updateCode'))
      },
    }
  }
</script>