<template>
  <a-modal
    :title="title"
    :width="800"
    :visible="visible"
    :confirmLoading="confirmLoading"
    :okButtonProps="{ props: {disabled: disableSubmit} }"
    @ok="handleOk"
    @cancel="handleCancel"
    cancelText="关闭">

    <!-- 编辑 -->
    <a-spin :spinning="confirmLoading" v-if="editStatus">
      <a-form :form="form">

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="车次号"
          hasFeedback>
          <a-input v-decorator="['trainNo']"
          />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="流水号"
          hasFeedback>
          <a-input v-decorator="['recordNo']"
                   />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="车厢序号"
          hasFeedback>
          <a-input v-decorator="['xhNum']"
                   />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="车号"
          hasFeedback>
          <a-input v-decorator="['carId']"
                   />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="轨道号"
          hasFeedback>
          <a-input v-decorator="['trackNo']"
                   />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="批次号"
          hasFeedback>
          <a-input v-decorator="['batchNo']"
                   />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="采样编码"
          hasFeedback>
          <a-input v-decorator="['sampleCode']"
                   />
        </a-form-item>
<!--        <a-form-item-->
<!--          :labelCol="labelCol"-->
<!--          :wrapperCol="wrapperCol"-->
<!--          label="抽检批次号"-->
<!--          hasFeedback>-->
<!--          <a-input v-decorator="['attachBatchNo']"-->
<!--                   />-->
<!--        </a-form-item>-->
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="矿点"
          hasFeedback>
<!--          <a-select style="width: 100%"-->
<!--                    options = 'this.selectOption'-->
<!--                    v-decorator="['mineName']">-->
<!--          </a-select>-->
<!--          <j-dict-select-tag v-model="queryParam.mineName" placeholder="请选择用户名称"-->
<!--                             dictCode="sys_user,realname,id"/>-->
          <j-dict-select-tag  v-decorator="['mineName', {}]" :triggerChange="true"
                              dictCode="COAL_MINE,mine_name,mine_no"/>
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="煤种"
          hasFeedback>
          <j-dict-select-tag  v-decorator="['coalName', {}]" :triggerChange="true"
                              dictCode="coal_type,coal_name,coal_no"/>
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="采样方式"
          hasFeedback>
          <a-select style="width: 100%" v-decorator="['sampleTypName']">
            <a-select-option value="0">
              皮带采样
            </a-select-option>
            <a-select-option value="1">
              皮带大水分旁路
            </a-select-option>
            <a-select-option value="2">
              人工采样
            </a-select-option>
            <a-select-option value="3">
              车厢采样
            </a-select-option>
            <a-select-option value="4">
              车厢大水分旁路
            </a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="是否空车"
          hasFeedback>
          <a-select style="width: 100%" v-decorator="['emptyFlgName']">
            <a-select-option value="1">
              否
            </a-select-option>
            <a-select-option value="0">
              是
            </a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="票重(t)"
          hasFeedback>
          <a-input v-decorator="['tickQty']"
                   />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="毛重(t)"
          hasFeedback>
          <a-input v-decorator="['mzQty']"
                   />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="皮重(t)"
          hasFeedback>
          <a-input v-decorator="['pzQty']"
                   />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="净重(t)"
          hasFeedback>
          <a-input v-decorator="['netQty']"
                   />
        </a-form-item>
