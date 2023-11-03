import Vue from 'vue'
import VueRouter from 'vue-router'

//fix error "Avoided redundant navigation to current location: "/home"."
const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => err)
}

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'HomeMain',
    component: () => import('../views/HomeMain.vue'),
    redirect: '/home',  //redirect to Home
    children:[
      { path: 'home', name: 'Home', meta:{name:'Home'} ,component:() => import('../views/UnderHomeMain/Home.vue')},
      { path: 'user', name: 'User', meta:{name:'Candidate Information'} ,component:() => import('../views/UnderHomeMain/User.vue')},
      { path: 'userprofile', name: 'UserProfile', meta:{name:'User Profile'} ,component:() => import('../views/UnderHomeMain/UserProfile.vue')},
    ]
  },

  {
    path: '/login',
    name: 'Login',
    meta:{name:'Login'} ,
    component: () => import('../views/Login.vue')
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})


export default router