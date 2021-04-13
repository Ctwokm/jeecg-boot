<template>
  <a-spin :spinning="confirmLoading">

    <div class="no-print" style="text-align: right">
      <a-button id="printClick" v-show="false" v-print="'#printContent'" ghost type="primary">打印</a-button>
    </div>
    <section ref="print" id="printContent" style="text-align: center">
      <barcode :value="codeValue" ></barcode>
    </section>

  </a-spin>
</template>

<script>

  import VueBarcode from '@xkeshi/vue-barcode'

  export default {
    name: 'BarcodeSpin',
    components: {
      barcode: VueBarcode
    },
    props: {

    },
    data () {
      return {
        codeValue:'',
        confirmLoading: false,
      }
    },
    computed: {

    },
    created () {

    },
    methods: {

      print(record,type) {
        if(type == 'sampling'){
          this.codeValue = record.samplingCode;
        }else if(type == 'sample'){
          this.codeValue = record.sampleCode;
        }else if(type == 'labor'){
          this.codeValue = record.laborCode;
        }
      },
      okPrint(){
        console.log("ok---print");
        document.getElementById("printClick").click();
      }


    }
  }
</script>

