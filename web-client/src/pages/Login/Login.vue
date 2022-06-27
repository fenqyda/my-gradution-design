<template>
  <div class="login-container">
    <!--登录面板内容部分-->
    <div class="login-inner">
      <!--面板头部-->
      <div class="login-header">
        <div class="login-logo">
          <img src="./images/logo-round.png" alt="" width="150" />
        </div>
        <!--面板标题-->
        <div class="login-header-title">
          <a
            href="javascript:;"
            :class="{ current: loginMode }"
            @click="dealLoginMode(true)"
            >密码登录</a
          >
          <a
            href="javascript:;"
            :class="{ current: !loginMode }"
            @click="dealLoginMode(false)"
            >注册</a
          >
        </div>
      </div>
      <!--面板表单部分-->
      <div class="login-content">
        <form>
          <!-- 注册部分 -->
          <div :class="{ current: !loginMode }">
            <section class="login-message">
              <input
                type="tel"
                maxlength="11"
                placeholder="手机号"
                v-model="phone"
              />
              <button
                v-if="!countDown"
                class="get-verification"
                :class="{ phone_right: phoneRight }"
                @click.prevent="getVerifyCode()"
              >
                获取验证码
              </button>
              <button v-else disabled="disabled" class="get-verification">
                已发送({{ countDown }}s)
              </button>
            </section>
            <section class="login-verification">
              <input
                type="password"
                maxlength="18"
                placeholder="密码"
                v-if="pwdMode"
                v-model="pwd"
              />
              <input
                type="text"
                maxlength="18"
                placeholder="密码"
                v-else
                v-model="pwd"
              />
              <div class="switch-show">
                <img
                  @click.prevent="dealPwdMode(false)"
                  :class="{ on: pwdMode }"
                  src="./images/hide_pwd.png"
                  alt=""
                  width="20"
                />
                <img
                  @click.prevent="dealPwdMode(true)"
                  :class="{ on: !pwdMode }"
                  src="./images/show_pwd.png"
                  alt=""
                  width="20"
                />
              </div>
            </section>
            <section class="login-verification">
              <input
                type="password"
                maxlength="18"
                placeholder="再次输入密码"
                v-if="pwdMode"
                v-model="secpwd"
              />
              <input
                type="text"
                maxlength="18"
                placeholder="再次输入密码"
                v-else
                v-model="secpwd"
              />
              <div class="switch-show">
                <img
                  @click.prevent="dealPwdMode(false)"
                  :class="{ on: pwdMode }"
                  src="./images/hide_pwd.png"
                  alt=""
                  width="20"
                />
                <img
                  @click.prevent="dealPwdMode(true)"
                  :class="{ on: !pwdMode }"
                  src="./images/show_pwd.png"
                  alt=""
                  width="20"
                />
              </div>
            </section>
            <section class="login-verification">
              <input
                type="tel"
                maxlength="18"
                placeholder="验证码"
                v-model="textcode"
              />
            </section>
            <section class="login-hint">
              温馨提示：默认情况下手机号码就是用户名，且代表已同意
              <a href="javascript:;">服务协议与隐私政策</a>
            </section>
          </div>
          <!--账号登录部分-->
          <div :class="{ current: loginMode }">
            <section>
              <section class="login-message">
                <input
                  type="text"
                  maxlength="11"
                  placeholder="账号"
                  v-model="username"
                />
              </section>
              <section class="login-verification">
                <input
                  type="password"
                  maxlength="18"
                  placeholder="密码"
                  v-if="pwdMode"
                  v-model="pwd"
                />
                <input
                  type="text"
                  maxlength="18"
                  placeholder="密码"
                  v-else
                  v-model="pwd"
                />
                <div class="switch-show">
                  <img
                    @click.prevent="dealPwdMode(false)"
                    :class="{ on: pwdMode }"
                    src="./images/hide_pwd.png"
                    alt=""
                    width="20"
                  />
                  <img
                    @click.prevent="dealPwdMode(true)"
                    :class="{ on: !pwdMode }"
                    src="./images/show_pwd.png"
                    alt=""
                    width="20"
                  />
                </div>
              </section>
              <section class="login-message">
                <input
                  type="text"
                  maxlength="11"
                  placeholder="验证码"
                  v-model="captcha"
                />
                <img
                  ref="captcha"
                  class="get-verification"
                  src="http://localhost:3000/mykaptcha"
                  alt="captcha"
                  @click.prevent="getCaptcha()"
                />
              </section>
              <section class="login-hint">
                温馨提示：默认情况下手机号码就是用户名，且代表已同意
                <a href="javascript:;">服务协议与隐私政策</a>
              </section>
            </section>
          </div>
          <button
            class="login-submit"
            v-if="loginMode"
            @click.prevent="login()"
          >
            登录
          </button>
          <button class="login-submit" v-if="!loginMode" @click.prevent="reg()">
            注册
          </button>
        </form>
        <button class="login-back" @click="$router.back()">返回</button>
      </div>
    </div>
  </div>
