<template>
  <a-card :bordered="false">

    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="24">

          <a-col :md="6" :sm="24">
            <a-form-item label="入厂日期">
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
              <a-select placeholder="状态" v-model="queryParam.leaveFlg">
                <a-select-option value="">全部</a-select-option>
                <a-select-option value="1">入厂</a-select-option>
                <a-select-option value="2">已出厂</a-select-option>
                <a-select-option value="3">已采样</a-select-option>
                <a-select-option value="4">已称重</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :md="5" :sm="24">
            <a-form-item label="矿点">
              <j-dict-select-tag v-model="queryParam.mineName" placeholder="请选择矿点"
                                 dictCode="coal_mine,mine_name,mine_no"/>
            </a-form-item>
          </a-col>

          <span style="float: right;overflow: hidden;" class="table-page-search-submitButtons">
            <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
            <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
          </span>

        </a-row>
      </a-form>
    </div>

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus">人工入厂</a-button>
      <a-button @click="handleEditKD()" type="primary" icon="edit">扣吨</a-button>
      <a-button type="primary" v-print="'#printTable'" @click="printList()" icon="printer">打印磅单</a-button>
      <a-button @click="changeCYJ()" type="primary" icon="export">切换通道</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('导出表格')">导出excel</a-button>
      <!--先前的批量操作-->
      <!--<a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel"><a-icon type="delete"/>作废</a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px"> 批量操作 <a-icon type="down" /></a-button>
      </a-dropdown>-->
    </div>

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择 <a style="font-weight: 600">{{
        selectedRowKeys.length }}</a>项
        <a style="margin-left: 24px" @click="onClearSelected">清空</a>
      </div>

      <!--table 中的rowKey 为行的key 可以定义为查询sql中的主键字段；-->
      <a-table
        ref="table"
        size="middle"
        bordered
        rowKey="recordNo"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        @change="handleTableChange"
        :customRow="clickThenCheck"
      >

        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)">编辑</a>

          <a-divider type="vertical"/>
          <a-dropdown>
            <a class="ant-dropdown-link">更多<a-icon type="down"/></a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.recordNo)">
                  <a>删除</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>

      </a-table>
    </div>
    <!-- table区域-end -->
    <!--定义一个表格打印的div   style="display:none" -> 设置div不可见；-->
    <div  ref="printTable" id="printTable" title="磅单打印" style="display:none" >
      <h6 style="font-size:24px;text-align: center;line-height:40px;">汽车来煤过磅单</h6>
      <table style="width:100%;">
        <thead>
        <tr>
          <th style="text-align:left;border:1px solid black;width:5%;">序号</th>
          <th style="text-align:left;border:1px solid black;width:15%;">车牌号</th>
          <th style="text-align:left;border:1px solid black;width:15%;">矿点</th>
          <th style="text-align:left;border:1px solid black;width:15%;">收货单位</th>
          <th style="text-align:left;border:1px solid black;width:10%;">车次</th>
          <th style="text-align:left;border:1px solid black;width:10%;">毛重</th>
          <th style="text-align:left;border:1px solid black;width:10%;">皮重</th>
          <th style="text-align:left;border:1px solid black;width:10%;">扣吨</th>
          <th style="text-align:left;border:1px solid black;width:10%;">净重</th>
        </tr>
        </thead>
        <tbody>
        <template v-if="selectionRows.length">
          <tr v-for="(item,index) in selectionRows"
              :key="index">
            <td style="text-align:left;border:1px solid black;width:5%;">{{ index+1 }}</td>
            <td style="text-align:left;border:1px solid black;width:15%;">{{ item.carId }}</td>
            <td style="text-align:left;border:1px solid black;width:15%;">{{ item.mineName }}</td>
            <td style="text-align:left;border:1px solid black;width:15%;">山西崇光发电有限责任公司</td>
            <td style="text-align:left;border:1px solid black;width:10%;">{{item.length}}</td>
            <td style="text-align:left;border:1px solid black;width:10%;">{{ item.mzQty }}</td>
            <td style="text-align:left;border:1px solid black;width:10%;">{{ item.pzQty }}</td>
            <td style="text-align:left;border:1px solid black;width:10%;">{{ item.kdQty }}</td>
            <td style="text-align:left;border:1px solid black;width:10%;">{{ item.netQty }}</td>
          </tr>
        </template>
        </tbody>
      </table>

    </div>

    <!-- 表单区域 -->
    <carCoalMgrModel ref="modalForm" @ok="modalFormOk"></carCoalMgrModel>

    <carCoalKdModel ref="modalkdForm" @ok="modalFormOk"></carCoalKdModel>

    <CarCoalCyjForm ref="modalCyjForm" @ok="modalFormOk"></CarCoalCyjForm>


  </a-card>
