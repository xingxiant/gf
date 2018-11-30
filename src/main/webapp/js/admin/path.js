$(function () {
    $("#jqGrid").jqGrid({
        url: '../data/pathList',
        datatype: "json",
        colModel: [
            {
                label: 'id',
                name: 'id',
                index: 'id',
                width: 50,
                //key: true,
                hidden: true,
                formatter: function (v, a, r) {
                    return "<a onclick='vm.info(\"" + r.id + "\")'>" + v + " </a>"
                }
            },
            {
                label: '渠道名称',
                name: 'name',
                index: 'name',
                width: 50,
            },
            {
                label: '渠道标识',
                name: 'companyKey',
                index: 'companykey',
                width: 50
            },
            {
                label: 'appId',
                name: 'appId',
                index: 'app_id',
                width: 50,
            },
            {
                label: 'app名称',
                name: 'appName',
                index: 'app_name',
                width: 50,
            },
            {
                label: '回调渠道地址',
                name: 'callBackPath',
                index: 'callback_path',
                width: 50,
            },
            {
                label: 'app回调我们的地址',
                name: 'callBackApp',
                index: 'callback_app',
                width: 50,
            },
            {
                label: 'app主机',
                name: 'appHost',
                index: 'app_host',
                width: 50,
            },
            {
                label: '创建时间',
                name: 'createTime',
                index: 'time',
                width: 50,
            }

        ],
        viewrecords: true,
        height: 400,
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
            name: '',
            companyKey: '',
            appId: '',
            appName: '',
            callBackPath: '',
            callBackApp: ''
        },
        showAdd: false,
        showInfo: false,
        showList: true,
        title: null,
        data: {},
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
            null ? "../data/pathSave" : "../data/pathUpdate";
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
            $.get("../data/pathInfo/" + id, function (r) {
                vm.data = r.data;
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