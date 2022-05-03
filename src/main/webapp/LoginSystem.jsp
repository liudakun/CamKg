<%@ page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>登录页面</title>
<style>
	h1{
		font-family:"楷体";
		font-size:40pt;
	   	color:blue;
	 } 
    *{
        margin: 0px;
        padding: 0px;
        box-sizing: border-box;
    }
    body{
        background: url("img/just1.jpg") no-repeat center;
        background-size:2000px 1450px;
        padding-top:25px;
    }

    .rg_layout{
        width: 750px;
        height: 300px;
        border: 8px solid #EEEEEE;
        background-color: white;
        /*让div水平居中
         margin: auto;
        */
        /*设置透明度*/
        opacity:0.8;
         /*设置边框圆角*/
        border-radius: 5px;
        padding-left: 10px;
        float: right;
       
    }

    .rg_left{
        /*border: 1px solid red;*/
        float: left;
        margin: 15px;
    }
    .rg_left > p:first-child{
        color:#FFD026;
        font-size: 25px;
    }

    .rg_left > p:last-child{
        color:#A6A6A6;
        font-size: 20px;

    }


    .rg_center{
        float: left;
       /* border: 1px solid red;*/

    }

    .rg_right{
        /*border: 1px solid red;*/
        float: right;
        margin: 15px;
    }

    .rg_right > p:first-child{
        font-size: 15px;

    }
    .rg_right p a {
        color:pink;
    }

    .td_left{
        width: 100px;
        text-align: right;
        height: 45px;
    }
    .td_right{
        padding-left: 50px ;
    }
    .rg_error{
    	color:red;
    }

    #username,#password,#email,#name,#tel,#birthday,#checkcode{
        width: 251px;
        height: 32px;
        border: 1px solid #A6A6A6 ;
        /*设置边框圆角*/
        border-radius: 5px;
        padding-left: 10px;
    }
    #checkcode{
        width: 110px;
    }

    #img_check{
        height: 32px;
        vertical-align: middle;
    }

    #btn_sub{
        width: 150px;
        height: 40px;
        background-color: #FFD026;
        border: 1px solid #FFD026 ;
    }
    #btn_reset{
      width: 60px;
        height: 40px;
        background-color: green;
        border: 1px solid green;
    }
    .error{
        color:red;
    }
    #td_sub{
        padding-left: 150px;
    }
    
    .rg_company{
     	text-align:center; 
    	font-size:4pt;
    }

</style>

</head>
<body>
<h1 >    先研所.船用柴油机关键件</h1><br>
<h1 >    知识图谱系统</h1>
<div class="rg_layout">
    <div class="rg_left">
        <p>用户登录</p>
        <p>USER LOGIN</p>
		<p ><font color="red">${promptMsg}</font></p>
    </div>
    <div class="rg_center">
        <div class="rg_form">
            <!--定义表单 form-->
            <form action="/CamKg/LoginSystemServlet" id="form" method="post">
                <table>
                    <tr>
                        <td class="td_left"><label for="username">用户名</label></td>
                        <td class="td_right">
                            <input type="text" name="username" id="username" value = "${cookie.username.value}" placeholder="请输入用户名">
                            <span id="s_username" class="error"></span>
                        </td>
                    </tr>

                    <tr>
                        <td class="td_left"><label for="password">密码</label></td>
                        <td class="td_right">
                            <input type="password" name="password" id="password"  value = "${cookie.password.value}" placeholder="请输入密码">
                            <span id="s_password" class="error"></span>
                             <p>记住密码<input id="remember" name="remember" value="1" type="checkbox"></p>
                            <div class="rg_error">${error}</div>
                        </td>
                    </tr>
                   
                        <td class="td_left"><label for="checkcode" >验证码</label></td>
                        <td class="td_right"><input type="text" name="checkcode" id="checkcode" placeholder="请输入验证码">
                            <img id="img_check" src="/CamKg/VerifyCodeServlet">
                               <a href="#"
									onclick="document.getElementById('img_check').src='/CamKg/VerifyCodeServlet?'+new Date().getTime();">看不清？换一个</a>
                        </td>
                    </tr>


                    <tr>
                        <td  id="td_sub" colspan="2"><input type="submit" id="btn_sub" value="登录"></td>
                        <td  id="td_reset"><input type="reset" id="btn_reset" value="重置"></td>
                        
                    </tr>
                </table>
            </form>
                 
            <br>
            <br>
            <hr>
            <div class ="rg_company"> 江苏科技大学</div>
            <div class ="rg_company"> 版权所有Copyright 2022&copy,All Rights Reserved</div>


        </div>

    </div>

<!--     <div class="rg_right"> -->
<!--         <p>已有账号?<a href="#">立即登录</a></p> -->
<!--     </div> -->


</div>


<script>

    /*
        分析：
            1.给表单绑定onsubmit事件。监听器中判断每一个方法校验的结果。
                如果都为true，则监听器方法返回true
                如果有一个为false，则监听器方法返回false

            2.定义一些方法分别校验各个表单项。
            3.给各个表单项绑定onblur事件。



     */

    window.onload = function(){
        //1.给表单绑定onsubmit事件
        document.getElementById("form").onsubmit = function(){
            //调用用户校验方法   chekUsername();
            //调用密码校验方法   chekPassword();
            //return checkUsername() && checkPassword();

            return checkUsername() && checkPassword();
        }

        //给用户名和密码框分别绑定离焦事件
        document.getElementById("username").onblur = checkUsername;
        document.getElementById("password").onblur = checkPassword;
    }

    //校验用户名
    function checkUsername(){
        //1.获取用户名的值
        var username = document.getElementById("username").value;
        //2.定义正则表达式
        var reg_username = /^\w{6,12}$/;
        //3.判断值是否符合正则的规则
        var flag = reg_username.test(username);
        //4.提示信息
        var s_username = document.getElementById("s_username");

        if(flag){
            //提示绿色对勾
            s_username.innerHTML = "<img width='35' height='25' src='img/gou.png'/>";
        }else{
            //提示红色用户名有误
            s_username.innerHTML = "用户名格式有误";
        }
        return flag;
    }

    //校验密码
    function checkPassword(){
        //1.获取用户名的值
        var password = document.getElementById("password").value;
        //2.定义正则表达式
        var reg_password = /^\w{6,12}$/;
        //3.判断值是否符合正则的规则
        var flag = reg_password.test(password);
        //4.提示信息
        var s_password = document.getElementById("s_password");

        if(flag){
            //提示绿色对勾
            s_password.innerHTML = "<img width='35' height='25' src='img/gou.png'/>";
        }else{
            //提示红色用户名有误
            s_password.innerHTML = "密码格式有误";
        }
        return flag;
    }

    //切换验证码图片
  	document.getElementByTagname("changeImage").onclick= function (){
		//document.getElementById("img_check").src = "/CamKg/img/verify_code.jpg";
		window.alert("咋地啦");
    }

</script>
</body>
</html>