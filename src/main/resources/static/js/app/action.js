const csrfToken = $('#_csrf').attr('content');
const csrfHeader = $('#_csrf_header').attr('content');

var action = {
    init : function () {
        var _this = this;
        $('#btn-apply').on('click', function () {
            if (clicked) {
                _this.apply();
            } else {
                // 다음 창 입력
                alert("지원 동기와 나의 역량을 설명하고 원하는 활동 계획이 있다면 기입해주세요.")
                var div = document.getElementById("applyComment");
                div.style.display = "block";
                clicked = !clicked;
            }
        });
        $('#btn-quit').on('click', function () {
            var result = confirm("지원을 정말 취소하시겠습니까?");
            if(result){
                _this.quit();
            }
        });
        $('#btn-approve').on('click', function () {
            var result = confirm("지원을 승인하시겠습니까?");
            if(result){
                _this.approve();
            }
        });
        $('#btn-reject').on('click', function () {
            var result = confirm("지원을 거절하시겠습니까?");
            if(result){
                _this.reject();
            }
        });
        $('#btn-store').on('click', function () {
            _this.store();
        });
        $('#btn-delete').on('click', function () {
            var result = confirm("저장소에서 삭제하시겠습니까?");
            if(result){
                _this.deleteFromStorage();
            }
        });
    },
    apply : function() {
        var data = { // groupName, comment
            groupId: $('#id').text(),
            comment: $('#comment').val()
        };

        $.ajax({
            type: 'POST',
            url: '/api/v1/participate/apply',
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data),
            beforeSend: function (xhr){
                xhr.setRequestHeader(csrfHeader, csrfToken);
            }
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
            contentType:'application/json; charset=utf-8',
            beforeSend: function (xhr){
                xhr.setRequestHeader(csrfHeader, csrfToken);
            }
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
            contentType:'application/json; charset=utf-8',
            beforeSend: function (xhr){
                xhr.setRequestHeader(csrfHeader, csrfToken);
            }
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
            contentType:'application/json; charset=utf-8',
            beforeSend: function (xhr){
                xhr.setRequestHeader(csrfHeader, csrfToken);
            }
        }).done(function() {
            alert('지원을 거절하였습니다.');
            window.location.href = '/group/'+groupId+'/apply/users';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    store : function () {
        var groupId = $('#id').text();

        $.ajax({
            type: 'POST',
            url: '/api/v1/storage/'+groupId,
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            beforeSend: function (xhr){
                xhr.setRequestHeader(csrfHeader, csrfToken);
            }
        }).done(function() {
            alert('저장에 성공했습니다.');
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    deleteFromStorage : function () {
        var id = $('#storeId').text(); //partiId

        $.ajax({
            type: 'DELETE',
            url: '/api/v1/storage/'+id,
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            beforeSend: function (xhr){
                xhr.setRequestHeader(csrfHeader, csrfToken);
            }
        }).done(function() {
            alert('저장소에서 삭제되었습니다.');
            window.location.href = '/user/storage';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    }
};

action.init();

var clicked = false;