</template>

<script>
import { pwdLogin, Userreg , getPhoneCode } from "./../../api/index";
import { MessageBox } from "element-ui";
import { mapActions } from "vuex";
import Axios from "axios";

export default {
  name: "Login",
  data() {
    return {
      loginMode: true, // 登录方式, true 验证码登录 false 账号登录
      phone: "", // 手机号码
      countDown: 0, // 倒计时
      pwdMode: true, // 密码的显示方式 true 密文 false 明文
      pwd: "", // 密码
      secpwd: "", //验证密码
      userInfo: {}, // 用户的信息
      username: "", // 用户名
      captcha: "", // 图形验证码
      textcode: "", //数字验证码
    };
  },
  computed: {
    // 验证手机号是否合理
    phoneRight() {
      return /^[1][3,4,5,7,8][0-9]{9}$/.test(this.phone);
    },
  },
  methods: {
    ...mapActions(["syncUserInfo"]),
    // 1. 登录or注册
    dealLoginMode(flag) {
      this.loginMode = flag;
    },
    // 3. 密码的显示方式
    dealPwdMode(flag) {
      this.pwdMode = flag;
    },
    async getVerifyCode() {
      // 1 开启倒计时
      if (this.phoneRight) {
        this.countDown = 60;
        this.intervalId = setInterval(() => {
          this.countDown--;
          // 判断
          if (this.countDown === 0) {
            clearInterval(this.intervalId);
          }
        }, 1000);
      }

      // 2 获取短信验证码
      let result = await getPhoneCode(this.phone);
      console.log(result);

      // 3 获取验证码失败
      if (!result) {
        // 提示信息
        MessageBox({
          type: "info",
          message: "验证码获取失败",
          showClose: true,
        });

        // 其他处理
        clearInterval(this.intervalId);
        this.countDown = 0;
      } else if (result) {
        MessageBox({
          type: "info",
          message: `验证码为:${result}, 短信功能暂未开通,十分抱歉对您产生的不便`,
          showClose: true,
        });
      }
    },
    // 4. 获取图形验证码
    getCaptcha() {
      this.$refs.captcha.src =
        "http://localhost:3000/mykaptcha?time=" + new Date();
    },
    // 5. 登录
    async login() {
      // 账号和密码登录
      // 5.1 前端校验
      if (!this.username) {
        MessageBox({
          type: "info",
          message: "请输入账号!",
          showClose: true,
        });
        return;
      } else if (!this.pwd) {
        MessageBox({
          type: "info",
          message: "请输入密码!",
          showClose: true,
        });
        return;
      } else if (!this.captcha) {
        MessageBox({
          type: "info",
          message: "请输入验证码!",
          showClose: true,
        });
        return;
      }
      Axios.get(
        `http://localhost:3000/user/testkaptcha?captcha=${this.captcha}`
      ).then((res) => {
        console.log(res);
        if (res.data == false) {
          MessageBox({
            type: "info",
            message: "登录失败,验证码不正确!",
            showClose: true,
          });
        } else {
          // 5.2 用户名和密码的登录
          pwdLogin(this.username, this.pwd).then((result) => {
            console.log(result);
            if (result.length >=1) {
              this.userInfo = result[0];
              this.phone = ""; // 手机号码
              this.countDown = 0; // 倒计时
              clearInterval(this.intervalId);
              this.captcha = ""; // 验证码
              this.pwd = ""; // 密码
              this.username = ""; // 账号

               this.syncUserInfo(this.userInfo);
              // 6.2 回到主界面
              MessageBox({
                type: "success",
                message: "登录成功!",
                showClose: true,
              });
              // window.localStorage.setItem("userInfo",JSON.stringify(this.userInfo));
              window.localStorage.setItem(
                "userInfo",
                JSON.stringify(this.userInfo)
              );
              window.localStorage.removeItem("adminInfo");
              this.$router.back();
            } else {
              MessageBox({
                type: "info",
                message: "登录失败, 账号或密码不正确!",
                showClose: true,
              });
            }
            // // 6. 后续处理
            // if (!this.userInfo.username) {
            //   // 失败
            //   MessageBox({
            //     type: "info",
            //     message: "登录失败！",
            //     showClose: true,
            //   });
            // } else {
            //   // 成功
            //   // 6.1 同步用户数据
            //   this.syncUserInfo(this.userInfo);
            //   // 6.2 回到主界面
            //   MessageBox({
            //     type: "success",
            //     message: "登录成功!",
            //     showClose: true,
            //   });
            //   // window.localStorage.setItem("userInfo",JSON.stringify(this.userInfo));
            //   window.localStorage.setItem(
            //     "userInfo",
            //     JSON.stringify(this.userInfo)
            //   );
            //   window.localStorage.removeItem("adminInfo");
            //   this.$router.back();
            // }
          });
        }
      });
    },
    //注册
    async reg() {
      // 注册
      // 7.1 前端校验
      if (!this.phone) {
        MessageBox({
          type: "info",
          message: "请输入手机号!",
          showClose: true,
        });
        return;
      } else if (!this.pwd) {
        MessageBox({
          type: "info",
          message: "请输入密码!",
          showClose: true,
        });
        return;
      } else if (this.pwd != this.secpwd) {
        MessageBox({
          type: "info",
          message: "两次密码不一致!",
          showClose: true,
        });
        return;
      } else if (!this.textcode) {
        MessageBox({
          type: "info",
          message: "请输入验证码!",
          showClose: true,
        });
        return;
      }

      // 7.2 注册
      Userreg({
        username: this.phone,
        pwd: this.pwd,
        phone: this.phone,
        avatar: "http://localhost:3000/avataruploads/avatar_default.jpg",
      }).then((result) => {
        if (result) {
          MessageBox({
            type: "success",
            message: "注册成功!",
            showClose: true,
          });
          this.username = this.phone;
          this.phone = "";
          this.textcode = "";
          this.secpwd = "";
          this.dealLoginMode(true);
        }else{
          MessageBox({
            type: "error",
            message: "注册失败!，请检查验证码",
            showClose: true,
          });
        }
      });
    },
  },
};
</script>

