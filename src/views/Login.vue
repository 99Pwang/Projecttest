<template>
  <div style="height: 100vh; overflow: hidden; display: flex;align-items: center;justify-content: center;background-color:#f2f5de;">
<!--background video-->
    <div id="video-background">
      <video autoplay muted loop>
        <source src="../assets/video.mp4" type="video/mp4">

      </video>
    </div>

    <!--cards-->
    <div class="cards-container">
    <div class="shell">
      <div class="card">
        <div class="wrapper">
          <img :src="coverImages[currentCoverImageIndex]" alt="" class="cover-image">
        </div>
        <img :src="titleImages[currentTitleImageIndex]" alt="" class="title">
        <img :src="characterImages[currentCharacterImageIndex]" alt="" class="character">
      </div>
    </div>

    </div>

    <!--Login box-->
    <div style="display: flex;background-color:#fff;height: 505px;width: 614px;box-shadow:rgb(0, 0, 0) 0px 0px 3px 0px inset;
    padding-right: 25px;box-sizing: border-box;border-radius: 20px ;overflow: hidden">
<!--left part-->
    <div style="flex: 0.7;background-color:#B3282D;">
      <img src="../assets/AlgomaU_Logo2.jpg" style="width: 100%;">

    </div>
<!--right part-->
      <div style="flex: 1;padding-top: 20px;padding-bottom: 20px;padding-left: 25px">
        <el-form :model="user" :rules="rules" ref="loginRef">
          <div style="font-size: 20px;font-weight: bold;text-align: center;margin-bottom: 25px">
            <a class="logo" href="https://algomau.ca/" target="_blank"><img src="../assets/logo-primary.svg" alt=""></a>
          </div>
          <el-form-item prop="username">
            <el-input  prefix-icon="el-icon-user" placeholder="username" v-model="user.username"></el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input prefix-icon="el-icon-lock" show-password placeholder="password" v-model="user.password"></el-input>
          </el-form-item>
          <el-form-item prop="code">
            <div style="display: flex">
              <el-input placeholder="verification code" prefix-icon="el-icon-circle-check" style="flex: 1" v-model="user.code"></el-input>
              <div style="flex: 1;height: 44px">
                <valid-code @update:value="getCode"></valid-code>
              </div>
            </div>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" style="width: 100%" @click="login">Login</el-button>
          </el-form-item>
          <div style="display: flex">
<!--            <div>Don't have an account yet?  <span style="cursor: pointer;color: #B3282D">Sign Up</span></div>-->
            <div><a style="color:#B3282D;cursor: pointer " href="https://students.algomau.ca/login/forgot" target="_blank">Forgot Password?</a></div>
          </div>
        </el-form>
      </div>

    </div>


  </div>

</template>

<script>
import ValidCode from "@/components/ValidCode.vue";

export default {
  name: 'Login',
  components: {
    ValidCode
  },
  data() {
    //Validation of verification code
    const validateCode = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('Please enter the verification code'))
        //If the user's input verification code does not match the transmitted code
      } else if(value.toLowerCase() !== this.code){
        callback(new Error('Incorrect verification code'))
      }else{
        callback()
      }
    };

    return {
      code:'',     //code passed from validcode component
      user:{
        code:'',   //code inputed by user
        username:'',
        password:''
      },
      rules: {
        username: [
          {required: true, message: 'Please enter your account', trigger: 'blur'},
        ],
        password: [
          {required: true, message: 'Please enter your password', trigger: 'blur'},
        ],
        code: [
          { validator: validateCode, trigger: 'blur' }
        ],
      },
      currentCoverImageIndex: 0,
      currentTitleImageIndex: 0,
      currentCharacterImageIndex: 0,
      coverImages: [
        require('../assets/01.png'),
        require('../assets/03.png'),
        require('../assets/05.png'),
      ],
      titleImages: [
        require('../assets/text-1.png'),
        require('../assets/text-2.png'),
        require('../assets/text-3.png'),
      ],
      characterImages: [
        require('../assets/02.png'),
        require('../assets/04.png'),
        require('../assets/06.png'),
      ],
    }
},
  created() {
  },
  mounted(){
    setInterval(() => {
      this.currentCoverImageIndex = (this.currentCoverImageIndex + 1) % this.coverImages.length;
      this.currentTitleImageIndex = (this.currentTitleImageIndex + 1) % this.titleImages.length;
      this.currentCharacterImageIndex = (this.currentCharacterImageIndex + 1) % this.characterImages.length;
    }, 7000);
  },
  methods: {
    getCode(code){
      this.code = code.toLowerCase()
    },
    login(){
      this.$refs['loginRef'].validate((valid) => {
        if (valid) {
          this.$request.post('/login',this.user).then(res => {
            if(res.code === '200'){
              this.$router.push('/')
              this.$message.success('login success')
              localStorage.setItem('hcs-user',JSON.stringify(res.data)) //Store user data
            }else{
              this.$message.error(res.msg)
            }
          })

        }
      })

    }
  }
}

</script>

<style scoped>
div[style*="height: 100vh;"] {
  z-index: 0;
  background-color: transparent !important;

}


#video-background {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  z-index: -1;
  overflow: hidden;
}

#video-background video {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  min-width: 100%;
  min-height: 100%;
  width: auto;
  height: auto;
  filter: brightness(67%) contrast(80%);


}

.el-input{

  outline: none;
  border: none;
  box-shadow: inset 0px 0px 2px rgb(207, 206, 206);
  box-shadow: 0px 0px 1px rgb(207, 206, 206);
  border-radius: 0.1cm;
}
.el-input__inner{
  outline: none;
  border: none;
}

.cards-container {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;

}


.card {

  height: 504px;
  width: 250px;
  position: relative;
  display: flex;
  justify-content: center;
  align-items: flex-end;
  padding: 0 25px;
  perspective: 2500px;
  transition: all 0.4s;
  margin: 0 10px;
}

.cover-image {
  width: 100%;
  height: 100%;
}

.wrapper {
  transition: all 0.3s;
  position: absolute;
  width: 100%;
  z-index: -1;
}

.card:hover .wrapper {
  transform: perspective(900px) translateY(-5%) rotateX(25deg) translateZ(0);
  box-shadow: 2px 35px 32px -8px rgba(0, 0, 0, 0.75);
}

.wrapper::before,
.wrapper::after {
  content: "";
  opacity: 0;
  width: 100%;
  height: 80px;
  transition: all 0.3s;
  position: absolute;
  left: 0;
}

.wrapper::before {
  top: 0;
  height: 100%;
  background-image: linear-gradient(to top,
  transparent 46%,
  rgba(12, 13, 19, 0.5) 68%,
  rgba(12, 13, 19) 97%);
}

.wrapper::after {
  bottom: 0;
  opacity: 1;
  background-image: linear-gradient(to bottom,
  transparent 46%,
  rgba(12, 13, 19, 0.5) 68%,
  rgba(12, 13, 19) 97%);
}

.card:hover .wrapper::before,
.wrapper::after {
  opacity: 1;
}

.card:hover .wrapper::after {
  height: 120px;
}

.title {
  width: 100%;
  transition: transform 0.3s;
}

.card:hover .title {
  transform: translate3d(0%, -50px, 100px);
}

.character {
  width: 100%;
  opacity: 0;
  transition: all 0.3s;
  position: absolute;
  z-index: -1;
}

.card:hover .character {
  opacity: 1;
  transform: translate3d(0%, -20%, 100px);
}

.card:hover {
  transform: translateY(40px);
}



</style>
