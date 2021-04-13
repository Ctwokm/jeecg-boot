<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :md="6" :sm="24">
            <a-form-item label="注册日期">
              <a-range-picker
                :ranges="{ 当天: [moment(), moment()], '三天内': [moment().subtract('days', 2), moment()], '一周内': [moment().subtract('days', 6), moment()], '本月': [moment().subtract('month',1), moment()] }"
                @change="onChange"
                format="YYYY/MM/DD "
              />
            </a-form-item>
          </a-col>
          <a-col :md="5" :sm="24">
            <a-form-item label="矿点选择">
              <j-dict-select-tag v-model="queryParam.mineNo" placeholder="请选择矿点"
                                 dictCode="COAL_MINE,mine_name,mine_no"/>
            </a-form-item>
          </a-col>
          <a-col :md="5" :sm="24">
            <a-form-item label="矿卡卡号">
              <a-input v-model="queryParam.mineCardId" placeholder="请扫卡" disabled></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="2">
            <a-button type="primary" style="margin-left:10px;margin-top: 0px;" @click="getCard" icon="search">扫描卡
            </a-button>
          </a-col>

          <span style="float: right;overflow: hidden;" class="table-page-search-submitButtons">
            <a-button @click="handleAdd" style="margin-right: 150px"  type="primary" icon="plus">新注册发卡</a-button>
            <a-button type="primary" style="margin-left: 15px" @click="searchQuery" icon="search">查询</a-button>
          </span>

        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->

    <!-- 操作按钮区域 -->
    <div class="table-operator">
<!--      <a-button @click="handleAdd" type="primary" icon="plus">新注册发卡</a-button>-->
     <!-- <a-button type="primary" icon="download" @click="handleExportXls('mine_card_info')">导出</a-button>
      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl"
                @change="handleImportExcel">
        <a-button type="primary" icon="import">导入</a-button>
      </a-upload>-->
      <!-- 高级查询区域 -->
    </div>

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择 <a style="font-weight: 600">{{
        selectedRowKeys.length }}</a>项
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
          <img v-else :src="getImgView(text)" height="25px" alt=""
               style="max-width:80px;font-size: 12px;font-style: italic;"/>
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

          <a-divider type="vertical"/>
          <a-dropdown>
            <a class="ant-dropdown-link">更多 <a-icon type="down"/></a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a @click="handleDetail(record)">详情</a>
              </a-menu-item>
              <a-menu-item>
                <a-popconfirm title="确定作废吗?" @confirm="() => handleDelete(record)">
                  <a>作废</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>

      </a-table>
    </div>

    <mine-card-info-modal ref="modalForm" @ok="modalFormOk"></mine-card-info-modal>
  </a-card>
</template>