<style scoped lang="stylus" rel="stylesheet/stylus">
@import '../../common/stylus/mixins.styl';

.login-container {
  width: 100%;
  height: 100%;
  background: #fff;

  .login-inner {
    padding-top: 60px;
    width: 25%;
    margin: 0 auto;

    .login-header {
      .login-logo {
        font-size: 20px;
        font-weight: bold;
        color: mediumpurple;
        text-align: center;
      }

      .login-header-title {
        padding-top: 40px;
        padding-bottom: 10px;
        text-align: center;

        > a {
          color: #333;
          font-size: 14px;
          padding-bottom: 4px;

          &:first-child {
            margin-right: 40px;
          }

          &.current {
            color: mediumpurple;
            font-weight: 700;
            border-bottom: 2px solid mediumpurple;
          }
        }
      }
    }

    .login-content {
      > form {
        > div {
          display: none;

          &.current {
            display: block;
          }

          input {
            width: 100%;
            height: 100%;
            padding-left: 8px;
            box-sizing: border-box;
            border: 1px solid #ddd;
            border-radius: 4px;
            outline: 0;
            font: 400 14px Arial;

            &:focus {
              border: 1px solid mediumpurple;
            }
          }

          .login-message {
            position: relative;
            margin-top: 16px;
            height: 48px;
            font-size: 14px;
            background: #fff;

            .get-verification {
              position: absolute;
              top: 50%;
              right: 10px;
              transform: translateY(-50%);
              border: 0;
              color: #ccc;
              font-size: 14px;
              background: transparent;

              &.phone_right {
                color: purple;
              }
            }
          }

          .login-verification {
            position: relative;
            margin-top: 16px;
            height: 48px;
            font-size: 14px;
            background: #fff;

            .switch-show {
              position: absolute;
              right: 10px;
              top: 12px;

              img {
                display: none;
              }

              img.on {
                display: block;
              }
            }
          }

          .login-hint {
            margin-top: 12px;
            color: #999;
            font-size: 12px;
            line-height: 20px;

            > a {
              color: mediumpurple;
            }
          }
        }

        .login-submit {
          display: block;
          width: 100%;
          height: 42px;
          margin-top: 30px;
          border-radius: 4px;
          background: mediumpurple;
          color: #fff;
          text-align: center;
          font-size: 16px;
          line-height: 42px;
          border: 0;
        }
      }

      .login-back {
        display: block;
        width: 100%;
        height: 42px;
        margin-top: 15px;
        border-radius: 4px;
        background: transparent;
        border: 1px solid mediumpurple;
        color: mediumpurple;
        text-align: center;
        font-size: 16px;
        line-height: 42px;
      }
    }
  }
}
</style>
