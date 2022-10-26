<%@ page language='java' contentType='text/html; charset=utf-8'
    pageEncoding='utf-8'%>
<!DOCTYPE html>
<html>
<head>
    <title></title>
    <script>
    	
   		const websocket = new WebSocket('ws://localhost:8080/ws/character');
		let character = '${loginInfo.character}';
       	let loadedCharacter = [character];
       	let imageMap = new Object();
        let infoMap = new Object();
                
		let canvas;
        let context;

        let dx=0;
        let dy=0;
 
        let keycode;
 
       	let characterState = 'front';
        let x=200, y=250; 
        const w=40, h=40; 
        
        let imgBg= new Image();
        imgBg.src='/resources/img/background/bg.png';
        let start = new Image();
        start.src = '/resources/img/background/start.png';
        
        function runGame(){
            moveAll(); 
            send();
        }

        function moveAll(){
            x+=dx;
            y+=dy;
        }
        
		function keydown(){
            keycode=event.keyCode;
          
           	if(keycode == 37){
               	dx=-40;
               	if(characterState == 'left_run'){
               		runGame();
               		characterState = 'left';
               	}else if(characterState == 'left'){
               		runGame();
               		characterState = 'left_run';
               	}else{
               		characterState = 'left';
               		runGame();
               		characterState = 'left_run';
               	}
               }else if(keycode == 38){
               	dy=-40;
               	if(characterState == 'back_run'){
               		runGame();
               		characterState = 'back';
               	}else if(characterState == 'back'){
               		runGame();
               		characterState = 'back_run';
               	}else{
               		characterState = 'back';
               		runGame();
               		characterState = 'back_run';
               	}
               }else if(keycode == 39){
               	dx=40;
               	if(characterState == 'right_run'){
               		runGame();
               		characterState = 'right';
               	}else if(characterState == 'right'){
               		runGame();
               		characterState = 'right_run';
               	}else{
               		characterState = 'right';
               		runGame();
               		characterState = 'right_run'
               	}
               }else if(keycode == 40){
               	dy=40;
               	if(characterState == 'front_run'){
               		runGame();
               		characterState = 'front';
               	}else if(characterState == 'front'){
               		runGame();
               		characterState = 'front_run';
               	}else{
               		characterState = 'front';
               		runGame();
               		characterState = 'front_run'
               	}
               }else{
            	   return false;
               }
        }
        
        function keyup(){
            keycode=event.keyCode;
            
            if(keycode == 37){
            	dx=0;
            }else if(keycode == 38){
            	dy=0;
            }else if(keycode == 39){
            	dx=0;
            }else if(keycode == 40){
            	dy=0;
            }else{
         	   return false;
            }
            
            if(characterState.indexOf('front') != -1){
            	characterState = 'front';
            	runGame();
            }else if(characterState.indexOf('back') != -1){
            	characterState = 'back';
            	runGame();
            }else if(characterState.indexOf('left') != -1){
            	characterState = 'left';
            	runGame();
            }else if(characterState.indexOf('right') != -1){
            	characterState = 'right';
            	runGame();
            }
		}

        function send(){
            websocket.send(x + '&' + y + '&' + characterState + '&' + character);
        }
      	
        
        websocket.onmessage = (event) => {
        	if(keycode == 37 || keycode == 38 || keycode == 39 || keycode == 40){
        		let info = (event.data).split('&');
                let characterState_ = info[2];
                let character_ = info[3];
                if(loadedCharacter.indexOf(character_) == -1){
                	loadedCharacter.push(character_)
                	let front= new Image(); 
                    front.src='/resources/img/character/' + character_ + '/front.png';
                    
                    let front_run= new Image();	
                    front_run.src='/resources/img/character/' + character_ + '/front_run.png';
                    
                    let back= new Image();
                    back.src='/resources/img/character/' + character_ + '/back.png';
                    
                    let back_run= new Image();
                    back_run.src='/resources/img/character/' + character_ + '/back_run.png';
                    
                    let left= new Image();
                    left.src='/resources/img/character/' + character_ + '/left.png';
                    
                    let left_run= new Image();
                    left_run.src='/resources/img/character/' + character_ + '/left_run.png';
                    
                    let right= new Image();
                    right.src='/resources/img/character/' + character_ + '/right.png';
                    
                    let right_run= new Image();
                    right_run.src='/resources/img/character/' + character_ + '/right_run.png';
                    
                    let imgArr = [front, front_run, back, back_run, left, left_run, right, right_run];
                    imageMap[character_] = imgArr;
                   	send()
                }
                infoMap[character_] = info;
                
                context.drawImage(imgBg,0,0,1200,600);

    			for(key in infoMap){
    				if((infoMap[key])[2].indexOf('front') != -1){
    	            	if((infoMap[key])[2].indexOf('run') != -1){
    	            		context.drawImage((imageMap[key])[1], (infoMap[key])[0] - w , (infoMap[key])[1] - h , w * 2 , h * 2 );
    	            	}else{
    	            		context.drawImage((imageMap[key])[0], (infoMap[key])[0] - w , (infoMap[key])[1] - h , w * 2 , h * 2 );
    	            	}
    	            }else if((infoMap[key])[2].indexOf('back') != -1){
    					if((infoMap[key])[2].indexOf('run') != -1){
    	            		context.drawImage((imageMap[key])[3], (infoMap[key])[0] - w , (infoMap[key])[1] - h , w * 2 , h * 2 );
    	            	}else{
    	            		context.drawImage((imageMap[key])[2], (infoMap[key])[0] - w , (infoMap[key])[1] - h , w * 2 , h * 2 );
    	            	}
    	            }else if((infoMap[key])[2].indexOf('left') != -1){
    					if((infoMap[key])[2].indexOf('run') != -1){
    	            		context.drawImage((imageMap[key])[5], (infoMap[key])[0] - w , (infoMap[key])[1] - h , w * 2 , h * 2 );
    	            	}else{
    	            		context.drawImage((imageMap[key])[4], (infoMap[key])[0] - w , (infoMap[key])[1] - h , w * 2 , h * 2 );
    	            	}
    	            }else if((infoMap[key])[2].indexOf('right') != -1){
    					if((infoMap[key])[2].indexOf('run') != -1){
    	            		context.drawImage((imageMap[key])[7], (infoMap[key])[0] - w , (infoMap[key])[1] - h , w * 2 , h * 2 );
    	            	}else{
    	            		context.drawImage((imageMap[key])[6], (infoMap[key])[0] - w , (infoMap[key])[1] - h , w * 2 , h * 2 );
    	            	}
    	            }				
    			}
  		
        	}
 
        };
        websocket.onclose = () => {
            console.log('Web Socket Connection Closed');
        };
        websocket.onopen = () => { 
        	canvas = document.getElementById('c1');
            context = canvas.getContext('2d');
        }
       
        function loaded(){
            
        	let front= new Image(); 
            front.src='/resources/img/character/' + character + '/front.png';
            
            let front_run= new Image();
            front_run.src='/resources/img/character/' + character + '/front_run.png';
            
            let back= new Image();
            back.src='/resources/img/character/' + character + '/back.png';
            
            let back_run= new Image();
            back_run.src='/resources/img/character/' + character + '/back_run.png';
            
            let left= new Image();
            left.src='/resources/img/character/' + character + '/left.png';
            
            let left_run= new Image();
            left_run.src='/resources/img/character/' + character + '/left_run.png';
            
            let right= new Image();
            right.src='/resources/img/character/' + character + '/right.png';
            
            let right_run= new Image();
            right_run.src='/resources/img/character/' + character + '/right_run.png';
      
            let imgArr = [front, front_run, back, back_run, left, left_run, right, right_run];
            imageMap[character] = imgArr;
            infoMap[character] = [x, y, characterState, character];
         
            context.drawImage(start,0,0,1200,600);
  
        }
    </script>
</head>

<body onload="loaded()" onkeydown="keydown()" onkeyup="keyup()">
	<div style="canvas-container:width:100%; text-align:center;">
    	<canvas width="1200" height="600" style="border: 2px solid black;" id="c1"></canvas>
    </div>
</body>
</html>
 