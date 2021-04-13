<template>
  <a-modal
    :title="title"
    :width="1200"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleOk"
    @cancel="handleCancel">

    <a-spin :spinning="confirmLoading">
      <a-form :form="form">
        <a-tabs defaultActiveKey="1">
          <a-tab-pane tab="来煤信息" key="1">
            <div>
              <a-row type="flex" style="margin-bottom:10px" :gutter="16">
                <a-col :lg="10">
                  <a-form-item
                    :labelCol="labelCol"
                    :wrapperCol="wrapperCol"
                    label="车牌号">
                    <a-input v-decorator="['carId']" placeholder="请输入车牌号"/>
                  </a-form-item>
                </a-col>
                <a-col :span="10">
                  <a-form-item
                    :labelCol="labelCol"
                    :wrapperCol="wrapperCol"
                    label="入厂时间">
                    <j-date v-decorator="['recordDtm']" :showTime="true" dateFormat="YYYY-MM-DD HH:mm:ss" style="width: 314px"/>
                  </a-form-item>
                </a-col>
              </a-row>
              <a-row type="flex" style="margin-bottom:10px" :gutter="16">
                <a-col :span="10">
                  <a-form-item
                    :labelCol="labelCol"
                    :wrapperCol="wrapperCol"
                    label="煤矿">
                    <j-dict-select-tag v-decorator="['colryNo',validatorRules.name]" :triggerChange="true"
                                       placeholder="请选择矿点" dictCode="COAL_MINE,mine_name,mine_no"/>
                  </a-form-item>
                </a-col>
                <a-col :span="10">
                  <a-form-item
                    :labelCol="labelCol"
                    :wrapperCol="wrapperCol"
                    label="煤种">
                    <j-dict-select-tag v-decorator="['coalNo',validatorRules.name]" :triggerChange="true"
                                       placeholder="请选择煤种" dictCode="COAL_TYPE,coal_name,coal_no"/>
                  </a-form-item>
                </a-col>
              </a-row>
              <a-row type="flex" style="margin-bottom:10px" :gutter="16">
                <a-col :span="10">
                  <a-form-item
                    :labelCol="labelCol"
                    :wrapperCol="wrapperCol"
                    label="供应商">
                    <j-dict-select-tag placeholder="请选择供应商" v-decorator="['venNo',validatorRules.name]"
                                       :triggerChange="true" dictCode="VENDOR_INFO,VEN_name,VEN_no"/>
                  </a-form-item>
                </a-col>
                <a-col :span="10">
                  <a-form-item
                    :labelCol="labelCol"
                    :wrapperCol="wrapperCol"
                    label="采样方式">
                    <a-select placeholder="采样方式" v-decorator="['sampleTyp']">
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
                    label="毛重">
                    <a-input placeholder="毛重" v-decorator="['mzQty']"/>
                  </a-form-item>
                </a-col>
                <a-col :span="10">
                  <a-form-item
                    :labelCol="labelCol"
                    :wrapperCol="wrapperCol"
                    label="皮重">
                    <a-input placeholder="皮重" v-decorator="['pzQty']"/>
                  </a-form-item>
                </a-col>
              </a-row>
              <a-row type="flex" style="margin-bottom:10px" :gutter="16">
                <a-col :span="10">
                  <a-form-item
                    :labelCol="labelCol"
                    :wrapperCol="wrapperCol"
                    label="称重时间">
                    <j-date v-decorator="['czDtm']" :showTime="true" style="width: 314px" dateFormat="YYYY-MM-DD HH:mm:ss"/>
                  </a-form-item>
                </a-col>
                <a-col :span="10">
                  <a-form-item
                    :labelCol="labelCol"
                    :wrapperCol="wrapperCol"
                    label="采样机">
                    <a-select placeholder="采样机" v-decorator="['cyjNo']">
                      <a-select-option value="1"># 1</a-select-option>
                      <a-select-option value="2"># 2</a-select-option>
                      <a-select-option value="3"># 3</a-select-option>
                    </a-select>
                  </a-form-item>
                </a-col>
              </a-row>
              <a-row type="flex" style="margin-bottom:10px" :gutter="16">
                <a-col :span="10">
                  <a-form-item
                    :labelCol="labelCol"
                    :wrapperCol="wrapperCol"
                    label="称轻时间">
                    <j-date v-decorator="['jqDtm']" :showTime="true" style="width: 314px" dateFormat="YYYY-MM-DD HH:mm:ss"/>
                  </a-form-item>
                </a-col>
                <a-col :span="10">
                  <a-form-item
                    :labelCol="labelCol"
                    :wrapperCol="wrapperCol"
                    label="汽车状态">
                    <a-select placeholder="汽车状态" v-decorator="['leaveFlg']">
                      <a-select-option value="1">已出厂</a-select-option>
                      <a-select-option value="0">未出厂</a-select-option>
                    </a-select>
                  </a-form-item>
                </a-col>

              </a-row>


              <a-row type="flex" style="margin-bottom:10px" :gutter="16">
                <a-col :span="10">
                  <a-form-item
                    :labelCol="labelCol"
                    :wrapperCol="wrapperCol"
                    label="备注">
                    <a-textarea placeholder="备注" v-decorator="['remark']"/>
                  </a-form-item>
                </a-col>
              </a-row>
            </div>
          </a-tab-pane>
        </a-tabs>
      </a-form>
    </a-spin>
    <!--通过自定义页脚取消显示：确认和取消按钮-->
    <!-- <div slot="footer"></div>-->
  </a-modal>
