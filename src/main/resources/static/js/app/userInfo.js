function chkEmail(str) {
    var regExp = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
    return regExp.test(str); //true or false
}
function convertTags(str){
    return str.toLowerCase().replace(/\s/g,'');
}
// 공백, null, undefined 체크
function isEmpty(target) {
    return target === undefined || target === null || target === '';
}
// convert array to comma_string
function arrayToComma(){
    var x = "${user.tags}";
    x = x.toString().slice(1,-1);
    document.getElementById("tags").innerHTML = x;
}
function commaToArray(param){
    return param.split(',');
}

const csrfToken = $('#_csrf').attr('content');
const csrfHeader = $('#_csrf_header').attr('content');


var user_info = {

    init : function () {
        var _this = this;

        $('#btn-update').on('click', function () {
            var form_continue = true;
            if( isEmpty($('#name').val())){
                alert("이름 항목은 필수 입력값입니다.")
                form_continue = false;
            }if( isEmpty($('#email').val())){
                alert("이메일 항목은 필수 입력값입니다.")
                form_continue = false;
            }

            if(form_continue){ _this.update();}
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
    // 유저 정보 수정
    update : function () {
        var data = {
            name: $('#name').val(),
            tags: commaToArray(convertTags($('#tags').val())),
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
            data: JSON.stringify(data),
            beforeSend: function (xhr){
                xhr.setRequestHeader(csrfHeader, csrfToken);
            }
        }).done(function() {
            alert('내정보가 수정되었습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    // 탈퇴
    delete : function () {
        var id = $('#id').val();

        $.ajax({
            type: 'DELETE',
            url: '/api/v1/user/'+id,
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            beforeSend: function (xhr){
                xhr.setRequestHeader(csrfHeader, csrfToken);
            }
        }).done(function() {
            alert('탈퇴하였습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    }
};

user_info.init();

window.onload = function() {
    arrayToComma();
};

// $(document).ready(function(){ //실행될 코드(실행이 빠르다) });

