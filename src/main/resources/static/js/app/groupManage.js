function convertTags(str){
    return str.toLowerCase().replace(/\s/g,'');
}
// 공백, null, undefined 체크
function isEmpty(target) {
    return target === undefined || target === null || target === '';
}
// convert array to comma_string - tags
function arrayToComma(){
    var x = "${group.tags}";
    x = x.toString().slice(1,-1);
    document.getElementById("tags").innerHTML = x;
}
function commaToArray(param){
    return param.split(',');
}

//form 공란 검사
function emptyFormCheck() {
    if (isEmpty($('#name').val())) {
        alert("이름 항목은 필수 입력값입니다.")
        return  false;
    }if (isEmpty($('#aim').val())){
        alert("목적 항목은 필수 입력값입니다.")
        return  false;
    }if(isEmpty($('#tags').val())){
        alert("태그 항목은 필수 입력값입니다. 그룹의 대한 구체적인 정보를 입력하는 것이 좋습니다.")
        return  false;
    }if(isEmpty($('#start').val()) || isEmpty($('#end'))){
        alert("활동 예정일을 입력해주세요.")
        return  false;
    }
    let des = $('#description').val();
    if(isEmpty(des)){
        alert("활동 목표, 진행 계획, 원하는 조건, 신청자에게 지원시 요구할 사항 등을 적어주세요.")
        return  false;
    }if(des.length > 500){
        alert("마지막의 '팀원들에게 하고싶은 말'을 500자 이내로 작성해주세요.")
        return false;
    }
    return true;
}

const csrfToken = $('#_csrf').attr('content');
const csrfHeader = $('#_csrf_header').attr('content');

var group_manage = {
    init : function () {
        var _this = this;
        $('#btn-save').on('click', function () {
            if(emptyFormCheck()){ _this.save(); }
        });

        $('#btn-update').on('click', function () {
            if(emptyFormCheck()){ _this.update(); }
        }); // id: btn-update인 버튼이 click 됐을 때 update함수 실행

        $('#btn-delete').on('click', function () {
            var result = confirm("그룹을 정말 삭제하시겠습니까?");
            if(result){
                _this.delete();
            }
        });
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
            tags: commaToArray(convertTags($('#tags').val())),
            education: $('#education').val(),
            location: $('#location').val()
        };

        $.ajax({
            type: 'POST',
            url: '/api/v1/group',
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data),
            beforeSend: function (xhr){
                xhr.setRequestHeader(csrfHeader, csrfToken);
            }
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
            tags: commaToArray(convertTags($('#tags').val())),
            education: $('#education').val(),
            location: $('#location').val()
        };

        var id = $('#groupId').text();

        $.ajax({
            type: 'PUT', // 수정할 때
            url: '/api/v1/group/'+id,  // 어느 게시글을 수정할 지
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data),
            beforeSend: function (xhr){
                xhr.setRequestHeader(csrfHeader, csrfToken);
            }
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
            contentType:'application/json; charset=utf-8',
            beforeSend: function (xhr){
                xhr.setRequestHeader(csrfHeader, csrfToken);
            }
        }).done(function() {
            alert('그룹이 삭제되었습니다.');
            window.location.href = '/group/manage';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    }
};

group_manage.init();

window.onload = function() {
    if($('body').is('#update')){ // 생성화면에서는 실행안됨.
        arrayToComma();
    }

};