<script>

    import '@/assets/less/TableExpand.less'
    import { mixinDevice } from '@/utils/mixin'
    import { JeecgListMixin } from '@/mixins/JeecgListMixin'
    import MineCardInfoModal from './form/MineCardInfoModal'
    import moment from 'moment'
    import { httpAction, getAction } from '@/api/manage'

    export default {
        name: 'MineCardInfoList',
        mixins: [JeecgListMixin, mixinDevice],
        components: {
            MineCardInfoModal
        },
        data() {
            return {
                queryParam: {},
                description: 'mine_card_info管理页面',
                // 表头
                columns: [
                    {
                        title: '#',
                        dataIndex: '',
                        key: 'rowIndex',
                        width: 60,
                        align: 'center',
                        customRender: function (t, r, index) {
                            return parseInt(index) + 1
                        }
                    },
                    {
                        title: '卡号',
                        align: 'center',
                        dataIndex: 'mineCardId'
                    },
                    {
                        title: '供应商',
                        align: 'center',
                        dataIndex: 'venName'
                    },
                    {
                        title: '煤矿',
                        align: 'center',
                        dataIndex: 'mineName'
                    },
                    {
                        title: '煤种',
                        align: 'center',
                        dataIndex: 'coalName'
                    },
                    {
                        title: '生效时间',
                        align: 'center',
                        dataIndex: 'effTime',
                        customRender: function (text) {
                            return !text ? '' : (text.length > 10 ? text.substr(0, 10) : text)
                        }
                    },
                    {
                        title: '失效时间',
                        align: 'center',
                        dataIndex: 'expTime',
                        customRender: function (text) {
                            return !text ? '' : (text.length > 10 ? text.substr(0, 10) : text)
                        }
                    }, {
                        title: '矿卡类型',
                        align: 'center',
                        dataIndex: 'mineCardType',
                        customRender: (text, record, index) => {
                            let re = ''
                            if (text === '101') {
                                re = '煤车'
                            } else if (text === '102') {
                                re = '物料车'
                            }
                            return re
                        }
                    },
                    {
                        title: '状态',
                        align: 'center',
                        dataIndex: 'validSta',
                        customRender: (text, record, index) => {
                            let re = ''
                            if (text === '1') {
                                re = '有效'
                            } else if (text === '0') {
                                re = '无效'
                            }
                            return re
                        }
                    },
                    {
                        title: '注册时间',
                        align: 'center',
                        dataIndex: 'insertTime',
                        customRender: function (text) {
                            return !text ? '' : (text.length > 10 ? text.substr(0, 10) : text)
                        }
                    },
                    {
                        title: '操作',
                        dataIndex: 'action',
                        align: 'center',
                        fixed: 'right',
                        width: 147,
                        scopedSlots: { customRender: 'action' }
                    }
                ],
                url: {
                    list: '/znrl/carInfo/mineRegisterList',
                    delete: '/znrl/carInfo/moveMsgToMineHis',

                    deleteBatch: '/demo/mineCardInfo/deleteBatch',
                    exportXlsUrl: '/demo/mineCardInfo/exportXls',
                    importExcelUrl: 'demo/mineCardInfo/importExcel',

                },
                isorter: {
                    sort: 'insert_time',
                    order: 'desc',
                },
                dictOptions: {},
                pickerOptions: {
                    shortcuts: [{
                        text: '最近一天',
                        onClick(picker) {
                            const end = new Date()
                            const start = new Date()
                            start.setTime(start.getTime() - 3600 * 1000 * 24 * 1)
                            picker.$emit('pick', [start, end])
                        }
                    }, {
                        text: '最近三天',
                        onClick(picker) {
                            const end = new Date()
                            const start = new Date()
                            start.setTime(start.getTime() - 3600 * 1000 * 24 * 3)
                            picker.$emit('pick', [start, end])
                        }
                    }, {
                        text: '最近一周',
                        onClick(picker) {
                            const end = new Date()
                            const start = new Date()
                            start.setTime(start.getTime() - 3600 * 1000 * 24 * 7)
                            picker.$emit('pick', [start, end])
                        }
                    }]
                },
                superFieldList: [],
            }
        },
        created() {
            this.getSuperFieldList()
        },
        computed: {
            importExcelUrl: function () {
                return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`
            },
        },
        methods: {
            initDictConfig() {
            },
            onChange(dates, dateStrings) {
                this.queryParam.effTime = dateStrings[0]
                this.queryParam.expTime = dateStrings[1]
            },
            moment,
            getSuperFieldList() {
                let fieldList = []
                fieldList.push({ type: 'string', value: 'mineCardId', text: '卡号', dictCode: '' })
                fieldList.push({ type: 'string', value: 'venName', text: '供应商', dictCode: '' })
                fieldList.push({ type: 'string', value: 'mineName', text: '煤矿', dictCode: '' })
                fieldList.push({ type: 'string', value: 'coalName', text: '煤种', dictCode: '' })
                fieldList.push({ type: 'date', value: 'effTime', text: '生效时间' })
                fieldList.push({ type: 'date', value: 'expTime', text: '失效时间' })
                fieldList.push({ type: 'date', value: 'insertTime', text: '注册时间' })
                fieldList.push({ type: 'string', value: 'validSta', text: '状态', dictCode: '' })
                fieldList.push({ type: 'string', value: 'mineCardType', text: '矿卡类型', dictCode: '' })
                fieldList.push({ type: 'string', value: 'remark', text: '备注', dictCode: '' })
                this.superFieldList = fieldList
            },
            getCard() {
                /*通过this.form.getFieldValue来获取对应的值；通过 this.form.setFieldsValue({来设置值*/
                //设置url属性
                let httpurl = '/znrl/carInfo/touchCarInfo'
                let method = 'Post'
                httpAction(httpurl, '1', method).then((res) => {
                    if (res.success) {
                        //that.$message.success(res.message);
                        this.queryParam = {
                            mineCardId: res.message
                        }
                       } else {
                        that.$message.warning(res.message)
                    }
                })

            },
            handleDelete :function(record){
                console.log(record.cardRecId);
                //之前作废的操作就是update 状态数据，现更新为作废为移除数据到his表中
                httpAction(this.url.delete,record,'delete').then((reg)=>{
                    if (reg.success){
                        alert(reg.message);
                        this.loadData(1);
                    }else{
                        alert(reg.message)
                    }
                })
            }
        }
    }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>