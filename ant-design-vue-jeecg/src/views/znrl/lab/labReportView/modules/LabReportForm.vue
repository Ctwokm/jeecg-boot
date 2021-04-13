<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form :form="form" slot="detail">
        <a-row>
          <a-col :span="24">
            <a-form-item label="记录号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['labReportId', validatorRules.labReportId]" placeholder="请输入记录号" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="制样编码" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['laborCode']" placeholder="请输入制样编码"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24"><!--2第2次 3第3次 4第4次-->
            <a-form-item label="化验次数 " :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['batchType']" placeholder="请输入1初次化验 2第2次 3第3次 4第4次"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="全水" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['mt']" placeholder="请输入全水" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="水分" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['mad']" placeholder="请输入水分" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="空干基灰分" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['aad']" placeholder="请输入空干基灰分" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="干燥基灰分" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['ad']" placeholder="请输入干燥基灰分" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="收到基灰分" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['aar']" placeholder="请输入收到基灰分" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="空干基挥发分" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['vad']" placeholder="请输入空干基挥发分" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="干燥基挥发分" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['vd']" placeholder="请输入干燥基挥发分" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="收到基挥发分" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['var']" placeholder="请输入收到基挥发分" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="干燥无灰基挥发分" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['vdaf']" placeholder="请输入干燥无灰基挥发分" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="空干基全硫" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['stAd']" placeholder="请输入空干基全硫" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="干燥基全硫" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['stD']" placeholder="请输入干燥基全硫" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="收到基全硫" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['stAr']" placeholder="请输入收到基全硫" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="干燥基氢" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['hd']" placeholder="请输入干燥基氢" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="空气干燥基氢" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['had']" placeholder="请输入空气干燥基氢" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="收到基氢" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['har']" placeholder="请输入收到基氢" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="干燥无灰基氢" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['hdaf']" placeholder="请输入干燥无灰基氢" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="空气干燥基碳" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['cad']" placeholder="请输入空气干燥基碳" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="空气干燥基氮" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['nad']" placeholder="请输入空气干燥基氮" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="空干基弹筒发热量" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['qbAd']" placeholder="请输入空干基弹筒发热量" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="收到基弹筒发热量" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['qbAr']" placeholder="请输入收到基弹筒发热量" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="干燥基弹筒发热量" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['qbD']" placeholder="请输入干燥基弹筒发热量" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="干燥无灰基弹筒发热量" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['qbDaf']" placeholder="请输入干燥无灰基弹筒发热量" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="空干基高位发热量" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['qgrAd']" placeholder="请输入空干基高位发热量" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="收到基高位发热量" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['qgrAr']" placeholder="请输入收到基高位发热量" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="干燥基高位发热量" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['qgrD']" placeholder="请输入干燥基高位发热量" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="干燥无灰基高位发热量" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['qgrDaf']" placeholder="请输入干燥无灰基高位发热量" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="空干基低位发热量" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['qnetAd']" placeholder="请输入空干基低位发热量" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="收到基低位发热量J" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['qnetArj']" placeholder="请输入收到基低位发热量J" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="收到基低位发热量M" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['qnetArm']" placeholder="请输入收到基低位发热量M" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="干燥基低位发热量" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['qnetD']" placeholder="请输入干燥基低位发热量" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="空干基固定碳" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['fcad']" placeholder="请输入空干基固定碳" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="收到基固定碳" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['fcar']" placeholder="请输入收到基固定碳" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="干燥基固定碳" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['fcd']" placeholder="请输入干燥基固定碳" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="干燥无灰基固定碳" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['fcdaf']" placeholder="请输入干燥无灰基固定碳" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="焦渣特性" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['crc']" placeholder="请输入焦渣特性" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="变形温度" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['dt']" placeholder="请输入变形温度" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="软化温度" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['st']" placeholder="请输入软化温度" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="半球温度" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['ht']" placeholder="请输入半球温度" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="流动温度" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['ft']" placeholder="请输入流动温度" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24"><!--1按比例加权  2按吨位加权-->
            <a-form-item label="加权类型  " :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['proportionType']" placeholder="请输入加权类型  1按比例加权  2按吨位加权"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="加权值" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['proportion']" placeholder="请输入加权值" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24"><!-- 0未加权 1已加权 9加权后报告-->
            <a-form-item label="加权标识" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['proportionTag']" placeholder="请输入加权标识 0未加权 1已加权 9加权后报告"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24"><!--0：未审核；1：审核中；2：审核通过；3：审核未通过-->
            <a-form-item label="审核状态" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['apprStatus']" placeholder="请输入0：未审核；1：审核中；2：审核通过；3：审核未通过"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="建立时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date placeholder="请选择建立时间" v-decorator="['insertTime']" :trigger-change="true" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="化验日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date placeholder="请选择化验日期" v-decorator="['hyDate']" :trigger-change="true" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="更新用户" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['updateCode']" placeholder="请输入更新用户"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="更新时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date placeholder="请选择更新时间" v-decorator="['jhTime']" :trigger-change="true" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="更新用户" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['jhCode']" placeholder="请输入更新用户"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="建立用户" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['opCode']" placeholder="请输入建立用户"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="opDesc" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['opDesc']" placeholder="请输入opDesc"  ></a-input>
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
    name: 'LabReportForm',
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
          labReportId: {
            rules: [
              { required: true, message: '请输入记录号!'},
            ]
          },
        },
        url: {
          add: "/modules/labReport/add",
          edit: "/modules/labReport/edit",
          queryById: "/modules/labReport/queryById"
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
          this.form.setFieldsValue(pick(this.model,'labReportId','laborCode','batchType','mt','mad','aad','ad','aar','vad','vd','var','vdaf','stAd','stD','stAr','hd','had','har','hdaf','cad','nad','qbAd','qbAr','qbD','qbDaf','qgrAd','qgrAr','qgrD','qgrDaf','qnetAd','qnetArj','qnetArm','qnetD','fcad','fcar','fcd','fcdaf','crc','dt','st','ht','ft','proportionType','proportion','proportionTag','apprStatus','insertTime','hyDate','updateCode','jhTime','jhCode','opCode','opDesc','remark'))
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
        this.form.setFieldsValue(pick(row,'labReportId','laborCode','batchType','mt','mad','aad','ad','aar','vad','vd','var','vdaf','stAd','stD','stAr','hd','had','har','hdaf','cad','nad','qbAd','qbAr','qbD','qbDaf','qgrAd','qgrAr','qgrD','qgrDaf','qnetAd','qnetArj','qnetArm','qnetD','fcad','fcar','fcd','fcdaf','crc','dt','st','ht','ft','proportionType','proportion','proportionTag','apprStatus','insertTime','hyDate','updateCode','jhTime','jhCode','opCode','opDesc','remark'))
      },
    }
  }
</script>