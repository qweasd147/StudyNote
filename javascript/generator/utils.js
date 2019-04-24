/**
 * 백엔드와 통신하는 fake function
 * @param {*} url 
 * @param {*} dummyTime 
 */
function request(url, dummyTime){
    
    dummyTime = dummyTime || 2000;
    console.log('input url => '+url);
    
    return new Promise(function(resolve, reject){
        setTimeout(function(){
            resolve("end : "+url);
        },dummyTime);
	});
}

/**
 * generator 함수를 끝까지 안전하게 실행 시키는 유틸성 함수
 * @param {*} gen generator
 */
function run(gen){
	var args = [].slice.call(arguments,1);
    var it = gen.apply(this, args);
    
    return Promise.resolve()
        .then(function handleNext(value){
            var next = it.next(value);
            
            return (function handleResult(next){
                if(next.done) return next.value;
                    			
                return Promise.resolve(next.value)
                    .then(
                        handleNext
                        , function handleErr(err){
                            return Promise.resolve(it.throw(err))
                                .then(handleResult);
                        }
                    )
			})(next)
			
		});
}