<!--        <a-form-item-->
<!--          :labelCol="labelCol"-->
<!--          :wrapperCol="wrapperCol"-->
<!--          label="过衡速度"-->
<!--          hasFeedback>-->
<!--          <a-input v-decorator="['speed']"-->
<!--                   />-->
<!--        </a-form-item>-->
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="发站"
          hasFeedback>
          <j-dict-select-tag  v-decorator="['startPlaceName', {}]" :triggerChange="true"
                              dictCode="dictionary_table,name,id,type='START_RAIL_STATION'"/>
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="到站"
          hasFeedback>
          <j-dict-select-tag  v-decorator="['finalPlaceName', {}]" :triggerChange="true"
                              dictCode="dictionary_table,name,id,type='FINAL_RAIL_STATION'"/>
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="对位时间"
          hasFeedback>
          <j-date v-decorator="['aimTime']"
                  :trigger-change="true"  style="width: 100%" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="车型"
          hasFeedback>
          <a-select style="width: 100%" v-decorator="['carTyp']">
            <a-select-option value="TC70H">
              TC70H
            </a-select-option>
            <a-select-option value="TC70EH">
              TC70EH
            </a-select-option>
            <a-select-option value="TC70E">
              TC70E
            </a-select-option>
            <a-select-option value="TC64K">
              TC64K
            </a-select-option>
            <a-select-option value="TC64T">
              TC64T
            </a-select-option>
            <a-select-option value="TC64H">
              TC64H
            </a-select-option>
            <a-select-option value="TC62BK">
              TC62BK
            </a-select-option>
            <a-select-option value="TC62BT">
              TC62BT
            </a-select-option>
            <a-select-option value="TC62AK">
              TC62AK
            </a-select-option>
            <a-select-option value="TC62A*">
              TC62A*
            </a-select-option>
            <a-select-option value="C70">
              C70
            </a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="翻车时间"
          hasFeedback>
          <j-date v-decorator="['fcTime']"
                  :trigger-change="true"  style="width: 100%" />
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
  import {httpAction} from '@/api/manage'
  import pick from 'lodash.pick'
  import Vue from 'vue'
  import {ACCESS_TOKEN} from "@/store/mutation-types"
  import JImageUpload from '@/components/jeecg/JImageUpload'
  import {getAction} from '@/api/manage'

  export default {
    name: "TrainInfoDetailModal",
    components: { JImageUpload },
    data() {
      return {
        title: "操作",
        visible: false,
        model: {},
        labelCol: {
          xs: {span: 24},
          sm: {span: 5},
        },
        wrapperCol: {
          xs: {span: 24},
          sm: {span: 16},
        },
        // 表头
        columns: [
          {dataIndex: 'trainNo', title: '车次',align:'center'},
          {dataIndex: 'recordNo', title: '流水号',align:'center'},
          {dataIndex: 'xhNum', title: '车厢序号',align:'center'},
          {dataIndex: 'carId', title: '车号',align:'center'},
          {dataIndex: 'trackNo', title: '轨道号',align:'center'},
          {dataIndex: 'batchNo', title: '批次号',align:'center'},
          {dataIndex: 'sampleCode', title: '采样编码',align:'center'},
          // {dataIndex: 'attachBatchNo', title: '抽检批次号',align:'center'},
          {dataIndex: 'mineName', title: '矿点',align:'center'},
          {dataIndex: 'coalName', title: '煤种',align:'center'},
          {dataIndex: 'venName', title: '供应商',align:'center'},
          {dataIndex: 'sampleTypName', title: '采样方式',align:'center'},
          {dataIndex: 'emptyFlgName', title: '是否空车',align:'center'},
          {dataIndex: 'tickQty', title: '票重(t)',align:'center'},
          {dataIndex: 'mzQty', title: '毛重(t)',align:'center'},
          {dataIndex: 'pzQty', title: '皮重(t)',align:'center'},
          {dataIndex: 'netQty', title: '净重(t)',align:'center'},
          // {dataIndex: 'speed', title: '过衡速度',align:'center'},

          {dataIndex: 'startPlaceName', title: '发站',align:'center'},
          {dataIndex: 'finalPlaceName', title: '到站',align:'center'},
          {dataIndex: 'aimTime', title: '对位时间',align:'center'},

          // {dataIndex: 'ydQty', title: '盈亏(t)',align:'center'},
          // {dataIndex: 'lossQty', title: '运损(t)',align:'center'},
          {dataIndex: 'carTyp', title: '车型',align:'center'},
          {dataIndex: 'fcTime', title: '翻车时间',align:'center'},
        ],
        fileList: [],
        disableSubmit: false,
        selectedRowKeys: [],
        orderId: "",
        hiding: false,
        headers: {},
        picUrl: "",
        picArray:[],
        previewVisible: false,
        previewImage: '',
        addStatus: false,
        editStatus: false,
        confirmLoading: false,
        form: this.$form.createForm(this),
        url: {
          add: "/test/order/addCustomer",
          edit: "/znrl/train/modifyOneTrainInfo",
          fileUpload: window._CONFIG['domianURL'] + "/sys/common/upload",
          getOrderCustomerList: "/test/order/listOrderCustomerByMainId",
        },
        validatorRules: {
          telphone: {rules: [{validator: this.validateMobile}]},
          idcard: {rules: [{validator: this.validateIdCard}]}
        },
      }
    },
    computed: {
      uploadAction: function () {
        return this.url.fileUpload;
      }
    },
    created() {
      const token = Vue.ls.get(ACCESS_TOKEN);
      this.headers = {"X-Access-Token": token}
      // this.
    },
    methods: {
      add(orderId) {
        this.hiding = true;
        if (orderId) {
          this.orderId = orderId;
          this.edit({orderId}, '');
        } else {
          this.$message.warning("请选择一个客户信息");
        }
      },
      detail(record) {
        this.edit(record, 'd');
      },
      edit(record, v) {
        if (v == 'e') {
          this.hiding = false;
          this.disableSubmit = false;
        } else if (v == 'd') {
          this.hiding = false;
          this.disableSubmit = true;
        } else {
          this.hiding = true;
          this.disableSubmit = false;
        }

        debugger
        this.form.resetFields();
        // this.orderId = record.orderId;
        this.model = Object.assign({}, record);
        if (record.recordNo) {
          this.hiding = false;
          this.addStatus = false;
          this.editStatus = true;
          this.$nextTick(() => {
            this.form.setFieldsValue(pick(this.model, 'trainNo','recordNo', 'xhNum', 'carId', 'trackNo','batchNo', 'sampleCode','mineName','coalName','sampleTypName','emptyFlgName','tickQty','mzQty','pzQty', 'netQty', 'startPlaceName', 'finalPlaceName', 'aimTime', 'carTyp', 'fcTime'))
          });
          setTimeout(() => {
            this.fileList = record.idcardPic
          }, 5)
        } else {
          this.addStatus = false;
          this.editStatus = true;
        }
        this.visible = true;
      },
      close() {
        this.$emit('close');
        this.visible = false;
        this.picUrl = "";
        this.fileList=[];
      },
      handleOk() {
        debugger
        const that = this;
        // 触发表单验证
        this.form.validateFields((err, values) => {
          debugger
          if (!err) {
            that.confirmLoading = true;
            let httpurl = '';
            let method = '';
            if (!this.model.recordNo) {
              httpurl += this.url.add;
              method = 'post';
            } else {
              httpurl += this.url.edit;
              method = 'post';
            }
            let formData = Object.assign(this.model, values);
            console.log(formData);
            formData.orderId = this.orderId;
            if(this.fileList != '') {
              formData.idcardPic = this.fileList;
            }else{
              formData.idcardPic = '';
            }
            httpAction(httpurl, formData, method).then((res) => {
              if (res.success) {
                that.$message.success(res.message);
                that.$emit('ok');
              } else {
                that.$message.warning(res.message);
              }
            }).finally(() => {
              that.confirmLoading = false;
              that.close();
            })
          }
        })
      },
      handleCancel() {
        this.close();
      },
      validateMobile(rule, value, callback) {
        if (!value || new RegExp(/^1([38][0-9]|4[579]|5[0-3,5-9]|6[6]|7[0135678]|9[89])\d{8}$/).test(value)) {
          callback();
        } else {
          callback("您的手机号码格式不正确!");
        }
      },
      validateIdCard(rule, value, callback) {
        if (!value || new RegExp(/(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/).test(value)) {
          callback();
        } else {
          callback("您的身份证号码格式不正确!");
        }
      },
    }
  }
</script>

<style scoped>
  /* tile uploaded pictures */
  .upload-list-inline > > > .ant-upload-list-item {
    float: left;
    width: 200px;
    margin-right: 8px;
  }

  .upload-list-inline > > > .ant-upload-animate-enter {
    animation-name: uploadAnimateInlineIn;
  }

  .upload-list-inline > > > .ant-upload-animate-leave {
    animation-name: uploadAnimateInlineOut;
  }
</style>