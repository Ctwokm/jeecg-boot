<template>
  <a-modal
    :title="title"
    :width="1200"
    :visible="visible"
    :closable="false"
    :confirmLoading="confirmLoading">
    <!--自定义底部按钮-->
    <template slot="footer">
      <a-button @click="handleCancel">
        关闭
      </a-button>
    </template>

    <div id="alert_div"><!--不要随意修改以下信息的排版，会直接影响页面展示效果-->
      <a-alert :show-icon="false" type="warning" message="“结束”说明："
               description="该功能主要用于如下2种情况，除以下两种情况外，随意使用该功能，会导致批次数据紊乱而影响采制流程：
1.一个批次卸煤吨位已经达到批次设定值，但是因为在停采样机或卸样或启制样机环节出现故障而无法自然结束该批次时候（从管控系统启动制样机，则该批次结束），
   用“结束”功能结束该批次以便下一个批次可以继续采样。
2.一船煤的最后一个批次转为人工控制情况下，卸煤结束，需要人工结束最后一个批次时，使用“结束”功能。"/>
      <br/>
    </div>

    <!--主要内容-->
    <a-spin :spinning="confirmLoading">
      <a-table
        ref="table"
        size="middle"
        bordered
        rowKey="batchNo"
        :columns="columns"
        :dataSource="dataSource"
        :loading="loading"
        :pagination="false"
        >

        <span slot="action" slot-scope="text, record">
          <a @click="handleCompleteBatch(record,'0')" v-if="record.isValid == '1'">开启</a>
          <a @click="handleCompleteBatch(record,'1')" v-else>结束</a>
        </span>

      </a-table>
    </a-spin>
  </a-modal>
</template>

<script>
  import {JeecgListMixin} from '@/mixins/JeecgListMixin'
  import {getAction} from '@/api/manage'
  import {putAction} from '@/api/manage'

  export default {
    name: "ShipBatchModal",
    mixins: [JeecgListMixin],
    components: {},
    data() {
      return {
        title: "分批详情",
        visible: false,
        shipRecId: "",
        confirmLoading: false,
        disableMixinCreated: true,
        // 表头
        columns: [
          {
            title: '序号',
            dataIndex: 'dayBatchNum',
            align: "center",
          },
          {
            title: '批次号',
            dataIndex: 'batchNo',
            align: "center",
          },
          // {
          //   title: '船运编号',
          //   dataIndex: 'shipTransNo',
          //   align: "center",
          // },
          {
            title: '矿点',
            dataIndex: 'mineName',
            align: "center",
          },
          {
            title: '煤种',
            dataIndex: 'coalName',
            align: "center",
          },
          {
            title: '本批吨位',
            dataIndex: 'thisBatchQty',
            align: "center",
          },
          {
            title: '采样编码',
            dataIndex: 'sampleCode',
            align: "center",
          },
          {
            title: '制样编码',
            dataIndex: 'samplingCode',
            align: "center",
          },
          // {
          //   title: '化验编码',
          //   dataIndex: 'laborCode',
          //   align: "center",
          // },
          {
            title: '分批时间',
            dataIndex: 'batchTime',
            align: "center",
          },
          {
            title: '状态',
            dataIndex: 'isValid_dictText',
            align: "center",
          },
          {
            title: '操作',
            dataIndex: '',
            align: 'center',
            width: 130,
            key: 'operation',
            scopedSlots: {customRender: 'action'},
          },
        ],
        url: {
          list: "business/ship/shipBatchInfo",
          completeBatch : "business/ship/forceCompleteBatch",
        }
      }
    },
    methods: {
      loadData() {
        this.loading = true;
        getAction(this.url.list, {shipRecId: this.shipRecId}).then((res) => {
          if (res.success) {
            this.dataSource = res.result.records;
          } else {
            this.dataSource = null;
          }
          this.loading = false;
        })
      },
      close() {
        this.visible = false;
      },
      handleCancel() {
        this.close();
      },
      detail(record){
        this.shipRecId = record.shipRecId;
        this.visible = true;
        this.loadData();
      },
      handleCompleteBatch(record,isValid) {
        let action,msg;
        if(isValid == '0'){
          action = '开启';
          msg = '\'未完成\'';
        }else{
          action = '结束';
          msg = '\'已完成\'';
        }
        let that = this;
        this.$confirm({
          title: '确认',
          content: '确认要' + action + '该批次吗 ?',
          onOk() {
            putAction(that.url.completeBatch, {sampleCode:record.sampleCode, isValid}).then(res=>{
              if(res.success){
                that.$message.success("本批次已经人工置成" + msg)
                that.loadData();
              }else{
                that.$message.error(res.message)
              }
            })
          },
          onCancel() {
          },
        });
      },
    }
  }
</script>
<style>
  #alert_div .ant-alert span{
    white-space:pre-wrap;
  }
</style>