
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
        r: {},
        list: [],
    },
    created: function () {
        $.getJSON("../data/tj",function (r) {
            r = r.r;
            var s = 0;
            if(r <0.45){
                s = 0
            }else if(r <0.55){
                s = 1
            }else{
                s =2;
            }
            var data = {
                positive_prob :r,
                sentiment : s
            }
            vm.r = data;
        })
    },
    methods: {

    }
});