<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form :form="form" slot="detail">
        <a-row>
          <a-col :span="24">
            <a-form-item label="卡号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['mineCardId', validatorRules.mineCardId]" placeholder="请刷卡" disabled></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-button type="primary" style="float: right;margin-top: -60px;margin-right: -13px" @click="getCard"
                      icon="search">扫描卡
            </a-button>
          </a-col>
          <a-col :span="24">
            <a-form-item label="煤矿" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-dict-select-tag :triggerChange="true" v-decorator="['mineNo',validatorRules.name]" placeholder="请选择煤矿"
                                 dictCode="COAL_MINE,mine_name,mine_no"/>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="煤种" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-select placeholder="请输入煤品种" v-decorator="['coalNo']">
                <a-select-option value="101">3500大卡混煤</a-select-option>
                <a-select-option value="100">3000大卡混煤</a-select-option>
                <a-select-option value="210288">川煤</a-select-option>
                <a-select-option value="210030">烟煤</a-select-option>
                <a-select-option value="210068">无烟煤</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="生效时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date placeholder="请选择生效时间" v-decorator="['effTime', { rules: [{ required: true, message: '请输入!' }] }]" :trigger-change="true" :showTime="true"
                      dateFormat="YYYY-MM-DD HH:mm:ss" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="失效时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date placeholder="请选择失效时间" v-decorator="['expTime', { rules: [{ required: true, message: '请输入!' }] }]" :trigger-change="true" :showTime="true"
                      dateFormat="YYYY-MM-DD HH:mm:ss" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="状态" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-select placeholder="状态" v-decorator="['validSta']">
                <a-select-option value="0">无效</a-select-option>
                <a-select-option value="1">有效</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['remark']" placeholder="请输入备注"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="矿卡类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-select placeholder="请输入矿卡所属的类型" v-decorator="['mineCardType']">
                <a-select-option value="101">煤车</a-select-option>
                <a-select-option value="102">物料车</a-select-option>
              </a-select>
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
        name: 'MineCardInfoForm',
        components: {},
        props: {
            //流程表单data
            formData: {
                type: Object,
                default: () => {
                },
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
        data() {
            return {
                form: this.$form.createForm(this),
                model: {},
                param:{},
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
                    mineCardId: {
                        rules: [
                            { required: true, message: '请输入RFID编号!' },
                        ]
                    },
                },
                url: {
                    add: '/znrl/carInfo/addMineCardInfo',
                    edit: '/znrl/carInfo/modMineCardInfo',
                    queryById: '/demo/mineCardInfo/queryById'
                }
            }
        },
        computed: {
            formDisabled() {
                if (this.formBpm === true) {
                    if (this.formData.disabled === false) {
                        return false
                    }
                    return true
                }
                return this.disabled
            },
            showFlowSubmitButton() {
                if (this.formBpm === true) {
                    if (this.formData.disabled === false) {
                        return true
                    }
                }
                return false
            }
        },
        created() {
            //如果是流程中表单，则需要加载流程表单data
            this.showFlowData()
        },
        methods: {
            add() {
                this.edit({})
            },
            getTime() {
                let nowDate = new Date()
                let date = {
                    year: nowDate.getFullYear(),
                    month: nowDate.getMonth() + 1,
                    date: nowDate.getDate(),
                }
                let systemDate = date.year + '-' + 0 + date.month + '-' + 0 + date.date;
                console.log(systemDate);
                return systemDate;

            },
            edit(record) {
                this.form.resetFields()
                this.model = Object.assign({}, record)
                this.visible = true
                this.$nextTick(() => {
                    this.form.setFieldsValue(pick(this.model, 'mineCardId', 'venNo', 'mineNo', 'coalNo', 'effTime', 'expTime', 'validSta', 'insertTime', 'remark', 'mineCardType'))
                })
            },
            //渲染流程表单数据
            showFlowData() {
                if (this.formBpm === true) {
                    let params = { id: this.formData.dataId }
                    getAction(this.url.queryById, params).then((res) => {
                        if (res.success) {
                            this.edit(res.result)
                        }
                    })
                }
            },
            submitForm() {
                const that = this
                // 触发表单验证
                this.form.validateFields((err, values) => {
                    if (!err) {
                        let formData = Object.assign(this.model, values)
                        console.log('表单提交数据', formData)

                        /*时间格式判断*/
                        console.log("expTime 失效日期:"+formData.expTime);
                        if (formData.expTime < formData.effTime){
                            alert("失效日期不能早于生效日期");
                            return false;
                        }
                        if (formData.expTime < this.getTime()){
                            alert("失效日期不能早于当前日期");
                            return false;
                        }
                        that.confirmLoading = true
                        let httpurl = ''
                        let method = ''
                        //判断当前选择是否包括mineCardId数据，没有就表示是增加add，有就表示是修改edit
                        if (!this.model.mineCardId) {
                            httpurl += this.url.add
                            method = 'post'
                            //alert("add");
                        } else {
                            httpurl += this.url.edit
                            method = 'post'
                            //alert("edit");
                        }

                        httpAction(httpurl, formData, method).then((res) => {
                            if (res.success) {
                                that.$message.success(res.message)
                                that.$emit('ok')
                            } else {
                                that.$message.warning(res.message)
                            }
                        }).finally(() => {
                            that.confirmLoading = false
                        })
                    }

                })
            },
            getCard() {
                /*通过this.form.getFieldValue来获取对应的值；通过 this.form.setFieldsValue({来设置值*/
                //设置url属性
                let httpurl = '/znrl/carInfo/touchCarInfo'
                let method = 'Post'
                httpAction(httpurl, '1', method).then((res) => {
                    if (res.success) {
                        //that.$message.success(res.message);
                        this.form.setFieldsValue({
                            mineCardId: res.message
                        })
                        // alert(res.message);
                        this.param.mineCardId=res.message;

                        if (res.success){
                            /*checkCardId*/
                            httpAction('/znrl/carInfo/checkCard',this.param,'post').then((res2)=>{
                                if (res2.success){
                                    res.message = res2.message;
                                }else{
                                    res.message = res2.message;
                                    alert(res.message);
                                    this.form.setFieldsValue({
                                        mineCardId: ''
                                    })
                                    return;
                                }
                            })
                        }

                    } else {
                        that.$message.warning(res.message)
                    }
                }).finally(() => {
                    that.confirmLoading = false
                })

            },
            popupCallback(row) {
                this.form.setFieldsValue(pick(row, 'mineCardId', 'venNo', 'mineNo', 'coalNo', 'effTime', 'expTime', 'validSta', 'insertTime', 'remark', 'mineCardType'))
            },
        }
    }
</script>