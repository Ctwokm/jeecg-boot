<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" >
        <a-row :gutter="24">
          <a-col :md="6" :sm="6">
            <a-form-item label="化验编码">
              <j-search-select-tag
                placeholder="请输入化验编码"
                v-model="queryParam.laborCode"
                dict="batch_no_info,labor_code,labor_code"
                :pageSize="6"
                :async="true">
              </j-search-select-tag>
            </a-form-item>
          </a-col>

          <a-col :md="6" :sm="6">
              <a-form-item label="化验批次类型">
                <j-dict-select-tag  v-model="queryParam.batchType" placeholder="请选择" dictCode="gk_hyjgpc"/>
              </a-form-item>
          </a-col>


            <a-col :md="2" :sm="24">
              <a-button type="primary" @click="qryResData">
                查询
              </a-button>
            </a-col>

            <a-col :md="6" :sm="24">
              <a-button type="primary" @click="genLabReport">
                生成化验报告
              </a-button>
            </a-col>

        </a-row>

      </a-form>
    </div>
    <!-- 查询区域-END -->

    <!--&lt;!&ndash; 操作按钮区域 &ndash;&gt;
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('lab_report')">导出</a-button>
      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
        <a-button type="primary" icon="import">导入</a-button>
      </a-upload>
      &lt;!&ndash; 高级查询区域 &ndash;&gt;
      <j-super-query :fieldList="superFieldList" ref="superQueryModal" @handleSuperQuery="handleSuperQuery"></j-super-query>
      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel"><a-icon type="delete"/>删除</a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px"> 批量操作 <a-icon type="down" /></a-button>
      </a-dropdown>
    </div>-->

    <!-- table区域-begin -->
    <div>
      <!--<div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择 <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>项
        <a style="margin-left: 24px" @click="onClearSelected">清空</a>
      </div>-->
      <span style="color: #0099FF"><strong>化验天平</strong></span>
      <a-table
        ref="table"
        size="middle"
        :scroll="{x:true}"
        bordered
        rowKey="id"
        :columns="columnsTP"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        class="j-table-force-nowrap"
        @change="handleTableChange">

       <!-- <template slot="htmlSlot" slot-scope="text">
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
        </span>-->

      </a-table>
      <br>
      <span style="color: #0099FF"><strong>定硫仪</strong></span>
      <a-table
        ref="table"
        size="middle"
        :scroll="{x:true}"
        bordered
        rowKey="id"
        :columns="columnsDL"
        :dataSource="dataSourceDL"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        class="j-table-force-nowrap"
        @change="handleTableChange">

       <!-- <template slot="htmlSlot" slot-scope="text">
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
        </span>-->

      </a-table>
      <br>
      <span style="color: #0099FF"><strong>工业分析仪</strong></span>
      <a-table
        ref="table"
        size="middle"
        :scroll="{x:true}"
        bordered
        rowKey="id"
        :columns="columnsGF"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        class="j-table-force-nowrap"
        @change="handleTableChange">

       <!-- <template slot="htmlSlot" slot-scope="text">
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
-->
      </a-table>
      <br>
      <span style="color: #0099FF"><strong>量热仪</strong></span>
      <a-table
        ref="table"
        size="middle"
        :scroll="{x:true}"
        bordered
        rowKey="id"
        :columns="columnsLR"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        class="j-table-force-nowrap"
        @change="handleTableChange">

       <!-- <template slot="htmlSlot" slot-scope="text">
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
        </span>-->

      </a-table>
      <br>
      <span style="color: #0099FF"><strong>光波分析仪</strong></span>
      <a-table
        ref="table"
        size="middle"
        :scroll="{x:true}"
        bordered
        rowKey="id"
        :columns="columnsGB"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        class="j-table-force-nowrap"
        @change="handleTableChange">

     <!--   <template slot="htmlSlot" slot-scope="text">
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
-->
      </a-table>
      <br>
      <span style="color: #0099FF"><strong>红外测氢仪</strong></span>
      <a-table
        ref="table"
        size="middle"
        :scroll="{x:true}"
        bordered
        rowKey="id"
        :columns="columnsHW"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        class="j-table-force-nowrap"
        @change="handleTableChange">

       <!-- <template slot="htmlSlot" slot-scope="text">
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
        </span>-->

      </a-table>
    </div>

  <!--  <lab-report-modal ref="modalForm" @ok="modalFormOk"></lab-report-modal>-->
  </a-card>
