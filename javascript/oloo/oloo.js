var Ajax = {
    callAjax:function(opt){
        opt = opt || {};
        /*
        var ajaxObj = $.ajax({
            url: opt.url
            , type : opt.method || "GET"
            , data : opt.data || {}
            , beforeSend: function( xhr ) {
                opt.handleBefore && opt.handleBefore(xhr);
            }
        });

        ajaxObj.done(function(data){
            opt.handleDone && opt.handleDone(data);
        });

        ajaxObj.fail(function(xhr){
            opt.handleFail && opt.handleFail(xhr);
        });
        */

       opt.handleBefore && opt.handleBefore();
       opt.handleDone && opt.handleDone("처리 완료");
    }
}

var AjaxWithMask = Object.create(Ajax);

AjaxWithMask.callAjaxWithMask = function(opt){
    
    var _handleBefore = opt.handleBefore;
    var _handleDone = opt.handleDone;
    var _handleFail = opt.handleFail;
    
    opt.handleBefore = function(xhr){
        //TODO : show loading mask
        console.log("on loading mask");
        _handleBefore && _handleBefore(xhr);
    }
    opt.handleDone = function(data){
        //TODO : hide loading mask
        console.log("off loading mask");
        _handleDone && _handleDone(data);
    }
    opt.handleFail = function(xhr){
        //TODO : hide loading mask
        console.log("off loading mask");
        _handleFail && _handleFail(xhr);
    }

    this.callAjax(opt);
}

//var Board = Object.create(Ajax);
var Board = Object.create(AjaxWithMask);

Board.setConfig = function(api, boardName, boardCode){
    this.api = api;
    this.boardName = boardName;
    this.boardCode = boardCode;
}

Board.insert = function(_title, _contents){
    var opt = {
        url : this.api
        , data : {
            title : _title
            , contents : _contents
            , boardName : this.boardName
            , boardCode : this.boardCode
        }
        , method : "POST"
        , handleDone : function(msg){
            alert(msg);
        }
    }

    //this.callAjax(opt);
    this.callAjaxWithMask(opt);
}

Board.update = function(_title, _contents){
    var opt = {
        url : this.api
        , data : {
            title : _title
            , contents : _contents
            , boardName : this.boardName
            , boardCode : this.boardCode
        }
        , method : "PUT"
        , handleDone : function(msg){
            alert(msg);
        }
    }

    //this.callAjax(opt);
    this.callAjaxWithMask(opt);
}

Board.delete = function(boardID){
    var opt = {
        url : this.api+"/"+boardID
        , method : "DELETE"
        , handleDone : function(msg){
            alert(msg);
        }
    }

    //this.callAjax(opt);
    this.callAjaxWithMask(opt);
}

Board.select = function(boardID, handleData){
    var opt = {
        url : this.api+"/"+boardID
        , method : "GET"
        , handleDone : function(data){
            handleData(data);
        }
    }

    //this.callAjax(opt);
    this.callAjaxWithMask(opt);
}

var board1 = Object.create(Board);
board1.setConfig("/board1", "게시판1", 1);

var board2 = Object.create(Board);
board1.setConfig("/board2", "게시판2", 2);

board1.insert("제목","내용");
board1.delete(123456);