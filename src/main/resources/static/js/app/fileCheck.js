const maxFileSize = 1024* 1024;  // 1MB

// 파일 사이즈 체크
function fileCheck( file, maxSize)
{
    // 사이즈체크
    var fileSize = 0;

    // 브라우저 확인
    var browser=navigator.appName;
    // 익스플로러일 경우
    if (browser==="Microsoft Internet Explorer")
    {
        var oas = new ActiveXObject("Scripting.FileSystemObject");
        fileSize = oas.getFile( file.value ).size;
    }
    // 익스플로러가 아닐경우
    else {fileSize = file.files[0].size;}

    if(fileSize > maxSize)
    {
        alert("첨부파일 사이즈는 1MB 이내로 등록 가능합니다.  ");
        return false;
    }
    //alert("파일사이즈 : "+ fileSize +", 최대파일사이즈 : 5MB");
    return true;
}

function submit_file_form(){
    if(document.forms['pictureForm'].picture.files.length ===0 ){
        alert("이미지를 선택해주세요.");
        return;
    }
    if (fileCheck(document.forms['pictureForm'].picture, maxFileSize)){ // document.forms['pictureForm'].picture
        document.forms['pictureForm'].submit();
    }else{
        window.history.back();
    }
}

// 이미지 선택
document.getElementById("picture").onchange = function () {
    if(this.value !== "") {
        if(!fileCheck(document.forms['pictureForm'].picture, maxFileSize)) { // 초기화
            this.value = "";
        }
    }
};
