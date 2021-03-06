$(function () {
    showCheckinfo();
    $(".more").on("click", function () {
        count++;
        var html = '';
        var len = count * 10;
        if (len <= arr.length) {
            for (var i = 0; i < len; i++) {
                var signedDate = getFormatDate(new Date(arr[i].signedDate));
                var startTime = getFormatDate(new Date(arr[i].startTime));
                html += '<tr><td>' + signedDate + '</td><td>' + arr[i].elecSupervisorNo + '</td>' +
                    '<td>' + arr[i].adminRegion + '</td><td>' + arr[i].location + '</td>' +
                    '<td>' + arr[i].totalArea + '</td><td>' + startTime + '</td>' + '</tr>';
            }
            $("#checkinfo").html(html);
        } else {
            $(this).html("已无更多数据").attr("disabled", true).after('<button class="btn btn-link top">返回顶部</button>');
            $(".top").click(function () {
                var sc = $(window).scrollTop();
                $('body,html').animate({scrollTop: 0}, 500);
            });
        }
    })
});

function showCheckinfo() {
    var req = {"cname": companyName, "pageNumber": 1, "pageSize": 200}
    $.ajax({
        type: 'post',
        data: JSON.stringify(req),
        async: false,
        contentType: 'application/json',
        url: '/apis/openeyes/getCheckInfo.json',
        success: function (res) {
            if (res.success) {
                if (res.data.error_code == 0 || res.data.error_code == null) {
                    arr = res.data.result.items;
                    var thead = '<tr><th class="text-left">日期</th><th class="text-left">类型</th><th class="text-left">结果</th><th class="text-left">检查实施机关</th></tr>';
                    $("#checkinfo").prev().html(thead);
                    var html = '';
                    if (arr.length > 10) {
                        for (var i = 0; i < 10; i++) {
                            var signedDate = getFormatDate(new Date(arr[i].signedDate));
                            var startTime = getFormatDate(new Date(arr[i].startTime));
                            html += '<tr><td>' + signedDate + '</td><td>' + arr[i].elecSupervisorNo + '</td>' +
                                '<td>' + arr[i].adminRegion + '</td><td>' + arr[i].location + '</td>' +
                                '<td>' + arr[i].totalArea + '</td><td>' + startTime + '</td>' + '</tr>';
                        }
                    } else {
                        for (var i = 0; i < arr.length; i++) {
                            var signedDate = getFormatDate(new Date(arr[i].signedDate));
                            var startTime = getFormatDate(new Date(arr[i].startTime));
                            html += '<tr><td>' + signedDate + '</td><td>' + arr[i].elecSupervisorNo + '</td>' +
                                '<td>' + arr[i].adminRegion + '</td><td>' + arr[i].location + '</td>' +
                                '<td>' + arr[i].totalArea + '</td><td>' + startTime + '</td>' + '</tr>';
                        }
                        $(".more").html("暂无更多数据").attr("disabled", true).after('<button class="btn btn-link top">返回顶部</button>');
                        $(".top").click(function () {
                            var sc = $(window).scrollTop();
                            $('body,html').animate({scrollTop: 0}, 500);
                        });
                    }

                    $("#checkinfo").html(html);
                } else {
                    var html = '<div class="not-data" style="text-align:center"><img src="/images/notData.png" /><p class="tips-text">暂无数据</p></div>';
                    $("#checkinfo").html(html);
                    window.setTimeout(goBack, 2000);
                }
            }
        }
    });
}