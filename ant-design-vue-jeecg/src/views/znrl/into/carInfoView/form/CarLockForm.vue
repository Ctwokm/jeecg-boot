<template>
  <a-modal
    :title="title"
    :width="1000"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleOk"
    @cancel="handleCancel">

    <a-spin :spinning="confirmLoading">
      <a-form :form="form" >
        <a-tabs defaultActiveKey="1">
          <a-tab-pane tab="锁车信息" key="1">
            <div>

              <a-row type="flex" style="margin-bottom:10px" :gutter="16">
                <a-col :lg="10">
                  <a-form-item
                    :label-col="{ span: 8 }" :wrapper-col="{ span: 12 }"
                    label="车牌号">
                    <a-input v-model="carId" placeholder="车牌号" disabled/>
                  </a-form-item>
                </a-col>
              </a-row>
              <a-row type="flex" style="margin-bottom:10px" :gutter="16">
                <a-col :lg="10">
                  <a-form-item
                    :label-col="{ span: 8 }" :wrapper-col="{ span: 16 }"
                    label="生效时间">
                    <j-date showTime dateFormat="YYYY-MM-DD HH:mm:ss" v-model="effTime"  ></j-date>
                  </a-form-item>
                </a-col>
                <a-col :lg="10">
                  <a-form-item
                    :label-col="{ span: 8 }" :wrapper-col="{ span: 16 }"
                    label="失效时间">
                    <j-date showTime dateFormat="YYYY-MM-DD HH:mm:ss" v-model="expTime" ></j-date>
                  </a-form-item>
                </a-col>
              </a-row>

              <!--分割线-->
              <br><hr><br>

              <a-row  type="flex" style="margin-bottom:10px;margin-left: -200px" :gutter="16">
                <a-col :span= "20">
                  <a-form-item
                    :label-col="{ span: 8 }" :wrapper-col="{ span: 16 }"
                    label="锁车原因">
                    <a-textarea   placeholder="锁车原因"  v-model="reason" />
                  </a-form-item>
                </a-col>
              </a-row>

              <a-row  type="flex" style="margin-bottom:10px;margin-left: -200px" :gutter="16">
                <a-col :span= "20">
                  <a-form-item
                    :label-col="{ span: 8 }" :wrapper-col="{ span: 16 }"
                    label="解锁原因">
                    <a-textarea   placeholder="解锁原因" v-model="remark" />
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
        name: 'CarLockForm',
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
               /* labelCol: {
                    xs: { span: 24 },
                    sm: { span: 5 },
                },
                wrapperCol: {
                    xs: { span: 24 },
                    sm: { span: 16 },
                },*/
                confirmLoading: false,
                form: this.$form.createForm(this),
                validatorRules: {},
                url: {
                   // kd:'/znrl/biztool/doCall'
                },
                carId:'',action:'',effTime:'',expTime:'',
                remark:'',reason:'',
                httpurl:'/znrl/biztool/doCall',
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
                this.entity.jsonString=JSON.stringify(this.entity.jsonString={
                    carId:this.carId,
                    effTime:this.effTime,
                    expTime:this.expTime,
                    action:this.action,
                    remark:this.remark,
                    reason:this.reason
                })
                this.entity.pkgName="CAR_SCHEDULE_carLockRecord";
                console.log("锁车的传参:"+this.entity.jsonString);
                httpAction(this.httpurl,this.entity,'Post').then((res)=>{
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
                this.visible = true;
                console.log(e);
                this.carId = e.carId;
                this.action = e.action;
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
