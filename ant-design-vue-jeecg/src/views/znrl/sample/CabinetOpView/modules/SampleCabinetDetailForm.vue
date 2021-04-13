<template>
  <a-modal
    :title="title"
    :width="800"
    :visible="visible"
    :okButtonProps="{ props: {disabled: disableSubmit} }"
    @ok="handleOk"
    @cancel="handleCancel"
    cancelText="关闭">
  <a-spin :spinning="confirmLoading" >
    <j-form-container :disabled="true">
      <a-form :form="form" slot="detail" >
        <a-row :gutter = "24">
          <a-col :span="12">
            <a-form-item label="柜名" :label-col="{ span: 8 }" :wrapper-col="{ span: 10 }">
               <a-input v-model="detailData.ggName"   ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="层名" :label-col="{ span: 8 }" :wrapper-col="{ span: 10 }">
              <a-input v-model="detailData.ccName"   ></a-input>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter = "24">
          <a-col :span="12">
            <a-form-item label="位名" :label-col="{ span: 8 }" :wrapper-col="{ span: 10 }">
              <a-input v-model="detailData.wwName"  ></a-input>
            </a-form-item>

          </a-col>

          <a-col :span="12">
            <a-form-item label="是否可用" :label-col="{ span: 8 }" :wrapper-col="{ span: 10 }">
<!--              <a-input  v-model="detailData.isEnabled" ></a-input>-->
              <a-select style="width: 100%" v-model="detailData.isEnabled" >
                <a-select-option value="1">可用
                </a-select-option>
                <a-select-option value="0">不可用
                </a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter = "24">
          <a-col :span="12">
            <a-form-item label="制样时间" :label-col="{ span: 8 }" :wrapper-col="{ span: 10 }">
              <a-input v-model="detailData.makeSampleTime" ></a-input>
            </a-form-item>
          </a-col>

          <a-col :span="12">
            <a-form-item label="存样时间" :label-col="{ span: 8 }" :wrapper-col="{ span: 10 }">
              <a-input v-model="detailData.depositTime"  ></a-input>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter = "24">
          <a-col :span="12">
            <a-form-item label="煤样类型" :label-col="{ span: 8 }" :wrapper-col="{ span: 10 }">
              <j-dict-select-tag placeholder="请选择煤样类型"  v-model="detailData.sampleType" dictCode="gk_sampleType"/>
            </a-form-item>
          </a-col>

          <a-col :span="12">
            <a-form-item label="封装码" :label-col="{ span: 8 }" :wrapper-col="{ span: 10 }">
              <a-input v-model="detailData.packCode"  ></a-input>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter = "24">
          <a-col :span="12">
            <a-form-item label="样瓶状态" :label-col="{ span: 8 }" :wrapper-col="{ span: 10 }">
              <a-select style="width: 100%" v-model="detailData.sampleStatus" >
                <a-select-option value="1">正常瓶
                </a-select-option>
                <a-select-option value="2">超期瓶
                </a-select-option>
                <a-select-option value="3">无效瓶
                </a-select-option>
                <a-select-option value="4">无瓶
                </a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="操作人":label-col="{ span: 8 }" :wrapper-col="{ span: 10 }">
              <a-input v-model="detailData.opName"  ></a-input>
            </a-form-item>
          </a-col>

        </a-row>
      </a-form>
    </j-form-container>
  </a-spin>
  </a-modal>
</template>

<script>

  import '@/assets/less/TableExpand.less'
  import { httpAction, getAction, postAction } from '@/api/manage'
  import moment from 'moment'

  export default {
    name: 'SampleCabinetDetail',
    data () {
      return {
        title:'',
        width:1000,
        visible: false,
        disableSubmit: false,
        description: '操作',
        confirmLoading: false,
        loading:false,
        dealType: 0,
        /* 排序参数 */

        // 表头

        dictOptions:{},
        superFieldList:[],
        disableMixinCreated: true,
        form: this.$form.createForm(this),
        model: {},
        entity:{
          jsonString:{},
          pkgName:""
        },
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },
        labelColZ: {
          xs: { span: 24 },
          sm: { span: 6 },
        },
        wrapperColD: {
          xs: { span: 24 },
          sm: { span: 15 },
        },
        detailData: {},
        url:{
          detailUrl: "/znrl/cabinet/qryAutoSampleDetail",
        }
      }
    },
    created() {
     // this.getSuperFieldList();
    },
    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      },
    },
    methods: {

      initDictConfig(){
      },
      moment,
      openSample(data){

        this.queryData(data);
        this.visible = true;
      },
      close () {
        //this.$emit('close');
        this.visible = false;
      },
      handleCancel () {
        this.close()
      },
      handleOk () {
        //this.$refs.realForm.submitForm();
      },
      queryData(data){
        const that = this;
        getAction(this.url.detailUrl+'/'+data, data).then((res) => {
          console.log(res);
          if (res.success) {
            debugger;
            if(res.result&&res.result.length>0){
              that.detailData = res.result[0];
              console.log(that.detailData);
            }
          }else {
            this.$message.warning(res.message)
          }
        });
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>