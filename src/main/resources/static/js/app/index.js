const csrfToken = $('#_csrf').attr('content');
const csrfHeader = $('#_csrf_header').attr('content');

var main = {
    init : function () {
        var _this = this;
        //안쓰임
        $('#btn-update').on('click', function () {
            _this.update();
        }); // id: btn-update인 버튼이 click 됐을 때 update함수 실행
    },
    update : function () {
        var data = {
            aim: $('#aim').val(),
            period: $('#period').val(),
            loc: $('#location').val(),
            age: $('#age').val(),
            tags: $('#tags').val()
        };
        console.log("javascript 함수 실행중");
        $.ajax({
            type: 'POST',
            url: '/search/redirect',
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data),
            beforeSend: function (xhr){
                xhr.setRequestHeader(csrfHeader, csrfToken);
            }
        }).done(function() {
            alert('결과 화면으로 이동합니다');
            window.location.href = '/search/result'
            //window.location.href = '/search/result/'+aim+'/'+period+'/'+age+'/'+loc+'/'+tag;
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });

        // $.redirect("/search/result",
        //     {
        //         aim : $('#aim').val(),
        //         period : $('#period').val(),
        //         loc : $('#location').val(),
        //         age : $('#age').val(),
        //         tag : $('#tags').val()
        //     }, "GET")
    }
};

main.init();