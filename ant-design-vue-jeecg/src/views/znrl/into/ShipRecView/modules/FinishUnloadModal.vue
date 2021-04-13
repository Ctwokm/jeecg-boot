<template>
  <a-modal
    :title="title"
    :width="1000"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleOk"
    @cancel="handleCancel">

    <a-spin :spinning="confirmLoading">
      <a-form :form="form">
        <!-- 主表单区域 -->
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="皮带秤吨位">
          <a-input-number v-decorator="['receiveQty',{rules:[{required:true}]}]" placeholder="请输入皮带秤吨位" :min="0" :max="300000" :step="0.1" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="卸完时间">
          <j-date showTime dateFormat="YYYY-MM-DD HH:mm:ss" v-decorator="['unloadTime',{rules:[{required:true}]}]" ></j-date>
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="离港时间">
          <j-date showTime dateFormat="YYYY-MM-DD HH:mm:ss" v-decorator="['departTime',{rules:[{required:true}]}]" ></j-date>
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
  import {httpAction} from '@/api/manage'
  import JDate from '@/components/jeecg/JDate'
  import pick from 'lodash.pick'

  export default {
    name: "ShipRecordModal",
    components: {
      JDate
    },
    data() {
      return {
        title: "操作",
        visible: false,
        finishUnloadModel: {},
        labelCol: {
          xs: {span: 24},
          sm: {span: 5},
        },
        wrapperCol: {
          xs: {span: 24},
          sm: {span: 16},
        },
        confirmLoading: false,
        form: this.$form.createForm(this),
        validatorRules: {},
        url: {
          finishUnload: "business/ship/finishUnload",
        },
      }
    },
    methods: {
      prepare(shipRecId) {
        this.form.resetFields();
        this.finishUnloadModel.shipRecId = shipRecId;
        this.visible = true;
      },
      handleOk() {
        const that = this;
        // 触发表单验证
        this.form.validateFields((err, values) => {
          if (!err) {
            that.confirmLoading = true;
            let httpurl = '';
            let method = '';
            httpurl = that.url.finishUnload;
            method = 'put';
            let finishUnloadData = Object.assign(this.finishUnloadModel, values);
            let formData = {
              ...finishUnloadData
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
        this.close()
      },
      close() {
        this.visible = false;
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

  .ant-calendar-picker{
    width: 100%;
  }

  .ant-input-number{
    width: 100%;
  }
</style>