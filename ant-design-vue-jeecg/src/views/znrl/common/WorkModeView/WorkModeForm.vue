<template>
  <div>
<!--    <a-card class="card" v-for="title in dataSource.workModeTypeResult" :key="title.parentTypeId">-->
<!--      <a-card class="card" :title=title1.parentTypeId v-for="title1 in title.workModeTypeResult" :key="title1.parentTypeId">-->
<!--        <a-card class="card" :title=title2.typeName v-for="title2 in title1.workModeTypeList" :key="title2.typeId">-->
<!--          {{title2}}-->
<!--          <a-form  class="form">-->
<!--            <a-row class="form-row" :gutter="16">-->
<!--              <a-col :lg="6" :md="12" :sm="24">-->
<!--                <a-form-item label="当前参数值">-->
<!--                  <a-input-->
<!--                    v-model = title2.workModeValue-->
<!--                  />-->
<!--                </a-form-item>-->
<!--              </a-col>-->
<!--              <a-col :xl="{span: 9, offset: 1}" :lg="{span: 10}" :md="{span: 24}" :sm="24">-->
<!--                <a-form-item label="修改参数值">-->
<!--                  {{title2.dataUrl}}-->
<!--                  <j-date v-model="jdate.value" :showTime="true" dateFormat="YYYY-MM-DD HH:mm:ss"  v-if="title2.showType === '1'"/>-->
<!--                  <a-select placeholder="请选择管理员" v-decorator="[ 'repository.manager', {rules: [{ required: true, message: '请选择管理员'}]} ]" v-if="title2.showType === '2'">-->
<!--                    <a-select-option value="李同学">李同学</a-select-option>-->
<!--                  </a-select>-->
<!--                  <a-select placeholder="请选择参数值" v-decorator="[ 'repository.manager', {rules: [{ required: true, message: '请选择参数值'}]} ]" v-if="title2.showType === '3'">-->
<!--&lt;!&ndash;                    <a-select-option v-bind:value=title3.id v-for="title3 in title2.dataUrl" :key="title3.id" >{{title3.name}}</a-select-option>&ndash;&gt;-->
<!--                    <a-select-option value="张同学">张同学</a-select-option>-->
<!--                  </a-select>-->
<!--                  <a-select placeholder="请选择管理员" v-decorator="[ 'repository.manager', {rules: [{ required: true, message: '请选择管理员'}]} ]" v-if="title2.showType === '4'">-->
<!--                    <a-select-option value="胡同学">胡同学</a-select-option>-->
<!--                  </a-select>-->

<!--                </a-form-item>-->
<!--              </a-col>-->
<!--            </a-row>-->
<!--          </a-form>-->
<!--        </a-card>-->
<!--      </a-card>-->
<!--    </a-card>-->
  </div>
</template>

<script>
  import {ajaxGetDictItems,getDictItemsFromCache} from '@/api/api'
  import { postAction, getAction } from '@/api/manage'
  import Vue from "vue";
  import JDate from '@/components/jeecg/JDate'

  export default {
    name: "WorkModeForm",
    components: {
    },
    data () {
      return {
        description: '系统参数设定',
        //titles : ["111","222","333"],
        // table
        jdate: {
          value: '2021-4-10 15:33:06'
        },
        titles : [
          // this.parentTypeId,
          // this.workModeTypeList,
        ],
        workModeTypeList : [
          {
            "typeId": 0,
            "parentTypeId": 0,
            "typeName": "",
            "showType": "",
            "dataLoadType": "",
            "dataUrl": [],
            "unit": "",
            "showColumn": null,
            "dependTypeId": null,
            "dependValue": "",
            "workModeValue": ""
          }
        ],
        workModeTypeResult : [
          {
            "parentTypeId": 0,
            "workModeTypeList": [],
            "workModeTypeResult": [
              {
                "parentTypeId": 0,
                "workModeTypeList": [],
                "workModeTypeResult": []
              }
            ]
          }
        ],
        dataSource:{},
        url: {
          list: "/znrl/config/workModeType/list/0",
        },
      }
    },
    created() {
      //获取字典数据
      this.initDictData();
    },
    methods: {
      //在页面加载的时候直接调用加载所需的选项数据
      initDictData() {
        // var ModeData = "work_mode_type,type_name,type_id,PARENT_TYPE_ID = '101' AND STATE = 1";
        // this.initWorkModeData(ModeData);
        this.loadData();
      },
      initWorkModeData(ModeData) {
        //优先从缓存中读取字典配置
        if(getDictItemsFromCache(ModeData)){
          this.dictOptions = getDictItemsFromCache(ModeData);
          return
        }

        //根据字典Code, 初始化字典数组
        ajaxGetDictItems(ModeData, null).then((res) => {
          if (res.success) {
            console.log(res.result);
            this.dictOptions = res.result;
            this.titles = res.result;
          }
        })
      },
      loadData() {
        if(!this.url.list){
          this.$message.error("请设置url.list属性!")
          return
        }
        getAction(this.url.list).then((res) => {
          if (res.success) {
            this.workModeTypeResult = res.result.workModeTypeResult[0].workModeTypeResult;
            // console.log(this.workModeTypeResult);
            // var jsonStr = this.workModeTypeResult[0].workModeTypeList[0].dataUrl.split('&');
            // console.log(jsonStr);
            // var jsonObj = JSON.parse(jsonStr[0]);
            // console.log(jsonObj);

          }
          if(res.code===510){
            this.$message.warning(res.message)
          }
        })
      },
    }
  }
</script>

<style lang="less" scoped>
  .card{
    margin-bottom: 24px;
  }
</style>