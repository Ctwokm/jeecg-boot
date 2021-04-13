<template>
  <j-modal
    :title="title"
    :width="width"
    :visible="visible"
    switchFullscreen
    @cancel="handleCancel"
    @ok="handleOk"
    cancelText="关闭"
    okText="打印"
    >
    <barcode-spin ref="realForm" @ok="submitCallback" ></barcode-spin>
  </j-modal>
</template>

<script>

  import BarcodeSpin from './BarcodeSpin'
  export default {
    name: 'BarcodeModal',
    components: {
      BarcodeSpin
    },
    data () {
      return {
        title:'',
        width:500,
        visible: false,
      }
    },
    methods: {
      print (record,type) {
        this.visible=true
        this.$nextTick(()=>{
          this.$refs.realForm.print(record,type);
        })
      },
      handleOk () {
        console.log("handleOk");

        this.$refs.realForm.okPrint();
      },
      submitCallback(){
        console.log("submitCallback");
        // this.$emit('ok');
        // this.visible = false;
      },
      close () {
        this.$emit('close');
        this.visible = false;
      },
      handleCancel () {
        this.close()
      }
    }
  }
</script>