</template>

<script>
    import { httpAction, postAction, getAction } from '@/api/manage'
    import carCoalMgrModel from './form/carCoalMgrModel'
    import carCoalKdModel from './form/carCoalKdModel'
    import CarCoalCyjForm from './form/CarCoalCyjForm'
    import { JeecgListMixin } from '@/mixins/JeecgListMixin'
    import moment from 'moment'

    export default {
        /*name: "JeecgOrderMainList",*/
        mixins: [JeecgListMixin],
        components: {
            carCoalMgrModel,
            carCoalKdModel,
            CarCoalCyjForm
        },
        data() {
            return {
                description: '汽车来煤管理',
                dateFormat: 'YYYY/MM/DD',
                /* 排序参数 */
                // 表头
                columns: [
                    {
                        title: '序号',
                        dataIndex: '',
                        key: 'rowIndex',
                        width: 40,
                        align: 'center',
                        customRender: function (t, r, index) {
                            return parseInt(index) + 1
                        }
                    },
                    {
                        title: '车牌号',
                        align: 'center',
                        dataIndex: 'carId'
                    },
                    {
                        title: '矿点',
                        align: 'center',
                        dataIndex: 'mineName'
                    },
                    {
                        title: '煤种',
                        align: 'center',
                        dataIndex: 'coalName'
                    },
                    {
                        title: '毛重',
                        align: 'center',
                        dataIndex: 'mzQty'
                    },
                    {
                        title: '皮重',
                        align: 'center',
                        dataIndex: 'pzQty'
                    },
                    {
                        title: '扣吨',
                        align: 'center',
                        dataIndex: 'kdQty'
                    },
                    {
                        title: '净重',
                        align: 'center',
                        dataIndex: 'netQty'
                    },
                    {
                        title: '采样类型',
                        align: 'center',
                        dataIndex: 'sampleTyp',
                        customRender: (text, record, index) => {
                            let re = ''
                            if (text === '1') {
                                re = '离线采样'
                            } else if (text === '0') {
                                re = '在线采样'
                            }
                            return re
                        }
                    },
                    {
                        title: '状态',
                        align: 'center',
                        dataIndex: 'leaveFlg',
                        customRender: (text, record, index) => {
                            let re = ''
                            if (text === '1') {
                                re = '已出厂'
                            } else if (text === '0') {
                                re = '未出厂'
                            }
                            return re
                        }
                    },
                    {
                        title: '采样机（通道）',
                        align: 'center',
                        dataIndex: 'cyjNo',
                        customRender: (text, record, index) => {
                            let re = ''
                            if (text === '1') {
                                re = '# 1'
                            } else if (text === '2') {
                                re = '# 2'
                            } else if (text === '3') {
                                re = '# 3'
                            }
                            return re
                        }
                    },
                    {
                        title: '入厂时间',
                        align: 'center',
                        dataIndex: 'recordDtm'
                    },
                    {
                        title: '称重时间',
                        align: 'center',
                        dataIndex: 'czDtm'
                    },
                    {
                        title: '回皮时间',
                        align: 'center',
                        dataIndex: 'jqDtm'
                    },
                    {
                        title: '操作',
                        dataIndex: 'action',
                        align: 'center',
                        scopedSlots: { customRender: 'action' },
                    }

                ],
                url: {
                    list: '/znrl/carInfo/carInCoalList',
                    delete: '/znrl/biztool/doCall',

                    exportXlsUrl: '/test/jeecgOrderMain/exportXls',
                },
                /*本页面自定义排序方法*/
                isorter: {
                    sort: 'record_dtm',
                    order: 'desc',
                },

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
            }
        },
        methods: {
            moment,
            onChange(dates, dateStrings) {
                this.queryParam.beginTime = dateStrings[0]
                this.queryParam.endTime = dateStrings[1]
            },
            handleAdd: function () {
                this.$refs.modalForm.add()
                this.$refs.modalForm.doActionTag = 'ADD'
                this.$refs.modalForm.title = '汽车来煤明细录入'
                this.$refs.modalForm.disableSubmit = false
            },
            handleEdit: function (record) {
                this.$refs.modalForm.edit(record)
                this.$refs.modalForm.doActionTag = 'MOD'
                this.$refs.modalForm.title = '来煤信息编辑'
                this.$refs.modalForm.disableSubmit = false
            },
            //delete 操作，
            handleDelete: function (id) {
                if (!this.url.delete) {
                    this.$message.error('请设置url.delete属性!')
                    return
                }
                this.entity.jsonString = JSON.stringify(this.entity.jsonString = {
                    recordNo: id,
                    doActionTag: 'DEL',
                    businessTag: 'TRUCK_TRANS_DEL',
                })
                this.entity.pkgName = 'Car_truck_trans_entry'

                //打印数据
                console.log('entity：==============================')
                console.log(this.entity)

                httpAction(this.url.delete, this.entity, 'post').then((res) => {
                    if (res.success) {
                        //删除成功，将选中的行reset为空；
                        this.selectedRowKeys = []
                        this.$message.success(res.message)
                        this.loadData()
                    } else {
                        this.$message.warning(res.message)
                    }
                })
            },
            clickThenCheck(record) {
                return {
                    on: {
                        click: () => {
                            //alert(this.selectedRowKeys);
                            this.onSelectChange(record.recordNo.split(','), [record])
                        }
                    }
                }
            },

            //将选中行的key和行内容赋值并打开子tab页面加载对应数据
            onSelectChange(selectedRowKeys, selectionRows) {
                this.selectedRowKeys = selectedRowKeys
                this.selectionRows = selectionRows
            },
            handleEditKD: function () {
                if (this.selectedRowKeys.length <= 0 || this.selectedRowKeys.length > 1) {
                    alert('请勾选单行记录！')
                    return
                }
                //console.log(this.selectionRows[0].recordNo);
                this.$refs.modalkdForm.add(this.selectionRows[0])
                this.$refs.modalkdForm.title = '扣吨'
                this.$refs.modalkdForm.disableSubmit = false
            },

            changeCYJ: function () {
                if (this.selectedRowKeys.length <= 0 || this.selectedRowKeys.length > 1) {
                    alert('请勾选单行记录！')
                    return
                }
                this.$refs.modalCyjForm.add(this.selectionRows[0])
                this.$refs.modalCyjForm.title = '切换采样机'
                this.$refs.modalCyjForm.disableSubmit = false

            },
            printList: function () {
                if (this.selectedRowKeys.length <= 0 || this.selectedRowKeys.length > 1) {
                    alert('请勾选单行记录！')
                    return
                }
                this.$refs.printTable.style.display = "";

            }
        }
    }
</script>
<style scoped>
  /** Button按钮间距 */
  .ant-btn {
    margin-left: 3px
  }

  .ant-card-body .table-operator {
    margin-bottom: 18px;
  }

  .ant-table-tbody .ant-table-row td {
    padding-top: 15px;
    padding-bottom: 15px;
  }

  .anty-row-operator button {
    margin: 0 5px
  }

  .ant-btn-danger {
    background-color: #ffffff
  }

  .ant-modal-cust-warp {
    height: 100%
  }

  .ant-modal-cust-warp .ant-modal-body {
    height: calc(100% - 110px) !important;
    overflow-y: auto
  }

  .ant-modal-cust-warp .ant-modal-content {
    height: 90% !important;
    overflow-y: hidden
  }
</style>