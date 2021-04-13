<template>
  <a-modal
    :title="title"
    :width="1200"
    :visible="visible"
    :okButtonProps="{ props: {disabled: disableSubmit} }"
    @ok="handleOk"
    @cancel="handleCancel"
    cancelText="关闭">
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <a-spin :spinning="confirmLoading">
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :md="8" :sm="24">
            <a-form-item label="批次号">
              <a-input placeholder="请输入批次号" v-model="queryParam.batchNo"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="8" :sm="24">
            <a-form-item label="化验编码">
              <a-input placeholder="请输入化验编码" v-model="queryParam.laborCode"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="供应商">
              <j-dict-select-tag placeholder="请选择供应商"  v-model="queryParam.venNo" dictCode="VENDOR_INFO,VEN_name,VEN_no"/>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24" >
            <a-col :md="8" :sm="24">
              <a-form-item label="煤样类型">
                <j-dict-select-tag placeholder="请选择煤样类型"  v-model="queryParam.sampleType" dictCode="gk_sampleType"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
            <a-form-item label="存样时间">
              <a-range-picker
                :ranges="{ 当天: [moment(), moment()], '三天内': [moment().subtract('days', 2), moment()], '一周内': [moment().subtract('days', 6), moment()], '本月': [moment().subtract('month',1), moment()] }"
                @change="onChange"
                format="YYYY-MM-DD"/>

            </a-form-item>

          </a-col>
          <a-col :md="8" :sm="24" >
            <span style="float: right;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" style="margin-left: 15px" @click="searchQuery" icon="search">查询</a-button>
            </span>
          </a-col>
        </a-row>
        <a-divider />
        <a-row :gutter="24" >
          <a-col :md="8" :sm="24" >
            <a-form-item label="目的地" v-if="dealType==1">
              <a-select style="width: 100%" v-model="queryParam.destination" >
                <a-select-option value="1">
                  化验室取样工作站
                </a-select-option>
                <a-select-option value="3">
                  存查样间弃样工作站
                </a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :md="8" :sm="24">
            <a-form-item label="原因">
              <a-input  v-model="queryParam.remark"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="8" :sm="24" >
            <span style="float: right;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" style="margin-left: 15px" @click="submitToAppr" icon="search">提交审批</a-button>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <!--      <a-button type="primary" icon="plus" @click="handleAdd">取样</a-button>-->
      <!--      <a-button type="primary" icon="download" @click="handleExportXls('cabinet_op_rec_his')">弃样</a-button>-->
      <!--      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">-->
      <!--        <a-button type="primary" icon="import">导入</a-button>-->
      <!--      </a-upload>-->
      <!--      &lt;!&ndash; 高级查询区域 &ndash;&gt;-->
      <!--      <j-super-query :fieldList="superFieldList" ref="superQueryModal" @handleSuperQuery="handleSuperQuery"></j-super-query>-->
      <!--      <a-dropdown v-if="selectedRowKeys.length > 0">-->
      <!--        <a-menu slot="overlay">-->
      <!--          <a-menu-item key="1" @click="batchDel"><a-icon type="delete"/>删除</a-menu-item>-->
      <!--        </a-menu>-->
      <!--        <a-button style="margin-left: 8px"> 批量操作 <a-icon type="down" /></a-button>-->
      <!--      </a-dropdown>-->
    </div>

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择 <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>项
        <a style="margin-left: 24px" @click="onClearSelected">清空</a>
      </div>

      <a-table
        ref="table"
        size="middle"
        :scroll="{y:500}"
        bordered
        rowKey="id"
        :columns="columns"
        :dataSource="dataSource"
        :pagination=false
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        class="j-table-force-nowrap"
        @change="handleTableChange">

        <template slot="htmlSlot" slot-scope="text">
          <div v-html="text"></div>
        </template>
        <template slot="imgSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">无图片</span>
          <img v-else :src="getImgView(text)" height="25px" alt="" style="max-width:80px;font-size: 12px;font-style: italic;"/>
        </template>
        <template slot="fileSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">无文件</span>
          <a-button
            v-else
            :ghost="true"
            type="primary"
            icon="download"
            size="small"
            @click="downloadFile(text)">
            下载
          </a-button>
        </template>

        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)">编辑</a>

          <a-divider type="vertical" />
          <a-dropdown>
            <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a @click="handleDetail(record)">详情</a>
              </a-menu-item>
              <a-menu-item>
                <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
                  <a>删除</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>

      </a-table>
    </div>
    </a-spin>
    <!--    <cabinet-op-rec-his-modal ref="modalForm" @ok="modalFormOk"></cabinet-op-rec-his-modal>-->
  </a-card>
  </a-modal>
