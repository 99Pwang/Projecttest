<template>
  <div>
    <div>
      <el-input style="width: 200px;" placeholder="search username" v-model="username"></el-input>
      <el-input style="width: 200px;margin: 0 5px" placeholder="search name" v-model="name"></el-input>
      <el-button type="primary" @click="load(1)">Search</el-button>
      <el-button type="info" @click="reset">Reset</el-button>
    </div>
    <div style="margin: 10px 0">
      <el-button type="success" @click="handleAdd">Add</el-button>
      <el-button type="primary" >Delete</el-button>
    </div>
    <el-table :data="tableData" stripe :header-cell-style="{backgroundColor:'#F6F8FB',color:'#666'}">
      <el-table-column type="selection" width="55" align="center"></el-table-column>
      <el-table-column prop="id" label="ID" width="55"></el-table-column>
      <el-table-column prop="username" label="Username"></el-table-column>
      <el-table-column prop="name" label="Name"></el-table-column>
      <el-table-column prop="phone" label="Phone"></el-table-column>
      <el-table-column prop="email" label="Email"></el-table-column>
      <el-table-column prop="address" label="Address"></el-table-column>
      <el-table-column label="Avatar">
        <template v-slot="scope">
          <div style="display: flex;align-items: center">
            <el-image style="width: 70px;height:70px;border-radius: 50%" v-if="scope.row.avatar" :src="scope.row.avatar" :preview-src-list="[scope.row.avatar]"></el-image>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="Control" align="center" width="150">
        <template v-slot="scope">
          <el-button size="mini" type="success" plain @click="handleEdit(scope.row)">Edit</el-button>
          <el-button size="mini" type="primary" plain>Delete</el-button>
        </template>
      </el-table-column>
    </el-table>

    <div style="margin: 10px 0">
      <el-pagination
          background
          @current-change="handleCurrentChange"
          :current-page="pageNum"
          :page-sizes="[100, 200, 300, 400]"
          :page-size="pageSize"
          layout="total, prev, pager, next, jumper"
          :total="total">
      </el-pagination>
    </div>

    <el-dialog title="收货地址" :visible.sync="userTableVisible" width="30%">
      <el-form :model="form" label-width="80px" style="padding-right: 20px" :rules="rules" ref="formRef">

        <el-form-item label="Username" prop="username">
          <el-input v-model="form.username" placeholder="username" ></el-input>
        </el-form-item>
        <el-form-item label="Name" prop="name">
          <el-input v-model="form.name" placeholder="name"></el-input>
        </el-form-item>
        <el-form-item label="Phone" prop="phone">
          <el-input v-model="form.phone" placeholder="phone"></el-input>
        </el-form-item>
        <el-form-item label="Email" prop="email">
          <el-input v-model="form.email" placeholder="email"></el-input>
        </el-form-item>
        <el-form-item label="Address" prop="address">
          <el-input type="textarea" v-model="form.address" placeholder="address"></el-input>
        </el-form-item>
        <el-form-item label="Avatar">
          <el-upload
              class="avatar-uploader"
              action="http://localhost:9090/file/upload"
              :headers="{ token: user.token }"
              :show-file-list="false"
              :on-success="handleAvatarSuccess"
          >
            <el-button type="primary">Upload</el-button>
          </el-upload>
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="userTableVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>


  </div>
</template>

<script>
export default {
  name: "User",
  data(){
    return {
      tableData:[],
      pageNum:1,
      pageSize:5,
      username:'',
      name:'',
      total: 0,
      userTableVisible: false,
      form:{},
      user:JSON.parse(localStorage.getItem('hcs-user')|| '{}'),
      rules:{
        username:[
          {required:true,message:'Please enter username',trigger:'blur'}
        ]
      }
    }
  },
  created(){
    this.load()
  },
  methods:{
    handleEdit(row){
      this.form = JSON.parse(JSON.stringify(row)) //give value to form, deep copy
      this.userTableVisible = true //open dialog
    },
    handleAdd(){
      this.form = {} //clear data
      this.userTableVisible = true //open dialog
    },
    save(){ //save:add or edit ;by id
      this.$refs.formRef.validate((valid) =>{
        if(valid){
          this.$request({
            url:this.form.id ? '/user/update':'/user/add',
            method:this.form.id ? 'PUT' : 'POST',
            data:this.form
          }).then(res => {
            if(res.code === '200'){ //success and save
              this.$message.success('save success')
              this.load(1)
              this.userTableVisible = false
            }else{
              this.$message.error(res.msg)
            }
          })
        }
      })
    },
    reset(){
      this.name = ''
      this.username = ''
      this.load()
    },
    load(pageNum){ //search by page
      if(pageNum){
        this.pageNum = pageNum
      }
      this.$request.get('/user/selectByPage',{
        params:{
          pageNum:this.pageNum,
          pageSize:this.pageSize,
          username:this.username,
          name:this.name
        }
      }).then(res => {
        this.tableData = res.data.records
        this.total = res.data.total
      })
    },
    handleCurrentChange(pageNum){
      this.load(pageNum)
    },
    handleAvatarSuccess(response,file,fileList){
      //Replace the user's avatar attribute with the link to the uploaded image
      this.form.avatar = response.data
    },
  }
}
</script>

<style scoped>

</style>