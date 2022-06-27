> A  vue & node / vue & spring boot  Project     网上订餐系统

#### 架构

- 前端：vue全家桶
- 后端： node:express框架 / sring boot整合mybatis-plus
- 数据库：MySQL、redis

#### 功能

##### 普通用户

- 注册、登录（图形验证码，六位数字验证码）
- 定位 （腾讯地图定位功能）、自主选择所在城市
- 商品
  - 分类
  - 简单展示商品
  - 查看商品详情
  - 商品评论
- 分页功能
- 购物车功能
  - 加入购物车
  - 购物车商品数量增减
  - 清空购物车
  - 商品结算
- 多关键词模糊搜索商品 （关键词需为商品名称）
- 用户个人中心
  + 修改用户信息 （头像、昵称、简介...）
  + 修改手机号
  + 修改密码
  + 查看订单信息
  + 修改订单

##### 后台管理员

- 登录（固定账号密码：admin）
- 查看所有用户
- 查看数据库餐品信息
- 餐品上架（添加餐品）
- 删除/修改餐品
- 查看评论信息
- 删除评论
- 查看用户信息
- 修改用户信息
- 分页功能



#### 运行

> node项目后端服务器是基于node、MySQL开发，运行前请确认系统已安装相关应用 
> spring boot项目后端整合了mybatis-plus和mybatis的，数据库除了MySQL之外还增加了一个redis缓存数据库

- `git clone`

##### 后端

+ 确认是否全局安装`nodemon`

+ 若无安装：

  + `npm i -g nodemon`

  + 或修改`web-server/package.json`

    ```javascript
    "scripts": {
        "test": "echo \"Error: no test specified\" && exit 1",
        "dev": "set PORT=3000 && node main.js",
        "build": "babel src -d dist",
        "start": "node dist/app.js"
      },
    ```

+ `cd web-server`
+ `mkdir -p public/uploads`  新建uploads文件夹保存商品图片
+ 将`web-server`文件夹下的`webshop.sql`导入`MySQL`数据库中
+ `npm install` 安装依赖
+ 修改`web-server/src/config.js`文件，此文件记录项目的全局变量，在文件中找到`password`、`database`、`user`属性，根据你的数据库信息修改它们的值
+ `nodemon main.js` 在本地运行，启动服务器

##### 前端

- `cd web-client`
- `mkdir static` 新建static文件夹，防止报错
- `npm install` 安装依赖
- `npm run dev` 在本地运行
- 接着就可以在`http://localhost:8080`下访问到该项目