</template>

<script>

  import '@/assets/less/TableExpand.less'
  import { mixinDevice } from '@/utils/mixin'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import moment from 'moment'
  import { httpAction, getAction } from '@/api/manage'

  export default {
    name: 'SampleCabinet',
    mixins:[JeecgListMixin, mixinDevice],
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
        columns: [
          {
            title: '#',
            dataIndex: '',
            key:'rowIndex',
            width:60,
            align:"center",
            customRender:function (t,r,index) {
              return parseInt(index)+1;
            }
          },
          {
            title:'样品批次号',
            align:"center",
            dataIndex: 'batchNo'
          },
          {
            title:'封装码',
            align:"center",
            dataIndex: 'packCode'
          },
          {
            title:'存样时间',
            align:"center",
            dataIndex: 'depositTime'
          },

          {
            title:'是否可用',
            align:"center",
            dataIndex: 'isEnabled',
            customRender:function (text) {
              return !text?"":(text=='1'?'是':'否')
            }
          },
          {
            title:'煤样类型',
            align:"center",
            dataIndex: 'sampleType',
            customRender:function (text) {
              let st_type = '';
              switch (text){
                case '61':
                  st_type = '6mm全水样';
                  break;
                case '62':
                  st_type = '6mm存查样';
                  break;
                case '31':
                  st_type = '3mm化验样';
                  break;
                case '32':
                  st_type = '3mm存查样';
                  break;
                case '21':
                  st_type = '0.2mm化验样';
                  break;
                case '22':
                  st_type = '0.2mm存查样';
                  break;
              }
              return st_type;

            }
          },

          {
            title:'样瓶状态',
            align:"center",
            dataIndex: 'sampleStatus',
            customRender:function (text) {
              let st_type = '';
              switch (text){
                case '1':
                  st_type = '正常瓶';
                  break;
                case '2':
                  st_type = '超期瓶';
                  break;
                case '3':
                  st_type = '无效瓶';
                  break;
                case '4':
                  st_type = '无瓶';
                  break;
              }
              return st_type;

            }
          }
        ],
        url: {
          // list: "/common/cabinetOpRecHis/list",
          list: "/znrl/cabinet/qryCabinetInfoList",
          // delete: "/common/cabinetOpRecHis/delete",
          // deleteBatch: "/common/cabinetOpRecHis/deleteBatch",
          // exportXlsUrl: "/common/cabinetOpRecHis/exportXls",
          // importExcelUrl: "common/cabinetOpRecHis/importExcel",
          commit: "/znrl/biztool/doCall",

        },
        dictOptions:{},
        superFieldList:[],
        disableMixinCreated: true,
      }
    },
    created() {
      this.getSuperFieldList();
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
      onChange(dates, dateStrings) {
        this.queryParam.depositStartTime = dateStrings[0]
        this.queryParam.depositEndTime = dateStrings[1]
      },
      submitToAppr(){
        if (this.selectedRowKeys.length > 0) {
          console.log('被选中的行: ', this.selectionRows);
          if (true) {
            //流程启动参数
            var params = {};
            //具体业务参数
            var bizParams = this.selectionRows[0];
            //把审批流程参数追加到业务参数中
            bizParams.apprEventTypeCd = 'UploadProcess_RC_CZH_2';
            //组装启动流程参数
            params.processKey = "UploadProcess_RC_CZH_2";
            params.jsonString = JSON.stringify(bizParams);

            //正式启动流程
            postAction(this.url.startProcess, params).then((res) => {
              if (res.success) {
                // this.$message.success(res.message);
                //调用回写业务操作（将流程标记写入业务数据中，方便查看）
                var param = {};
                debugger
                let formData = this.selectionRows;
                console.log("表单提交数据",formData);
                var params = {}
                params.search_dealType = this.queryParam.sampleStatus;//操作类型
                params.search_remark = this.queryParam.remark;//原因
                //取样才要目的站
                if (this.dealTag == "1") {
                  params.search_destination = this.queryParam.destination;//目的站
                }
                params.search_jsonStr = formData;
                this.entity.pkgName = "sample_store_submit_approve";

                param.pkgName = 'sample_store_submit_approve';
                param.jsonString = JSON.stringify(params);
                postAction(this.url.doCall, param).then((res) => {
                  if (res.success) {
                    this.$message.success(res.message);
                  } else {
                    this.$message.warning(res.message);
                  }
                })
              } else {
                this.$message.warning(res.message);
              }
            })
            this.loadData();
          }else {
            this.$message.warning(" 只有未审批或审批不通过状态的数据才能提交审批。");
            return
          }
        }else {
          this.$message.warning(" 请选择一条或多条未审批数据！");
          return
        }
      },
     /* submitToAppr(){
        const that = this;
        that.confirmLoading = true;
        let httpurl = '';
        let method = '';
        httpurl+=this.url.commit;
        method = 'post';
        let formData = this.selectionRows;
        console.log("表单提交数据",formData);
        var params = {}
        params.search_dealType = this.queryParam.sampleStatus;//操作类型
        params.search_remark = this.queryParam.remark;//原因
        //取样才要目的站
        if (this.dealTag == "1") {
          params.search_destination = this.queryParam.destination;//目的站
        }
        params.search_jsonStr = formData;
        this.entity.pkgName = "sample_store_submit_approve";
        let jsonString = JSON.stringify(params);
        this.entity.jsonString  = jsonString;
        debugger;
        httpAction(httpurl,this.entity,method).then((res)=>{
          if(res.success){
            that.$message.success(res.message);
            that.$emit('ok');
          }else{
            that.$message.warning(res.message);
          }
        }).finally(() => {
          that.confirmLoading = false;
        })
      },*/
      openSample(dealType){
        this.visible = true;
        this.dealType = dealType;
        if(dealType&&dealType=='1'){
          this.queryParam.sampleStatus = '1';
        }
        if(dealType&&dealType=='2'){
          this.queryParam.sampleStatus = '2';
        }
        this.searchQuery();
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
      getSuperFieldList(){
        let fieldList=[];
        fieldList.push({type:'int',value:'cabinetRecId',text:'记录ID'})
        fieldList.push({type:'string',value:'ggName',text:'柜名;存查样柜1-10'})
        fieldList.push({type:'string',value:'ccName',text:'层名'})
        fieldList.push({type:'string',value:'wwName',text:'位名 1到30'})
        fieldList.push({type:'string',value:'isEnabled',text:'是否可用 1可 0否'})
        fieldList.push({type:'date',value:'makeSampleTime',text:'制样时间'})
        fieldList.push({type:'date',value:'makeSampleTime',text:'存样时间'})
        fieldList.push({type:'string',value:'sampleType',text:'煤样类型'})
        fieldList.push({type:'string',value:'packCode',text:'封装码'})
        fieldList.push({type:'string',value:'sampleStatus',text:'样瓶状态'})
        fieldList.push({type:'string',value:'opName',text:'操作人'})
        this.superFieldList = fieldList
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>