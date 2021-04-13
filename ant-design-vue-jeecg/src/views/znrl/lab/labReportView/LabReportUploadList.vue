<template>

  <a-card>
    <!--查询区域-->
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="24">
          <a-col :md="8" :sm="24">
            <a-form-item label="化验编码">
              <!-- dict="batch_no_info,labor_code,labor_code,1=1 order by insert_time desc"-->
              <!--<a-input placeholder="请输入化验编码" v-model="queryParam.laborCode" ></a-input>-->
              <j-search-select-tag
                placeholder="请输入化验编码"
                v-model="from.laborCode"
                dict="batch_no_info,labor_code,labor_code"
                :pageSize="6"
                :async="true">
              </j-search-select-tag>
            </a-form-item>
          </a-col>

          <a-col :md="6" :sm="24">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
            <!--  <a-button type="primary" @click="searchQuery" icon="search">模糊查询</a-button>-->
              <a-button type="primary" @click="uploadAllAction"  style="margin-left: 8px">一键上传</a-button>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>


    <!-- table区域-begin -->
    <div>
      <a-table
        ref="table"
        size="middle"
        :scroll="{x:true}"
        bordered
        rowKey="id"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        class="j-table-force-nowrap"
        @change="handleTableChange">
        <!-- :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"-->

        <span slot="action" slot-scope="text, record">
          <a @click="uploadAction(record)">数据上传</a>

          <a-divider type="vertical" />

        </span>

      </a-table>
    </div>

    <!--<lab-report-modal ref="modalForm" @ok="modalFormOk"></lab-report-modal>-->
  </a-card>
</template>

<script>
  import '@/assets/less/TableExpand.less'
  import { mixinDevice } from '@/utils/mixin'
  import { BusinessListMixin } from '@/mixins/BusinessListMixin'
  import { getAction, uploadAction } from '@api/manage'

  export default {
  name: 'LabReportUploadList',
    mixins:[BusinessListMixin, mixinDevice],

    data () {
      return {
        description: 'lab_report数据上传页面',
        //laborCode:'',
        //record:{laborCode:''},
        from:{laborCode:''},
        allRows:[],
        Obj:{
          labCfgId:'',
          laborCode:'',
        },
        /*record:{
          laborCode:'',
        },*/

        //var JsBarcode = require('jsbarcode')
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
            title:'化验设备名称',
            align:"center",
            dataIndex: 'configName'
          },
          {
            title:'服务器IP',
            align:"center",
            dataIndex: 'ip'
          },
          {
            title:'最近上传成功时间',
            align:"center",
            dataIndex: 'latestOpTime',
            customRender:function (text) {
              return !text?"":(text.length>10?text.substr(0,10):text)
            }
          },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            fixed:"right",
            width:147,
            scopedSlots: { customRender: 'action' }
          }
        ],
        url: {
          list: "/znrl/labory/qryLabDataUploadCfg",
          delete: "/modules/labReport/delete",
          deleteBatch: "/modules/labReport/deleteBatch",
          exportXlsUrl: "/modules/labReport/exportXls",
          importExcelUrl: "modules/labReport/importExcel",
          uploadDataUrl:"/znrl/labory/uploadLabData",

        },
        dictOptions:{},
        superFieldList:[],
      }
    },
    methods:{
      //化验室上传数据 “/business/labory/uploadLabData”  = this.laborCode1;,
      uploadAction(record){
       record.laborCode=this.from.laborCode;
        getAction(this.url.uploadDataUrl, record).then((res) => {
          console.log(res);
          if (res.code==0){
            this.$message.success('上传成功');
          }
          else {
            this.$message.error(res.message);
          }
        })
      },
      uploadAllAction(){
         //this.allRows = this.columns.getAll("getRows");

        /* console.log(this.dataSource);
        this.dataSource.push({laborCode:this.from.laborCode})
        console.log(this.dataSource.forEach(var i=0, i<dataSource.length, i++){

        });
        //console.log(this.columns);
        this.Obj.laborCode = this.from.laborCode;
        //this.Obj.ip =this.columns.
*/
       // console.log('xxxxxxxxxxxxxxxxxxxxxxxxxxx');
       // console.log(this.dataSource);
       // console.log(this.dataSource[0]);
       // var rows = this.dataSource.values();
       // console.log(rows);
        for (var i=0;i<this.dataSource.length;i++){
          //console.log(this.dataSource.keys());
          this.dataSource[i].laborCode = this.from.laborCode;
          //this.Obj.laborCode = this.from.laborCode;
          //this.Obj.labCfgId = this.dataSource.keys();
          getAction(this.url.uploadDataUrl,this.dataSource[i]).then((res) => {
           // console.log(res);
            if (res.code==0){
              this.$message.success('上传成功');
            }
            else {
              this.$message.error(res.message);
            }
          })
         }


      }

     /* loadData(arg) {
        if(!this.url.list){
          this.$message.error("请设置url.list属性!")
          return
        }
        //加载数据 若传入参数1则加载第一页的内容
        if (arg === 1) {
          this.ipagination.current = 1;
        }
        var params = this.getQueryParams();//查询条件
        this.loading = true;
        getAction(this.url.list, params).then((res) => {
          if (res.success) {
            //update-begin---author:zhangyafei    Date:20201118  for：适配不分页的数据列表------------
            this.dataSource = res.result.records||res.result;
            if(res.result.total)
            {
              this.ipagination.total = res.result.total;
            }else{
              this.ipagination.total = 0;
            }
            //update-end---author:zhangyafei    Date:20201118  for：适配不分页的数据列表------------
          }
          if(res.code===510){
            this.$message.warning(res.message)
          }
          this.loading = false;
        })
      },*/
    },
   /* created() {


      //this.getSuperFieldList();
    }*/
   /* getSuperFieldList(){
      let fieldList=[];
      fieldList.push({type:'string',value:'configName',text:'化验设备名称'})
      fieldList.push({type:'string',value:'ip',text:'服务器IP'})
      fieldList.push({type:'date',value:'latestOpTime',text:'最近上传成功时间'})
      this.superFieldList = fieldList
    }*/
  }


</script>

<style scoped>
@import '~@assets/less/common.less';
</style>