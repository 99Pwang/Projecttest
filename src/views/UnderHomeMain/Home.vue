<template>
  <div>
    <div style="box-shadow: 0 0 10px rgba(0,0,0,.1); padding: 10px 20px; border-radius: 5px; margin-bottom: 10px">
      Welcome to AlgomaU HR Candidate Selection System :D
    </div>
    <!-- test left-->
    <div style="display:flex;">
      <el-card style="width: 100%;">
        <div slot="header" class="clearfix">
          <span>Good morning, {{ user.name }}</span>
        </div>
        <div>
          This is the main part.
          <div style="margin-top: 20px">
            <div style="margin: 10px 0"><strong>Color</strong></div>
            <el-button type="primary">button</el-button>
            <el-button type="success">button</el-button>
            <el-button type="warning">button</el-button>
            <el-button type="danger">button</el-button>
            <el-button type="info">button</el-button>
          </div>
        </div>
      </el-card>

    </div>

  </div>
</template>

<script>
export default {
  name: "Home",
  data(){
    return{
      user: JSON.parse(localStorage.getItem('hcs-user') || '{}'),
    }
  },
  created(){

  },
  methods:{}
}
</script>


<style scoped>

</style>