var action = {
    init : function () {
        var _this = this;
        $('#btn-apply').on('click', function () {
            if(clicked){
                _this.apply();
            }else{
                // 다음 창 입력
                var div = document.getElementById("applyComment");
                div.style.display = "block";
                clicked= !clicked;
            }
        });
        $('#btn-quit').on('click', function () {
            _this.quit();
        });
        $('#btn-approve').on('click', function () {
            _this.approve();
        });
        $('#btn-reject').on('click', function () {
            _this.reject();
        });
    },
    apply : function () {
        var data = { // groupName, comment
            groupId: $('#id').text(),
            comment: $('#comment').val()
        };

        $.ajax({
            type: 'POST',
            url: '/api/v1/participate/apply',
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('지원에 성공했습니다.');
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    quit : function () {
        var id = $('#partiId').text(); //partiId

        $.ajax({
            type: 'DELETE',
            url: '/api/v1/participate/'+id,
            dataType: 'json',
            contentType:'application/json; charset=utf-8'
        }).done(function() {
            alert('지원/참가가 취소되었습니다.');
            window.location.href = '/user/apply';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    approve : function () {
        var id = $('#partiId').text(); //partiId
        var groupId = $('#groupId').text();

        $.ajax({
            type: 'POST',
            url: '/api/v1/participate/approve/'+id,
            dataType: 'json',
            contentType:'application/json; charset=utf-8'
        }).done(function() {
            alert('지원을 승인하였습니다.');
            window.location.href = '/group/'+groupId+'/apply/users';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    reject : function () {
        var id = $('#partiId').text(); //partiId
        var groupId = $('#groupId').text();

        $.ajax({
            type: 'POST',
            url: '/api/v1/participate/reject/'+id,
            dataType: 'json',
            contentType:'application/json; charset=utf-8'
        }).done(function() {
            alert('지원을 거절하였습니다.');
            window.location.href = '/group/'+groupId+'/apply/users';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    }
};

action.init();

var clicked = false;
