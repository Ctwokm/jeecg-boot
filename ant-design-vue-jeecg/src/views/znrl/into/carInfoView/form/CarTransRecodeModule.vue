<template>
  <a-modal
    :title="title"
    :width="1200"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleOk"
    @cancel="handleCancel">

    <a-spin :spinning="confirmLoading">
      <a-form :form="form" >
        <!-- 主表单区域 -->
        <a-row class="form-row" :gutter="16">
          <a-col :lg="8">
            <a-form-item
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
              label="卡号">
              <a-input v-model="hcardId" placeholder="请输入卡号"/>
            </a-form-item>
          </a-col>
          <a-col :lg="8">
            <a-form-item
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
              label="车牌号">
              <a-input v-model="hcarId" placeholder="请输入车牌号"/>
            </a-form-item>
          </a-col>
          <a-col :lg="8">
            <a-button type="primary" @click="searchQuery" icon="search">查询车辆</a-button>
            <a-button type="primary" @click="save" icon="save" style="margin-left:15px;">保&nbsp;&nbsp;&nbsp;&nbsp;存
            </a-button>
          </a-col>
        </a-row>
        <!-- 子表单区域 -->
        <a-tabs defaultActiveKey="1">
          <a-tab-pane tab="来煤信息" key="1">
            <div>
              <a-row type="flex" style="margin-bottom:10px" :gutter="16">
                <a-col :span="10">
                  <a-form-item
                    :labelCol="labelCol"
                    :wrapperCol="wrapperCol"
                    label="卡类型">
                    <a-select placeholder="卡类型"  v-model="cardTyp">
                      <a-select-option value="1">普通卡</a-select-option>
                      <a-select-option value="2">临时卡</a-select-option>
                    </a-select>
                  </a-form-item>
                </a-col>
                <a-col :span="10">
                  <a-form-item
                    :labelCol="labelCol"
                    :wrapperCol="wrapperCol"
                    label="卡号">
                    <a-input placeholder="卡号" v-model="cardId"/>
                  </a-form-item>
                </a-col>
              </a-row>
              <a-row type="flex" style="margin-bottom:10px" :gutter="16">
                <a-col :span="10">
                  <a-form-item
                    :labelCol="labelCol"
                    :wrapperCol="wrapperCol"
                    label="运输单位">
                    <j-dict-select-tag v-model="orgNo" placeholder="请选择运输单位"  dictCode="CARRIER_INFO,carrier_name,carrier_no"/>
                  </a-form-item>
                </a-col>
                <a-col :span="10">
                  <a-form-item
                    :labelCol="labelCol"
                    :wrapperCol="wrapperCol"
                    label="车牌号">
                    <a-input placeholder="车牌号" v-model="carId"/>
                  </a-form-item>
                </a-col>
              </a-row>
              <a-row>
                <hr>
              </a-row>

              <a-row type="flex" style="margin-bottom:10px" :gutter="16">
                <a-col :span="10">
                  <a-form-item
                    :labelCol="labelCol"
                    :wrapperCol="wrapperCol"
                    label="煤矿">
                    <j-dict-select-tag v-model="mineNo" placeholder="请选择矿点" dictCode="COAL_MINE,mine_name,mine_no"/>
                  </a-form-item>
                </a-col>
                <a-col :span="10">
                  <a-form-item
                    :labelCol="labelCol"
                    :wrapperCol="wrapperCol"
                    label="煤种">
                    <j-dict-select-tag v-model="coalNo" placeholder="请选择煤种" dictCode="COAL_TYPE,coal_name,coal_no"/>
                  </a-form-item>
                </a-col>
              </a-row>
              <a-row type="flex" style="margin-bottom:10px" :gutter="16">
                <a-col :span="10">
                  <a-form-item
                    :labelCol="labelCol"
                    :wrapperCol="wrapperCol"
                    label="供应商">
                    <j-dict-select-tag placeholder="请选择供应商"  v-model="venNo" dictCode="VENDOR_INFO,VEN_name,VEN_no"/>
                  </a-form-item>
                </a-col>
                <a-col :span="10">
                  <a-form-item
                    :labelCol="labelCol"
                    :wrapperCol="wrapperCol"
                    label="采样方式">
                    <a-select placeholder="采样方式"  v-model="sampleTyp">
                      <a-select-option value="0">皮带采样</a-select-option>
                      <a-select-option value="1">皮带大水分旁路</a-select-option>
                      <a-select-option value="2">人工采样</a-select-option>
                      <a-select-option value="3" selected>车厢采样</a-select-option>
                      <a-select-option value="4">车厢大水分旁路</a-select-option>
                    </a-select>
                  </a-form-item>
                </a-col>
              </a-row>
              <a-row type="flex" style="margin-bottom:10px" :gutter="16">
                <a-col :span="10">
                  <a-form-item
                    :labelCol="labelCol"
                    :wrapperCol="wrapperCol"
                    label="湿度">
                    <a-select placeholder="湿度"  v-model="coalWater">
                      <a-select-option value="1">湿煤</a-select-option>
                      <a-select-option value="2">一般湿煤</a-select-option>
                      <a-select-option value="3">干煤</a-select-option>
                      <a-select-option value="4">一般干煤</a-select-option>
                    </a-select>
                  </a-form-item>
                </a-col>
                <a-col :span="10">
                  <a-form-item
                    :labelCol="labelCol"
                    :wrapperCol="wrapperCol"
                    label="票重">
                    <a-input placeholder="票重"  v-decorator="['tickQty', {rules: [{ required: true, message: '请输入票重!' }]}]" />
                  </a-form-item>
                </a-col>
              </a-row>
              <a-row type="flex" style="margin-bottom:10px" :gutter="16">
                <a-col :span="10">
                  <a-form-item
                    :labelCol="labelCol"
                    :wrapperCol="wrapperCol"
                    label="毛重">
                    <a-input placeholder="毛重"  v-model="mzQty"/>
                  </a-form-item>
                </a-col>
                <a-col :span="10">
                  <a-form-item
                    :labelCol="labelCol"
                    :wrapperCol="wrapperCol"
                    label="皮重">
                    <a-input placeholder="皮重" v-model="pzQty"/>
                  </a-form-item>
                </a-col>
              </a-row>
              <a-row type="flex" style="margin-bottom:10px" :gutter="16">
                <a-col :span="10">
                  <a-form-item
                    :labelCol="labelCol"
                    :wrapperCol="wrapperCol"
                    label="扣吨">
                    <a-input placeholder="扣吨" v-model="kdQty"/>
                  </a-form-item>
                </a-col>
                <a-col :span="10">
                  <a-form-item
                    :labelCol="labelCol"
                    :wrapperCol="wrapperCol"
                    label="票单号">
                    <a-input placeholder="票单号"  v-decorator="['tickNo', {rules: [{ required: true, message: '请输入票单号!' }]}]"/>
                  </a-form-item>
                </a-col>
              </a-row>
              <a-row  type="flex" style="margin-bottom:10px" :gutter="16">
                <a-col :span="10">
                  <a-form-item
                    :labelCol="labelCol"
                    :wrapperCol="wrapperCol"
                    label="备注">
                    <a-input placeholder="备注" v-model="remark"/>
                  </a-form-item>
                </a-col>
              </a-row>
            </div>
          </a-tab-pane>
        </a-tabs>

      </a-form>
    </a-spin>
    <!--通过自定义页脚取消显示：确认和取消按钮-->
    <div slot="footer"></div>
  </a-modal>
