<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form :form="form" slot="detail">
        <a-row>
          <a-col :span="24">
            <b>基本信息</b><br>
            <hr>
          </a-col>
        </a-row>
        <a-row>
          <a-col :span="8">
            <a-form-item label="车卡类型" :labelCol="labelCol" :wrapperCol="wrapperCol" :disabled="rowDisabled">
              <a-select placeholder="车卡类型" v-decorator="['cardTyp']">
                <a-select-option value="1">普通卡</a-select-option>
                <a-select-option value="2">临时卡</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="卡号" :labelCol="labelCol" :wrapperCol="wrapperCol" :disabled="rowDisabled">
              <a-input v-decorator="['cardId', { rules: [{ required: true, message: '请刷卡!' }] }]"
                       placeholder="请扫卡"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-button type="primary" style="margin-left:100px;margin-top: 3px;" @click="getCard" icon="search">扫描卡
            </a-button>
          </a-col>
        </a-row>
        <a-row>
          <a-col :span="8">
            <a-form-item label="前车牌" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['carId', { rules: [{ required: true, message: '请输入车牌号！' }] }]"
                       placeholder="请输入前车牌"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="后车牌" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['backcarId']" placeholder="请输入后车牌"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="标准皮重" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['stdQty']" placeholder="请输入标准皮重"></a-input>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row>
          <a-col :span="8">
            <a-form-item label="浮动皮重" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['floatQty']" placeholder="请输入浮动皮重"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="汽车车型" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-select placeholder="汽车车型" v-decorator="['carTyp']">
                <a-select-option value="0">半挂</a-select-option>
                <a-select-option value="1">罐车</a-select-option>
                <a-select-option value="2">双挂</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="运输类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-dict-select-tag :triggerChange="true" v-decorator="['transTyp',validatorRules.name]"
                                 placeholder="请选择车辆类型"
                                 dictCode="dictionary_table,name,id,type = 'CAR_TYPE'"/>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row>
          <a-col :span="8">
            <a-form-item label="状态" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-select placeholder="状态" v-decorator="['validSta']">
                <a-select-option value="0">无效</a-select-option>
                <a-select-option value="1">有效</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="失效时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date placeholder="请选择失效时间" v-decorator="['endDtm']" :trigger-change="true" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['rmtNot']" placeholder="请选择备注"></a-input>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row>
          <a-col :span="24">
            <b>车厢信息（单位：mm）</b><br>
            <hr>
          </a-col>
        </a-row>

        <!--模板测试-->
        <a-row>
          <a-col :span="12">
            <a-form-item label="车厢长度" :label-col="{ span: 8 }" :wrapper-col="{ span: 16 }">
              <a-input v-decorator="['car1Length', { rules: [{ required: true, message: '请输入车厢长度!' }] }]"
                       placeholder="请输入车厢长度"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="车厢宽度" :label-col="{ span: 8 }" :wrapper-col="{ span: 16 }">
              <a-input v-decorator="['car1Width', { rules: [{ required: true, message: '请输入车厢宽度!' }] }]"
                       placeholder="请输入车厢宽度"></a-input>
            </a-form-item>
          </a-col>
        </a-row>

        <a-row>
          <a-col :span="12">
            <a-form-item label="车厢前底离地高度" :label-col="{ span: 8 }" :wrapper-col="{ span: 16 }">
              <a-input v-decorator="['car1ntfLength', { rules: [{ required: true, message: '请输入!' }] }]"
                       placeholder="请输入车厢前底离地高度"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="车厢后底离地高度" :label-col="{ span: 8 }" :wrapper-col="{ span: 16 }">
              <a-input v-decorator="['car1L10TL9Length', { rules: [{ required: true, message: '请输入!' }] }]"
                       placeholder="请输入车厢前底离地高度"></a-input>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row>
          <a-col :span="12">
            <a-form-item label="车底坡距车厢后内壁" :label-col="{ span: 8 }" :wrapper-col="{ span: 16 }">
              <a-input v-decorator="['car1L11TL10Length', { rules: [{ required: true, message: '请输入!' }] }]"
                       placeholder="请输入数值"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="拉筋1离车厢后内壁" :label-col="{ span: 8 }" :wrapper-col="{ span: 16 }">
              <a-input v-decorator="['car1L1TWLength', { rules: [{ required: true, message: '请输入!' }] }]"
                       placeholder="请输入数值"></a-input>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row>
          <a-col :span="12">
            <a-form-item label="拉筋2离车厢后内壁" :label-col="{ span: 8 }" :wrapper-col="{ span: 16 }">
              <a-input v-decorator="['car1L2TL1Length']" placeholder="请输入数值" ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="拉筋3离车厢后内壁" :label-col="{ span: 8 }" :wrapper-col="{ span: 16 }">
              <a-input v-decorator="['car1L3TL2Length']" placeholder="请输入数值"></a-input>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row>
          <a-col :span="12">
            <a-form-item label="拉筋4离车厢后内壁" :label-col="{ span: 8 }" :wrapper-col="{ span: 16 }">
              <a-input v-decorator="['car1L4TL3Length']" placeholder="请输入数值"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="拉筋5离车厢后内壁" :label-col="{ span: 8 }" :wrapper-col="{ span: 16 }">
              <a-input v-decorator="['car1L5TL4Length']" placeholder="请输入数值"></a-input>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row>
          <a-col :span="12">
            <a-form-item label="拉筋6离车厢后内壁" :label-col="{ span: 8 }" :wrapper-col="{ span: 16 }">
              <a-input v-decorator="['car1L6TL5Length']" placeholder="请输入数值"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="拉筋7离车厢后内壁	" :label-col="{ span: 8 }" :wrapper-col="{ span: 16 }">
              <a-input v-decorator="['car1L7TL6Length']" placeholder="请输入数值"></a-input>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row>

        </a-row>
      </a-form>
    </j-form-container>
  </a-spin>
