项目功能  
一：基于Jfinal构建信息管理系统，要求包含用户管理，翻译业务模块管理，图片优化模块管理  
二：要求不同用户登录后可进行文字翻译和图片优化业务处理，并且可查看提交业务处理结果，时间，处理列表，图形汇总等  
三：要求管理员登录后可查看不同用户的各项业务使用情况  

下载本项目前请务必先看一下演示视频的效果  
项目演示地址：https://www.bilibili.com/video/BV1uw411J7cy/  
项目演示地址：https://www.bilibili.com/video/BV1uw411J7cy/  
项目演示地址：https://www.bilibili.com/video/BV1uw411J7cy/  

登录页面
<img width="1172" alt="image" src="https://github.com/mendianyu/AdvancedManagement/assets/125875687/f3108dbb-fc7d-40b2-b93c-b90c41f60ca1">
注册页面
<img width="1172" alt="image" src="https://github.com/mendianyu/AdvancedManagement/assets/125875687/4aa922c5-f0c2-4a87-8d68-750606704373">
管理员相关页面
<img width="1217" alt="image" src="https://github.com/mendianyu/AdvancedManagement/assets/125875687/4ebf4dcd-38d5-4e7b-a57d-5e85ad9a5956">
<img width="1217" alt="image" src="https://github.com/mendianyu/AdvancedManagement/assets/125875687/6b457ae0-2400-4b98-865a-5b1c2adb88c1">
<img width="1217" alt="image" src="https://github.com/mendianyu/AdvancedManagement/assets/125875687/3ba58850-bec4-4771-9fb4-b1a83d330e7d">
<img width="1217" alt="image" src="https://github.com/mendianyu/AdvancedManagement/assets/125875687/3406abf9-9227-420c-a1b3-e84c3340034e">
<img width="1217" alt="image" src="https://github.com/mendianyu/AdvancedManagement/assets/125875687/39b8bf64-eb0f-4487-9eb6-12ab55f69c92">
<img width="1217" alt="image" src="https://github.com/mendianyu/AdvancedManagement/assets/125875687/36415dde-9126-4d7e-9c88-f9da92490ecb">
<img width="1217" alt="image" src="https://github.com/mendianyu/AdvancedManagement/assets/125875687/a7430302-7298-4751-a2fd-87af84c0d326">
<img width="1217" alt="image" src="https://github.com/mendianyu/AdvancedManagement/assets/125875687/e064bcf2-d264-4f64-a74c-77c22375fa24">

普通用户相关页面
<img width="1217" alt="image" src="https://github.com/mendianyu/AdvancedManagement/assets/125875687/cd961a70-a718-4027-88ce-73a046528144">
<img width="1217" alt="image" src="https://github.com/mendianyu/AdvancedManagement/assets/125875687/16be4e79-13b4-4afa-bbf1-343f76175307">
其他和管理员的页面都差不多  



项目部署相关  
本项目有三个表
![image](https://github.com/mendianyu/AdvancedManagement/assets/125875687/4dac4c93-819a-416d-94e9-e0037ca17c95)

分别记录用户信息，翻译记录和图片处理记录
导入表后，同样更改resources中的这个文件内容为自己的数据库配置信息
![image](https://github.com/mendianyu/AdvancedManagement/assets/125875687/b25374f3-282b-4484-808b-ef40c9add8e0)


还需要把项目配置修改为以下
![image](https://github.com/mendianyu/AdvancedManagement/assets/125875687/a5abef45-f983-488e-ad3d-c8572d1eb7fd)  
![image](https://github.com/mendianyu/AdvancedManagement/assets/125875687/0ef64c9f-6110-4173-b77d-5ae3bf659a52)  
![image](https://github.com/mendianyu/AdvancedManagement/assets/125875687/e0a6426b-6a04-4253-a243-fc0e1b513ab0)  


运行AppConfig1即可启动，启动后浏览器输入localhost回车即可进入登录页面
![image](https://github.com/mendianyu/AdvancedManagement/assets/125875687/6ffb3762-bae6-4a24-ac5f-02244cc55be1)

登录账号和密码为user表中的信息
其中第一个为管理员，其他为普通用户
![image](https://github.com/mendianyu/AdvancedManagement/assets/125875687/002b913d-6cde-4ce8-848c-63291bf87e2a)

















