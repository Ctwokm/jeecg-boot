<template>
  <j-modal
    :title="title"
    :width="width"
    :visible="visible"
    switchFullscreen
    @ok="handleOk"
    :okButtonProps="{ class:{'jee-hidden': disableSubmit} }"
    @cancel="handleCancel"
    cancelText="关闭">
    <device-error-form ref="realForm" @ok="submitCallback" :disabled="disableSubmit"></device-error-form>
  </j-modal>
</template>

<script>

  import DeviceErrorForm from './DeviceErrorForm'
  export default {
    name: 'DeviceErrorModal',
    components: {
      DeviceErrorForm
    },
    data () {
      return {
        title:'',
        width:900,
        visible: false,
        disableSubmit: false
      }
    },
    methods: {
      edit (record) {
        this.visible=true
        this.$nextTick(()=>{
          this.$refs.realForm.edit(record);
        })
      },
      close () {
        this.$emit('close');
        this.visible = false;
      },
      handleOk () {
        this.$refs.realForm.submitForm();
      },
      submitCallback(){
        this.$emit('ok');
        this.visible = false;
      },
      handleCancel () {
        this.close()
      }
    }
  }
</script>