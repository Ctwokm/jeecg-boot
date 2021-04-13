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
          <a-col :md="3" :sm="24">
            <a-form-item label="车牌号">
              <a-input placeholder="请输入车牌号" v-model="queryParam.carId"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="3" :sm="24">
            <a-form-item label="状态">
              <a-select placeholder="请选择状态" v-model="queryParam.validSta">
                <a-select-option value="">全部</a-select-option>
                <a-select-option value="1">有效</a-select-option>
                <a-select-option value="0">无效</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :md="5" :sm="24">
            <a-form-item label="车辆类型">
              <j-dict-select-tag v-model="queryParam.carTyp" placeholder="请选择车辆类型"
                                 dictCode="dictionary_table,name,id,type = 'CAR_TYPE'"/>
            </a-form-item>
          </a-col>

          <span style="float: right;overflow: hidden;" class="table-page-search-submitButtons">
            <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
            <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
          </span>

        </a-row>
      </a-form>
    </div>


    <!-- 查询区域-END -->

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus">发卡</a-button>
      <a-button @click="handleUpd" type="primary" icon="plus">重新发卡</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('临时表')">导出</a-button>
      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl"
                @change="handleImportExcel">
        <a-button type="primary" icon="import">导入</a-button>
      </a-upload>
      <!-- 高级查询区域 -->
      <j-super-query :fieldList="superFieldList" ref="superQueryModal"
                     @handleSuperQuery="handleSuperQuery"></j-super-query>
      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel">
            <a-icon type="delete"/>
            删除
          </a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px"> 批量操作
          <a-icon type="down"/>
        </a-button>
      </a-dropdown>
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
                <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
                  <a>删除</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>

      </a-table>
    </div>

    <CarRegisterModal ref="modalForm" @ok="modalFormOk"></CarRegisterModal>

  </a-card>
</template>

<script>

    import '@/assets/less/TableExpand.less'
    import { mixinDevice } from '@/utils/mixin'
    import { JeecgListMixin } from '@/mixins/JeecgListMixin'
    import CarRegisterModal from './form/CarRegisterModal'
    import moment from 'moment'

    export default {
        name: 'CarRegisterList',
        mixins: [JeecgListMixin, mixinDevice],
        components: {
            CarRegisterModal
        },
        data() {
            return {
                description: '临时表管理页面',
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
                        dataIndex: 'cardId'
                    },
                    {
                        title: '前车牌',
                        align: 'center',
                        dataIndex: 'carId'
                    },
                    {
                        title: '后车牌',
                        align: 'center',
                        dataIndex: 'backcarId'
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
                        title: '运输类型',
                        align: 'center',
                        dataIndex: 'transTyp',
                        customRender: (text, record, index) => {
                            let re = ''
                            if (text === '0') {
                                re = '半挂'
                            } else if (text === '1') {
                                re = '罐车'
                            } else if (text === '2') {
                                re = '双挂'
                            }
                            return re
                        }
                    },
                    {
                        title: '车型',
                        align: 'center',
                        dataIndex: 'carTyp',
                        customRender: (text, record, index) => {
                            let re = ''
                            if (text === '0') {
                                re = '煤车'
                            } else if (text === '1') {
                                re = '原灰车'
                            } else if (text === '2') {
                                re = '搬倒车'
                            } else if (text === '3') {
                                re = '渣车'
                            } else if (text === '4') {
                                re = '石粉车'
                            } else if (text === '5') {
                                re = '石膏车'
                            } else if (text === '6') {
                                re = '粗灰车'
                            } else if (text === '7') {
                                re = '细灰车'
                            } else if (text === '8') {
                                re = '搅拌灰车'
                            }
                            return re
                        }

                    },
                    {
                        title: '皮重',
                        align: 'center',
                        dataIndex: 'stdQty'
                    },
                    {
                        title: '注册时间',
                        align: 'center',
                        dataIndex: 'regDtm',
                        customRender: function (text) {
                            return !text ? '' : (text.length > 10 ? text.substr(0, 10) : text)
                        }
                    },
                    {
                        title: '失效时间',
                        align: 'center',
                        dataIndex: 'endDtm',
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
                    list: '/znrl/carInfo/carRegisterList',
                    delete: '',
                    deleteBatch: '',
                    exportXlsUrl: '/auto/tempZhucecard/exportXls',
                    importExcelUrl: 'auto/tempZhucecard/importExcel',

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
                /*默认为注册时间排序*/
                add:'add'

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
            moment,
            initDictConfig() {
            },
            onChange(dates, dateStrings) {
                this.queryParam.beginTime = dateStrings[0]
                this.queryParam.endTime = dateStrings[1]
            },
            handleUpd:function () {
                if(this.selectedRowKeys.length <= 0){
                    alert("请勾选一条记录！");
                    return;
                }
                this.$refs.modalForm.upd(this.selectionRows[0]);
                this.$refs.modalForm.title = "重新发卡";
                this.$refs.modalForm.disableSubmit = false;

            },
            getSuperFieldList() {
                let fieldList = []
                fieldList.push({ type: 'string', value: 'cardId', text: '卡号', dictCode: '' })
                fieldList.push({ type: 'string', value: 'carId', text: '前车牌', dictCode: '' })
                fieldList.push({ type: 'string', value: 'backcarId', text: '后车牌', dictCode: '' })
                fieldList.push({ type: 'string', value: 'validSta', text: '状态', dictCode: '' })
                fieldList.push({ type: 'string', value: 'transTyp', text: '运输类型', dictCode: '' })
                fieldList.push({ type: 'string', value: 'carTyp', text: '车型', dictCode: '' })
                fieldList.push({ type: 'string', value: 'stdQty', text: '皮重', dictCode: '' })
                fieldList.push({ type: 'date', value: 'regDtm', text: '注册时间' })
                fieldList.push({ type: 'date', value: 'endDtm', text: '失效时间' })
                fieldList.push({type:'string',value:'car1Length',text:'车厢长度'})
                this.superFieldList = fieldList
            },
        }
    }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>