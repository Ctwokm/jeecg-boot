<template>
  <div class="main">
    <div :class="{dialog:propsItem.rgba}" v-if="propsItem.isMask">
      <div class="content" v-bind:style="propsItem.widthHeight">
        <span class="close big" @click="close">X</span>
        <h1 class="title">{{propsItem.title}}</h1>
        <hr class="hr1">
        <div class="mainContent">
          {{propsItem.contents}}
          <slot></slot>
        </div>
        <hr class="hr1">
        <input type="button" class="ok" :value="propsItem.ok" @click="okCallback">
        <input type="button" class="cancel" :value="propsItem.cancel" @click="cancelCallback">
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name:'LabCodeDialog',
  props:{
    //   接受父组件传的值
    propsItem:{
      type:Object
    }
  },
  data(){
    return {

    }
  },
  methods:{
    close(){
      this.propsItem.isMask = false
    },
    okCallback(){
      this.$emit('child-ok')
      this.propsItem.isMask = false
    },
    cancelCallback(){
      this.$emit('child-cancel')
      this.propsItem.isMask = false
    }
  }
}
</script>

<style lang="less" scoped>
@position:absolute;
.alignCtener(@width,@height,@bgcolor){
  position:@position;
  top:50%;
  left:50%;
  transform:translate(-50%,-50%);
  background-color: @bgcolor;
  width: @width;
  height: @height;
  border-radius: 4%;
}
.hr1{
  position: relative;
  width: 100%;
  border:1px solid #cccccc;
}
.mainContent{
  width: 100%;
  height: 65%;
  overflow-y: scroll;
  margin-bottom: 5%;
}
.dialog{
  background-color: rgba(0,0,0,0.8);
  position:absolute;
  top: 0;
  left:0;
  width: 100%;
  height: 100%;
}
.close.big {
  -webkit-transform: scale(2);
  -moz-transform: scale(2);
  -ms-transform: scale(2);
  -o-transform: scale(2);
  transform: scale(2);
}
.close{
  position: absolute;
  display: inline-block;
  overflow: hidden;
  right: 20px;
  top:20px;
  color:#000;
}
.content{
  .alignCtener(500px,500px,#fff);
  padding: 30px;
  z-index: 999;
  text-align: left;
  box-shadow:5px 5px 13px #ccc;
}
.cancel,.ok{
  position: absolute;
  bottom: 10px;
  right: 10px;
}
.ok{
  right:100px;
}
</style>


