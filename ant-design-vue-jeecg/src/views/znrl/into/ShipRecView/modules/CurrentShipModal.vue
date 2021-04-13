<template>
  <a-modal
    :title="title"
    :width="1200"
    :visible="visible"
    :closable="false"
    :confirmLoading="confirmLoading"
    @ok="handleOk"
    @cancel="handleCancel">

    <!--主要内容-->
    <a-spin :spinning="confirmLoading">
      <a-table
        ref="table"
        size="middle"
        bordered
        rowKey="shipRecId"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{selectedRowKeys:selectedRowKeys, onChange:onSelectChange, type:'radio'}"
        :customRow="clickThenSelect"
      >
      </a-table>
    </a-spin>
  </a-modal>
</template>

<script>
  import {JeecgListMixin} from '@/mixins/JeecgListMixin'
  import {getAction} from '@/api/manage'
  import {putAction} from '@/api/manage'

  export default {
    name: "CurrentShipModal",
    mixins: [JeecgListMixin],
    components: {},
    data() {
      return {
        title: "设置当前卸煤船",
        visible: false,
        confirmLoading: false,
        disableMixinCreated: true,
        /* 分页参数 */
        ipagination:{
          current: 1,
          pageSize: 5,
          pageSizeOptions: ['5', '10', '20'],
          showTotal: (total, range) => {
            return range[0] + "-" + range[1] + " 共" + total + "条"
          },
          showQuickJumper: true,
          showSizeChanger: false,
          total: 0
        },
        // 表头
        columns: [
          {
            title: 'ID',
            dataIndex: 'shipRecId',
            align: "center",
          },
          {
            title: '运输编号',
            dataIndex: 'shipTransNo',
            align: "center",
          },
          {
            title: '船名',
            dataIndex: 'shipName',
            align: "center",
          },
          {
            title: '船号',
            dataIndex: 'shipCode',
            align: "center",
          },
          {
            title: '到港时间',
            dataIndex: 'factTime',
            align: "center",
          },
        ],
        url: {
          list: "business/ship/recordByPage",
          setCurrentShip : "business/ship/setCurrentShip",
        }
      }
    },
    methods: {
      prepare(){
        this.visible = true;
        this.loadData();
      },
      loadData() {
        this.loading = true;
        getAction(this.url.list, {}).then((res) => {
          if (res.success) {
            this.dataSource = res.result.records;
          } else {
            this.dataSource = null;
          }
          this.loading = false;
        })
      },
      handleOk() {
        if(this.selectedRowKeys.length != 1){
          this.$message.warning("请选择一个船运信息");
          return;
        }
        let that = this;
        this.$confirm({
          title: '确认',
          content: '确认要设置该船为当前卸煤船么 ?',
          onOk() {
            var shipRecId = that.selectedRowKeys[0];
            putAction(that.url.setCurrentShip, {shipRecId}).then(res=>{
              if(res.success){
                that.$message.success("设置成功！")
                that.loadData();
              }else{
                that.$message.error(res.message)
              }
              that.close();
              that.$emit("ok");//触发父组件中绑定的ok函数，刷新父组件列表
            })
          },
          onCancel() {
          },
        });
      },
      handleCancel() {
        this.close();
      },
      close() {
        this.visible = false;
      },
      //当鼠标点击行时触发
      clickThenSelect(record) {
        return {
          on: {
            click: () => {
              this.onSelectChange([record.shipRecId], [record]);
            }
          }
        };
      },
    }
  }
</script>
