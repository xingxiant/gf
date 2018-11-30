$(function () {
    $("#jqGrid").jqGrid({
        url: '../data/list',
        datatype: "json",
        colModel: [
            {
                label: 'id',
                name: 'id',
                index: 'id',
                width: 50,
                key: true,
                hidden: true,
                formatter: function (v, a, r) {
                    return "<a onclick='vm.info(\"" + r.id + "\")'>" + v + " </a>"
                }
            },
            {
                label: '文本内容',
                name: 'content',
                index: 'content',
                width: 300,
                formatter: function (v, a, r) {
                    return "<a onclick='vm.info(\"" + r.id + "\")'>" + v + " </a>"
                }
            },
            {
                label: '上传时间',
                name: 'gmttime',
                index: 'gmtTime',
                width: 80
            }
        ],
        viewrecords: true,
        height: 385,
        rowNum: 10,
        rowList: [10, 30, 50],
        rownumbers: true,
        rownumWidth: 25,
        autowidth: true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader: {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames: {
            page: "page",
            rows: "limit",
            order: "order"
        },
        gridComplete: function () {
            //隐藏grid底部滚动条
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
        }
    });
});

var vm = new Vue({
    el: '#rrapp',
    data: {
        q: {
            content: ''
        },
        showAdd: false,
        showInfo: false,
        showList: true,
        title: null,
        data: {},
        r: null,
        r2: null,
        list: [],
        list2: []
    },
    created: function () {

    },
    methods: {
        query: function () {
            vm.reload();
        }
        ,
        add: function () {
            vm.showAdd = true;
            vm.showList = false;
            vm.title = "新增";
            vm.data = {};
        }
        ,
        update: function (event) {
            var id =
                getSelectedRow();
            if (id == null
            ) {
                return;
            }

            vm.showAdd = true;
            vm.showList = false;
            vm.title = "修改";

            vm.getInfo(id)


        }
        ,
        saveOrUpdate: function (event) {
            var url = vm
                .data.id ==
            null ? "../data/save" : "../data/update";
            $.ajax({
                type: "POST",
                url: url,
                data: JSON.stringify(vm.data),
                success: function (r) {
                    if (r.code === 0) {
                        alert('操作成功', function (index) {
                            vm.reload();
                        });
                    } else {
                        alert(r.msg);
                    }
                }
            });
        }
        ,
        del: function (event) {
            var ids = getSelectedRows();
            if (ids == null) {
                return;
            }

            confirm('确定要删除选中的记录？', function () {
                $.ajax({
                    type: "POST",
                    url: "../data/delete",
                    data: JSON.stringify(ids),
                    success: function (r) {
                        if (r.code == 0) {
                            alert('操作成功', function (index) {
                                $("#jqGrid").trigger("reloadGrid");
                            });
                        } else {
                            alert(r.msg);
                        }
                    }
                });
            });
        }
        ,
        getInfo: function (id) {
            $.get("../data/info/" + id, function (r) {
                vm.data = r.data;
                vm.data.content = r.data.content.split("\n").join("<br>")
            });

            layer.load(0);

            $.get("../data/sf2/" + id, function (r) {
                layer.closeAll();

                var total = 0;
                for (var k in r.r) {
                    total += r.r[k];
                }
                var list = [];
                var names = [];
                var vs = [];
                for (var k in r.r) {
                    list.push({
                        k: k,
                        v: r.r[k],
                        percent: r.r[k] / total
                    })
                    names.push(k)
                    vs.push({
                        name : k,
                        value :parseInt(( r.r[k] / total) * 100)
                    })
                }
                vm.r = r.r;

                total = 0;
                var names1 = [];
                var vs1 = [];
                var list2 = [];
                for (var k in r.r2) {
                    total += r.r2[k];
                }

                for (var k in r.r2) {
                    list2.push({
                        k: k,
                        v: r.r2[k],
                        percent: r.r2[k] / total
                    })
                    names1.push(k)
                    vs1.push({
                        name : k,
                        value : parseInt(( r.r2[k] / total) * 100)
                    })
                }

                vm.r2 = r.r2;
                vm.list = list;
                vm.list2 = list2;



                var echartsPie;

                var option = {
                    title : {
                        text: '情感小类占比',
                        subtext: ' ',
                        x:'center'
                    },
                    tooltip : {
                        trigger: 'item',
                        formatter: "{a} <br/>{b} : {c}% "
                    },
                    legend: {
                        orient : 'vertical',
                        x : 'left',
                        data: names
                    },
                    toolbox: {
                        show : true,
                        feature : {
                            mark : {show: true},
                            dataView : {show: true, readOnly: false},
                            magicType : {
                                show: true,
                                type: ['pie', 'funnel'],
                                option: {
                                    funnel: {
                                        x: '25%',
                                        width: '50%',
                                        funnelAlign: 'left',
                                        max: 1548
                                    }
                                }
                            },
                            restore : {show: true},
                            saveAsImage : {show: true}
                        }
                    },
                    calculable : true,
                    series : [
                        {
                            name:'小类',
                            type:'pie',
                            radius : '55%',//饼图的半径大小
                            center: ['50%', '60%'],//饼图的位置
                            data:vs
                        }
                    ]
                };

                echartsPie = echarts.init(document.getElementById('echartsPie'));
                echartsPie.setOption(option);


                var option1 = {
                    title : {
                        text: '情感大类占比',
                        subtext: ' ',
                        x:'center'
                    },
                    tooltip : {
                        trigger: 'item',
                        formatter: "{a} <br/>{b} : {c} "
                    },
                    legend: {
                        orient : 'vertical',
                        x : 'left',
                        data: names1
                    },
                    toolbox: {
                        show : true,
                        feature : {
                            mark : {show: true},
                            dataView : {show: true, readOnly: false},
                            magicType : {
                                show: true,
                                type: ['pie', 'funnel'],
                                option: {
                                    funnel: {
                                        x: '25%',
                                        width: '50%',
                                        funnelAlign: 'left',
                                        max: 1548
                                    }
                                }
                            },
                            restore : {show: true},
                            saveAsImage : {show: true}
                        }
                    },
                    calculable : true,
                    series : [
                        {
                            name:'大类',
                            type:'pie',
                            radius : '55%',//饼图的半径大小
                            center: ['50%', '60%'],//饼图的位置
                            data:vs1
                        }
                    ]
                };

                echartsPie1 = echarts.init(document.getElementById('echartsPie1'));
                echartsPie1.setOption(option1);
            });
        }
        ,
        info: function (id) {
            if (isNaN(id)) {
                id
                    = getSelectedRow();
                if (id == null
                ) {
                    return;
                }
            }
            vm.showAdd = false;
            vm.showList = false;
            vm.showInfo = true;
            vm.title = "详情";

            vm.getInfo(id)
        }
        ,
        reload: function (event) {
            vm.showList = true;
            vm.showInfo = false;
            vm.showAdd = false;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: vm.q,
                page: page
            }).trigger("reloadGrid");
        }
    }
});