</template>

<script>

    import { httpAction, getAction } from '@/api/manage'
    import pick from 'lodash.pick'
    import { validateDuplicateValue } from '@/utils/util'
    import { JeecgListMixin } from '@/mixins/JeecgListMixin'

    export default {
        name: 'CarRegisterForm',
        components: {},
        mixins: [JeecgListMixin],
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
            },
            rowDisabled: {
                type: Boolean,
                default: false,
                required: false
            }
        },
        data() {
            return {
                form: this.$form.createForm(this),
                model: {},
                a:{},
                labelCol: {
                    xs: { span: 24 },
                    sm: { span: 5 },
                },
                wrapperCol: {
                    xs: { span: 24 },
                    sm: { span: 16 },
                },
                confirmLoading: false,
                validatorRules: {},
                url: {
                    fk: '/znrl/biztool/doCall',
                    add: '/auto/tempZhucecard/add',
                    edit: '/auto/tempZhucecard/edit',
                    queryById: '/auto/tempZhucecard/queryById'
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
            //用于取消混合jx的绑定
            loadData() {
            },
            upd(record) {
                this.form.resetFields()
                this.model = Object.assign({}, record)
                this.visible = true
                this.$nextTick(() => {
                    this.form.setFieldsValue(pick(this.model, 'cardTyp', 'cardId', 'carId', 'backcarId', 'stdQty',
                        'floatQty', 'carTyp', 'transTyp', 'validSta', 'endDtm', 'rmtNot', 'car1Length',
                        'car1Width', 'car1ntfLength', 'car1L10TL9Length', 'car1L11TL10Length', 'car1L1TWLength',
                        'car1L2TL1Length', 'car1L3TL2Length', 'car1L4TL3Length', 'car1L5TL4Length', 'car1L6TL5Length',
                        'car1L7TL6Length'))
                })
                //放开 车辆类型和卡号的disabled
                this.rowDisabled = true

            },
            edit(record) {
                this.form.resetFields()
                this.model = Object.assign({}, record)
                /*在编辑发卡的时候通过判断点进去的时候是不是有carid 来判断是新增还是edit*/
                console.log("列数据：" + record)
                this.model.id = this.model.car_id;
                console.log("此次编辑的id为：" + this.model.id);

                this.visible = true
                this.$nextTick(() => {
                    this.form.setFieldsValue(pick(this.model, 'cardTyp', 'cardId', 'carId', 'backcarId', 'stdQty',
                        'floatQty', 'carTyp', 'transTyp', 'validSta', 'endDtm', 'rmtNot', 'car1Length',
                        'car1Width', 'car1ntfLength', 'car1L10TL9Length', 'car1L11TL10Length', 'car1L1TWLength',
                        'car1L2TL1Length', 'car1L3TL2Length', 'car1L4TL3Length', 'car1L5TL4Length', 'car1L6TL5Length',
                        'car1L7TL6Length'))
                })
                /*
            * 'cardTyp', 'cardId', 'carId', 'backCarId', 'stdQty',
                        'floatQty', 'carTyp', 'transTyp', 'validSta', 'endDtm', 'rmtNot', 'car1Length',
                        'car1Width', 'car1ntfLength', 'car1L10TL9Length', 'car1L11TL10Length', 'car1L1TWLength',
                        'car1L2TL1Length', 'car1L3TL2Length', 'car1L4TL3Length', 'car1L5TL4Length', 'car1L6TL5Length',
                        'car1L7TL6Length'
            *
            * */
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
                        that.confirmLoading = true
                        let httpurl = this.url.fk
                        let method = 'post'

                        let a = Object;
                        // 在给列赋值的时候先获取当前列是否有值，用于后面判断是否add、mod
                        this.model.id = this.model.carId;

                        /*获取formDate 的值 */
                        let formData = Object.assign(this.model, values)
                        console.log('表单提交数据==>' + formData)
                        /*formDate Json 格式数据*/
                        /* 将 key中的 大驼峰改变为下划线格式数据*/
                        let obj2 = Object.keys(formData).reduce((newData, key) => {
                            let newKey = this.toLine(key);
                            //console.log(newData,key,newKey);
                            newData[newKey] = formData[key];
                            return newData;
                        },{});

                        console.log("转换格式后的json数据："+JSON.stringify(JSON.stringify(obj2)));

                        //判断是否有id值，如果没有表示没有选中行，表示是发卡
                        if (this.model.id == undefined) {
                            console.log("add");
                            //没有id 发卡操作
                            this.entity.pkgName = 'car_business_base'
                            obj2.businessTag = 'TRUCK_ADD'
                            obj2.doActionTag = 'ADD'
                            this.entity.jsonString = JSON.stringify(obj2);
                        } else {

                            console.log("mod");
                            this.entity.pkgName = 'car_business_base'
                            obj2.businessTag = 'TRUCK_LENGTH_MOD'
                            obj2.doActionTag = 'MOD'

                            this.entity.jsonString = JSON.stringify(obj2);
                        }

                        httpAction(httpurl, this.entity, method).then((res) => {
                            //console.log("formData提交数据："+formData);
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
                            cardId: res.message
                        })

                    } else {
                        that.$message.warning(res.message)
                    }
                }).finally(() => {
                    that.confirmLoading = false
                })

            },
            popupCallback(row) {
                //this.form.setFieldsValue(pick(row,'cardId','carId','backcarId','validSta','statusNames','transTypeName','carTypeName','stdQty','regDtm','endDtm'))
            },
            /*下划线转驼峰*/
            toHump(name) {
                return name.replace(/\_(\w)/g, function (all, letter) {
                    return letter.toUpperCase();
                });
            },
            /*驼峰转下划线*/
            toLine(name) {
                return name.replace(/([A-Z]+)/g, "_$1").toLowerCase();
            },

            jsonToUnderline(obj) {
                if (obj instanceof Array) {
                    obj.forEach(function (v, i) {
                        this.jsonToUnderline(v)
                    })
                } else if (obj instanceof Object) {
                    Object.keys(obj).forEach(function (key) {
                        var newKey = this.toLine(key)
                        if (newKey !== key) {
                            obj[newKey] = obj[key]
                            delete obj[key]
                        }
                        this.toLine(obj[newKey])
                    })
                }
            }
        }

    }
</script>