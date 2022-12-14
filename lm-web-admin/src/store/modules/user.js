import {LmMessageError} from "@/utils/index.js";
import loginService from "@/services/login/LoginService.js"
export default {
    namespaced: true,

    state() {
        return {
            // 管理用户信息
            userId: "",
            username: "",
            avatar: "",
            tokenjj: "",
            // 这个是用户角色
            roleList: [],
            permissionList: [],
            tokenUuid: ""
        }
    },
    mutations: {
        // 设置token_jj jwt
        setToken_jj(state, token_jj){
            state.tokenJj = token_jj;
        },
        savaUserData(state, serverUserData) {
            state.tokenJj = serverUserData.tokenJj;
            state.tokenUuid = serverUserData.tokenUuid;
            state.userId = serverUserData.user.id;
            state.username = serverUserData.user.username;
            state.avatar = serverUserData.avatar;
            state.roleList = serverUserData.roleNames;
            state.permissionList = serverUserData.permissions;
            // console.log("StorePermissions", serverUserData.permissions);
        },
        // 清除状态
        delUser(state) {
            state.tokenJj = "";
            state.tokenUuid = "";
            state.userId = "";
            state.username = "";
            state.avatar = "";
            state.roleList = [];
            state.permissionList = [];
        }
    },
    actions: {

        async toLogout(context) {
            try {
                //1: 异步请求去执行服务器退出操作
                //2: 执行页面状态清空
                let serverLogout = await loginService.toLogout();// 执行退出
                // console.log("----------------------",serverLogout);
                context.commit("delUser");//  清除用户在本地的状态
                return Promise.resolve(serverLogout);
            } catch (error) {
                LmMessageError("退出失败" + error);
                return Promise.reject(error);
            }

        },

        // store.dispatch("toLogin",Login_UserData);
        // 如果使用模块 那就 ***/toLogin
        async toLogin(context, loginUserData) {
            // console.log("store----tologin");
            try {
                let LoginServer_retrunMessage = await loginService.toLogin(loginUserData); // 这个会抛出异常
                // console.log("toLogin",LoginServer_retrunMessage);

                // console.log("go to server later of data:", LoginServer_retrunMessage);// 这个会为空
                // 这里是通过方法的名称调用方法的
                context.commit("savaUserData", LoginServer_retrunMessage.data);
                // 抛出成功
                // console.log(LoginServer_retrunMessage);
                return Promise.resolve(LoginServer_retrunMessage);
            } catch (err) {
                // alert(err);
                // 捕获到异常抛出给页面
                // alert("2-----err");
                // console.log(err);
                return Promise.reject(err);

            }
        }
    },
    // 对state数据的改造和加工处理。给未来的页面和组件进行调用。
    // 从而达到一个封装的目录 computed
    getters: {
        // 组装一个角色信息返回
        getRoleName(state) {
            return state.roleList.map(role => role.name).join(",");
        },
        // 获取权限
        getPermissions(state) {
            return state.permissionList;
        },
        // 获取用户ID
        getUserId(state) {
            return state.userId;
        },
        // 获取token
        getTokenJj(state) {
            return state.tokenJj;
        },
        // 获取tokenUuid 用于同一账号挤下线
        // 因为你每次登录都会产生新的uuid。所以旧的就被你新挤掉
        getTokenUuid(state) {
            return state.tokenUuid;
        },
        // 判断是否登录
        isLogin(state) {
            return state.userId != "";
        },
        // 获取角色列表
        getRoleNames(state){
            return state.roleList.join(",");
        }
    }

}