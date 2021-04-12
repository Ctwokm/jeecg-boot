<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form :form="form" slot="detail">
        <a-row>
          <a-col :span="24">
            <a-form-item label="供应商" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['venNo']" placeholder="请输入供应商"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="承运商" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['carrierNo']" placeholder="请输入承运商"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="煤品种" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['coalNo']" placeholder="请输入煤品种"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="煤矿" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['colryNo']" placeholder="请输入煤矿"  ></a-input>
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
            <a-form-item label="票重" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['tickQty']" placeholder="请输入票重" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="毛重" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['mzQty']" placeholder="请输入毛重" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="皮重" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['pzQty']" placeholder="请输入皮重" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="净重" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['netQty']" placeholder="请输入净重" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="运损" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['lossQty']" placeholder="请输入运损" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="扣吨" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['kdQty']" placeholder="请输入扣吨" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="亏吨" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['kudQty']" placeholder="请输入亏吨" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="盈吨" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['ydQty']" placeholder="请输入盈吨" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="称重时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date placeholder="请选择称重时间" v-decorator="['czDtm']" :trigger-change="true" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="检轻时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date placeholder="请选择检轻时间" v-decorator="['jqDtm']" :trigger-change="true" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="卸煤时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date placeholder="请选择卸煤时间" v-decorator="['xmDtm']" :trigger-change="true" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="速度" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['carSpd']" placeholder="请输入速度" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="火车来煤批次号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['trainBatchNo']" placeholder="请输入火车来煤批次号"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="采样编码" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['sampleCode']" placeholder="请输入采样编码"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="采样方式 ：0：皮带采样  1：皮带大水分旁路 2：人工采样 3：车厢采样 4：车厢大水分旁路" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['sampleTyp']" placeholder="请输入采样方式 ：0：皮带采样  1：皮带大水分旁路 2：人工采样 3：车厢采样 4：车厢大水分旁路"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="是否空车，1：为非空车，0或者null为空车" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['emptyFlg']" placeholder="请输入是否空车，1：为非空车，0或者null为空车"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="已出厂(0:否、1:是)" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['leaveFlg']" placeholder="请输入已出厂(0:否、1:是)"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="入厂时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date placeholder="请选择入厂时间" v-decorator="['recordDtm']" :trigger-change="true" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="出厂时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date placeholder="请选择出厂时间" v-decorator="['leaveDtm']" :trigger-change="true" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="翻车机编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['fcjCode']" placeholder="请输入翻车机编号"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="上车单是否确认 0 ：未确认 1：已确认" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['cfmFlg']" placeholder="请输入上车单是否确认 0 ：未确认 1：已确认"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="发站" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['startPlace']" placeholder="请输入发站"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="到站" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['finalPlace']" placeholder="请输入到站"  ></a-input>
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
            <a-form-item label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['remark']" placeholder="请输入备注"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="入厂轨道号  1号轨道 1#；2号轨道 2#；3号轨道 3# ..." :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['trackNo']" placeholder="请输入入厂轨道号  1号轨道 1#；2号轨道 2#；3号轨道 3# ..."  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="过衡号，1号衡：1;   2号衡：2;" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['balanceNo']" placeholder="请输入过衡号，1号衡：1;   2号衡：2;"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="对位时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date placeholder="请选择对位时间" v-decorator="['aimTime']" :trigger-change="true" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="附加批次号，如底样面样的" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['attachBatchNo']" placeholder="请输入附加批次号，如底样面样的"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="收货人（配置ID）" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['goodsReceiver']" placeholder="请输入收货人（配置ID）"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="发站时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date placeholder="请选择发站时间" v-decorator="['deliverTime']" :trigger-change="true" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="合同编码" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['contractNo']" placeholder="请输入合同编码"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="入厂流水" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['recordNo', validatorRules.recordNo]" placeholder="请输入入厂流水" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="大票编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['tickNo']" placeholder="请输入大票编号"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="车次" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['trainNo']" placeholder="请输入车次"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="卡号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['cardId']" placeholder="请输入卡号"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="车牌号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['carId']" placeholder="请输入车牌号"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="火车车型" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['carTyp']" placeholder="请输入火车车型"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="车厢序号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['xhNum']" placeholder="请输入车厢序号" style="width: 100%" />
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
    name: 'RlrecordmsthyForm',
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
          recordNo: {
            rules: [
              { required: true, message: '请输入入厂流水!'},
            ]
          },
        },
        url: {
          add: "/modules/rlrecordmsthy/add",
          edit: "/modules/rlrecordmsthy/edit",
          queryById: "/modules/rlrecordmsthy/queryById"
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
          this.form.setFieldsValue(pick(this.model,'venNo','carrierNo','coalNo','colryNo','coalSize','coalWater','tickQty','mzQty','pzQty','netQty','lossQty','kdQty','kudQty','ydQty','czDtm','jqDtm','xmDtm','carSpd','trainBatchNo','sampleCode','sampleTyp','emptyFlg','leaveFlg','recordDtm','leaveDtm','fcjCode','cfmFlg','startPlace','finalPlace','insertTime','opCode','updateCode','remark','trackNo','balanceNo','aimTime','attachBatchNo','goodsReceiver','deliverTime','contractNo','recordNo','tickNo','trainNo','cardId','carId','carTyp','xhNum'))
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
        this.form.setFieldsValue(pick(row,'venNo','carrierNo','coalNo','colryNo','coalSize','coalWater','tickQty','mzQty','pzQty','netQty','lossQty','kdQty','kudQty','ydQty','czDtm','jqDtm','xmDtm','carSpd','trainBatchNo','sampleCode','sampleTyp','emptyFlg','leaveFlg','recordDtm','leaveDtm','fcjCode','cfmFlg','startPlace','finalPlace','insertTime','opCode','updateCode','remark','trackNo','balanceNo','aimTime','attachBatchNo','goodsReceiver','deliverTime','contractNo','recordNo','tickNo','trainNo','cardId','carId','carTyp','xhNum'))
      },
    }
  }
</script>