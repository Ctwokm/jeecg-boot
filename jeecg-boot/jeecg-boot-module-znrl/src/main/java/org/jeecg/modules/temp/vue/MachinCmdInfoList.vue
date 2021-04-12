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
      <a-button type="primary" icon="download" @click="handleExportXls('machin_cmd_info')">导出</a-button>
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
                <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
                  <a>删除</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>

      </a-table>
    </div>

    <machin-cmd-info-modal ref="modalForm" @ok="modalFormOk"></machin-cmd-info-modal>
  </a-card>
</template>

<script>

  import '@/assets/less/TableExpand.less'
  import { mixinDevice } from '@/utils/mixin'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import MachinCmdInfoModal from './modules/MachinCmdInfoModal'

  export default {
    name: 'MachinCmdInfoList',
    mixins:[JeecgListMixin, mixinDevice],
    components: {
      MachinCmdInfoModal
    },
    data () {
      return {
        description: 'machin_cmd_info管理页面',
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
            title:'控制信息记录ID',
            align:"center",
            dataIndex: 'cmdRecId'
          },
          {
            title:'machinCode',
            align:"center",
            dataIndex: 'machinCode'
          },
          {
            title:'设备类型 1:	汽车车厢采样机 2	:	制样机 3	:	翻车机 4:		存查样柜 5:		调度转发用 6	:	火车车厢采样机 7:		入厂皮带采样机 8	:	入炉皮带采样机 ',
            align:"center",
            dataIndex: 'machinType'
          },
          {
            title:'命令代码 1：启动 2：停止 3：继续工作（采样、制样）4.卸样 5.打包 6.大水分旁路',
            align:"center",
            dataIndex: 'commandCode'
          },
          {
            title:'采样编码(采样机命令)；制样编码（制样机命令）',
            align:"center",
            dataIndex: 'sampleCode'
          },
          {
            title:'命令发送时间',
            align:"center",
            dataIndex: 'sendCmdTime',
            customRender:function (text) {
              return !text?"":(text.length>10?text.substr(0,10):text)
            }
          },
          {
            title:'数据状态 0: 未读取 1: 已读取',
            align:"center",
            dataIndex: 'dataStatus'
          },
          {
            title:'入厂流水',
            align:"center",
            dataIndex: 'recordNo'
          },
          {
            title:'车号：汽车采样时需要填此字段给采样机',
            align:"center",
            dataIndex: 'carId'
          },
          {
            title:'建立时间',
            align:"center",
            dataIndex: 'insertTime',
            customRender:function (text) {
              return !text?"":(text.length>10?text.substr(0,10):text)
            }
          },
          {
            title:'建立用户',
            align:"center",
            dataIndex: 'opCode'
          },
          {
            title:'更新用户',
            align:"center",
            dataIndex: 'updateCode'
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
          list: "/modules/machinCmdInfo/list",
          delete: "/modules/machinCmdInfo/delete",
          deleteBatch: "/modules/machinCmdInfo/deleteBatch",
          exportXlsUrl: "/modules/machinCmdInfo/exportXls",
          importExcelUrl: "modules/machinCmdInfo/importExcel",
          
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
        fieldList.push({type:'int',value:'cmdRecId',text:'控制信息记录ID'})
        fieldList.push({type:'string',value:'machinCode',text:'machinCode'})
        fieldList.push({type:'string',value:'machinType',text:'设备类型 1:	汽车车厢采样机 2	:	制样机 3	:	翻车机 4:		存查样柜 5:		调度转发用 6	:	火车车厢采样机 7:		入厂皮带采样机 8	:	入炉皮带采样机 '})
        fieldList.push({type:'string',value:'commandCode',text:'命令代码 1：启动 2：停止 3：继续工作（采样、制样）4.卸样 5.打包 6.大水分旁路'})
        fieldList.push({type:'string',value:'sampleCode',text:'采样编码(采样机命令)；制样编码（制样机命令）'})
        fieldList.push({type:'date',value:'sendCmdTime',text:'命令发送时间'})
        fieldList.push({type:'string',value:'dataStatus',text:'数据状态 0: 未读取 1: 已读取'})
        fieldList.push({type:'int',value:'recordNo',text:'入厂流水'})
        fieldList.push({type:'string',value:'carId',text:'车号：汽车采样时需要填此字段给采样机'})
        fieldList.push({type:'date',value:'insertTime',text:'建立时间'})
        fieldList.push({type:'string',value:'opCode',text:'建立用户'})
        fieldList.push({type:'string',value:'updateCode',text:'更新用户'})
        this.superFieldList = fieldList
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>