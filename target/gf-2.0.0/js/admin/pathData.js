$(document).ready(function(){
    //初始化刷新数据
    $(window).on('load', function() {
        $('.selectpicker').selectpicker('refresh');
    });


    $.get("../data/pathSelect",function(data){
        var data1=JSON.parse(data);
        var app=data1.result.app;
        console.log(app);
        for(var i=0 ;i< app.length ;i++){
            //先创建好select里面的option元素
            var option=document.createElement("option");
            //转换DOM对象为JQ对象,好用JQ里面提供的方法 给option的value赋值
            $(option).val(app[i].value);
            //给option的text赋值,这就是你点开下拉框能够看到的东西
            $(option).text(app[i].name);
            //获取select 下拉框对象,并将option添加进select
            console.log(option);
            $('#slpkApp').append(option);
            console.log("-----")

        }
        var path=data1.result.path;
        for(var i=0 ;i< path.length ;i++){
            //先创建好select里面的option元素
            var option=document.createElement("option");
            //转换DOM对象为JQ对象,好用JQ里面提供的方法 给option的value赋值
            $(option).val(path[i].value);
            //给option的text赋值,这就是你点开下拉框能够看到的东西
            $(option).text(path[i].name);
            //获取select 下拉框对象,并将option添加进select
            $('#slpkPath').append(option);

        }
        $('.selectpicker').selectpicker('refresh');
    });

    $("#clear").click(function(){
        $("#pathData tr").not(':eq(0)').empty();
    });

    $("#query").click(function(){
        var appId = $('#slpkApp').val();
        var companyKey=$('#slpkPath').val();
        var startDate = $('#startDate').val();
        var endDate = $('#endDate').val();
        var url = "../data/pathDataList?startDate="+startDate+"&endDate="+endDate+"&appId="+appId+"&companyKey="+companyKey;
        console.log(url);
        $.get(url,function(data){
            console.log(data);
            var data1=JSON.parse(data);
            if (data1.code!=0) {
                $("#pathData").children("tr").remove();
                console.log("!!")
                return;
            }
            var result = (data1.result);
            var one = (result[0]);
            console.log(one);
            var r = document.createElement('tr');
            var d1 = document.createElement('td');
            var d2 = document.createElement('td');
            var d3 = document.createElement('td');
            var d4 = document.createElement('td');
            var d5= document.createElement('td');
            var d6 = document.createElement('td');
            var d7 = document.createElement('td');
            var d8 = document.createElement('td');
            var d9 = document.createElement('td');
            var d10 = document.createElement('td');
            if ($('#startDate').val()==""){
                $(d1).text("创建");
            } else {
                $(d1).text($('#startDate').val());
            }
            $(r).append(d1);
            if ($('#endDate').val()==""){
                $(d2).text("现在");
            } else {
                $(d2).text($('#endDate').val());
            }
            $(r).append(d2);
            $(d3).text(one.path);
            $(r).append(d3);
            $(d4).text(one.app);
            $(r).append(d4);
            $(d5).text(one.fromPathCount);
            $(r).append(d5);
            $(d6).text(one.fromAppCount);
            $(r).append(d6);
            $(d7).text(one.fromAppTrueCount);
            $(r).append(d7);
            $(d8).text(one.toPathCount);
            $(r).append(d8);
            $(d9).text(one.toPathTrueCount);
            $(r).append(d9);
            $(d10).text((one.toPathTrueCount/one.fromPathCount).toFixed(2));
            $(r).append(d10);
            $("#pathData").append(r);



        });
    });

});

