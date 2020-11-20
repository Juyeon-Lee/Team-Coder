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
            _this.delete();
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
            window.location.href = '/group';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    update : function () {
        var data = {
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
            alert('글이 수정되었습니다.');
            window.location.href = '/group';
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
            alert('글이 삭제되었습니다.');
            window.location.href = '/group';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    }

};

group_manage.init();


window.onload = function() {
    //group_manage.arrayToComma();

};