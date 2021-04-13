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
          label="船名">
          <j-popup v-decorator="['shipName',{rules:[{required:true}]}]"
                   :trigger-change="true"
                   code="SHIP_INFO"
                   org-fields="ship_id,ship_name"
                   dest-fields="shipId,shipName"
                   @callback="popupCallback" />
          <a-input v-decorator="['shipId']" disabled></a-input>
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="运输单位">
          <j-popup v-decorator="['carrierName']"
                   :trigger-change="true"
                   code="CARRIER_INFO"
                   org-fields="carrier_no,carrier_name"
                   dest-fields="carrierNo,carrierName"
                   @callback="popupCallback" />
          <a-input v-decorator="['carrierNo']" disabled></a-input>
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="收货单位">
          <j-dict-select-tag v-decorator="['receiverNo', {}]" placeholder="请选择收货单位" :triggerChange="true"
                             dictCode="dictionary_table, name, id, type = 'GOODS_RECEIVER' order by create_date"/>
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="货票号">
          <a-input placeholder="请输入货票号" v-decorator="['waybillNo', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="状态">
          <j-dict-select-tag v-decorator="['status', {'initialValue':'4'}]" placeholder="请选择状态" :triggerChange="true" dictCode="gk_ship_record_status"/>
        </a-form-item>
        <!--<a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="水尺吨位">
          <a-input-number v-decorator="['waterTun']" placeholder="请输入水尺吨位" :min="0" :max="1000000" :step="0.01" />
        </a-form-item>-->
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="锚地时间">
          <j-date showTime dateFormat="YYYY-MM-DD HH:mm:ss" v-decorator="['norTime',{rules:[{required:true}]}]" ></j-date>
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="装港用时(h)">
          <a-input-number v-decorator="['loadHours']" placeholder="请输入装港用时" :min="0" :max="1000" :step="0.1" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="卸港用时(h)">
          <a-input-number v-decorator="['unloadHours']" placeholder="请输入卸港用时" :min="0" :max="1000" :step="0.1" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="始发港口">
          <j-dict-select-tag v-decorator="['startPortNo', {}]" placeholder="请选择始发港" :triggerChange="true" dictCode="dictionary_table, name, id, type = 'GOODS_RECEIVER'"/>
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="到达港口">
          <j-dict-select-tag v-decorator="['finalPortNo', {}]" placeholder="请选择到达港" :triggerChange="true" dictCode="dictionary_table, name, id, type = 'GOODS_RECEIVER'"/>
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="发港时间">
          <j-date showTime dateFormat="YYYY-MM-DD HH:mm:ss" v-decorator="['startTime',{}]" ></j-date>
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="预计到港时间">
          <j-date showTime dateFormat="YYYY-MM-DD HH:mm:ss" v-decorator="['estimateTime',{}]" ></j-date>
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="实际到港时间">
          <j-date showTime dateFormat="YYYY-MM-DD HH:mm:ss" v-decorator="['factTime',{rules:[{required:true}]}]" ></j-date>
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="接卸时间">
          <j-date showTime dateFormat="YYYY-MM-DD HH:mm:ss" v-decorator="['arrangeTime',{}]" ></j-date>
        </a-form-item>
        <!--<a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="卸港完成时间">
          <j-date showTime dateFormat="YYYY-MM-DD HH:mm:ss" v-decorator="['unloadTime',{}]" ></j-date>
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="离港时间">
          <j-date showTime dateFormat="YYYY-MM-DD HH:mm:ss" v-decorator="['departTime',{}]" ></j-date>
        </a-form-item>-->
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="泊位号">
          <a-input-number v-decorator="['berthNo']" placeholder="请输入泊位号" :min="0" :max="4" :step="1" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="备注">
          <a-textarea v-decorator="['remark']" placeholder="请输入备注" :rows="3" />
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
        shipRecordModel: {
          shipCargoList: [{}],
        },
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
          //add: "/rapid/shipRecord/add",
          //edit: "/rapid/shipRecord/edit",
          add: "business/ship/addRecord",
          edit: "business/ship/editRecord",
        },
      }
    },
    methods: {
      add() {
        this.edit({});
      },
      edit(record) {
        this.form.resetFields();
        this.shipRecordModel = Object.assign({}, record);
        //初始化数据
        console.log(this.shipRecordModel.shipRecId)
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.shipRecordModel, 'shipName','shipId','carrierName','carrierNo','receiverNo','waybillNo','status','norTime','loadHours','unloadHours','startPortNo','finalPortNo','startTime','estimateTime','factTime','arrangeTime','berthNo','remark'))
        });
        console.log(this.shipRecordModel)
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
            if (!this.shipRecordModel.shipRecId) {
              httpurl += this.url.add;
              method = 'post';
            } else {
              httpurl += this.url.edit;
              method = 'put';
            }
            let shipRecordData = Object.assign(this.shipRecordModel, values);
            let formData = {
              ...shipRecordData
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
      popupCallback(row){
        this.form.setFieldsValue(row)
      }
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