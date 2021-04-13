<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <!--        日期的表单  -->
          <a-col :md="6" :sm="24">
            <a-form-item label="入厂时间">
              <a-range-picker
                :ranges="{ 当天: [moment(), moment()], '三天内': [moment().subtract('days', 2), moment()], '一周内': [moment().subtract('days', 6), moment()], '本月': [moment().subtract('month',1), moment()] }"
                @change="onChange"
                :default-value="getDateRange(queryParam.beginTime,queryParam.endTime)"
                format="YYYY-MM-DD"
              />
            </a-form-item>
          </a-col>
          <!--         模糊查询   首台模糊查询-->
          <!--          下拉框字典-->
          <a-col :md="6" :sm="24">
            <a-form-item label="矿点">
              <j-dict-select-tag v-model="queryParam.mineNo" placeholder="请选择矿点"
                                 dictCode="coal_mine,mine_name,mine_no"/>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="24">
            <a-form-item label="供应商">
              <j-dict-select-tag v-model="queryParam.venNo" placeholder="请选择供应商"
                                 dictCode="vendor_info,ven_name,ven_no"/>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="24">
            <a-form-item label="承运商">
              <j-dict-select-tag v-model="queryParam.carrierNo" placeholder="请选择承运商"
                                 dictCode="carrier_info,carrier_name,carrier_no"/>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :md="6" :sm="24">
            <a-form-item label="煤种">
              <j-dict-select-tag v-model="queryParam.coalNo" placeholder="请选择煤种"
                                 dictCode="coal_type,coal_name,coal_no"/>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="24">
            <a-form-item label="车牌号">
              <a-input placeholder="请输入车牌号" v-model="queryParam.carId"></a-input>
            </a-form-item>
          </a-col>

          <a-col :md="12" :sm="24">
            <span style="float: right;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="qryTaskList" icon="search">查询</a-button>
              <!--              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>-->
<!--               <a-button type="primary" icon="download" @click="handleExportXls('计量结果')" style="margin-left: 8px">导出</a-button>-->
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <a-divider/>

    <a-row :gutter="24">
      <a-col :md="3" :sm="24">
         <a-input :disabled = "true" v-model="totalResult.totalEntryCnt" addon-before="入厂车数" addon-after="辆" />
      </a-col>
      <a-col :md="3" :sm="24">
        <a-input :disabled = "true" v-model="totalResult.totalCZCnt" addon-before="称重车数" addon-after="辆" />
      </a-col>
      <a-col :md="3" :sm="24">
        <a-input :disabled = "true" v-model="totalResult.totalJQCnt" addon-before="回皮车数" addon-after="辆" />
      </a-col>
      <a-col :md="3" :sm="24">
        <a-input :disabled = "true" v-model="totalResult.totalMzQty" addon-before="总毛重" addon-after="t" />
      </a-col>
      <a-col :md="3" :sm="24">
        <a-input :disabled = "true" v-model="totalResult.totalPzQty" addon-before="总皮重" addon-after="t" />
      </a-col>
      <a-col :md="3" :sm="24">
        <a-input :disabled = "true" v-model="totalResult.totalNetQty" addon-before="总净重" addon-after="t" />
      </a-col>
      <a-col :md="3" :sm="24">
        <a-input :disabled = "true" v-model="totalResult.totalTickQty" addon-before="总票重" addon-after="t" />
      </a-col>
      <a-col :md="3" :sm="24">
        <a-input :disabled = "true" v-model="totalResult.totalKdQty" addon-before="总扣重" addon-after="tr" />
      </a-col>
    </a-row>
    <!-- 查询区域-END -->
    <!-- 操作按钮区域 -->
    <!-- table区域-begin -->

      <!-- 报表结果展示区域 -->

    <iframe :src="this.url.ureportlist" width="100%" height="800px"
            frameborder="0">

    </iframe>

  </a-card>

</template>

