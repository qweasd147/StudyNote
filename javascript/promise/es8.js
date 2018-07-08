function promise1(text){
    return new Promise((resolve, reject)=>{
        setTimeout(()=>{
            return resolve(text+" END");
        },2000);
    });
}

function promise2(text){
	return new Promise((resolve, reject)=>{
		setTimeout(()=>{
			return resolve(text+" END");
		},3000);
	});
}

const after5sec = async ()=>{
    const p1Result = await promise1("첫번째 결과물");
    const p2Result = await promise2("두번째 결과물");
	
	//5초 후
	console.log("after5sec 전체 끝");
}

const after3sec = async ()=>{
	
    const resultArr = await Promise.all([promise1("첫번째 결과물"), promise2("두번째 결과물")]);
    
    console.log("after3sec 끝..."+resultArr.toString());
}

async function errPromise(){
    //throw new Error("error");
    return "끝";
}

async function handleErrPromise(){
    try{
        let results = await errPromise();

        console.log(results);
    }catch(err){
        console.log(err);
    }
}

after5sec();
after3sec();