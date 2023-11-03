<template>
  <div>
    <el-card style="width: 60%">
      <el-form :model="user" label-width="90px" style="padding-right: 20px;">
        <div style="margin: 15px;text-align: center">
          <el-upload
              class="avatar-uploader"
              action="http://localhost:9090/file/upload"
              :headers="{token: user.token}"
              :show-file-list="false"
              :on-success="handleAvatarSuccess"
              >
            <img v-if="user.avatar" :src="user.avatar" class="avatar" />
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </div>
        <el-form-item label="User name" prop="username">
          <el-input v-model="user.username" placeholder="user name" disabled></el-input>
        </el-form-item>
        <el-form-item label="Name" prop="name">
          <el-input v-model="user.name" placeholder="name"></el-input>
        </el-form-item>
        <el-form-item label="Phone" prop="phone">
          <el-input v-model="user.phone" placeholder="phone number"></el-input>
        </el-form-item>
        <el-form-item label="Email" prop="email">
          <el-input v-model="user.email" placeholder="email"></el-input>
        </el-form-item>
        <el-form-item label="Address" prop="address">
          <el-input type="textarea" v-model="user.address" placeholder="address"></el-input>
        </el-form-item>
        <div style="text-align: center">
          <el-button type="primary" @click="update">save</el-button>
        </div>
      </el-form>
    </el-card>
  </div>
</template>

<script>
export default {
  name: "UserProfile",
  data(){
    return{
      user:JSON.parse(localStorage.getItem('hcs-user') || '{}')
    }
  },
  created(){

  },
  methods:{
    update(){
      //Save the current user information to the database
      this.$request.put('/user/update', this.user).then(res => {
        if(res.code === '200'){
          this.$message.success('Update success')
          //Update the user information in the browser cache
          localStorage.setItem('hcs-user', JSON.stringify(this.user))
          //trigger parent data update
          this.$emit('update:user',this.user)
        }else{
          this.$message.error(res.msg)
        }
      })

    },
    handleAvatarSuccess(response,file,fileList){
      //Replace the user's avatar attribute with the link to the uploaded image
      this.user.avatar = response.data
    },

  }
}
</script>



<style scoped>
/deep/.el-form-item__label{
  font-weight: bold;
}
/deep/.el-upload{
  border-radius: 50%;
}
/deep/.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 50%;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
/deep/.avatar-uploader .el-upload:hover {
  border-color: #B3282D;
}
/deep/.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
  border-radius: 50%;
}
/deep/.avatar {
  width: 178px;
  height: 178px;
  display: block;
  border-radius: 50%;
}
</style>