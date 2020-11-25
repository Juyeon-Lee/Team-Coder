var user_info = {

    init : function () {
        var _this = this;

        $('#btn-update').on('click', function () {
            _this.update();
        }); // id: btn-update인 버튼이 click 됐을 때 update함수 실행

        $('#btn-delete').on('click', function () {
            var result = confirm("정말 탈퇴하시겠습니까?");
            if(result){
                _this.delete();
            }
        });

        $('#btn-picture').on('click', function () {
            _this.updatePic();
        });
    },
    arrayToComma : function (){ // param : array
        var x = "${user.tags}";
        x = x.toString().slice(1,-1);
        console.log(x);
        document.getElementById("tags").innerHTML = x;
    },
    commaToArray : function (param){
        return param.split(',');
    },
    update : function () {
        var data = {
            name: $('#name').val(),
            tags: this.commaToArray($('#tags').val()),
            email: $('#email').val(),
            birth: $('#birth').val(),
            education: $('#education').val().toUpperCase(),
            location: $('#location').val()
        };

        var id = $('#id').val();

        $.ajax({
            type: 'PUT', // 수정할 때
            url: '/api/v1/user/'+id,  // 어느 게시글을 수정할 지
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('내정보가 수정되었습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    delete : function () {
        var id = $('#id').val();

        $.ajax({
            type: 'DELETE',
            url: '/api/v1/user/'+id,
            dataType: 'json',
            contentType:'application/json; charset=utf-8'
        }).done(function() {
            alert('탈퇴하였습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    updatePic : function () {
        var id = $('#id').val();

        $.ajax({
            type: 'POST',
            enctype: 'multipart/form-data',
            url: '/api/v1/user/pic/'+id,  // 어느 게시글을 수정할 지
            data: $('#picture').val(),
            processData: false,
        }).done(function() {
            alert('내정보가 수정되었습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    }
};

user_info.init();

window.onload = function() {
    user_info.arrayToComma();

};

// $(document).ready(function(){ //실행될 코드(실행이 빠르다) });

