$(function(){
     var newpage = 0;
    //  var num = $('.skippage .page');
    //  console.log(num)
     $('.skippage').swipe({
         swipe:function(event, direction, distance, duration, fingerCount) {
             if(direction == 'up'){
                 newpage = newpage + 1;
             }else if(direction == 'down'){
                newpage = newpage - 1;
             }
             if(newpage > 19) {
                 newpage = 19;
             }
             if( newpage < 0){
                 newpage = 0;
             }
             $('.skippage').animate({"top":newpage* -100 + "%"})
         }
     })
    
     //swiper 
     var swiper = new Swiper('.swiper-container', {
        spaceBetween: 30,
        pagination: {
            el: '.swiper-pagination',
            clickable: true,
        },
    });

    // 返回上一页
    $('.left').click(function(){
        history.go(-1)
    })
})