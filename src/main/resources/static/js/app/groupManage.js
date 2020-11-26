var group_manage = {
    init : function () {
        var _this = this;
        $('#btn-save').on('click', function () {
            _this.save();
        });

        $('#btn-update').on('click', function () {
            _this.update();
        }); // id: btn-update인 버튼이 click 됐을 때 update함수 실행

        $('#btn-delete').on('click', function () {
            var result = confirm("그룹을 정말 삭제하시겠습니까?");
            if(result){
                _this.delete();
            }
        });
    },
    arrayToComma : function (){ // param : array
        var x = "${group.tags}";
        x = x.toString().slice(1,-1);
        console.log(x);
        document.getElementById("tags").innerHTML = x;
    },
    commaToArray : function (param){
        return param.split(',');
    },
    save : function () {
        var data = {
            name: $('#name').val(),
            ownerId: $('#id').val(),
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

        $.ajax({
            type: 'POST',
            url: '/api/v1/group',
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('새로운 그룹이 생성되었습니다.');
            window.location.href = '/group/manage';
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

        var id = $('#groupId').text();

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
        var id = $('#groupId').text();

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

group_manage.init();

var windowLoc = $(location).attr('pathname'); // to get window.location.pathname

window.onload = function() {
    if($('body').is('#update')){ // 생성화면에서는 실행안됨.
        group_manage.arrayToComma();
    }

};