<template>
  <a-card :bordered="false">
    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择 <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>项
        <a style="margin-left: 24px" @click="onClearSelected">清空</a>
      </div>

      <a-table
        ref="table"
        size="middle"
        bordered
        rowKey="lockRecId"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        @change="handleTableChange"
        :customRow="clickThenCheck">

        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)">编辑</a>
          <a-divider type="vertical"/>
          <a-dropdown>
            <a class="ant-dropdown-link">
              更多 <a-icon type="down"/>
            </a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a href="javascript:;" @click="handleDetail(record)">详情</a>
              </a-menu-item>
              <a-menu-item>
                <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.lockRecId)">
                  <a>删除</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>
      </a-table>
    </div>
    <!-- table区域-end -->
    <!-- 表单区域 -->
  </a-card>
</template>

<script>
    import {JeecgListMixin} from '@/mixins/JeecgListMixin'
    import {getAction} from '@/api/manage'

    export default {
        name: "CarLockDetailList",
        mixins: [JeecgListMixin],
        components: {

        },
        data() {
            return {
                lockRecId: '',
                description: '车辆锁车记录',
                jsonString:{},
                isorter:{
                    column: 'insert_time',
                    order: 'desc',
                },
                // 表头
                columns: [
                    {
                        title: '流水号',
                        align: "center",
                        dataIndex: 'lockRecId',
                    },
                    {
                        title: '车牌号',
                        align: "center",
                        dataIndex: 'carId',
                    },
                    {
                        title: '锁车状态',
                        align: "center",
                        dataIndex: 'status',
                    },
                    {
                        title: '锁车生效时间',
                        dataIndex: 'effTime',
                        align: "center",
                    },
                    {
                        title: '锁车失效时间',
                        dataIndex: 'expTime',
                        align: "center",
                    },
                    {
                        title: '锁车人',
                        dataIndex: 'opCode',
                        align: "center",
                    },
                    {
                        title: '解锁人',
                        dataIndex: 'updateCode',
                        align: "center",
                    },
                    {
                        title: '锁车原因',
                        dataIndex: 'reason',
                        align: "center",
                    },
                    {
                        title: '备注',
                        dataIndex: 'remark',
                        align: "center",
                    }
                ],
                url: {
                    //list: "/rapid/shipCargoRecord/list",
                    //delete: "/rapid/shipCargoRecord/delete",
                    list: "/znrl/biztool/query",
                    delete: "business/ship/deleteCargoRecord",
                }
            }
        },
        methods: {
            getLinkedData(id) {
                // alert(this.carId);
                this.jsonString.carId = id;
                /*调用通用查询语句使用的参数，sql语句的id属性，赋值后可直接调用后台mapper.xml中的sql*/
                this.queryParam.sqlId = "qryLockCarList";
                /*调用通用查询语句使用的参数，分页属性参数，"0"为分页，其他为不分页*/
                this.queryParam.isPage = "Y";
                /*调用通用查询语句使用的业务参数*/
                this.queryParam.jsonString = this.jsonString;

                var params = this.getQueryParams();//查询条件
                this.loading = true;
                getAction(this.url.list,params).then((res) => {
                    if (res.success) {
                        this.dataSource = res.result.records;
                        this.ipagination.total = res.result.total;
                    } else {
                        this.dataSource = null;
                    }
                })
                this.loading = false;
            },
            //当鼠标点击行时触发
            clickThenCheck(record) {
                return {
                    on: {
                        click: () => {
                            this.onSelectChange([record.lockRecId], [record]);
                        }
                    }
                };
            },
            //将选中行的key和行内容赋值并打开子tab页面加载对应数据
            onSelectChange(selectedRowKeys, selectionRows) {
                this.selectedRowKeys = selectedRowKeys;
                this.selectionRows = selectionRows;
            },

        }
    }
</script>
<style scoped>
  .ant-card {
    margin-left: -30px;
    margin-right: -30px;
  }
</style>