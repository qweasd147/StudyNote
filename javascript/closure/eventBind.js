function bindEvent(){
    var innerData = "inner scope data";

    $('#bindBtn').on('click', function(){
        alert(innerData);
    });
}

bindEvent();