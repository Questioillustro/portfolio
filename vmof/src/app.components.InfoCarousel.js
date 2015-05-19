    var $carousel = $('#myCarousel');
    var $carouselCaptions = $carousel.find('.item .carousel-caption');
    var $carouselImages = $carousel.find('.item img');
    var carouselTimeout;
    
    $carousel.on('slid', function () {
        var $item = $carousel.find('.item.active');
        carouselTimeout = setTimeout(function() { // start the delay
            carouselTimeout = false;
            $('.carousel-caption', $item).animate({'opacity': 1}, 500);
            $('img', $item).animate({'opacity': 0.2}, 500);
        }, 2000);
    }).on('slide', function () {
        if(carouselTimeout) { // Carousel is sliding, stop pending animation if any
            clearTimeout(carouselTimeout);
            carouselTimeout = false;
        }
        // Reset styles
        $carouselCaptions.animate({'opacity': 0}, 500);
        $carouselImages.animate({'opacity': 1}, 500);
    });;
    
    $('#myCarousel').carousel({
        interval: 6000,
        cycle: true,
    }).trigger('slid'); // Make the carousel believe that it has just been slid so that the first item gets the animation