</template>

<script>

  import '@/assets/less/TableExpand.less'
  import { mixinDevice } from '@/utils/mixin'
  import { BusinessListMixin } from '@/mixins/BusinessListMixin'
  import { getAction } from '@api/manage'
  //import LabReportModal from './modules/LabReportModal'

  export default {
    name: 'laboryDataList',
    mixins:[BusinessListMixin, mixinDevice],
    components: {
    //  LabReportModal
    },
    data () {
      return {
        description: '化验结果查询',
        dataSourceTP:[],
        dataSourceDL:[],
        dataSourceGF:[],
        dataSourceLR:[],
        dataSourceGB:[],
        dataSourceHW:[],
        ipagination:{
          current: 1,
          pageSize: 2,
          pageSizeOptions: ['10', '20', '30'],
          showTotal: (total, range) => {
            return range[0] + "-" + range[1] + " 共" + total + "条"
          },
          showQuickJumper: true,
          showSizeChanger: true,
          total: 0
        },
        // 化验天平表头
        columnsTP:[
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
          title:'化验编码',
          align:"center",
          dataIndex: 'laborCode'
        },
        {
          title:'确认状态',
          align:"center",
          dataIndex: 'statusName'
        },
        {
          title:'化验批次类型',
          align:"center",
          dataIndex: 'batchType'
        },
        {
          title:'化验时间',
          align:"center",
          dataIndex: 'opTime'
        },
        {
          title:'化验人',
          align:"center",
          dataIndex: 'opName'
        },
        {
          title:'全水分',
          align:"center",
          dataIndex: 'mt'
        },
        {
          title:'水分',
          align:"center",
          dataIndex: 'mad'
        },
        {
          title:'空干基灰分',
          align:"center",
          dataIndex: 'aad'
        },
        {
          title:'挥发分',
          align:"center",
          dataIndex: 'vad'
        },
        {
          title:'化验设备',
          align:"center",
          dataIndex: 'deviceName'
        },
        {
          title:'煤样瓶重量',
          align:"center",
          dataIndex: 'mypQty'
        },
        {
          title:'样品重量',
          align:"center",
          dataIndex: 'samplingQty'
        },
        {
          title:'总重量',
          align:"center",
          dataIndex: 'totalQty'
        },{
          title:'干燥后总重量',
          align:"center",
          dataIndex: 'afterDryQty'
        },
        {
          title:'检查后干燥重量',
          align:"center",
          dataIndex: 'testQty'
        },
        {
          title:'重量损失',
          align:"center",
          dataIndex: 'qtyLoss'
        },

        /*{
          title: '操作',
          dataIndex: 'action',
          align:"center",
          fixed:"right",
          width:147,
          scopedSlots: { customRender: 'action' }
        }*/
      ],
        // 定硫表头
        columnsDL: [
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
            title:'化验编码',
            align:"center",
            dataIndex: 'laborCode'
          },
          {
            title:'确认状态',
            align:"center",
            dataIndex: 'statusName'
          },
          {
            title:'化验批次类型',
            align:"center",
            dataIndex: 'batchType'
          },
          {
            title:'化验时间',
            align:"center",
            dataIndex: 'opTime'
          },
          {
            title:'化验人',
            align:"center",
            dataIndex: 'opName'
          },
          {
            title:'试样重量',
            align:"center",
            dataIndex: 'samplingWeight'
          },
          {
            title:'空干基全硫',
            align:"center",
            dataIndex: 'stAd'
          },
          {
            title:'总时间',
            align:"center",
            dataIndex: 'totalTime'
          },
          {
            title:'化验设备',
            align:"center",
            dataIndex: 'deviceName'
          },
          /*{
            title: '操作',
            dataIndex: 'action',
            align:"center",
            fixed:"right",
            width:147,
            scopedSlots: { customRender: 'action' }
          }*/
        ],
        //工分表头
        columnsGF: [
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
            title:'化验编码',
            align:"center",
            dataIndex: 'laborCode'
          },
          {
            title:'确认状态',
            align:"center",
            dataIndex: 'statusName'
          },
          {
            title:'化验批次类型',
            align:"center",
            dataIndex: 'batchType'
          },
          {
            title:'化验时间',
            align:"center",
            dataIndex: 'opTime'
          },
          {
            title:'化验人',
            align:"center",
            dataIndex: 'opName'
          },
          {
            title:'空干基水分',
            align:"center",
            dataIndex: 'mad'
          },
          {
            title:'空干基灰分',
            align:"center",
            dataIndex: 'aad'
          },
          {
            title:'空干基挥发分',
            align:"center",
            dataIndex: 'vad'
          },
          {
            title:'水灰空坩埚重',
            align:"center",
            dataIndex: 'shKggWeight'
          },
          {
            title:'水灰煤样重',
            align:"center",
            dataIndex: 'shSamplingWeight'
          },
          {
            title:'挥空坩埚加样重',
            align:"center",
            dataIndex: 'hkTotalWeight'
          },
          {
            title:'挥空煤样重量',
            align:"center",
            dataIndex: 'hkSamplingWeight'
          },
          {
            title:'化验设备',
            align:"center",
            dataIndex: 'deviceName'
          },

          /*{
            title: '操作',
            dataIndex: 'action',
            align:"center",
            fixed:"right",
            width:147,
            scopedSlots: { customRender: 'action' }
          }*/
        ],
        // 量热表头
        columnsLR: [
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
            title:'化验编码',
            align:"center",
            dataIndex: 'laborCode'
          },
          {
            title:'确认状态',
            align:"center",
            dataIndex: 'statusName'
          },
          {
            title:'化验批次类型',
            align:"center",
            dataIndex: 'batchType'
          },
          {
            title:'化验时间',
            align:"center",
            dataIndex: 'opTime'
          },
          {
            title:'化验人',
            align:"center",
            dataIndex: 'opName'
          },
          {
            title:'试样重量',
            align:"center",
            dataIndex: 'samplingWeight'
          },
          {
            title:'校正值',
            align:"center",
            dataIndex: 'jzValue'
          },
          {
            title:'弹筒热',
            align:"center",
            dataIndex: 'qbAd'
          },
          {
            title:'化验设备',
            align:"center",
            dataIndex: 'deviceName'
          },
          /*{
            title: '操作',
            dataIndex: 'action',
            align:"center",
            fixed:"right",
            width:147,
            scopedSlots: { customRender: 'action' }
          }*/
        ],
        // 光波表头
        columnsGB: [
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
            title:'化验编码',
            align:"center",
            dataIndex: 'laborCode'
          },
          {
            title:'确认状态',
            align:"center",
            dataIndex: 'statusName'
          },
          {
            title:'化验批次类型',
            align:"center",
            dataIndex: 'batchType'
          },
          {
            title:'化验时间',
            align:"center",
            dataIndex: 'opTime'
          },
          {
            title:'化验人',
            align:"center",
            dataIndex: 'opName'
          },
          {
            title:'全水分',
            align:"center",
            dataIndex: 'mt'
          },
          {
            title:'试样重量',
            align:"center",
            dataIndex: 'samplingWeight'
          },
          {
            title:'煤样瓶重量',
            align:"center",
            dataIndex: 'mypWeight'
          },
          {
            title:'残重',
            align:"center",
            dataIndex: 'weightLoss'
          },
          {
            title:'化验设备',
            align:"center",
            dataIndex: 'deviceName'
          },
          /*{
            title: '操作',
            dataIndex: 'action',
            align:"center",
            fixed:"right",
            width:147,
            scopedSlots: { customRender: 'action' }
          }*/
        ],
        // 表头
        columnsHW: [
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
            title:'化验编码',
            align:"center",
            dataIndex: 'laborCode'
          },
          {
            title:'确认状态',
            align:"center",
            dataIndex: 'statusName'
          },
          {
            title:'化验批次类型',
            align:"center",
            dataIndex: 'batchType'
          },
          {
            title:'化验时间',
            align:"center",
            dataIndex: 'opTime'
          },
          {
            title:'化验人',
            align:"center",
            dataIndex: 'opName'
          },
          {
            title:'空干基氢',
            align:"center",
            dataIndex: 'had'
          },
          {
            title:'干基氢',
            align:"center",
            dataIndex: 'hd'
          },
          {
            title:'试样重量',
            align:"center",
            dataIndex: 'samplingWeight'
          },
          {
            title:'空干基碳',
            align:"center",
            dataIndex: 'cad'
          },
          {
            title:'空干基碳',
            align:"cd",
            dataIndex: 'totalTime'
          },

          {
            title:'化验设备',
            align:"center",
            dataIndex: 'deviceName'
          },
          /*{
            title: '操作',
            dataIndex: 'action',
            align:"center",
            fixed:"right",
            width:147,
            scopedSlots: { customRender: 'action' }
          }*/
        ],
        url: {
          list: "/modules/labReport/list",
          delete: "/modules/labReport/delete",
          deleteBatch: "/modules/labReport/deleteBatch",
          exportXlsUrl: "/modules/labReport/exportXls",
          importExcelUrl: "modules/labReport/importExcel",
          columnsTPlist: "/business/labory/qryScalesResData",
          columnsDLlist: "/business/labory/qrySResData",
          columnsGFlist: "/business/labory/qryIAResData",
          columnsLRlist: "/business/labory/qryHotResData",
          columnsGBlist: "/business/labory/qryLaResData",
          columnsHWlist: "/business/labory/qryHwResData",
          genLabReportUrl: "/business/labory/submitLabReport",




        },
        dictOptions:{},
        //superFieldList:[],
      }
    },

    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      },
    },

    mounted() {
      //定时调用初始化方法
      /*this.$nextTick(() => {
        setInterval(this.created, 0,1000);
      })*/

    },
    methods: {
      initDictConfig(){
        this.isorter.column = 'createDate'
        this.isorter.order = 'desc'
      },
      //查询
      qryResData(){
        var qryLaborCode = this.queryParam.laborCode;
        var qryBatchType = this.queryParam.batchType;

        if (qryLaborCode == null || qryLaborCode == undefined || qryLaborCode == "") {
          this.$message.warning("请选择要查询的化验编码");
          return false;
        } else {
          var paramObj = new Object();
          paramObj.laborCode = qryLaborCode;
          paramObj.batchType = qryBatchType;
          this.initcolumnsTP(paramObj);
          this.initcolumnsDL(paramObj);
          this.initcolumnsGF(paramObj);
          this.initcolumnsLR(paramObj);
          this.initcolumnsGB(paramObj);
          this.initcolumnsHW(paramObj);

        }

      },

      //生成化验报告
      genLabReport(){
        var qryLaborCode = this.queryParam.laborCode;
        var qryBatchType = this.queryParam.batchType;

        if (qryLaborCode == null || qryLaborCode == undefined || qryLaborCode == "") {
          this.$message.warning("请先选择化验编码进行相应的化验数据查询");
          return false;
        }
        var scalesRows =  this.dataSourceTP;
        var sRows =  this.dataSourceDL;
        var iRows =  this.dataSourceGF;
        var hotRows =  this.dataSourceLR;
        var laRows =  this.dataSourceGB;
        var hwRows =  this.dataSourceHW;

        console.log(scalesRows);
        //校验数据状态 必须为0 未审批状态
        for ( var i = 0; !(i >= scalesRows.length); i++) {
          if (scalesRows[i].status != "0") {
            this.$message.warning("提示", "化验天平有数据状态不满足生成化验报告条件");
            return false;
          }
        }
        //校验数据状态 必须为0 未审批状态
        for ( var i = 0; !(i >= sRows.length); i++) {
          if (sRows[i].status != "0") {
            this.$message.warning("提示", "定硫仪有数据状态不满足生成化验报告条件");
            return false;
          }
        }
        //校验数据状态 必须为0 未审批状态
        for ( var i = 0; !(i >= iRows.length); i++) {
          if (iRows[i].status != "0") {
            this.$message.warning("提示", "工业分析仪有数据状态不满足生成化验报告条件");
            return false;
          }
        }
        //校验数据状态 必须为0 未审批状态
        for ( var i = 0; !(i >= hotRows.length); i++) {
          if (hotRows[i].status != "0") {
            this.$message.warning("提示", "量热仪有数据状态不满足生成化验报告条件");
            return false;
          }
        }
        //校验数据状态 必须为0 未审批状态
        for ( var i = 0; !(i >= laRows.length); i++) {
          if (laRows[i].status != "0") {
            this.$message.warning("提示", "光波分析仪有数据状态不满足生成化验报告条件");
            return false;
          }
        }
        //校验数据状态 必须为0 未审批状态
        for ( var i = 0; !(i >= hwRows.length); i++) {
          if (hwRows[i].status != "0") {
            this.$message.warning("提示", "红外测氢仪有数据状态不满足生成化验报告条件");
            return false;
          }
        }


        var paramObj = new Object();
        paramObj.laborCode = qryLaborCode;
        paramObj.batchType = qryBatchType;
        paramObj.apprEventTypeCd = '5';//审批事件类型，5生成化验报告
        getAction(this.url.genLabReportUrl, paramObj).then((res) => {
          console.log(res);
          if (res.code==0){
            this.$message.success( '化验报告生成完成...' );
          }
          else {
            this.$message.error("报告生成异常"+res.message);
          }
        })

      },
      initcolumnsTP(paramObj){
        console.log(paramObj);
        console.log(this.url.columnsTPlist);
        getAction(this.url.columnsTPlist, paramObj).then((res) => {
          console.log(res);
          if (res.code==0){
            this.$message.success( '正在查询已确认的化验数据...' );
            this.dataSourceTP = res.result;
          }
          else {
            this.$message.error("抱歉未找到化验数据");
          }

        })
      },
      initcolumnsDL(paramObj){
        getAction(this.url.columnsDLlist, paramObj).then((res) => {
          console.log(res);
          if (res.code==0){
            this.$message.success( '正在查询已确认的化验数据...' );
            this.dataSourceDL = res.result;
          }
          else {
            this.$message.error("抱歉未找到化验数据");
          }
        })
      },
      initcolumnsGF(paramObj){
        getAction(this.url.columnsGFlist, paramObj).then((res) => {
          console.log(res);
          if (res.code==0){
            this.$message.success( '正在查询已确认的化验数据...' );
            this.dataSourceGF = res.result;
          }
          else {
            this.$message.error("抱歉未找到化验数据");
          }
        })
      },
      initcolumnsLR(paramObj){
        getAction(this.url.columnsLRlist, paramObj).then((res) => {
          console.log(res);
          if (res.code==0){
            this.$message.success( '正在查询已确认的化验数据...' );
            this.dataSourceLR = res.result;
          }
          else {
            this.$message.error("抱歉未找到化验数据");
          }
        })
      },
      initcolumnsGB(paramObj){
        getAction(this.url.columnsGBlist, paramObj).then((res) => {
          console.log(res);
          if (res.code==0){
            this.$message.success( '正在查询已确认的化验数据...' );
            this.dataSourceGB = res.result;
          }
          else {
            this.$message.error("抱歉未找到化验数据");
          }
        })
      },
      initcolumnsHW(paramObj){
        getAction(this.url.columnsHWlist, paramObj).then((res) => {
          console.log(res);
          if (res.code==0){
            this.$message.success( '正在查询已确认的化验数据...' );
            this.dataSourceHW = res.result;
          }
          else {
            this.$message.error("抱歉未找到化验数据");
          }
        })
      },

    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>