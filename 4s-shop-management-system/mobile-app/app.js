// 4S店管理系统 - 微信小程序 - 应用入口文件
App({
  globalData: {
    userInfo: null,
    token: '',
    baseUrl: 'https://api.4s-shop.com', // 生产环境API地址
    // baseUrl: 'http://localhost:8080', // 开发环境API地址
  },

  onLaunch: function () {
    console.log('App Launch');
    
    // 获取用户信息
    this.getUserInfo();
    
    // 检查登录状态
    this.checkLoginStatus();
  },

  onShow: function (options) {
    console.log('App Show', options);
  },

  onHide: function () {
    console.log('App Hide');
  },

  // 获取用户信息
  getUserInfo: function() {
    return new Promise((resolve, reject) => {
      if (this.globalData.userInfo) {
        resolve(this.globalData.userInfo);
      } else {
        // 调用微信登录接口
        wx.login({
          success: res => {
            if (res.code) {
              // 发送code到后端换取token
              wx.request({
                url: `${this.globalData.baseUrl}/auth/miniprogram/login`,
                method: 'POST',
                data: {
                  code: res.code
                },
                success: loginRes => {
                  if (loginRes.data.success) {
                    this.globalData.token = loginRes.data.data.token;
                    // 获取用户信息
                    wx.getUserInfo({
                      success: userRes => {
                        this.globalData.userInfo = userRes.userInfo;
                        resolve(this.globalData.userInfo);
                      },
                      fail: () => {
                        reject('获取用户信息失败');
                      }
                    });
                  } else {
                    reject('登录失败');
                  }
                },
                fail: () => {
                  reject('请求登录接口失败');
                }
              });
            } else {
              reject('获取code失败');
            }
          },
          fail: () => {
            reject('调用微信登录接口失败');
          }
        });
      }
    });
  },

  // 检查登录状态
  checkLoginStatus: function() {
    const token = wx.getStorageSync('token');
    if (token) {
      this.globalData.token = token;
    }
  },

  // 通用请求方法
  request: function(options) {
    const { url, method = 'GET', data = {}, header = {} } = options;
    
    return new Promise((resolve, reject) => {
      wx.request({
        url: this.globalData.baseUrl + url,
        method: method,
        data: data,
        header: {
          'Authorization': `Bearer ${this.globalData.token}`,
          'Content-Type': 'application/json',
          ...header
        },
        success: res => {
          if (res.statusCode === 200) {
            resolve(res.data);
          } else {
            reject(res);
          }
        },
        fail: err => {
          reject(err);
        }
      });
    });
  }
});
