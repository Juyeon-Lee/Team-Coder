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

        $('#btn-update').on('click', function () {
            _this.update();
        }); // id: btn-update인 버튼이 click 됐을 때 update함수 실행

        $('#btn-delete').on('click', function () {
            _this.delete();
        });
    },
    apply : function () {
        var data = { // groupName, comment
            groupId: $('#id').text(),
            comment: $('#comment').val()
        };

        $.ajax({
            type: 'POST',
            url: '/api/v1/participate',
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('지원에 성공했습니다.');
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    update : function () {
        var data = {
            name: $('#name').val(),
            aim: $('#aim').val(),
            description: $('#description').val(),
            maxNum: $('#maxNum').val(),
            start: $('#start').val(),
            end: $('#end').val(),
            minAge: $('#minAge').val(),
            maxAge: $('#maxAge').val(),
            tags: this.commaToArray($('#tags').val()),
            education: $('#education').val(),
            location: $('#location').val()
        };

        var id = $('#id').val();

        $.ajax({
            type: 'PUT', // 수정할 때
            url: '/api/v1/group/'+id,  // 어느 게시글을 수정할 지
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('그룹이 수정되었습니다.');
            window.location.href = '/group/manage';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    delete : function () {
        var id = $('#id').val();

        $.ajax({
            type: 'DELETE',
            url: '/api/v1/group/'+id,
            dataType: 'json',
            contentType:'application/json; charset=utf-8'
        }).done(function() {
            alert('그룹이 삭제되었습니다.');
            window.location.href = '/group/manage';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    }

};

action.init();

var clicked = false;
