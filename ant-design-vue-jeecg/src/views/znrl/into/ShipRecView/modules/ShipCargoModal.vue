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
          label="矿点"
          hasFeedback>
          <j-search-select-tag
            v-decorator="['mineNo',{rules:[{required:true}]}]"
            dict="coal_mine,mine_name,mine_no"
            :async="true">
          </j-search-select-tag>
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="煤种"
          hasFeedback>
          <j-search-select-tag
            v-decorator="['coalNo',{rules:[{required:true}]}]"
            dict="coal_type,coal_name,coal_no"
            :async="true">
          </j-search-select-tag>
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="装港水尺吨位"
          hasFeedback>
          <a-input-number placeholder="请输入装港水尺吨位" v-decorator="['departQty',{rules:[{required:true}]}]" :min="0" :max="300000" :step="0.01" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="到港水尺吨位"
          hasFeedback>
          <a-input-number placeholder="请输入到港水尺吨位" v-decorator="['arriveQty',{rules:[{required:true}]}]"  :min="0" :max="300000" :step="0.01" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="扣损类型"
          hasFeedback>
          <j-dict-select-tag v-decorator="['lossType', {'initialValue':'0'}]" placeholder="请选择扣损类型" :triggerChange="true" dictCode="gk_ship_loss_type"/>
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="扣吨"
          hasFeedback>
          <a-input-number placeholder="请输入扣吨" v-decorator="['kdQty',{}]" :min="0" :max="50000" :step="0.1" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="运单吨位"
          hasFeedback>
          <a-input-number placeholder="请输入运单吨位" v-decorator="['tickQty',{rules:[{required:true}]}]" :min="0" :max="300000" :step="0.1" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="舱位编号"
          hasFeedback>
          <j-multi-select-tag v-decorator="['cabinNos',{rules:[{required:true}]}]" placeholder="请选择舱位编号" :options="cabinNoOpt" />
        </a-form-item>
        <!--<a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="到厂皮带秤吨位"
          hasFeedback>
          <a-input-number placeholder="请输入到厂皮带秤吨位" v-decorator="['receiveQty',{}]" :min="0" :max="300000" :step="0.01" />
        </a-form-item>-->
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="采样方式"
          hasFeedback>
          <j-dict-select-tag v-decorator="['sampleType', {rules:[{required:true}],'initialValue':'0'}]" placeholder="请选择采样方式" :triggerChange="true" dictCode="gk_ship_sample_type"/>
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
  import {httpAction} from '@/api/manage'
  import pick from 'lodash.pick'

  export default {
    name: "ShipCargoModal",
    components: {},
    data() {
      return {
        cabinNoOpt:[
          {label:"1",value:"1"},
          {label:"2",value:"2"},
          {label:"3",value:"3"},
          {label:"4",value:"4"},
          {label:"5",value:"5"},
        ],
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
        disableSubmit: false,
        selectedRowKeys: [],
        shipRecId: "",
        hiding: false,
        addStatus: false,
        editStatus: false,
        confirmLoading: false,
        form: this.$form.createForm(this),
        url: {
          //add: "/rapid/shipCargoRecord/add",
          //edit: "/rapid/shipCargoRecord/edit",
          add: "business/ship/addCargoRecord",
          edit: "business/ship/editCargoRecord",
        },
        validatorRules: {},
      }
    },
    computed: {
    },
    created() {},
    methods: {
      add(shipRecId) {
        this.hiding = true;
        if (shipRecId) {
          this.shipRecId = shipRecId;
          this.edit({shipRecId}, '');
        } else {
          this.$message.warning("请选择一个船运信息");
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

        this.form.resetFields();
        this.shipRecId = record.shipRecId;
        this.recordNo = record.recordNo;
        this.model = Object.assign({}, record);
        if (record.recordNo) {
          this.hiding = false;
          this.addStatus = false;
          this.editStatus = true;
          this.$nextTick(() => {
            this.form.setFieldsValue(pick(this.model, 'mineNo', 'coalNo', 'departQty', 'arriveQty', 'lossType', 'kdQty', 'tickQty', 'cabinNos', 'sampleType'))
          });
        } else {
          this.addStatus = false;
          this.editStatus = true;
        }
        this.visible = true;
      },
      close() {
        this.$emit('close');
        this.visible = false;
      },
      handleOk() {
        const that = this;
        // 触发表单验证
        this.form.validateFields((err, values) => {
          if (!err) {
            that.confirmLoading = true;
            let httpurl = '';
            let method = '';
            if (!this.model.recordNo) {
              httpurl += this.url.add;
              method = 'post';
            } else {
              httpurl += this.url.edit;
              method = 'put';
            }
            let formData = Object.assign(this.model, values);
            console.log(formData);
            formData.shipRecId = this.shipRecId;
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

  .ant-input-number{
    width: 100%;
  }
</style>