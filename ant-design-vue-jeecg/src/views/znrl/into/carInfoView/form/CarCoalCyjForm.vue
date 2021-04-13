<template>
  <a-modal
    :title="title"
    :width="500"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleOk"
    @cancel="handleCancel">

    <a-spin :spinning="confirmLoading">
      <a-form :form="form" >
        <a-tabs defaultActiveKey="1">
          <a-tab-pane tab="采样机" key="1">
            <div>
              <a-row type="flex" style="margin-bottom:10px" :gutter="16">
                <a-col :lg="10">
                  <a-form-item
                    :label-col="{ span: 8 }" :wrapper-col="{ span: 16 }"
                    label="采样机">
                    <a-select placeholder="切换采样机"  v-model="CHANGE_CYJ" >
                      <a-select-option value="1">#1 采样机</a-select-option>
                      <a-select-option value="2">#2 采样机</a-select-option>
                      <a-select-option value="3">#3 采样机</a-select-option>
                    </a-select>
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
        name: 'CarCoalCyjForm',
        mixins:[JeecgListMixin],
        components: {
            JDate,
            JeecgListMixin

        },
        data() {
            return {
                e:{},
                CHANGE_CYJ:'',
                title: '操作',
                visible: false,
                confirmLoading: false,
                form: this.$form.createForm(this),
                validatorRules: {},
                url: {
                    //定义出来
                    changeCYJ:'/znrl/biztool/doCall'
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
                this.entity.jsonString=JSON.stringify(this.entity.jsonString={
                    record_no:this.e.recordNo,
                    ChangeCYJNo:this.CHANGE_CYJ,
                    businessTag:"CHANGE_CYJ"
                })
                this.entity.pkgName="Car_truck_trans_entry";
                console.log("jsonString :"+this.entity.jsonString);
                this.httpurl=this.url.changeCYJ;
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
                this.visible = true;
                console.log( "e:+e");
                console.log(e);
                this.e=e;
                this.CHANGE_CYJ = this.e.cyjNo;
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
