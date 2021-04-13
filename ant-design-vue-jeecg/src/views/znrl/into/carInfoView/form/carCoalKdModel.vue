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
        <a-tabs defaultActiveKey="1">
          <a-tab-pane tab="扣吨" key="1">
            <div>
              <a-row type="flex" style="margin-bottom:10px" :gutter="16">
                <a-col :lg="10">
                  <a-form-item
                    :labelCol="labelCol"
                    :wrapperCol="wrapperCol"
                    label="扣吨（吨）">
                    <a-input v-model="kdQty" placeholder="扣吨"/>
                  </a-form-item>
                </a-col>
              </a-row>
              <a-row  type="flex" style="margin-bottom:10px" :gutter="16">
                <a-col :span= "10">
                  <a-form-item
                    :labelCol="labelCol"
                    :wrapperCol="wrapperCol"
                    label="备注">
                    <a-textarea   placeholder="备注"  v-model="remark"/>
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
    import JDate from '@/components/jeecg/JDate'
    import { httpAction, getAction } from '@/api/manage'
    import { JeecgListMixin } from '@/mixins/JeecgListMixin'

    export default {
        name: 'carCoalKdModal',
        mixins:[JeecgListMixin],
        components: {
            JDate,
            JeecgListMixin

        },
        data() {
            return {
                e:{},
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
                    kd:'/znrl/biztool/doCall'
                },
                kdQty:'',
                remark:'',
                httpurl:'',
                method:'',

            }
        },
        created() {
        },
        methods: {
            save() {
            },
            add(record) {
                this.edit(record)
            },
            close() {
                this.$emit('close')
                this.visible = false
            },
            loadData(arg) {},
            handleOk(){
                //alert("ok");

                this.entity.jsonString=JSON.stringify(this.entity.jsonString={
                    record_no:this.e.recordNo,
                    kd_qty:this.kdQty,
                    businessTag:"TRUCK_KD",
                    remark:this.remark
                })

                this.entity.pkgName="Car_truck_trans_entry";

                console.log(this.entity.jsonString);

                this.httpurl=this.url.kd;
                this.method = 'post';

                httpAction(this.httpurl,this.entity,this.method).then((res)=>{
                    if(res.success){
                        this.$message.success(res.message);
                        this.$emit('ok');
                        this.visible = false;
                    }else{
                        this.$message.warning(res.message);
                    }
                });

            },

            handleCancel() {
                this.close()
            },
            edit(e){
                //alert("执行到这里了。");
                this.visible = true;
                console.log(e);
                this.e=e;
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

</style>