<script>

  import '@/assets/less/TableExpand.less'
  // { mixinDevice } from '@/utils/mixin'
 // import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import moment from 'moment'
  import { filterObj } from '@/utils/util';
  import { deleteAction, getAction,downFile,getFileAccessHttpUrl } from '@/api/manage'
  //导入组件



  export default {
    name: 'CarWeightRpt',
  //  mixins:[JeecgListMixin, mixinDevice],
    data () {
      return {
        description: '汽车计量报表',
        queryParam: {beginTime:moment().subtract('month',1).format('YYYY-MM-DD'),endTime:moment().format('YYYY-MM-DD')},
        totalResult:{},
        // 表头
        url: {
          list: "http://localhost:8088/jeecg-boot/ureport/preview?_u=file:weightRpt_daily.ureport.xml",
          ureportlist:"",
          totalResultlist:"/znrl/report/weightSummaryRpt"

        },

        pickerOptions: {
          shortcuts: [{
            text: '最近一天',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 1);
              picker.$emit('pick', [start, end]);
            }
          }, {
            text: '最近三天',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 3);
              picker.$emit('pick', [start, end]);
            }
          }, {
            text: '最近一周',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
              picker.$emit('pick', [start, end]);
            }
          }]
        },
        dictOptions:{},
      }
    },
    created() {
      this.qryTaskList();
    },
    computed: {
    },
    methods: {
      initDictConfig(){
      },
      moment,
      //给时间查询条件赋值
      onChange(dates, dateStrings) {
        this.queryParam.beginTime = dateStrings[0];
        this.queryParam.endTime = dateStrings[1];
      },
      //默认赋值
      getDateRange(startDate,endDate){
        return [
          startDate,
          endDate
        ];
      },
      qryTaskList(){
        //加载报表
        this.url.ureportlist = this.url.list
        if(this.queryParam.beginTime !=null && this.queryParam.beginTime !=""&&this.queryParam.beginTime.length >0){
          this.url.ureportlist = this.url.ureportlist + "&beginTime="+this.queryParam.beginTime
        }
        if(this.queryParam.endTime !=null && this.queryParam.endTime !=""&&this.queryParam.endTime.length >0){
          this.url.ureportlist = this.url.ureportlist + "&endTime="+this.queryParam.endTime
        }
        if(this.queryParam.venNo !=null && this.queryParam.venNo !=""&&this.queryParam.venNo.length >0){
          this.url.ureportlist = this.url.ureportlist + "&ven_no="+this.queryParam.venNo
        }
        if(this.queryParam.coalNo !=null && this.queryParam.coalNo !=""&&this.queryParam.coalNo.length >0){
          this.url.ureportlist = this.url.ureportlist + "&coal_no="+this.queryParam.coalNo
        }
        if(this.queryParam.carrierNo !=null && this.queryParam.carrierNo !=""&&this.queryParam.carrierNo.length >0){
          this.url.ureportlist = this.url.ureportlist + "&carrier_no="+this.queryParam.carrierNo
        }
        if(this.queryParam.mineNo !=null && this.queryParam.mineNo !=""&&this.queryParam.mineNo.length >0){
          this.url.ureportlist = this.url.ureportlist + "&colry_no="+this.queryParam.mineNo
        }
        if(this.queryParam.carId !=null && this.queryParam.carId !=""&&this.queryParam.carId.length >0){
          this.url.ureportlist = this.url.ureportlist + "&car_id="+this.queryParam.carId
        }
        //this.url.ureportlist = this.url.list + "&beginTime="+this.queryParam.beginTime+"&endTime="+this.queryParam.endTime+"&ven_no="+this.queryParam.venNo+"&coal_no="+this.queryParam.coalNo+"&carrier_no="+this.queryParam.carrierNo+"&colry_no="+this.queryParam.mineNo+"&car_id="+this.queryParam.carId;
        //加载汇总信息
        this.qryWeightSummary();
      },
      qryWeightSummary(){
        debugger;
        if(!this.url.totalResultlist){
          this.$message.error("请设置url.list属性!")
          return
        }
        //加载数据 若传入参数1则加载第一页的内容
        var params = this.getQueryParams();//查询条件
        getAction(this.url.totalResultlist, params).then((res) => {
          if (res.success) {
            debugger;
            console.log(res.result.records);
            //update-begin---author:zhangyafei    Date:20201118  for：适配不分页的数据列表------------
            this.totalResult = res.result.records||res.result;

            //update-end---author:zhangyafei    Date:20201118  for：适配不分页的数据列表------------
          }else {
            this.$message.error(res.message);
          }
          if(res.code===510){
            this.$message.warning(res.message)
          }
        })
      },
      getQueryParams() {
        //获取查询条件
        let sqp = {}
        var param = Object.assign(sqp, this.queryParam);
        return filterObj(param);
      },

    }
  }
</script>
<style scoped>

</style>