</template>

<script>
    import { httpAction, postAction, getAction } from '@/api/manage'
    import JDate from '@/components/jeecg/JDate'
    import pick from 'lodash.pick'
    import moment from 'moment'
    import { JeecgListMixin } from '@/mixins/JeecgListMixin'


    export default {
        name: 'JeecgOrderMainModal',
        components: {
            JDate
        },
        mixins: [JeecgListMixin],
        data() {
            return {
                title: '操作',
                visible: false,
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
                    add:'/znrl/biztool/doCall'

                   /* findOne: '/znrl/carInfo/focusCar',
                    add: '/znrl/carInfo/carTransRec/add',
                    edit: '/znrl/carInfo/carTransRec/mod',*/

                },

            }
        },
        created() {
        },
        methods: {
            save() {
                console.log('zou--这个是：' + this.doActionTag + '操作')
                this.form.validateFields((err, values) => {
                })
                let venNo = this.venNo
                if ((venNo == null || venNo == '' || venNo.length == 0)) {
                    alert('请选择供应商信息!')
                    return false
                }
                this.$confirm('确认提交吗？').then(() => {
                    let params = {}
                    params.cardTyp = this.cardTyp
                    params.cardId = this.cardId
                    params.orgNo = this.orgNo
                    params.carId = this.carId
                    params.mineNo = this.mineNo
                    params.coalNo = this.coalNo
                    params.sampleTyp = this.sampleTyp
                    params.coalWater = this.coalWater
                    params.recordNo = this.recordNo
                    params.tickNo = this.tickNo
                    params.tickQty = this.tickQty
                    params.venNo = this.venNo
                    params.mzQty = this.mzQty
                    params.pzQty = this.pzQty
                    params.kdQty = this.kdDty
                    params.remark = this.remark
                    /*判断是add还是mod*/
                    postAction(this.url.add, params).then((res) => {
                        console.log(res)
                        //console.log(res.result.cardId);
                        if (res.success) {
                            this.$message.success(res.message)
                        } else {
                            this.$message.warning(res.message)
                        }
                    })
                }).catch(() => {
                    return false
                })

            },
            add() {
                this.edit({})
            },
            edit(record) {
                console.log('加载当前行数据')
                /*将table展现*/
                this.visible = true
                this.model = Object.assign({}, record)
                console.log(this.model)
                this.$nextTick(() => {
                    this.form.setFieldsValue(pick(this.model, 'cardTyp', 'cardId', 'carId', 'orgNo', 'carId',
                        'colryNo', 'coalNo', 'sampleTyp', 'coalWater', 'recordNo', 'tickNo', 'tickQty',
                        'venNo', 'mzQty', 'remark', 'pzQty', 'kdDty', 'czDtm','jqDtm','cyjNo','leaveFlg','recordDtm'))
                })
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
                        let httpurl = this.url.add
                        let method = 'post'
                        //在进去赋值前先把
                        this.model.id = this.model.carId;
                        //获取form中的数据
                        let formData = Object.assign(this.model, values)
                       /* //打印数据
                        console.log("formData：==============================")
                        console.log(formData);*/
                        //判断是否有id值，如果没有表示没有选中行，表示是add
                        if (this.model.id == undefined) {
                            // alert("add");
                            //没有id 发卡操作
                            this.entity.pkgName ='car_truck_trans_entry'
                            formData.businessTag = 'TRUCK_MANUAL_ENTRY'
                            formData.doActionTag = 'ADD'
                            this.entity.jsonString = JSON.stringify(formData);
                            //打印数据
                            console.log("entity：==============================")
                            console.log(this.entity);
                        } else {
                            // alert("edit");
                            this.entity.pkgName ='car_truck_trans_entry'
                            formData.businessTag = 'TRUCK_MANUAL_MOD'
                            formData.doActionTag = 'MOD'
                            this.entity.jsonString = JSON.stringify(formData);
                            //打印数据
                            console.log("entity：==============================")
                            console.log(this.entity);
                        }

                        httpAction(httpurl, this.entity, method).then((res) => {
                            if (res.success) {
                                that.$message.success(res.message)
                                that.$emit('ok')
                            } else {
                                that.$message.warning(res.message)
                            }
                        }).finally(() => {
                            //不管处理是否成功都关闭编辑窗口
                            that.confirmLoading = false
                            that.close()
                        })

                    }
                })
            },
            handleCancel() {
                this.close()
                /*取消后重置*/
                this.form.resetFields()

            },
            /*引入混合js后会默认加载数据 调url.list 自定义的方法不加载list*/
            loadData(arg) {},
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
                    params.cardId = hcardId
                }

                if (hcarId != null && hcarId != '' && hcarId.length > 0) {
                    params.carId = hcarId
                }
                postAction(this.url.findOne, params).then((res) => {
                    console.log(res)
                    //console.log(res.result.cardId);
                    if (res.success) {
                        this.$message.success(res.message)
                        this.hcardId = res.result.cardId
                        this.hcarId = res.result.carId
                        this.cardTyp = res.result.cardTyp
                        this.carId = res.result.carId
                        this.cardId = res.result.cardId
                        this.orgNo = res.result.orgNo
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