</template>

<script>
    import { httpAction, postAction,getAction } from '@/api/manage'
    import JDate from '@/components/jeecg/JDate'
    import pick from 'lodash.pick'
    import moment from 'moment'

    export default {
        name: 'JeecgOrderMainModal',
        components: {
            JDate
        },
        data() {
            return {
                title: '操作',
                visible: false,
                orderMainModel: {
                    jeecgOrderCustomerList: [{}],
                    jeecgOrderTicketList: [{}]
                },
                labelCol: {
                    xs: { span: 24 },
                    sm: { span: 5 },
                },
                wrapperCol: {
                    xs: { span: 24 },
                    sm: { span: 16 },
                },

                confirmLoading: false,
                form: this.$form.createForm(this),
                validatorRules: {},
                url: {
                    findOne:'/znrl/carInfo/focusCar',
                    add: '/znrl/carInfo/carTransRec/add',
                    edit: '/znrl/carInfo/carTransRec/mod',
                    orderCustomerList: '/test/jeecgOrderMain/queryOrderCustomerListByMainId',
                    orderTicketList: '/test/jeecgOrderMain/queryOrderTicketListByMainId',
                },
                hcardId: '',
                hcarId: '',
                cardTyp: '',
                cardId: '',
                orgNo: '',
                carId: '',
                mineNo:'',
                coalNo:'',
                sampleTyp:'',
                coalWater:'',
                recordNo:'',
                tickNo:'',
                tickQty:'',
                venNo:'',
                mzQty:'',
                pzQty:'',
                kdQty:'',
                remark:''
            }
        },
        created() {
        },
        methods: {
            save() {
                console.log("zou--这个是："+this.doActionTag+"操作")
                this.form.validateFields((err, values) => {});
                let venNo = this.venNo;
                if ((venNo == null || venNo == '' || venNo.length == 0)) {
                    alert('请选择供应商信息!');
                    return false
                }
                this.$confirm("确认提交吗？").then(() => {
                    let params = {}
                    params.cardTyp = this.cardTyp;
                    params.cardId = this.cardId;
                    params.orgNo = this.orgNo;
                    params.carId = this.carId;
                    params.mineNo = this.mineNo;
                    params.coalNo = this.coalNo;
                    params.sampleTyp = this.sampleTyp;
                    params.coalWater = this.coalWater;
                    params.recordNo = this.recordNo;
                    params.tickNo = this.tickNo;
                    params.tickQty = this.tickQty;
                    params.venNo = this.venNo;
                    params.mzQty = this.mzQty;
                    params.pzQty = this.pzQty;
                    params.kdQty = this.kdDty;
                    params.remark = this.remark;
                    /*判断是add还是mod*/
                    postAction(this.url.add,params).then((res)=>{
                        console.log(res);
                        //console.log(res.result.cardId);
                        if (res.success) {
                            this.$message.success(res.message);
                        } else {
                            this.$message.warning(res.message)
                        }
                    })
                }).catch(() => {
                    return false
                });

            },
            add() {
                this.edit({})
            },
            edit(record) {
                console.log("加载当前行数据");
                this.visible = true
                this.cardTyp = record.cardTyp;
                this.cardId = record.cardId;
                this.orgNo = record.orgNo;
                this.carId = record.carId;
                this.mineNo = record.mineNo;
                this.coalNo = record.coalNo;
                this.sampleTyp = record.sampleTyp;
                this.coalWater = record.coalWater;
                this.recordNo = record.recordNo;
                this.tickNo = record.tickNo;
                this.tickQty = record.tickQty;
                this.venNo = record.venNo;
                this.mzQty = record.mzQty;
                this.pzQty = record.pzQty;
                this.kdDty = record.kdDty;

            },
            close() {
                this.$emit('close')
                this.visible = false
            },
            handleOk() {
                const that = this
                // 触发表单验证
                this.form.validateFields((err, values) => {
                    if (!err) {
                        that.confirmLoading = true
                        let httpurl = ''
                        let method = ''
                        if (!this.orderMainModel.id) {
                            httpurl += this.url.add
                            method = 'post'
                        } else {
                            httpurl += this.url.edit
                            method = 'put'
                        }
                        let orderMainData = Object.assign(this.orderMainModel, values)
                        //时间格式化
                        orderMainData.orderDate = orderMainData.orderDate ? orderMainData.orderDate.format('YYYY-MM-DD HH:mm:ss') : null
                        let formData = {
                            ...orderMainData,
                            jeecgOrderCustomerList: orderMainData.jeecgOrderCustomerList,
                            jeecgOrderTicketList: orderMainData.jeecgOrderTicketList
                        }
                        console.log(formData)
                        httpAction(httpurl, formData, method).then((res) => {
                            if (res.success) {
                                that.$message.success(res.message)
                                that.$emit('ok')
                            } else {
                                that.$message.warning(res.message)
                            }
                        }).finally(() => {
                            that.confirmLoading = false
                            that.close()
                        })

                    }
                })
            },
            handleCancel() {
                this.close()
            },
            addRowCustom() {
                this.orderMainModel.jeecgOrderCustomerList.push({})
                this.$forceUpdate()
            },
            delRowCustom(index) {
                console.log(index)
                let all = this.form.getFieldsValue()
                all['jeecgOrderCustomerList'].splice(index, 1)
                this.form.setFieldsValue(all)
                this.orderMainModel.jeecgOrderCustomerList.splice(index, 1)
                this.$forceUpdate()
            },
            addRowTicket() {
                this.orderMainModel.jeecgOrderTicketList.push({})
                console.log(this.orderMainModel.jeecgOrderTicketList)
                this.$forceUpdate()
            },
            delRowTicket(index) {
                console.log(index)
                let all = this.form.getFieldsValue()
                all['jeecgOrderTicketList'].splice(index, 1)
                this.form.setFieldsValue(all)
                this.orderMainModel.jeecgOrderTicketList.splice(index, 1)
                this.$forceUpdate()
            },
            searchQuery() {
                let params = {}
                let hcardId = this.hcardId
                let hcarId = this.hcarId
                if ((hcardId == null || hcardId == '' || hcardId.length == 0) && (hcarId == null || hcarId == '' || hcarId.length == 0)) {
                    alert('请至少输入一个查询条件!')
                    return false
                }
                if (hcardId != null && hcardId != '' && hcardId.length > 0) {
                    params.cardId = hcardId;
                }

                if (hcarId != null && hcarId != '' && hcarId.length > 0) {
                    params.carId = hcarId;
                }
                postAction(this.url.findOne,params).then((res)=>{
                    console.log(res);
                    //console.log(res.result.cardId);
                    if (res.success) {
                        this.$message.success(res.message);
                        this.hcardId =res.result.cardId;
                        this.hcarId = res.result.carId;
                        this.cardTyp = res.result.cardTyp;
                        this.carId= res.result.carId;
                        this.cardId= res.result.cardId;
                        this.orgNo= res.result.orgNo;
                    } else {
                        this.$message.warning(res.message)
                    }
                })

            },

        }
    }
</script>

<style scoped>
  .ant-btn {
    padding: 0 10px;
    margin-left: 3px;
  }

  .ant-form-item-control {
    line-height: 0px;
  }

  /** 主表单行间距 */
  .ant-form .ant-form-item {
    margin-bottom: 10px;
  }

  /** Tab页面行间距 */
  .ant-tabs-content .ant-form-item {
    margin-bottom: 0px;
  }

</style>
