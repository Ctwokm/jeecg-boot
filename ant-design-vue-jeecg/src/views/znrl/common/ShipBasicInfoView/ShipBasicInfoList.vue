<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('ship_info')">导出</a-button>
      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
        <a-button type="primary" icon="import">导入</a-button>
      </a-upload>
      <!-- 高级查询区域 -->
      <j-super-query :fieldList="superFieldList" ref="superQueryModal" @handleSuperQuery="handleSuperQuery"></j-super-query>
      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel"><a-icon type="delete"/>删除</a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px"> 批量操作 <a-icon type="down" /></a-button>
      </a-dropdown>
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
        :scroll="{x:true}"
        bordered
        rowKey="id"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
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
                <a-popconfirm title="确定删除吗?" @confirm="() => deleteById(record.shipId)">
                  <a>删除</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>

      </a-table>
    </div>

    <ship-info-modal ref="modalForm" @ok="modalFormOk"></ship-info-modal>
  </a-card>
</template>

<script>

  import '@/assets/less/TableExpand.less'
  import { mixinDevice } from '@/utils/mixin'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import ShipInfoModal from './modules/ShipBasicInfoModal'
  import { deleteAction, getAction,downFile,getFileAccessHttpUrl } from '@/api/manage'

  export default {
    name: 'ShipInfoList',
    mixins:[JeecgListMixin, mixinDevice],
    components: {
      ShipInfoModal
    },
    data () {
      return {
        description: 'ship_info管理页面',
        // 表头
        columns: [
          {
            title:'船ID',
            align:"center",
            key:'rowIndex',
            dataIndex: 'shipId'
          },
          {
            title:'船名称',
            align:"center",
            dataIndex: 'shipName'
          },
          {
            title:'船英文名称',
            align:"center",
            dataIndex: 'shipEngName'
          },
          {
            title:'船号',
            align:"center",
            dataIndex: 'shipCode'
          },
          {
            title:'如果无航次则为空',
            align:"center",
            dataIndex: 'fixShipNo'
          },
          {
            title:'舱位数量',
            align:"center",
            dataIndex: 'cabinCnt'
          },
          {
            title:'额定载重',
            align:"center",
            dataIndex: 'loadTun'
          },
          {
            title:'总排水量',
            align:"center",
            dataIndex: 'totalTun'
          },
          {
            title:'舷宽',
            align:"center",
            dataIndex: 'width'
          },
          {
            title:'船长度',
            align:"center",
            dataIndex: 'length'
          },
          {
            title:'国籍',
            align:"center",
            dataIndex: 'nationCd'
          },
          {
            title:'经营公司',
            align:"center",
            dataIndex: 'companyNo'
          },
          {
            title:'备注',
            align:"center",
            dataIndex: 'remark'
          },
          {
            title:'0未生效  1生效  2失效',
            align:"center",
            dataIndex: 'status'
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
          list: "/ShipInfo/shipInfo/list",
          delete: "/ShipInfo/shipInfo/delete",
          deleteBatch: "/ShipInfo/shipInfo/deleteBatch",
          exportXlsUrl: "/ShipInfo/shipInfo/exportXls",
          importExcelUrl: "ShipInfo/shipInfo/importExcel",

        },
        dictOptions:{},
        superFieldList:[],
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
      getSuperFieldList(){
        let fieldList=[];
        fieldList.push({type:'int',value:'shipId',text:'船ID'})
        fieldList.push({type:'string',value:'shipName',text:'船名称'})
        fieldList.push({type:'string',value:'shipEngName',text:'船英文名称'})
        fieldList.push({type:'string',value:'shipCode',text:'船号'})
        fieldList.push({type:'string',value:'fixShipNo',text:'如果无航次则为空'})
        fieldList.push({type:'int',value:'cabinCnt',text:'舱位数量'})
        fieldList.push({type:'number',value:'loadTun',text:'额定载重'})
        fieldList.push({type:'number',value:'totalTun',text:'总排水量'})
        fieldList.push({type:'number',value:'width',text:'舷宽'})
        fieldList.push({type:'number',value:'length',text:'船长度'})
        fieldList.push({type:'string',value:'nationCd',text:'国籍'})
        fieldList.push({type:'string',value:'companyNo',text:'经营公司'})
        fieldList.push({type:'string',value:'remark',text:'备注'})
        fieldList.push({type:'string',value:'status',text:'0未生效  1生效  2失效'})
        this.superFieldList = fieldList
      },
      deleteById(shipId){
        if(!this.url.delete){
          this.$message.error("请设置url.delete属性!")
          return
        }
        var that = this;
        deleteAction(that.url.delete, {shipId: shipId}).then((res) => {
          if (res.success) {
            that.$message.success(res.message);
            that.loadData();
            that.onClearSelected();
          } else {
            that.$message.warning(res.message);
          }
        }).finally(() => {
          that.loading = false;
        });
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>