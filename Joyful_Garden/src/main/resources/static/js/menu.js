var imgEle = document.querySelector("#click");
var menu = document.querySelector("#menu");
var menuWidth = menu.offsetWidth;
var menuMask = document.querySelector("#menuMask");
var topImage = document.querySelector(".topImage");
var hidden = document.querySelector(".hidden");

topImage.style.zIndex = '6000';
hidden.style.opacity ='0';

menuMask.addEventListener('click',()=>{
	var currentSrc = imgEle.src;
    var newSrc = (currentSrc.includes('menu')) ? '/image/cross.png' : '/image/menu.png';
    imgEle.src = newSrc;
	menu.style.left = (menu.style.left === '110px' ) ? `-${menuWidth}px` : '110px';
	menuMask.classList.remove('open');
	topImage.style.zIndex = (topImage.style.zIndex ==='6000')? '10':'6000';
	hidden.style.opacity = (hidden.style.opacity ==='0')? '1':'0';
})

imgEle.addEventListener('click',()=>{
    var currentSrc = imgEle.src;
    var newSrc = (currentSrc.includes('menu')) ? '/image/cross.png' : '/image/menu.png';
    imgEle.src = newSrc;
	var scrollPosition = window.scrollY;

    if(menuMask.classList.contains('open')){
        menuMask.classList.remove('open');
		menu.style.left = `-${menuWidth}px`;
		topImage.style.zIndex = '6000';
		if(scrollPosition<750)
		hidden.style.opacity = '0';
		else
		hidden.style.opacity = '1';
	}
    else{
        menuMask.classList.add('open');
        menu.style.left = '110px';
        topImage.style.zIndex = '10';
        hidden.style.opacity = '1';
        }
     
});


document.addEventListener('DOMContentLoaded', function() {
  var triggerPosition = 750; 

  window.addEventListener('scroll', function() {
	var scrollPosition = window.scrollY;
    if (scrollPosition < triggerPosition) {
		if(menuMask.classList.contains('open')){
			hidden.style.opacity = '1';
		}else{
			hidden.style.opacity = '0';
		}
    } else {
      hidden.style.opacity = '1';
    }
  });
});


document.querySelector('#ul1').addEventListener('mouseover',function(){
	var li = document.querySelector('.li')
	li.style.display = 'block';
});

document.querySelector('#menuOp').addEventListener('mouseleave',function(){
	var li = document.querySelector('.li')
	li.style.display = 'none';
});

