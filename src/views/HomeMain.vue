<template>
  <div>
    <el-container>
      <!--left aside -->
      <el-aside :width="asideWidth" :class="{ 'is-collapsed': isCollapsed }"
        style="min-height: 100vh;background-color: #B3282D;">
        <div style="display: flex;align-items:center;justify-content: center;height: 100px;color: rgb(255, 255, 255)">
          <img v-if="!isCollapsed" src="@/assets/AlgomaU_Logo.svg" alt=""
            style="width: 200px;height: 50px;margin-top: 15px;">
          <img v-else src="@/assets/AlgomaU_Logo2.jpg" alt="" style="width: 50px;height: 50px;margin-top: 15px;">
        </div>
        <!-- NavMenu -->
        <el-menu :collapse="isCollapse" :collapse-transition="false" router text-color="rgba(255, 255, 255, 0.75)"
          active-text-color="#fff" style="border: none;background-color: #B3282D" :default-active="$route.path">
          <!-- v-show let text invisible -->
          <el-menu-item index="/home"><i class="el-icon-s-home"></i><span>Dashboard</span></el-menu-item>
          <el-menu-item index="/user"><i class="el-icon-s-custom"></i><span>Candidate Information</span></el-menu-item>
          <el-menu-item><i class="el-icon-s-custom"></i><span>Third
              element</span></el-menu-item>
          <el-menu-item><i class="el-icon-s-custom"></i><span>Fourth
              element</span></el-menu-item>
          <el-menu-item><i class="el-icon-s-custom"></i><span>Fifth
              element</span></el-menu-item>
<!--          <el-submenu>-->
<!--            <template slot="title">-->
<!--              <i class="el-icon-location"></i>-->
<!--              <span>Menu 1</span>-->
<!--            </template>-->
<!--            <el-menu-item><i class="el-icon-s-custom"></i><span>First-->
<!--                element</span></el-menu-item>-->
<!--            <el-menu-item><i class="el-icon-s-custom"></i><span>Second-->
<!--                element</span></el-menu-item>-->
<!--          </el-submenu>-->
        </el-menu>
      </el-aside>
      <!-- right part -->
      <el-container>
        <!-- header -->
        <el-header style="display: flex;align-items: center;">
          <!-- click to collapse -->
          <i :class="collapseIcon" style="font-size: 26px;" @click="handleCollapse"></i>
          <!-- breadcrumb -->
          <el-breadcrumb separator-class="el-icon-arrow-right" style="margin-left: 20px;">
            <el-breadcrumb-item :to="{ path: '/' }">Welcome</el-breadcrumb-item>
            <el-breadcrumb-item :to="{ path: $route.path }">{{ $route.meta.name }}</el-breadcrumb-item>
          </el-breadcrumb>

          <div style="flex: 1;width: 0;display: flex;align-items: center;justify-content: flex-end;">
            <i class="el-icon-fullscreen" style="font-size: 20px;margin-right: 10px" @click="fullScreen"></i>
            <el-dropdown placement="bottom">
              <div style="display: flex;align-items: center;cursor: default;">
                <img :src="user.avatar" alt="" style="width: 40px;height: 40px;border-radius: 50%;margin: 0 5px">
                <span>{{ user.name }}</span>
              </div>
              <!-- dropdown menu -->
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item @click.native="$router.push('/userprofile')">User Profile</el-dropdown-item>
                <el-dropdown-item>Help center</el-dropdown-item>
                <el-dropdown-item @click.native="logout">Sign out</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </div>
        </el-header>

        <!-- main part -->
        <el-main style="background-color: #ffffff;">
          <router-view @update:user="updateUser" />
        </el-main>

      </el-container>

    </el-container>
  </div>
</template>

<script>


export default {
  name: 'HomeView',
  data() {
    return {
      isCollapsed: false,
      asideWidth: '260px',
      collapseIcon: 'el-icon-s-fold',
      user: JSON.parse(localStorage.getItem('hcs-user') || '{}'),
    }
  },
  // triggered after the page is rendered
  mounted() {

  },
  methods: {
    //incorporate data passed from child components to update the current page's data
    updateUser(user){
      this.user = JSON.parse(JSON.stringify(user))
    },
    logout(){
      localStorage.removeItem('hcs-user')  //Clear the current token and user data
      this.$router.push('/login')
    },
    // click to fullscreen
    fullScreen() {
      let element = document.documentElement;
      if (this.fullscreen) {
        if (document.exitFullscreen) {
          document.exitFullscreen();
        }
      } else {
        if (element.requestFullscreen) {
          element.requestFullscreen();
        }
      }
      this.fullscreen = !this.fullscreen;
    },

    // control collapse
    handleCollapse() {
      this.isCollapsed = !this.isCollapsed;
      this.asideWidth = this.isCollapsed ? '64px' : '260px';
      this.collapseIcon = this.isCollapsed ? 'el-icon-s-unfold' : 'el-icon-s-fold';
    },

  }
}
</script>

<style>
/* Inner subtitle's father */
.el-menu--inline {
  background-color: #8e1e22;
}

/* Inner subtitle */
.el-menu--inline .el-menu-item {
  background-color: #8e1e22;
  margin: 12px;
  height: 44px;
  line-height: 44px;
  border-radius: 10px;
}

/* hover */
.el-menu-item:hover,
.el-submenu__title:hover {
  color: white !important;
  background-color: #751518;
}

/* normal icon */
.el-menu-item:hover i {
  color: white !important;
}

/* subtitle icon */
.el-submenu__title:hover i {
  color: white !important;
}

/* normal active */
.el-menu-item.is-active {
  background-color: #570e11 !important;
  border-radius: 10px;
  margin: 12px;
}

/* normal */
.el-menu-item {
  margin: 12px;
  height: 44px;
  line-height: 44px;
  border-radius: 10px;

}

/* sub title */
.el-submenu__title {
  margin: 12px;
  height: 44px;
  line-height: 44px;
  border-radius: 10px;
}

.el-submenu__icon-arrow {
  position: relative;
  right: -90px;
  top: 4px;
}

.el-menu-item i,
.el-submenu__title i {
  transform: translateX(0);
  transition: transform 0.9s !important;
}

.is-collapsed .el-menu-item i,
.is-collapsed .el-submenu__title i {
  transform: translateX(-12px);
}

/* change margin when is-collapsed */
/* .is-collapsed .el-menu-item i {
  margin-left: -11px;
}

.is-collapsed .el-submenu__title i {
  margin-left: -11px;
} */

/* hidden overflow when is-collapsed */
.el-aside.is-collapsed {
  overflow-x: hidden;
}

/* .is-collapsed .el-menu--inline .el-menu-item i {
  margin-left: -31px;
} */

/* when collapsed,cannot open submenu */
.is-collapsed .el-submenu .el-menu {
  display: none;
}

.el-aside {
  transition: width .3s;
  z-index: 1;
  box-shadow: 2px 0 8px rgba(0, 21, 41, 0.55);
}

.el-menu-item span {
  transform: scaleX(1);
  transform-origin: left;
  opacity: 1;
  display: inline-block;
  transition: transform 0.9s, opacity 0.9s !important;
  white-space: nowrap;
}

.is-collapsed .el-menu-item span {
  transform: scaleX(0);
  opacity: 0;
}

.is-collapsed .el-submenu__title span {
  transform: scaleX(0);
  opacity: 0;
}

.el-header {
  z-index: 1;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.2);
}

.el-dropdown-menu__item:hover {
  color: white !important;
  background-color: #B3282D !important;
  /* border-radius: 24px; */
}
</style>